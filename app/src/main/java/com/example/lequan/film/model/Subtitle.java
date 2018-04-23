package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Subtitle implements Parcelable {
    public static final Creator<Subtitle> CREATOR = new C05961();
    private int itag;
    private String quality;
    private String url;

    static class C05961 implements Creator<Subtitle> {
        C05961() {
        }

        public Subtitle createFromParcel(Parcel source) {
            return new Subtitle(source);
        }

        public Subtitle[] newArray(int size) {
            return new Subtitle[size];
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

    protected Subtitle(Parcel in) {
        this.itag = in.readInt();
        this.quality = in.readString();
        this.url = in.readString();
    }
}
