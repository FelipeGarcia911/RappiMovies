package com.garcia.felipe.rappimovies.HomeList.Interactor;

import com.garcia.felipe.rappimovies.Details.Events.GetDetailsEvent;
import com.garcia.felipe.rappimovies.Helpers.EventBus.GreenRobotEventBus;
import com.garcia.felipe.rappimovies.HomeList.Events.DataEvent;
import com.garcia.felipe.rappimovies.HomeList.Events.ListEvent;
import com.garcia.felipe.rappimovies.HomeList.Repository.MainRepositoryImp;
import com.garcia.felipe.rappimovies.Models.MultimediaItem;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class HomeListInteractorImp implements HomeListInteractor {

    private static final int INITIAL_PAGE = 1;
    private final GreenRobotEventBus eventBus;
    private final MainRepositoryImp repository;
    private ArrayList<MultimediaItem> currentArrayList;
    private int currentPage = INITIAL_PAGE;

    public HomeListInteractorImp() {
        this.currentArrayList = new ArrayList<>();
        this.repository = new MainRepositoryImp();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    public static int getInitialPage() {
        return INITIAL_PAGE;
    }

    @Override
    public void refreshList(String multimediaType, String rankingType) {
        repository.getMultimediaByRanking(multimediaType, rankingType, INITIAL_PAGE);
    }

    @Override
    public void getNextPage(String multimediaType, String rankingType) {
        repository.getMultimediaByRanking(multimediaType, rankingType, currentPage + 1);
    }

    @Subscribe
    public void onGetDataFromServerEvent(DataEvent event) {
        switch (event.getEventType()) {
            case DataEvent.ON_SUCCESS:
                currentPage = event.getPageRequested();
                if (currentPage == INITIAL_PAGE) {
                    onRefreshListSuccess(event);
                } else {
                    onNextPageSuccess(event);
                }
                break;
            case DataEvent.ON_FAILURE:
                onFailureGetList(event.getMsgError());
                break;
            default:
                onFailureGetList("Unknown error, please try again.");
        }
    }

    private void onFailureGetList(String message) {
        ListEvent event = new ListEvent();
        event.setEventType(DataEvent.ON_FAILURE);
        event.setMsgError(message);
        eventBus.post(event);
    }

    @Override
    public void onStartListView(String rankingType) {
        currentArrayList = repository.getMultimediaFromLS(rankingType);
        if (currentArrayList.size() > 0) {
            currentPage = Math.abs(currentArrayList.size() / 20);
            sendListEvent(currentArrayList, ListEvent.ON_REFRESH);
        } else {
            onFailureGetList("Empty local data, please pull to download the new posts.");
        }
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    private void sendListEvent(ArrayList<MultimediaItem> items, int listType) {
        ListEvent event = new ListEvent();
        event.setEventType(DataEvent.ON_SUCCESS);
        event.setListType(listType);
        event.setList(items);
        eventBus.post(event);
    }

    private void onRefreshListSuccess(DataEvent event) {
        repository.deleteMultimediaFromLS(event.getRankingType());
        currentArrayList = event.getList();
        repository.saveDataToLS(currentArrayList, event.getRankingType());
        sendListEvent(currentArrayList, ListEvent.ON_REFRESH);
    }

    private void onNextPageSuccess(DataEvent event) {
        currentArrayList.addAll(event.getList());
        repository.saveDataToLS(currentArrayList, event.getRankingType());
        sendListEvent(event.getList(), ListEvent.ON_NEXT_PAGE);
    }

    @Override
    public void onItemClick(MultimediaItem item) {
        if (item.getDetails() != null) {
            GetDetailsEvent event = new GetDetailsEvent();
            event.setEventType(DataEvent.ON_SUCCESS);
            event.setItem(item);
            eventBus.post(event);
        } else {
            repository.getMultimediaItemFromServer(item);
        }
    }

}
