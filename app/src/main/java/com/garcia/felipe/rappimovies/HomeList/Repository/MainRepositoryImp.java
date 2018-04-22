package com.garcia.felipe.rappimovies.HomeList.Repository;

import android.util.Log;

import com.garcia.felipe.rappimovies.Details.Events.GetDetailsEvent;
import com.garcia.felipe.rappimovies.Helpers.AsyncHttpClientHelper;
import com.garcia.felipe.rappimovies.Helpers.Constants;
import com.garcia.felipe.rappimovies.Helpers.EventBus.GreenRobotEventBus;
import com.garcia.felipe.rappimovies.Helpers.HttpConnectionHelper;
import com.garcia.felipe.rappimovies.Helpers.LocalStorage.ListLocalStorageHelper;
import com.garcia.felipe.rappimovies.HomeList.Events.DataEvent;
import com.garcia.felipe.rappimovies.Models.MultimediaItem;
import com.garcia.felipe.rappimovies.Models.MultimediaItemDetails;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainRepositoryImp implements MainRepository {

    private final GreenRobotEventBus eventBus;
    private final AsyncHttpClient asyncHttpClient;
    private final HttpConnectionHelper connectionHelper;


    public MainRepositoryImp() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.connectionHelper = new HttpConnectionHelper();
        this.asyncHttpClient = new AsyncHttpClientHelper().getAsyncHttpClient();
    }

    @Override
    public void getMultimediaByRanking(final String multimediaType, final String rankingType, final int page) {
        String urlConnection = Constants.URL_SERVER + Constants.API_VERSION + "/" + multimediaType + "/" + rankingType;
        RequestParams requestParams = new RequestParams();
        requestParams.put("api_key", Constants.API_KEY_V3);
        requestParams.put("page", page);
        asyncHttpClient.get(urlConnection, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject = connectionHelper.stringBuilderJSONObject(responseBody);
                handleOnGetDataFromServerSuccess(jsonObject, multimediaType, rankingType, page);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("HTTP-Error", error.toString());
                onGetDatFailure("Error connecting to the server, please try again later.");
            }

            private void handleOnGetDataFromServerSuccess(JSONObject jsonObject, String multimediaType, String rankingType, int page) {
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    ArrayList<MultimediaItem> multimediaItems = getMultimediaFromJSONArray(results);

                    onGetDatSuccess(multimediaItems, multimediaType, rankingType, page);

                } catch (JSONException e) {
                    Log.e("JSON-Error", e.toString());
                    onGetDatFailure("Error parsing JSON data, please try again.");
                }
            }

            private ArrayList<MultimediaItem> getMultimediaFromJSONArray(JSONArray jsonArray) throws JSONException {
                ArrayList<MultimediaItem> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    arrayList.add(createItemFromJSONObject(jsonObject));
                }
                return arrayList;
            }

            private MultimediaItem createItemFromJSONObject(JSONObject item) throws JSONException {
                String voteCount = item.getString("vote_count");
                long id = item.getLong("id");
                String voteAverage = item.getString("vote_average");
                String title = item.getString("title");
                String popularity = item.getString("popularity");
                String posterPath = item.getString("poster_path");

                String originaLanguage = item.getString("original_language");
                String originalTitle = item.getString("original_title");
                String overview = item.getString("overview");

                MultimediaItem multimediaItem = new MultimediaItem(id, title);
                multimediaItem.setVoteCount(voteCount);
                multimediaItem.setOverview(overview);
                multimediaItem.setVoteAverage(voteAverage);
                multimediaItem.setPopularity(popularity);
                multimediaItem.setPosterPath(posterPath);
                multimediaItem.setMultimediaType(multimediaType);
                multimediaItem.setRankingType(rankingType);

                return multimediaItem;
            }

            private void onGetDatSuccess(ArrayList<MultimediaItem> multimediaItems, String multimediaType, String rankingType, int page) {
                DataEvent dataEvent = new DataEvent();
                dataEvent.setEventType(DataEvent.ON_SUCCESS);
                dataEvent.setList(multimediaItems);
                dataEvent.setMultimediaType(multimediaType);
                dataEvent.setPageRequest(page);
                dataEvent.setRankingType(rankingType);
                eventBus.post(dataEvent);
            }

            private void onGetDatFailure(String message) {
                DataEvent dataEvent = new DataEvent();
                dataEvent.setEventType(DataEvent.ON_FAILURE);
                dataEvent.setMsgError(message);
                eventBus.post(dataEvent);
            }
        });
    }

    @Override
    public ArrayList<MultimediaItem> getMultimediaFromLS(String rankingType) {
        ListLocalStorageHelper storageHelper = ListLocalStorageHelper.getInstance();
        return storageHelper.getList(rankingType);
    }

    @Override
    public void saveDataToLS(ArrayList<MultimediaItem> arrayList, String rankingType) {
        ListLocalStorageHelper storageHelper = ListLocalStorageHelper.getInstance();
        storageHelper.saveList(arrayList, rankingType);
    }

    @Override
    public void deleteMultimediaFromLS(String rankingType) {
        ListLocalStorageHelper storageHelper = ListLocalStorageHelper.getInstance();
        storageHelper.deleteList(rankingType);
    }

    @Override
    public void getMultimediaItemFromServer(final MultimediaItem item) {
        String urlConnection = Constants.URL_SERVER + Constants.API_VERSION + "/" + item.getMultimediaType() + "/" + String.valueOf(item.getIdStr());
        RequestParams requestParams = new RequestParams();
        requestParams.put("api_key", Constants.API_KEY_V3);
        asyncHttpClient.get(urlConnection, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject = connectionHelper.stringBuilderJSONObject(responseBody);
                handleOnGetDataFromServerSuccess(jsonObject);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("HTTP-Error", error.toString());
                onGetDatFailure("Error connecting to the server, please try again later.");
            }

            private void handleOnGetDataFromServerSuccess(JSONObject jsonObject) {
                try {
                    MultimediaItemDetails details = createItemFromJSONObject(jsonObject);
                    item.setDetails(details);
                    onGetDatSuccess(item);

                } catch (JSONException e) {
                    Log.e("JSON-Error", e.toString());
                    onGetDatFailure("Error parsing JSON data, please try again.");
                }
            }

            private MultimediaItemDetails createItemFromJSONObject(JSONObject item) throws JSONException {
                String budget = item.getString("budget");
                String releaseDate = item.getString("release_date");
                String revenue = item.getString("revenue");
                String runtime = item.getString("runtime");
                String tagline = item.getString("tagline");
                String homepage = item.getString("homepage");
                JSONArray genresJSON = item.getJSONArray("genres");

                ArrayList<String> genres = getGenres(genresJSON);

                MultimediaItemDetails details = new MultimediaItemDetails();
                details.setBudget(budget);
                details.setReleaseDate(releaseDate);
                details.setRevenue(revenue);
                details.setRuntime(runtime);
                details.setTagline(tagline);
                details.setGenres(genres);
                details.setHompage(homepage);
                return details;
            }

            private ArrayList<String> getGenres(JSONArray jsonArray) throws JSONException {
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    arrayList.add(jsonObject.getString("name"));
                }
                return arrayList;
            }

            private void onGetDatSuccess(MultimediaItem item) {
                GetDetailsEvent event = new GetDetailsEvent();
                event.setEventType(DataEvent.ON_SUCCESS);
                event.setItem(item);
                eventBus.post(event);
            }

            private void onGetDatFailure(String message) {
                GetDetailsEvent event = new GetDetailsEvent();
                event.setEventType(DataEvent.ON_FAILURE);
                event.setMsgError(message);
                eventBus.post(event);
            }
        });
    }

}
