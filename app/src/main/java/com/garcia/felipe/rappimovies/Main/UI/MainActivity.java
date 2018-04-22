package com.garcia.felipe.rappimovies.Main.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.garcia.felipe.rappimovies.About.AboutFragment;
import com.garcia.felipe.rappimovies.Details.UI.DetailFragment;
import com.garcia.felipe.rappimovies.Helpers.Constants;
import com.garcia.felipe.rappimovies.Helpers.LocalStorage.ListLocalStorageHelper;
import com.garcia.felipe.rappimovies.Helpers.LocalStorage.SharedPreferencesHelper;
import com.garcia.felipe.rappimovies.HomeList.UI.HomeListFragment;
import com.garcia.felipe.rappimovies.Main.Presenter.MainPresenter;
import com.garcia.felipe.rappimovies.Main.Presenter.MainPresenterImp;
import com.garcia.felipe.rappimovies.Models.MultimediaItem;
import com.garcia.felipe.rappimovies.R;

public class MainActivity extends AppCompatActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {

    private static int MOVE_DEFAULT_TIME = 200;
    private static int FADE_DEFAULT_TIME = 200;
    private FragmentManager fragmentManager;
    private MainPresenter presenter;
    private Fragment currentFragment;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeSingletons();
        fragmentManager = getSupportFragmentManager();

        presenter = new MainPresenterImp(this);
        presenter.onCreate();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            if (currentFragment instanceof HomeListFragment) super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_top_movies:
                presenter.onNavTopMovies();
                break;
            case R.id.nav_popular_movies:
                presenter.onNavPopularMovies();
                break;
            case R.id.nav_upcoming_movies:
                presenter.onNavUpcomingMovies();
                break;
            case R.id.nav_about:
                presenter.onNavAbout();
                break;
            default:
                presenter.onNavTopMovies();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Slide getExitAnimation() {
        Slide exitFade = new Slide();
        exitFade.setDuration(FADE_DEFAULT_TIME);
        return exitFade;
    }

    private Slide getEnterAnimation() {
        Slide enterFade = new Slide();
        enterFade.setStartDelay(MOVE_DEFAULT_TIME + FADE_DEFAULT_TIME);
        enterFade.setDuration(FADE_DEFAULT_TIME);
        return enterFade;
    }

    private TransitionSet getTransitionSet() {
        TransitionSet enterTransitionSet = new TransitionSet();
        enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
        enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
        enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
        return enterTransitionSet;
    }

    private void animFragmentTransaction(Fragment enterFragment, Fragment exitFragment) {
        exitFragment.setExitTransition(getExitAnimation());
        enterFragment.setSharedElementEnterTransition(getTransitionSet());
        enterFragment.setEnterTransition(getEnterAnimation());
    }

    private void executeFragmentTransaction(Fragment fragment, String fragmentName, boolean removeFromBackStack) {
        animFragmentTransaction(fragment, currentFragment);
        if (removeFromBackStack) fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment, fragmentName)
                .addToBackStack(fragmentName)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void navToDetailsFragment(MultimediaItem item) {
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setDataObject(item);
        currentFragment = detailFragment;
        String fragmentName = String.valueOf("Movie Details");
        if (!isFragmentVisible(fragmentName)) {
            executeFragmentTransaction(currentFragment, fragmentName, false);
        }
    }

    @Override
    public void navToHomeListViewFragment(String multimediaType, String rankingType) {
        currentFragment = HomeListFragment.newInstance(multimediaType, rankingType);
        String fragmentName = String.valueOf(rankingType);
        if (!isFragmentVisible(fragmentName)) {
            executeFragmentTransaction(currentFragment, fragmentName, true);
        }
    }

    @Override
    public void navToAboutFragment() {
        currentFragment = new AboutFragment();
        String fragmentName = String.valueOf("About");
        if (!isFragmentVisible(fragmentName)) {
            executeFragmentTransaction(currentFragment, fragmentName, true);
        }
    }

    private boolean isFragmentVisible(String fragmentName) {
        Fragment myFragment = fragmentManager.findFragmentByTag(fragmentName);
        return (myFragment != null && myFragment.isVisible());
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void initializeSingletons() {
        // Initialize Shared Preferences Helper
        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance();
        sharedPreferencesHelper.initialize(this);

        ListLocalStorageHelper listLocalStorageHelper = ListLocalStorageHelper.getInstance();
        listLocalStorageHelper.initialize();
    }

    @Override
    public void setInitialFragment() {
        navigationView.getMenu().getItem(0).setChecked(true);
        navToHomeListViewFragment(Constants.MOVIES, Constants.TOP_RATED);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
