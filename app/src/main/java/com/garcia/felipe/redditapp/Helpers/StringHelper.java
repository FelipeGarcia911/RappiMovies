package com.garcia.felipe.redditapp.Helpers;

import android.text.TextUtils;

public class StringHelper {

    public boolean isValidString(String string) {
        return !TextUtils.isEmpty(string);
    }

    public boolean containString(String string, String search) {
        return isValidString(string) && isValidString(search) && string.toLowerCase().contains(search.toLowerCase());
    }
}
