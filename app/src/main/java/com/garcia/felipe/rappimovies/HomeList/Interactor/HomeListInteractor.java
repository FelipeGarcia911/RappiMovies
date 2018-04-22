package com.garcia.felipe.rappimovies.HomeList.Interactor;

import com.garcia.felipe.rappimovies.Models.MultimediaItem;

interface HomeListInteractor {

    void refreshList(String multimediaType, String rankingType);

    void getNextPage(String multimediaType, String rankingType);

    void onStartListView(String rankingType);

    void onCreate();

    void onDestroy();

    void onItemClick(MultimediaItem item);
}
