package com.garcia.felipe.redditapp.HomeList.UI.Adapters;

import com.garcia.felipe.redditapp.Models.MultimediaItem;

public interface OnItemClickListener {
    void onClick(MultimediaItem item);

    void onLongClick(MultimediaItem item);
}
