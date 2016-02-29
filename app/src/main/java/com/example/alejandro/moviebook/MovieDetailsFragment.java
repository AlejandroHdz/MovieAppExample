package com.example.alejandro.moviebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsFragment extends Fragment {

    String moviePosterURL, movieTitle, movieDate, movieRate, movieOverview;
    ImageView imgPoster;
    TextView txtTitle, txtDate, txtRate, txtOverview;

    public MovieDetailsFragment() {
    // Empty Constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        imgPoster = (ImageView) view.findViewById(R.id.moviePoster);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtRate = (TextView) view.findViewById(R.id.txtRate);
        txtOverview = (TextView) view.findViewById(R.id.txtOverView);

        if(getArguments() != null){
            moviePosterURL = getArguments().getString("moviePosterURL");
            movieTitle = getArguments().getString("movieTitle");
            movieDate = getArguments().getString("movieDate");
            movieRate = getArguments().getString("movieRate");
            movieOverview = getArguments().getString("movieOverview");

            Log.e("IMAGEEE--->",movieTitle);

            Picasso.with(view.getContext()).load(moviePosterURL).into(imgPoster);
            txtTitle.setText(movieTitle);
            txtDate.setText(movieDate);
            txtRate.setText(movieRate);
            txtOverview.setText(movieOverview); txtOverview.setMovementMethod(new ScrollingMovementMethod());
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {super.onDetach();}

}
