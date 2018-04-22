package com.garcia.felipe.rappimovies.Helpers;

import android.text.TextUtils;

public class StringHelper {

    private boolean isValidString(String string) {
        return !TextUtils.isEmpty(string);
    }

    public boolean containString(String string, String search) {
        return isValidString(string) && isValidString(search) && string.toLowerCase().contains(search.toLowerCase());
    }
}
