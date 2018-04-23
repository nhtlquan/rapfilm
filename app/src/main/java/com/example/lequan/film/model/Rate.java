package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.io.Serializable;

public class Rate implements Serializable {
    public static final Creator<Rate> CREATOR = new C05871();
    private String id;
    private int mark;
    private long timestamp;
    private int total_rate;

    static class C05871 implements Creator<Rate> {
        C05871() {
        }

        public Rate createFromParcel(Parcel source) {
            return new Rate(source);
        }

        public Rate[] newArray(int size) {
            return new Rate[size];
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getMark() {
        return this.mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getTotal_rate() {
        return this.total_rate;
    }

    public void setTotal_rate(int total_rate) {
        this.total_rate = total_rate;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.timestamp);
        dest.writeInt(this.mark);
        dest.writeInt(this.total_rate);
    }

    private Rate(Parcel in) {
        this.id = in.readString();
        this.timestamp = in.readLong();
        this.mark = in.readInt();
        this.total_rate = in.readInt();
    }
}
