package com.garcia.felipe.redditapp.HomeList.Presenter;

import com.garcia.felipe.redditapp.HomeList.Events.ListEvent;
import com.garcia.felipe.redditapp.Models.MultimediaItem;

import org.greenrobot.eventbus.Subscribe;

interface HomeListPresenter {

    void onCreate();
    void onDestroy();
    void initListView();

    @Subscribe
    void onEventRefreshList(ListEvent event);


    void onItemClick(MultimediaItem object);

    void onSwipeTop();
    void onSwipeBottom();

}
