package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LinkPlay implements Parcelable {
    public static final Creator<LinkPlay> CREATOR = new C05721();
    private String itag;
    private String quality;
    private String url;

    static class C05721 implements Creator<LinkPlay> {
        C05721() {
        }

        public LinkPlay createFromParcel(Parcel in) {
            return new LinkPlay(in);
        }

        public LinkPlay[] newArray(int size) {
            return new LinkPlay[size];
        }
    }

    protected LinkPlay(Parcel in) {
        this.itag = in.readString();
        this.quality = in.readString();
        this.url = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itag);
        dest.writeString(this.quality);
        dest.writeString(this.url);
    }

    public int describeContents() {
        return 0;
    }

    public String getItag() {
        return this.itag;
    }

    public void setItag(String itag) {
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
}
