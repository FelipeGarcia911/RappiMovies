package com.garcia.felipe.redditapp.HomeList.Interactor;

import com.garcia.felipe.redditapp.Details.Events.GetDetailsEvent;
import com.garcia.felipe.redditapp.Helpers.EventBus.GreenRobotEventBus;
import com.garcia.felipe.redditapp.HomeList.Events.DataEvent;
import com.garcia.felipe.redditapp.HomeList.Events.ListEvent;
import com.garcia.felipe.redditapp.HomeList.Repository.MainRepositoryImp;
import com.garcia.felipe.redditapp.Models.MultimediaItem;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class HomeListInteractorImp implements HomeListInteractor {

    private final GreenRobotEventBus eventBus;
    private final MainRepositoryImp repository;
    private static final int INITIAL_PAGE = 1;
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
                    currentArrayList = new ArrayList<>();
                    repository.deleteMultimediaFromLS(event.getRankingType());
                }
                currentArrayList.addAll(event.getList());
                repository.saveDataToLS(currentArrayList, event.getRankingType());
                onSuccessGetList(event.getList(), currentPage);
                return;
            case DataEvent.ON_FAILURE:
                onFailureGetList(event.getMsgError());
                return;
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
        ArrayList<MultimediaItem> arrayList = repository.getMultimediaFromLS(rankingType);
        if (arrayList.size() > 0) {
            currentArrayList = arrayList;
            onSuccessGetList(arrayList, currentPage);
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

    private void onSuccessGetList(ArrayList<MultimediaItem> items, int currentPage) {
        ListEvent event = new ListEvent();
        event.setEventType(DataEvent.ON_SUCCESS);
        event.setList(items);
        event.setPage(currentPage);
        eventBus.post(event);
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
