package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.io.Serializable;

public class Episode implements Serializable {
    private boolean free;
    private String id;
    private String name;
    private int order;
    private long timestamp;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isFree() {
        return this.free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
