package com.garcia.felipe.redditapp.HomeList.Presenter;

import com.garcia.felipe.redditapp.Details.Events.GetDetailsEvent;
import com.garcia.felipe.redditapp.Helpers.EventBus.GreenRobotEventBus;
import com.garcia.felipe.redditapp.HomeList.Events.ListEvent;
import com.garcia.felipe.redditapp.HomeList.Interactor.HomeListInteractorImp;
import com.garcia.felipe.redditapp.HomeList.UI.HomeListView;
import com.garcia.felipe.redditapp.Models.MultimediaItem;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class HomeListPresenterImp implements HomeListPresenter {

    private HomeListView view;
    private final GreenRobotEventBus eventBus;
    private final HomeListInteractorImp interactor;

    public HomeListPresenterImp(HomeListView view) {
        this.view = view;
        this.interactor = new HomeListInteractorImp();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    @Subscribe
    public void onEventRefreshList(ListEvent event) {
        switch (event.getEventType()) {
            case ListEvent.ON_SUCCESS:
                onSuccessRefreshList(event.getList(), event.getPage());
                return;
            case ListEvent.ON_FAILURE:
                onFailureRefreshList(event.getMsgError());
                return;
            default:
                onFailureRefreshList("Unknown error, please try again.");
        }
    }

    @Subscribe
    public void onItemDetail(GetDetailsEvent event) {
        view.hideProgressDialog();
        switch (event.getEventType()) {
            case GetDetailsEvent.ON_FAILURE:
                view.showMessage(event.getMsgError());
        }
    }

    private void onFailureRefreshList(String msgError) {
        if (view != null) {
            view.hideSwipeProgressBar();
            view.showMessage(msgError);
        }
    }

    private void onSuccessRefreshList(ArrayList<MultimediaItem> items, int page) {
        if (view != null) {
            if (items.size() > 0) {
                if (page == HomeListInteractorImp.getInitialPage()) {
                    view.setItemsToListView(items);
                } else {
                    for (int i = 0; i < items.size(); i++) {
                        view.addItemToListView(items.get(i));
                    }
                    moveScrollPosition();
                }
            } else {
                view.showMessage("No more data available.");
            }
            view.hideSwipeProgressBar();
        }
    }

    private void moveScrollPosition() {
        if (view != null) {
            int scrollOffset = view.getVerticalScrollRange() / 2;
            view.moveVerticalScrollPosition(scrollOffset);
        }
    }

    //----------------------------------------------------------------------------------------------


    @Override
    public void onItemClick(MultimediaItem item) {
        if (view != null) {
            view.showProgressDialog("Loading data...");
            interactor.onItemClick(item);
        }
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void onSwipeTop() {
        if (view != null) {
            view.showSwipeProgressBar();
            interactor.refreshList(view.getMultimediaType(), view.getRankingType());
        }
    }

    @Override
    public void onSwipeBottom() {
        if (view != null) {
            view.showSwipeProgressBar();
            interactor.getNextPage(view.getMultimediaType(), view.getRankingType());
        }
    }

    //----------------------------------------------------------------------------------------------


    @Override
    public void initListView() {
        if (view != null) {
            interactor.onStartListView(view.getRankingType());
        }
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate() {
        eventBus.register(this);
        interactor.onCreate();
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
        interactor.onDestroy();
    }

}
