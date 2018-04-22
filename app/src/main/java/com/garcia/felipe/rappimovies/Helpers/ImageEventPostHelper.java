package com.garcia.felipe.rappimovies.Helpers;

import android.content.Intent;

import com.garcia.felipe.rappimovies.Helpers.EventBus.EventBus;
import com.garcia.felipe.rappimovies.Helpers.EventBus.GreenRobotEventBus;
import com.garcia.felipe.rappimovies.HomeList.Events.ImageEvent;


class ImageEventPostHelper {

    private int requestCode;
    private int resultCode;
    private Intent data;

    public void postImageEvent(int requestCode, int resultCode, Intent data) {
        ImageEvent imageEvent = new ImageEvent();
        imageEvent.setRequestCode(requestCode);
        imageEvent.setResultCode(resultCode);
        imageEvent.setData(data);

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(imageEvent);
    }
}
