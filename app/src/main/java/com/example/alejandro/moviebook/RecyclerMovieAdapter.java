package com.example.alejandro.moviebook;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Alejandro on 08/02/2016.
 */
public class RecyclerMovieAdapter extends RecyclerView.Adapter<RecyclerMovieAdapter.ViewHolder>  {

    private static String[] moviePosterData, movieTitle, movieDate, movieRate, movieOverview;
    private static android.support.v4.app.FragmentManager fragmentManager;
    private static Boolean tablet;

    public RecyclerMovieAdapter(String[] myMoviePosterData,
                                android.support.v4.app.FragmentManager fm,
                                Boolean isTablet,
                                String[] title,
                                String[] date,
                                String[] rate,
                                String[] overview){
        moviePosterData=myMoviePosterData;
        fragmentManager = fm;
        tablet = isTablet;
        movieTitle = title;
        movieDate = date;
        movieRate = rate;
        movieOverview = overview;
    }

    @Override
    public RecyclerMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movies_item, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerMovieAdapter.ViewHolder holder, int position) {
        Picasso.with(holder.ctx).load(moviePosterData[position]).into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return moviePosterData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView moviePoster;
        Context ctx;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            moviePoster = (ImageView) itemLayoutView.findViewById(R.id.image);
            ctx = itemLayoutView.getContext();

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    MovieDetailsFragment detailsFragment = new MovieDetailsFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("moviePosterURL",moviePosterData[getAdapterPosition()]);
                    arguments.putString("movieTitle",movieTitle[getAdapterPosition()]);
                    arguments.putString("movieDate",movieDate[getAdapterPosition()]);
                    arguments.putString("movieOverview",movieOverview[getAdapterPosition()]);
                    arguments.putString("movieRate",movieRate[getAdapterPosition()]);

                    detailsFragment.setArguments(arguments);

                    if (tablet){
                        Log.e("TheTabletWORKING","DADADADAD");
                        fragmentTransaction.add(R.id.moviesDetailsFragment,detailsFragment).addToBackStack("");
                    }else{
                        fragmentTransaction.add(R.id.moviesContainerFragment,detailsFragment).addToBackStack("");
                    }
                    fragmentTransaction.commit();
                }
            });
        }

    }
}
