package com.garcia.felipe.rappimovies.HomeList.UI;

import com.garcia.felipe.rappimovies.Models.MultimediaItem;

import java.util.ArrayList;

public interface HomeListView {
    void hideSwipeProgressBar();

    void showSwipeProgressBar();

    void showMessage(String string);

    void showProgressDialog(String message);

    void hideProgressDialog();

    void setItemsToListView(ArrayList<MultimediaItem> items);

    void addItemToListView(MultimediaItem object);

    void moveVerticalScrollPosition(int scrollPosition);

    int getVerticalScrollRange();

    void onClickListener(MultimediaItem object);

    String getMultimediaType();

    String getRankingType();
}
