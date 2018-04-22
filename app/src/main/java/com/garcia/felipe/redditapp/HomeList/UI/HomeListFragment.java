package com.garcia.felipe.redditapp.HomeList.UI;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.garcia.felipe.redditapp.Helpers.Image.GlideImageLoader;
import com.garcia.felipe.redditapp.Helpers.Image.ImageLoader;
import com.garcia.felipe.redditapp.Helpers.SimpleProgressDialogHelper;
import com.garcia.felipe.redditapp.HomeList.Presenter.HomeListPresenterImp;
import com.garcia.felipe.redditapp.HomeList.UI.Adapters.ListViewAdapter;
import com.garcia.felipe.redditapp.HomeList.UI.Adapters.OnItemClickListener;
import com.garcia.felipe.redditapp.Models.MultimediaItem;
import com.garcia.felipe.redditapp.R;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeListFragment extends Fragment implements HomeListView, OnItemClickListener {

    private static final String MULTIMEDIA_TYPE = "MULTIMEDIA_TYPE";
    private static final String RANKING_TYPE = "RANKING_TYPE";
    @BindView(R.id.searchView)
    SearchView searchView;
    // TODO: Rename and change types of parameters
    private String multimediaType;

    private final HomeListPresenterImp presenter;
    @BindView(R.id.swipe_refresh_layout) SwipyRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private String rankingType;
    private Unbinder unbinder;
    private ListViewAdapter listAdapter;
    private ImageLoader imageLoader;
    private SimpleProgressDialogHelper progressDialogHelper;

    public HomeListFragment() {
        this.presenter = new HomeListPresenterImp(this);
        presenter.onCreate();
    }

    public static HomeListFragment newInstance(String multimediaType, String rankingType) {
        HomeListFragment fragment = new HomeListFragment();
        Bundle args = new Bundle();
        args.putString(MULTIMEDIA_TYPE, multimediaType);
        args.putString(RANKING_TYPE, rankingType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_list, container, false);
        if (getArguments() != null) {
            multimediaType = getArguments().getString(MULTIMEDIA_TYPE);
            rankingType = getArguments().getString(RANKING_TYPE);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initializeUI();
    }

    private void initializeUI() {
        initDialogs();
        initSearchView();
        initImageLoader();
        initSwipeRefresh();
        initRecyclerView();
        setFragmentNameInToolbar();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        listAdapter = new ListViewAdapter(new ArrayList<MultimediaItem>(), imageLoader, this);
        recyclerView.setAdapter(listAdapter);

        presenter.initListView();
    }

    private void initImageLoader() {
        imageLoader = new GlideImageLoader(getActivity());
    }

    private void initSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    presenter.onSwipeTop();
                } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
                    presenter.onSwipeBottom();
                }
            }
        });
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary));
    }

    private void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                listAdapter.onCloseSearch();
                return false;
            }
        });
        searchView.setQueryHint("Ej. Felipe Castro");
        searchView.clearFocus();
    }

    private void initDialogs() {
        progressDialogHelper = new SimpleProgressDialogHelper(getActivity());
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void hideSwipeProgressBar() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showSwipeProgressBar() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void showMessage(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressDialog(String message) {
        if (progressDialogHelper != null) {
            progressDialogHelper.setMessage(message);
            progressDialogHelper.showProgressDialog();
        }
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void hideProgressDialog() {
        if (progressDialogHelper != null) {
            progressDialogHelper.hideProgressDialog();
        }
    }

    @Override
    public void setItemsToListView(ArrayList<MultimediaItem> items) {
        listAdapter = new ListViewAdapter(items, imageLoader, this);
        if (recyclerView != null) {
            recyclerView.setAdapter(listAdapter);
        }
    }

    @Override
    public void addItemToListView(MultimediaItem object) {
        listAdapter.add(object);
    }

    //----------------------------------------------------------------------------------------------


    @Override
    public void moveVerticalScrollPosition(int scrollPosition) {
        if (recyclerView != null) {
            recyclerView.smoothScrollBy(0, scrollPosition);
        }
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public int getVerticalScrollRange() {
        if (recyclerView != null) {
            return recyclerView.computeVerticalScrollExtent();
        } else {
            return 0;
        }
    }

    @Override
    public void onClickListener(MultimediaItem object) {
        presenter.onItemClick(object);
    }

    @Override
    public String getMultimediaType() {
        return multimediaType;
    }

    @Override
    public String getRankingType() {
        return rankingType;
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onClick(MultimediaItem item) {
        presenter.onItemClick(item);
    }

    @Override
    public void onLongClick(MultimediaItem item) {
        presenter.onItemClick(item);
    }

    private void setFragmentNameInToolbar() {
        getActivity().setTitle(rankingType.toUpperCase());
    }
}
