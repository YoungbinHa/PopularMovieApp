package com.example.popularmovieapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovieapp.R;
import com.example.popularmovieapp.model.PosterItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PosterItemAdapter extends RecyclerView.Adapter<PosterItemAdapter.PosterItemViewHolder> {
    private static final String TAG = "PosterItemAdapter";
    private static final String BASEURL = "http://image.tmdb.org/t/p/";
    private static final String PHOTOSZIE = "w185";

    private ArrayList<PosterItem> mPosterItems;

    final private PosterItemClickListener mPosterItemClickListener;

    public interface PosterItemClickListener {
        void onItemClick(int id);
    }

    public PosterItemAdapter(ArrayList<PosterItem> posterItems, PosterItemClickListener mPosterItemClickListener) {
        this.mPosterItems = posterItems;
        this.mPosterItemClickListener = mPosterItemClickListener;
    }

    @NonNull
    @Override
    public PosterItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster, parent, false);
        return new PosterItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterItemViewHolder holder, int position) {
        PosterItem posterItem = mPosterItems.get(position);
        String imageURL = posterItem.getPosterPath();
        Picasso.get().load(BASEURL + PHOTOSZIE + imageURL).fit().error(R.drawable.default_poster).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (mPosterItems == null) {
            return 0;
        } else {
            return mPosterItems.size();

        }
    }

    public void setmPosterItems(ArrayList<PosterItem> mPosterItems) {
        this.mPosterItems = mPosterItems;
        notifyDataSetChanged();
    }

    class PosterItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        public PosterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_poster_imageView);
            imageView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int id = getAdapterPosition();
            mPosterItemClickListener.onItemClick(id);
        }
    }
}
