package com.example.popularmovieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.popularmovieapp.adapter.PosterItemAdapter;
import com.example.popularmovieapp.model.PosterItem;
import com.example.popularmovieapp.util.NetworkUtils;
import com.example.popularmovieapp.util.PosterItemJsonUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PosterItemAdapter.PosterItemClickListener{
    private RecyclerView mRecyclerView;
    private PosterItemAdapter mPosterItemAdapter;
    private ArrayList<PosterItem> mPosterItems;

    private static final String SORT_BY_POPULARITY = "popular";
    private static final String SORT_BY_TOP_RATED = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.activity_main_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mPosterItems = new ArrayList<>();
        mPosterItemAdapter = new PosterItemAdapter(mPosterItems,this);
        mRecyclerView.setAdapter(mPosterItemAdapter);

        loadPopularMovieData(SORT_BY_POPULARITY);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void loadPopularMovieData(String sortBy) {
        if (isNetworkConnected()) {
            new FetchPopularMovieData().execute(sortBy);
        } else {
            Toast.makeText(this, "Fetching Data Failed: No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("posterItem", mPosterItems.get(id));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sortby, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_sort_by_rate:
                loadPopularMovieData(SORT_BY_TOP_RATED);
                return true;
            case R.id.menu_sort_by_popularity:
                loadPopularMovieData(SORT_BY_POPULARITY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class FetchPopularMovieData extends AsyncTask<String, Void, ArrayList<PosterItem>> {

        private static final String TAG = "FetchPopularMovieData";

        @Override
        protected ArrayList<PosterItem> doInBackground(String... strings) {
            String apiKey = getResources().getString(R.string.mv_db_api);

            URL url = null;
            // Check if there is sort by variable or not
            url = NetworkUtils.buildUrl(apiKey, strings[0]);

            Log.d(TAG, "doInBackground: URL: " + url);

            try {
                String jsonData = NetworkUtils.getResponseFromHttpUrl(url);
                Log.d(TAG, "doInBackground: JSON" + jsonData);
                return PosterItemJsonUtils.parsePosterItemJson(jsonData);

            } catch (Exception e) {
                Log.d(TAG, "doInBackground: JSON ERROR");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<PosterItem> posterItems) {
            if (posterItems != null) {
                mPosterItems = posterItems;
                mPosterItemAdapter.setmPosterItems(posterItems);
            }
            super.onPostExecute(posterItems);
        }
    }

    @Override
    protected void onResume() {
        loadPopularMovieData(SORT_BY_POPULARITY);
        super.onResume();
    }
}
