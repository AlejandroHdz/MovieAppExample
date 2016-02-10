package com.example.alejandro.moviebook;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MovieFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    boolean flagIsTablet;

    int[] moviePoster={
            R.drawable.deadpool_movie,R.drawable.deadpool_movie,
            R.drawable.deadpool_movie,R.drawable.deadpool_movie,
            R.drawable.deadpool_movie,R.drawable.deadpool_movie};

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
        }
        // use a layout manager depending on orientation
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        }else if (flagIsTablet){
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        }else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        }
        // create an Object for Adapter
        mAdapter = new RecyclerMovieAdapter(moviePoster);
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
