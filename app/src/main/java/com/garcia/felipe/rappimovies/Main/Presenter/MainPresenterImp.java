package com.garcia.felipe.rappimovies.Main.Presenter;

import com.garcia.felipe.rappimovies.Details.Events.GetDetailsEvent;
import com.garcia.felipe.rappimovies.Helpers.Constants;
import com.garcia.felipe.rappimovies.Helpers.EventBus.GreenRobotEventBus;
import com.garcia.felipe.rappimovies.Main.UI.MainView;

import org.greenrobot.eventbus.Subscribe;

public class MainPresenterImp implements MainPresenter {

    private final GreenRobotEventBus eventBus;
    private MainView view;

    public MainPresenterImp(MainView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Subscribe
    public void onItemDetail(GetDetailsEvent event) {
        switch (event.getEventType()) {
            case GetDetailsEvent.ON_SUCCESS:
                view.navToDetailsFragment(event.getItem());
        }
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
        if (view != null) view.setInitialFragment();
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void onNavAbout() {
        view.navToAboutFragment();
    }

    @Override
    public void onNavTopMovies() {
        if (view != null) {
            view.navToHomeListViewFragment(Constants.MOVIES, Constants.TOP_RATED);
        }
    }

    @Override
    public void onNavPopularMovies() {
        if (view != null) {
            view.navToHomeListViewFragment(Constants.MOVIES, Constants.POPULAR);
        }
    }

    @Override
    public void onNavUpcomingMovies() {
        if (view != null) {
            view.navToHomeListViewFragment(Constants.MOVIES, Constants.UPCOMING);
        }
    }
}
