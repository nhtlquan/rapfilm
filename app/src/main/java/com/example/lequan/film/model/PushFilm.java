package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.io.Serializable;

public class PushFilm implements Serializable {
    private String description;
    private String id;
    private String name;
    private String name_vi;
    private Poster poster;



    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getName_vi() {
        return this.name_vi;
    }

    public String getDescription() {
        return this.description;
    }

    public Poster getPoster() {
        return this.poster;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName_vi(String name_vi) {
        this.name_vi = name_vi;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }
}
