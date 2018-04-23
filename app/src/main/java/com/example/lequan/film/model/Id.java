package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Id implements Parcelable {
    public static final Creator<Id> CREATOR = new C05711();
    private String commentId;
    private PushFilm film;
    private String filmId;

    static class C05711 implements Creator<Id> {
        C05711() {
        }

        public Id createFromParcel(Parcel source) {
            return new Id(source);
        }

        public Id[] newArray(int size) {
            return new Id[size];
        }
    }

    public String getFilmId() {
        return this.filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getCommentId() {
        return this.commentId;
    }

    public void setFilm(PushFilm film) {
        this.film = film;
    }

    public PushFilm getFilm() {
        return this.film;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.filmId);
        dest.writeString(this.commentId);
        dest.writeString(this.filmId);
    }

    protected Id(Parcel in) {
        this.filmId = in.readString();
        this.commentId = in.readString();
        this.film = (PushFilm) in.readParcelable(PushFilm.class.getClassLoader());
    }
}
