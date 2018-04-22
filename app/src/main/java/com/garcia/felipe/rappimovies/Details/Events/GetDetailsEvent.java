package com.garcia.felipe.rappimovies.Details.Events;

import com.garcia.felipe.rappimovies.Models.MultimediaItem;

public class GetDetailsEvent {

    public static final int ON_SUCCESS = 1;
    public static final int ON_FAILURE = 0;

    private int eventType;
    private String msgError;
    private MultimediaItem item;

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

    public MultimediaItem getItem() {
        return item;
    }

    public void setItem(MultimediaItem item) {
        this.item = item;
    }
}
