package com.example.popularmovieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PosterItem implements Parcelable {
    /*
        JSON
        original_title
        poster_path
        overview
        vote_average
        release_date
        popularity
     */
    private String originalTitle;
    private String posterPath;
    private String overView;
    private double voteAverage;
    private String releaseDate;
    private double popularity;

    public PosterItem() {
    }

    public PosterItem(String originalTitle, String posterPath, String overView, double voteAverage,
                      String releaseDate, double popularity) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overView = overView;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
    }

    protected PosterItem(Parcel in) {
        originalTitle = in.readString();
        posterPath = in.readString();
        overView = in.readString();
        voteAverage = in.readDouble();
        releaseDate = in.readString();
        popularity = in.readDouble();
    }

    public static final Creator<PosterItem> CREATOR = new Creator<PosterItem>() {
        @Override
        public PosterItem createFromParcel(Parcel in) {
            return new PosterItem(in);
        }

        @Override
        public PosterItem[] newArray(int size) {
            return new PosterItem[size];
        }
    };

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(originalTitle);
        parcel.writeString(posterPath);
        parcel.writeString(overView);
        parcel.writeDouble(voteAverage);
        parcel.writeString(releaseDate);
        parcel.writeDouble(popularity);
    }
}
