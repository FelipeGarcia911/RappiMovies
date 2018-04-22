package com.garcia.felipe.redditapp.Details.Presenter;

import com.garcia.felipe.redditapp.Models.MultimediaItem;

public interface DetailPresenter {
    void onCreate(MultimediaItem item);
    void onDestroy();
}
