package com.garcia.felipe.redditapp.HomeList.Repository;

import com.garcia.felipe.redditapp.Models.MultimediaItem;

import java.util.ArrayList;

interface MainRepository {

    void getMultimediaByRanking(String multimediaType, String rankingType, int i);

    ArrayList<MultimediaItem> getMultimediaFromLS(String rankingType);

    void saveDataToLS(ArrayList<MultimediaItem> arrayList, String rankingType);

    void getMultimediaItemFromServer(MultimediaItem item);

    void deleteMultimediaFromLS(String rankingType);
}
