package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class TextAds implements Parcelable {
    public static final Creator<TextAds> CREATOR = new C05971();
    private ArrayList<String> ads;
    private String movie;
    private String tip;

    static class C05971 implements Creator<TextAds> {
        C05971() {
        }

        public TextAds createFromParcel(Parcel in) {
            return new TextAds(in);
        }

        public TextAds[] newArray(int size) {
            return new TextAds[size];
        }
    }

    protected TextAds(Parcel in) {
        this.tip = in.readString();
        this.ads = in.createStringArrayList();
        this.movie = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tip);
        dest.writeStringList(this.ads);
        dest.writeString(this.movie);
    }

    public int describeContents() {
        return 0;
    }

    public void setAds(ArrayList<String> ads) {
        this.ads = ads;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public ArrayList<String> getAds() {
        return this.ads;
    }

    public String getMovie() {
        return this.movie;
    }

    public String getTip() {
        return this.tip;
    }
}
