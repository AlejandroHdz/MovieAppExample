package com.example.alejandro.moviebook;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alejandro on 29/02/2016.
 */
public class FavoriteMovie extends RealmObject {

    @PrimaryKey private String name;
    private  String date, rate, overview, url;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getRate() {return rate;}
    public void setRate(String rate) {this.rate = rate;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public String getOverview() {return overview;}
    public void setOverview(String overview) {this.overview = overview;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
}
