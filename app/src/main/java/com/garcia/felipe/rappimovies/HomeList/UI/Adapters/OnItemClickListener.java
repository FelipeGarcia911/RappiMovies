package com.garcia.felipe.rappimovies.HomeList.UI.Adapters;

import com.garcia.felipe.rappimovies.Models.MultimediaItem;

public interface OnItemClickListener {
    void onClick(MultimediaItem item);

    void onLongClick(MultimediaItem item);
}
