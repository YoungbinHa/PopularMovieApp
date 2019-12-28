package com.example.popularmovieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovieapp.model.PosterItem;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String BASEURL = "http://image.tmdb.org/t/p/";
    private static final String PHOTOSZIE = "w185";

    ImageView mPosterImageView;
    TextView mMovieTitleTextView;
    TextView mMovieReleaseTextView;
    TextView mMovieRateTextView;
    TextView mMovieSynopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        PosterItem posterItem = null;

        if (getIntent().hasExtra("posterItem")) {
            posterItem = getIntent().getExtras().getParcelable("posterItem");
        }

        mPosterImageView = findViewById(R.id.movie_detail_poster_imageView);
        mMovieTitleTextView = findViewById(R.id.movie_detail_poster_title);
        mMovieReleaseTextView = findViewById(R.id.movie_detail_release_date);
        mMovieRateTextView = findViewById(R.id.movie_detail_vote_average);
        mMovieSynopsisTextView = findViewById(R.id.movie_detail_plot_synopsis);

        if (null != posterItem) {
            String imageURL = posterItem.getPosterPath();
            Picasso.get().load(BASEURL + PHOTOSZIE + imageURL).fit().error(R.drawable.default_poster).into(mPosterImageView);
            mMovieTitleTextView.setText(posterItem.getOriginalTitle());
            mMovieReleaseTextView.setText(posterItem.getReleaseDate());

            StringBuilder sb = new StringBuilder(""+posterItem.getVoteAverage());
            sb.append("/10");
            mMovieRateTextView.setText(sb.toString());
            mMovieSynopsisTextView.setText(posterItem.getOverView());
        }


    }


}
