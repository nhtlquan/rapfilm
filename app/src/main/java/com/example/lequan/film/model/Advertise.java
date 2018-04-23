package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Advertise implements Parcelable {
    public static final Creator<Advertise> CREATOR = new C05561();
    private String id;
    private String image;
    private String link_out;
    private String movieId;
    private String schema;
    private String url;
    private String video;

    static class C05561 implements Creator<Advertise> {
        C05561() {
        }

        public Advertise createFromParcel(Parcel source) {
            return new Advertise(source);
        }

        public Advertise[] newArray(int size) {
            return new Advertise[size];
        }
    }

    public String getSchema() {
        return this.schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getMovieId() {
        return this.movieId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideo() {
        return this.video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLink_out(String link_out) {
        this.link_out = link_out;
    }

    public String getLink_out() {
        return this.link_out;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schema);
        dest.writeString(this.url);
        dest.writeString(this.video);
        dest.writeString(this.image);
        dest.writeString(this.id);
        dest.writeString(this.movieId);
        dest.writeString(this.link_out);
    }

    protected Advertise(Parcel in) {
        this.schema = in.readString();
        this.url = in.readString();
        this.video = in.readString();
        this.image = in.readString();
        this.id = in.readString();
        this.movieId = in.readString();
        int tmpTypeAds = in.readInt();
        this.link_out = in.readString();
    }
}
