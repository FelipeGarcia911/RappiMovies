package com.garcia.felipe.redditapp.Helpers.LocalStorage;

import com.garcia.felipe.redditapp.Models.MultimediaItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListLocalStorageHelper {

    private static final String LIST_KEY = "LIST_KEY";

    private Gson gson;
    private SharedPreferencesHelper preferencesHelper;

    public static ListLocalStorageHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void initialize() {
        this.gson = new Gson();
        this.preferencesHelper = SharedPreferencesHelper.getInstance();
    }

    public ArrayList<MultimediaItem> getList(String key) {
        ArrayList<MultimediaItem> list = new ArrayList<>();
        String jsonString = preferencesHelper.read(key);
        if (jsonString == null || jsonString.isEmpty()) {
            return list;
        } else {
            Type collectionType = new TypeToken<List<MultimediaItem>>() {
            }.getType();
            list = gson.fromJson(jsonString, collectionType);
            return list;
        }
    }

    public void saveList(ArrayList<MultimediaItem> items, String key) {
        String gsonString = gson.toJson(items);
        preferencesHelper.write(key, gsonString);
    }

    public void deleteList(String key) {
        preferencesHelper.remove(key);
    }


    private static class SingletonHolder {
        private static final ListLocalStorageHelper INSTANCE = new ListLocalStorageHelper();
    }
}
