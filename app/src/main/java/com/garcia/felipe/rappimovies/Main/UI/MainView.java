package com.garcia.felipe.rappimovies.Main.UI;

import com.garcia.felipe.rappimovies.Models.MultimediaItem;

public interface MainView {

    void setInitialFragment();

    void navToDetailsFragment(MultimediaItem item);

    void navToHomeListViewFragment(String multimediaType, String rankingType);

    void showMessage(String string);

    void navToAboutFragment();

}
