package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.io.Serializable;
import java.util.ArrayList;

public class Collection implements Serializable {
    private boolean ads;
    private ArrayList<Film> contents;
    private String cover;
    private String id;
    private String name;
    private int style;
    private String type;

    public boolean isAds() {
        return ads;
    }

    public void setAds(boolean ads) {
        this.ads = ads;
    }

    public ArrayList<Film> getContents() {
        return contents;
    }

    public void setContents(ArrayList<Film> contents) {
        this.contents = contents;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
