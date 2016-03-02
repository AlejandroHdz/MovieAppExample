package com.example.alejandro.moviebook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private ViewGroup fragmentTablet;
    String []movieTitle, movieDate, movieRate, movieOverview,posterPath, moviePosterUrl;
    String urlIma= "http://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////////////////////////////////////////////////////////////////////////////////////////////GETTING JSONOBJECT
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";
        String url = "http://api.themoviedb.org/3/movie/top_rated?api_key=9745f064cdd1c6e1fa1f9c8b39c9fe48";

        final ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        //Buscando el espacio para MoviesFragment
        if(savedInstanceState == null){

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("JSONOBJECT", response.toString());
                            pDialog.hide();
                            try {
                                JSONArray jsonArrayResults = response.getJSONArray("results");
                                Log.d("THERESULTS--->>",jsonArrayResults.toString());
                                // Inicializamos arreglos
                                movieTitle = new String[jsonArrayResults.length()];
                                movieDate = new String[jsonArrayResults.length()];
                                movieOverview = new String[jsonArrayResults.length()];
                                movieRate = new String[jsonArrayResults.length()];
                                posterPath = new String[jsonArrayResults.length()];
                                moviePosterUrl = new String[jsonArrayResults.length()];

                                for (int i=0;i<jsonArrayResults.length();i++){
                                    JSONObject jsonObjectMovie = jsonArrayResults.getJSONObject(i);
                                    movieTitle[i] = jsonObjectMovie.optString("title");
                                    movieDate[i] = jsonObjectMovie.optString("release_date");
                                    movieOverview[i] = jsonObjectMovie.optString("overview");
                                    movieRate[i] = jsonObjectMovie.optString("vote_average");
                                    posterPath[i] = jsonObjectMovie.optString("poster_path");
                                    moviePosterUrl[i] = urlIma+posterPath[i];

                                    /*Log.d("LENGHT--->>>", Integer.toString(i));
                                    Log.d("TITLE--->>>>>>>", movieTitle[i]);
                                    Log.d("TITLE--->>>>>>>", movieDate[i]);
                                    Log.d("TITLE--->>>>>>>", movieOverview[i]);
                                    Log.d("TITLE--->>>>>>>", movieRate[i]);
                                    Log.d("TITLE--->>>>>>>", moviePosterUrl[i]);*/
                                    if (i==19){
                                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                        MovieFragment moviesFragment = new MovieFragment();
                                        MovieDetailsFragment detailsFragment = new MovieDetailsFragment();

                                        //Log.e("INFOOO-->>>",moviePosterUrl.toString());
                                        Bundle arguments = new Bundle();
                                        arguments.putBoolean("tabletInfo", isTablet());
                                        arguments.putStringArray("moviePosterURL", moviePosterUrl);
                                        arguments.putStringArray("movieTitle",movieTitle);
                                        arguments.putStringArray("movieDate",movieDate);
                                        arguments.putStringArray("movieRate",movieRate);
                                        arguments.putStringArray("movieOverview",movieOverview);
                                        moviesFragment.setArguments(arguments);

                                        fragmentTransaction.add(R.id.moviesContainerFragment, moviesFragment);

                                        if (isTablet()){
                                            Bundle arguments2 = new Bundle();
                                            arguments2.putBoolean("tabletInfo", isTablet());
                                            arguments2.putString("moviePosterURL",moviePosterUrl[0]);
                                            arguments2.putString("movieTitle",movieTitle[0]);
                                            arguments2.putString("movieDate",movieDate[0]);
                                            arguments2.putString("movieRate",movieRate[0]);
                                            arguments2.putString("movieOverview",movieOverview[0]);
                                            detailsFragment.setArguments(arguments2);
                                            fragmentTransaction.add(R.id.moviesDetailsFragment, detailsFragment);
                                        }
                                        fragmentTransaction.commit();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("JSONOBJECTERRORRRRR", "Error: " + error.getMessage());
                    // hide the progress dialog
                    pDialog.hide();

                }
            });
// Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
            ////*******************************************************************************************JSONBOJECT
        }

    }

    public boolean isTablet(){
        if(findViewById(R.id.moviesDetailsFragment) != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_favorite) {

            final RealmConfiguration realmConfig = new RealmConfiguration.Builder(MainActivity.this).build();
            final Realm realm = Realm.getInstance(realmConfig);

            RealmResults<FavoriteMovie> results = realm.where(FavoriteMovie.class).findAll();
            moviePosterUrl = new String[moviePosterUrl.length];
            movieTitle = new String[movieTitle.length];
            movieDate = new String[movieDate.length];
            movieRate = new String[movieRate.length];
            movieOverview = new String[movieOverview.length];
            for(int i=0;i<results.size();i++){
                moviePosterUrl[i] = results.get(i).getUrl();
                movieTitle[i] = results.get(i).getName();
                movieDate[i] = results.get(i).getDate();
                movieRate[i] = results.get(i).getRate();
                movieOverview[i] = results.get(i).getOverview();

                if (i==(results.size()-1)){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    MovieFragment moviesFragment = new MovieFragment();
                    MovieDetailsFragment detailsFragment = new MovieDetailsFragment();
                    //Log.e("INFOOO-->>>",moviePosterUrl.toString());
                    Bundle arguments = new Bundle();
                    arguments.putBoolean("tabletInfo", isTablet());
                    arguments.putStringArray("moviePosterURL", moviePosterUrl);
                    arguments.putStringArray("movieTitle",movieTitle);
                    arguments.putStringArray("movieDate",movieDate);
                    arguments.putStringArray("movieRate",movieRate);
                    arguments.putStringArray("movieOverview",movieOverview);
                    moviesFragment.setArguments(arguments);

                    fragmentTransaction.add(R.id.moviesContainerFragment, moviesFragment);

                    if (isTablet()){
                        Bundle arguments2 = new Bundle();
                        arguments2.putBoolean("tabletInfo", isTablet());
                        arguments2.putString("moviePosterURL",moviePosterUrl[0]);
                        arguments2.putString("movieTitle",movieTitle[0]);
                        arguments2.putString("movieDate",movieDate[0]);
                        arguments2.putString("movieRate",movieRate[0]);
                        arguments2.putString("movieOverview", movieOverview[0]);
                        detailsFragment.setArguments(arguments2);
                        fragmentTransaction.add(R.id.moviesDetailsFragment, detailsFragment);
                    }
                    fragmentTransaction.commit();
                }
            }
            return true;
        }

        if (id == R.id.menu_popular){
            /////////////////////////////////////////////////////////////////////////////////////////////GETTING JSONOBJECT
            // Tag used to cancel the request
            String tag_json_obj = "json_obj_req";
            String url = "http://api.themoviedb.org/3/movie/popular?api_key=9745f064cdd1c6e1fa1f9c8b39c9fe48";

            final ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("JSONOBJECT", response.toString());
                                pDialog.hide();
                                try {
                                    JSONArray jsonArrayResults = response.getJSONArray("results");
                                    Log.d("THERESULTS--->>",jsonArrayResults.toString());
                                    // Inicializamos arreglos
                                    movieTitle = new String[jsonArrayResults.length()];
                                    movieDate = new String[jsonArrayResults.length()];
                                    movieOverview = new String[jsonArrayResults.length()];
                                    movieRate = new String[jsonArrayResults.length()];
                                    posterPath = new String[jsonArrayResults.length()];
                                    moviePosterUrl = new String[jsonArrayResults.length()];

                                    for (int i=0;i<jsonArrayResults.length();i++){
                                        JSONObject jsonObjectMovie = jsonArrayResults.getJSONObject(i);
                                        movieTitle[i] = jsonObjectMovie.optString("title");
                                        movieDate[i] = jsonObjectMovie.optString("release_date");
                                        movieOverview[i] = jsonObjectMovie.optString("overview");
                                        movieRate[i] = jsonObjectMovie.optString("vote_average");
                                        posterPath[i] = jsonObjectMovie.optString("poster_path");
                                        moviePosterUrl[i] = urlIma+posterPath[i];

                                    /*Log.d("LENGHT--->>>", Integer.toString(i));
                                    Log.d("TITLE--->>>>>>>", movieTitle[i]);
                                    Log.d("TITLE--->>>>>>>", movieDate[i]);
                                    Log.d("TITLE--->>>>>>>", movieOverview[i]);
                                    Log.d("TITLE--->>>>>>>", movieRate[i]);
                                    Log.d("TITLE--->>>>>>>", moviePosterUrl[i]);*/
                                        if (i==19){
                                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                            MovieFragment moviesFragment = new MovieFragment();
                                            MovieDetailsFragment detailsFragment = new MovieDetailsFragment();
                                            //Log.e("INFOOO-->>>",moviePosterUrl.toString());
                                            Bundle arguments = new Bundle();
                                            arguments.putBoolean("tabletInfo", isTablet());
                                            arguments.putStringArray("moviePosterURL", moviePosterUrl);
                                            arguments.putStringArray("movieTitle",movieTitle);
                                            arguments.putStringArray("movieDate",movieDate);
                                            arguments.putStringArray("movieRate",movieRate);
                                            arguments.putStringArray("movieOverview",movieOverview);
                                            moviesFragment.setArguments(arguments);

                                            fragmentTransaction.add(R.id.moviesContainerFragment, moviesFragment);

                                            if (isTablet()){
                                                Bundle arguments2 = new Bundle();
                                                arguments2.putBoolean("tabletInfo", isTablet());
                                                arguments2.putString("moviePosterURL",moviePosterUrl[0]);
                                                arguments2.putString("movieTitle",movieTitle[0]);
                                                arguments2.putString("movieDate",movieDate[0]);
                                                arguments2.putString("movieRate",movieRate[0]);
                                                arguments2.putString("movieOverview",movieOverview[0]);
                                                detailsFragment.setArguments(arguments2);
                                                fragmentTransaction.add(R.id.moviesDetailsFragment, detailsFragment);
                                            }
                                            fragmentTransaction.commit();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("JSONOBJECTERRORRRRR", "Error: " + error.getMessage());
                        // hide the progress dialog
                        pDialog.hide();

                    }
                });
// Adding request to request queue
                AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                ////*******************************************************************************************JSONBOJECT
        }
        if(id == R.id.menu_topten) {

            String tag_json_obj = "json_obj_req";
            String url = "http://api.themoviedb.org/3/movie/top_rated?api_key=9745f064cdd1c6e1fa1f9c8b39c9fe48";

            final ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("JSONOBJECT", response.toString());
                            pDialog.hide();
                            try {
                                JSONArray jsonArrayResults = response.getJSONArray("results");
                                Log.d("THERESULTS--->>",jsonArrayResults.toString());
                                // Inicializamos arreglos
                                movieTitle = new String[jsonArrayResults.length()];
                                movieDate = new String[jsonArrayResults.length()];
                                movieOverview = new String[jsonArrayResults.length()];
                                movieRate = new String[jsonArrayResults.length()];
                                posterPath = new String[jsonArrayResults.length()];
                                moviePosterUrl = new String[jsonArrayResults.length()];

                                for (int i=0;i<jsonArrayResults.length();i++){
                                    JSONObject jsonObjectMovie = jsonArrayResults.getJSONObject(i);
                                    movieTitle[i] = jsonObjectMovie.optString("title");
                                    movieDate[i] = jsonObjectMovie.optString("release_date");
                                    movieOverview[i] = jsonObjectMovie.optString("overview");
                                    movieRate[i] = jsonObjectMovie.optString("vote_average");
                                    posterPath[i] = jsonObjectMovie.optString("poster_path");
                                    moviePosterUrl[i] = urlIma+posterPath[i];

                                    /*Log.d("LENGHT--->>>", Integer.toString(i));
                                    Log.d("TITLE--->>>>>>>", movieTitle[i]);
                                    Log.d("TITLE--->>>>>>>", movieDate[i]);
                                    Log.d("TITLE--->>>>>>>", movieOverview[i]);
                                    Log.d("TITLE--->>>>>>>", movieRate[i]);
                                    Log.d("TITLE--->>>>>>>", moviePosterUrl[i]);*/
                                    if (i==19){
                                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                        MovieFragment moviesFragment = new MovieFragment();
                                        MovieDetailsFragment detailsFragment = new MovieDetailsFragment();

                                        //Log.e("INFOOO-->>>",moviePosterUrl.toString());
                                        Bundle arguments = new Bundle();
                                        arguments.putBoolean("tabletInfo", isTablet());
                                        arguments.putStringArray("moviePosterURL", moviePosterUrl);
                                        arguments.putStringArray("movieTitle",movieTitle);
                                        arguments.putStringArray("movieDate",movieDate);
                                        arguments.putStringArray("movieRate",movieRate);
                                        arguments.putStringArray("movieOverview",movieOverview);
                                        moviesFragment.setArguments(arguments);

                                        fragmentTransaction.add(R.id.moviesContainerFragment, moviesFragment);

                                        if (isTablet()){
                                            Bundle arguments2 = new Bundle();
                                            arguments2.putBoolean("tabletInfo", isTablet());
                                            arguments2.putString("moviePosterURL",moviePosterUrl[0]);
                                            arguments2.putString("movieTitle",movieTitle[0]);
                                            arguments2.putString("movieDate",movieDate[0]);
                                            arguments2.putString("movieRate",movieRate[0]);
                                            arguments2.putString("movieOverview",movieOverview[0]);
                                            detailsFragment.setArguments(arguments2);
                                            fragmentTransaction.add(R.id.moviesDetailsFragment, detailsFragment);
                                        }
                                        fragmentTransaction.commit();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("JSONOBJECTERRORRRRR", "Error: " + error.getMessage());
                    // hide the progress dialog
                    pDialog.hide();
                }
            });
// Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

        return super.onOptionsItemSelected(item);
    }

}
