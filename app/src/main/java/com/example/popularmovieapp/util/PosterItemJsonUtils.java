package com.example.popularmovieapp.util;

import android.util.Log;

import com.example.popularmovieapp.model.PosterItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class PosterItemJsonUtils {
    private static final String TAG = "PosterItemJsonUtils";
    private static final String UNKNOWN = "Unknown";
    private static final double UNKNOWN_NUMBER = 0.0;

    public static ArrayList<PosterItem> parsePosterItemJson(String json) throws JSONException {
        /*
        original_title
        poster_path
        overview
        vote_average int
        release_date
        popularity int
     */
        JSONObject posterItemJsonObject = new JSONObject(json);
        ArrayList<PosterItem> posterItems = new ArrayList<>();
        Log.d(TAG, "parsePosterItemJson: JSONOBJECT" + posterItemJsonObject.toString());

        final String RESULT = "results";
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";
        final String POPULARITY = "popularity";

        if (posterItemJsonObject.has(RELEASE_DATE)) {
            int errorCode = posterItemJsonObject.getInt(RELEASE_DATE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray results = posterItemJsonObject.getJSONArray(RESULT);
        Log.d(TAG, "parsePosterItemJson: Results" + results.toString());
        for (int i = 0; i < results.length(); i++) {
            JSONObject postItemJson = results.getJSONObject(i);

            PosterItem posterItem = new PosterItem();
            // Get the Json Object representing "original_title" from result and set values in sandwich object
            if (postItemJson.has(ORIGINAL_TITLE)) {
                posterItem.setOriginalTitle(postItemJson.getString(ORIGINAL_TITLE));
            } else {
                posterItem.setOriginalTitle(UNKNOWN);
            }

            // Get the Json Object representing "poster_path" from result and set values in sandwich object
            if (postItemJson.has(POSTER_PATH)) {
                Log.d(TAG, "parsePosterItemJson: getString Poster Path" + postItemJson.getString(POSTER_PATH));
                posterItem.setPosterPath(postItemJson.getString(POSTER_PATH));
            } else {
                posterItem.setPosterPath(UNKNOWN);
            }

            // Get the Json Object representing "overview" from result and set values in sandwich object
            if (postItemJson.has(OVERVIEW)) {
                posterItem.setOverView(postItemJson.getString(OVERVIEW));
            } else {
                posterItem.setOverView(UNKNOWN);
            }

            // Get the Json Object representing "vote_average" from result and set values in sandwich object
            if (postItemJson.has(VOTE_AVERAGE)) {
                posterItem.setVoteAverage(postItemJson.getDouble(VOTE_AVERAGE));
            } else {
                posterItem.setVoteAverage(UNKNOWN_NUMBER);
            }

            // Get the Json Object representing "release_date" from result and set values in sandwich object
            if (postItemJson.has(RELEASE_DATE)) {
                posterItem.setReleaseDate(postItemJson.getString(RELEASE_DATE));
            } else {
                posterItem.setReleaseDate(UNKNOWN);
            }
            // Get the Json Object representing "popularity" from result and set values in sandwich object
            if (postItemJson.has(POPULARITY)) {
                posterItem.setPopularity(postItemJson.getDouble(POPULARITY));
            } else {
                posterItem.setPopularity(UNKNOWN_NUMBER);
            }

            posterItems.add(posterItem);
        }

        return posterItems;
    }

}
