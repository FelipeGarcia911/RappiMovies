package com.garcia.felipe.redditapp.Details.Presenter;

import com.garcia.felipe.redditapp.Details.UI.DetailFragment;
import com.garcia.felipe.redditapp.Models.MultimediaItem;
import com.garcia.felipe.redditapp.Models.MultimediaItemDetails;

public class DetailPresenterImp implements DetailPresenter {


    private final DetailFragment view;

    public DetailPresenterImp(DetailFragment detailFragment) {
        this.view = detailFragment;
    }

    @Override
    public void onCreate(MultimediaItem item) {
        if (item != null){
            setMultimediaDetails(item);
        }
    }

    private void setMultimediaDetails(MultimediaItem item) {
        if (view != null) {
            view.setTitle(item.getTitle());
            view.setImage(item.getBannerImage());
            view.setOverview(item.getOverview());

            MultimediaItemDetails details = item.getDetails();
            if (details != null) {
                view.setReleasedDate(details.getReleaseDate());
                view.setRuntime(details.getRuntime());
                view.setHomepage(details.getHompage());
                view.setGenres(details.getGenres().toString());
                view.setTagline(details.getTagline());
            }
            view.setFragmentNameInToolbar(item.getTitle());
        }
    }

    @Override
    public void onDestroy() {

    }
}
