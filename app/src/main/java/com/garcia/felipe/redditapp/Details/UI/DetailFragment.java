package com.garcia.felipe.redditapp.Details.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.garcia.felipe.redditapp.Details.Presenter.DetailPresenter;
import com.garcia.felipe.redditapp.Details.Presenter.DetailPresenterImp;
import com.garcia.felipe.redditapp.Helpers.Image.GlideImageLoader;
import com.garcia.felipe.redditapp.Models.MultimediaItem;
import com.garcia.felipe.redditapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment implements DetailFragmentUI{

    @BindView(R.id.titleDetails)
    TextView title;
    @BindView(R.id.tagline)
    TextView tagline;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.genres)
    TextView genres;
    @BindView(R.id.homepage)
    TextView homepage;
    @BindView(R.id.runtime)
    TextView runtime;
    @BindView(R.id.release)
    TextView release;
    @BindView(R.id.progressBarDetails)
    ProgressBar progressBarDetails;
    @BindView(R.id.bannerImage) ImageView bannerImage;
    private MultimediaItem dataObject;
    private DetailPresenter detailPresenter;

    public DetailFragment() {
        this.detailPresenter = new DetailPresenterImp(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        detailPresenter.onCreate(dataObject);
    }

    @Override
    public void setDataObject(MultimediaItem item) {
        this.dataObject = item;
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(Html.fromHtml(title));
    }

    @Override
    public void setTagline(String string) {
        tagline.setText(string);
    }

    @Override
    public void setOverview(String description) {
        overview.setText(description);
    }

    @Override
    public void setReleasedDate(String string) {
        release.setText(string);
    }

    @Override
    public void setGenres(String string) {
        genres.setText(string);
    }

    @Override
    public void setRuntime(String string) {
        runtime.setText(string);
    }

    @Override
    public void setHomepage(String string) {
        homepage.setText(string);
    }


    @Override
    public void setImage(String imageURL) {
        GlideImageLoader imageLoader = new GlideImageLoader(getContext());
        imageLoader.load(bannerImage, imageURL, progressBarDetails);
    }

    @Override
    public void setFragmentNameInToolbar(String fragmentName) {
        getActivity().setTitle(fragmentName);
    }

}
