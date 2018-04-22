package com.garcia.felipe.rappimovies.HomeList.Events;

import com.garcia.felipe.rappimovies.Models.MultimediaItem;

import java.util.ArrayList;

public class ListEvent {

    public static final int ON_SUCCESS = 1;
    public static final int ON_FAILURE = 0;

    public static final int ON_REFRESH = 10;
    public static final int ON_NEXT_PAGE = 11;

    private int eventType;
    private int listType;
    private String msgError;
    private ArrayList<MultimediaItem> list;
    private int page;

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

    public int getListType() {
        return listType;
    }

    public void setListType(int listType) {
        this.listType = listType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
