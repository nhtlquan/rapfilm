package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CollectionSimple implements Parcelable {
    private String id;
    private String name;

    public CollectionSimple() {
    }

    protected CollectionSimple(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public CollectionSimple(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CollectionSimple> CREATOR = new Creator<CollectionSimple>() {
        @Override
        public CollectionSimple createFromParcel(Parcel in) {
            return new CollectionSimple(in);
        }

        @Override
        public CollectionSimple[] newArray(int size) {
            return new CollectionSimple[size];
        }
    };

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
}
