package com.garcia.felipe.rappimovies.Models;

import android.support.annotation.NonNull;

import com.garcia.felipe.rappimovies.Helpers.Constants;
import com.garcia.felipe.rappimovies.Helpers.StringHelper;

public class MultimediaItem {

    private String voteCount;
    private long id;
    private String voteAverage;
    private String title;
    private String popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private String multimediaType;
    private String rankingType;
    private MultimediaItemDetails details;
    private String bannerImage;

    public MultimediaItem(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdStr() {
        return String.valueOf(this.id);
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        if (voteAverage.isEmpty() || voteAverage.equals("0") || voteAverage.toLowerCase().equals("null")) {
            this.voteAverage = "-";
        } else {
            this.voteAverage = voteAverage;
        }
    }

    public String getVoteAverageStr() {
        return String.valueOf(this.voteAverage);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = Constants.POSTER_IMAGE_URL + posterPath;
        this.bannerImage = Constants.BANNER_IMAGE_URL + posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getMultimediaType() {
        return multimediaType;
    }

    public void setMultimediaType(String multimediaType) {
        this.multimediaType = multimediaType;
    }

    public String getRankingType() {
        return rankingType;
    }

    public void setRankingType(String rankingType) {
        this.rankingType = rankingType;
    }

    public MultimediaItemDetails getDetails() {
        return details;
    }

    public void setDetails(MultimediaItemDetails details) {
        this.details = details;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public boolean valueFound(@NonNull String searchKey) {
        StringHelper stringHelper = new StringHelper();
        if (stringHelper.containString(title, searchKey)) {
            return true;
        }
        if (stringHelper.containString(overview, searchKey)) {
            return true;
        }
        if (details != null) {
            return stringHelper.containString(details.getTagline(), searchKey);
        }
        return false;
    }
}
