package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;

import com.example.rapfilm.Debug;
import com.example.rapfilm.common.FilmPreferences;
import com.example.rapfilm.common.Language;

import java.io.Serializable;

public class Film implements Serializable {
    private Cover cover;
    private String id;
    private double imdb_rate;
    private String name = "";
    private String name_vi = "";
    private Poster poster;
    private String quality;
    private int total_episode = 0;
    private String type;
    private int view;
    private String year;
    private long curent_history = 0;
    private Episode curent_episode;

    public Episode getCurent_episode() {
        return curent_episode;
    }

    public void setCurent_episode(Episode curent_episode) {
        this.curent_episode = curent_episode;
    }

    public long getCurent_history() {
        return curent_history;
    }

    public void setCurent_history(long curent_history) {
        this.curent_history = curent_history;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getImdb_rate() {
        return imdb_rate;
    }

    public void setImdb_rate(double imdb_rate) {
        this.imdb_rate = imdb_rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_vi() {
        return name_vi;
    }

    public void setName_vi(String name_vi) {
        this.name_vi = name_vi;
    }

    public Poster getPoster() {
        return poster;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getTotal_episode() {
        return total_episode;
    }

    public void setTotal_episode(int total_episode) {
        this.total_episode = total_episode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
