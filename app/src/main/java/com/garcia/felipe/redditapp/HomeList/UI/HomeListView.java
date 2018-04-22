package com.garcia.felipe.redditapp.HomeList.UI;

import com.garcia.felipe.redditapp.Models.MultimediaItem;

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
