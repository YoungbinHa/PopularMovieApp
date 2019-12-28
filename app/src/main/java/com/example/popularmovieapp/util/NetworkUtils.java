package com.example.popularmovieapp.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.popularmovieapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";

    private static final String API_KEY_PARAM = "api_key";
    private static final String SORT_BY_PARAM = "sort_by";

//    private static final String SORT_BY_POPULARITY = "popularity.desc";
//    private static final String SORT_BY_RATE = "vote_average.desc";


    public static URL buildUrl(String apiKey, String sortBy) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .appendPath(sortBy)
                .build();
        Log.d(TAG, "buildSortByUrl: " + builtUri.toString());

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        Log.d(TAG, "getResponseFromHttpUrl: url connection" + httpURLConnection.toString());
        try {
            InputStream in = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
