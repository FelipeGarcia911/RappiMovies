package com.garcia.felipe.rappimovies.Helpers.EventBus;

public interface EventBus {
    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

}
