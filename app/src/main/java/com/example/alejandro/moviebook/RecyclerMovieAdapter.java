package com.example.alejandro.moviebook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Alejandro on 08/02/2016.
 */
public class RecyclerMovieAdapter extends RecyclerView.Adapter<RecyclerMovieAdapter.ViewHolder>  {

    private  static int[] moviePoserData;

    public RecyclerMovieAdapter(int[] myMoviePosterData){
        moviePoserData=myMoviePosterData;
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
        holder.moviePoster.setImageResource(moviePoserData[position]);
    }

    @Override
    public int getItemCount() {
        return moviePoserData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView moviePoster;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            moviePoster = (ImageView) itemLayoutView.findViewById(R.id.image);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ALL WORK'S FINE","ALL WORK'S FINE");
                }
            });
        }

    }
}
