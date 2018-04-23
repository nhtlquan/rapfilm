package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Video implements Parcelable {
    public static final Creator<Video> CREATOR = new C06081();
    private int itag;
    private String quality;
    private String url;

    public Video() {
    }

    static class C06081 implements Creator<Video> {
        C06081() {
        }

        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    }

    public int getItag() {
        return this.itag;
    }

    public void setItag(int itag) {
        this.itag = itag;
    }

    public String getQuality() {
        return this.quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itag);
        dest.writeString(this.quality);
        dest.writeString(this.url);
    }

    private Video(Parcel in) {
        this.itag = in.readInt();
        this.quality = in.readString();
        this.url = in.readString();
    }
}
