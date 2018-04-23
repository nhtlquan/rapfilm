package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.net.URI;
import java.net.URISyntaxException;

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new C05731();
    private static final String TAG = Movie.class.getSimpleName();
    private String bgImageUrl;
    private String cardImageUrl;
    private String category;
    private String description;
    private long id;
    private String studio;
    private String title;
    private String videoUrl;

    public Movie() {
    }

    static class C05731 implements Creator<Movie> {
        C05731() {
        }

        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return this.studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackgroundImageUrl() {
        return this.bgImageUrl;
    }

    public void setBackgroundImageUrl(String bgImageUrl) {
        this.bgImageUrl = bgImageUrl;
    }

    public String getCardImageUrl() {
        return this.cardImageUrl;
    }

    public void setCardImageUrl(String cardImageUrl) {
        this.cardImageUrl = cardImageUrl;
    }

    public URI getCardImageURI() {
        try {
            return new URI(getCardImageUrl());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toString() {
        return "Movie{id=" + this.id + ", title='" + this.title + '\'' + ", studio='" + this.studio + '\'' + ", description='" + this.description + '\'' + ", cardImageUrl='" + this.cardImageUrl + '\'' + ", videoUrl='" + this.videoUrl + '\'' + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.studio);
        dest.writeString(this.description);
        dest.writeString(this.bgImageUrl);
        dest.writeString(this.cardImageUrl);
        dest.writeString(this.videoUrl);
        dest.writeString(this.category);
    }

    protected Movie(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.studio = in.readString();
        this.description = in.readString();
        this.bgImageUrl = in.readString();
        this.cardImageUrl = in.readString();
        this.videoUrl = in.readString();
        this.category = in.readString();
    }
}
