package com.example.alejandro.moviebook;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MovieDetailsFragment extends Fragment {

    String moviePosterURL, movieTitle, movieDate, movieRate, movieOverview;
    ImageView imgPoster;
    TextView txtTitle, txtDate, txtRate, txtOverview;
    LikeButton likeButton;

    public MovieDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        final RealmConfiguration realmConfig = new RealmConfiguration.Builder(view.getContext()).build();
        final Realm realm = Realm.getInstance(realmConfig);

        imgPoster = (ImageView) view.findViewById(R.id.moviePoster);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtRate = (TextView) view.findViewById(R.id.txtRate);
        txtOverview = (TextView) view.findViewById(R.id.txtOverView);
        likeButton = (LikeButton) view.findViewById(R.id.favorite_button);

        if(getArguments() != null){
            moviePosterURL = getArguments().getString("moviePosterURL");
            movieTitle = getArguments().getString("movieTitle");
            movieDate = getArguments().getString("movieDate");
            movieRate = getArguments().getString("movieRate");
            movieOverview = getArguments().getString("movieOverview");

            //FavoriteMovie movie = new FavoriteMovie();
            RealmResults<FavoriteMovie> results = realm.where(FavoriteMovie.class).equalTo("name",movieTitle).findAll();
            if (results.size() == 1){
                likeButton.setLiked(true);
            }else{
                likeButton.setLiked(false);
            }


            Picasso.with(view.getContext()).load(moviePosterURL).into(imgPoster);
            txtTitle.setText(movieTitle);
            txtDate.setText(movieDate);
            txtRate.setText(movieRate);
            txtOverview.setText(movieOverview); txtOverview.setMovementMethod(new ScrollingMovementMethod());

            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                    FavoriteMovie movie = new FavoriteMovie();
                    movie.setName(movieTitle);
                    movie.setDate(movieDate);
                    movie.setUrl(moviePosterURL);
                    movie.setRate(movieRate);
                    movie.setOverview(movieOverview);

                    RealmResults<FavoriteMovie> results = realm.where(FavoriteMovie.class).findAll();
                    realm.beginTransaction();

                    realm.copyToRealmOrUpdate(movie);
                    realm.commitTransaction();

                    likeButton.setLiked(true);
                    //RealmResults<FavoriteMovie> resultsone = realm.where(FavoriteMovie.class).findAll();
                    results.size();
                    Log.e("RESULTS--->",results.toString());
                }

                @Override
                public void unLiked(LikeButton likeButton) {

                    likeButton.setLiked(false);
                    FavoriteMovie movie = new FavoriteMovie();
                    RealmResults<FavoriteMovie> results = realm.where(FavoriteMovie.class).equalTo("name",movieTitle).findAll();
                    realm.beginTransaction();
                    results.clear();
                    realm.commitTransaction();

                }
            });
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
