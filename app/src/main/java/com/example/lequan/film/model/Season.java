package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Season implements Serializable {
    private List<Episode> contents;
    private String id;
    private String name;
    private int order;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Episode> getContents() {
        return this.contents;
    }

    public void setContents(List<Episode> contents) {
        if (contents != null) {
            for (Episode episode : contents) {
                this.contents.add(episode);
            }
        }
    }

}
