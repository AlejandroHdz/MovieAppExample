package com.example.alejandro.moviebook;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MovieFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    boolean flagIsTablet;
    String[] moviePosterURL, movieTitle, movieDate, movieRate, movieOverview;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        if (getArguments() != null) {
            flagIsTablet = getArguments().getBoolean("tabletInfo");
            moviePosterURL = getArguments().getStringArray("moviePosterURL");
            movieTitle = getArguments().getStringArray("movieTitle");
            movieDate = getArguments().getStringArray("movieDate");
            movieRate = getArguments().getStringArray("movieRate");
            movieOverview = getArguments().getStringArray("movieOverview");
        }
        // use a layout manager depending on orientation
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        }else if (flagIsTablet){
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        }else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        }
        // create an Object for Adapter
        android.support.v4.app.FragmentManager fm = getFragmentManager();
        mAdapter = new RecyclerMovieAdapter(moviePosterURL,fm,flagIsTablet,movieTitle,movieDate,movieRate,movieOverview);
        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
