package com.garcia.felipe.rappimovies.HomeList.Presenter;

import com.garcia.felipe.rappimovies.HomeList.Events.ListEvent;
import com.garcia.felipe.rappimovies.Models.MultimediaItem;

import org.greenrobot.eventbus.Subscribe;

interface HomeListPresenter {

    void onCreate();

    void onDestroy();

    void initListView();

    @Subscribe
    void onListEvent(ListEvent event);


    void onItemClick(MultimediaItem object);

    void onSwipeTop();

    void onSwipeBottom();

}
