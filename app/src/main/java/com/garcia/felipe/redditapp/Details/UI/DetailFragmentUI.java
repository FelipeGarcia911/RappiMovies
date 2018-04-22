package com.garcia.felipe.redditapp.Details.UI;

import com.garcia.felipe.redditapp.Models.MultimediaItem;

interface DetailFragmentUI {

    void setDataObject(MultimediaItem redditPost);

    void setTitle(String title);

    void setTagline(String string);

    void setOverview(String description);

    void setImage(String urlImage);

    void setReleasedDate(String string);

    void setGenres(String category);

    void setRuntime(String category);

    void setHomepage(String category);
}
