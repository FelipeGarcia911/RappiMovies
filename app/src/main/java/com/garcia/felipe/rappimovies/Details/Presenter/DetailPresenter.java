package com.garcia.felipe.rappimovies.Details.Presenter;

import com.garcia.felipe.rappimovies.Models.MultimediaItem;

public interface DetailPresenter {
    void onCreate(MultimediaItem item);

    void onDestroy();
}
