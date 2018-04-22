package com.garcia.felipe.redditapp.HomeList.Events;

import com.garcia.felipe.redditapp.Models.MultimediaItem;

import java.util.ArrayList;

public class DataEvent {

    public static final int ON_SUCCESS = 1;
    public static final int ON_FAILURE = 0;


    private int eventType;
    private String msgError;
    private String multimediaType;
    private String rankingType;
    private int pageRequest;
    private ArrayList<MultimediaItem> list;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public ArrayList<MultimediaItem> getList() {
        return list;
    }

    public void setList(ArrayList<MultimediaItem> list) {
        this.list = list;
    }

    public String getMultimediaType() {
        return multimediaType;
    }

    public void setMultimediaType(String multimediaType) {
        this.multimediaType = multimediaType;
    }

    public String getRankingType() {
        return rankingType;
    }

    public void setRankingType(String rankingType) {
        this.rankingType = rankingType;
    }

    public int getPageRequested() {
        return pageRequest;
    }

    public void setPageRequest(int pageRequest) {
        this.pageRequest = pageRequest;
    }
}
