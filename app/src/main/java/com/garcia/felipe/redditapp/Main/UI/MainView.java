package com.garcia.felipe.redditapp.Main.UI;

import com.garcia.felipe.redditapp.Models.MultimediaItem;

public interface MainView {

    void navToDetailsFragment(MultimediaItem item);

    void navToHomeListViewFragment(String multimediaType, String rankingType);

    void showMessage(String string);

    void navToAboutFragment();
}
