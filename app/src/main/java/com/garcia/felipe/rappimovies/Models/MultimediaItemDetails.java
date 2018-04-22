package com.garcia.felipe.rappimovies.Models;

import java.util.ArrayList;

public class MultimediaItemDetails {

    private String budget;
    private String releaseDate;
    private String revenue;
    private String runtime;
    private String status;
    private String tagline;
    private ArrayList<String> genres;
    private String hompage;

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getHompage() {
        return hompage;
    }

    public void setHompage(String hompage) {
        if (hompage.isEmpty() || hompage.toLowerCase().equals("null")) {
            this.hompage = "-";
        } else {
            this.hompage = hompage;
        }
    }
}
