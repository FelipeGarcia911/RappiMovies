package com.garcia.felipe.rappimovies.Helpers.EventBus;

public class GreenRobotEventBus implements EventBus {
    private final org.greenrobot.eventbus.EventBus eventBus;

    private GreenRobotEventBus() {
        eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    public static GreenRobotEventBus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    public void post(Object event) {
        eventBus.post(event);
    }

    private static class SingletonHolder {
        private static final GreenRobotEventBus INSTANCE = new GreenRobotEventBus();
    }
}
