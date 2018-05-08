package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Film_MN implements Parcelable {
    public static final Creator<Film_MN> CREATOR = new C05701();
    private String filmId;
    private String name;
    private String poster;

    static class C05701 implements Creator<Film_MN> {
        C05701() {
        }

        public Film_MN createFromParcel(Parcel in) {
            return new Film_MN(in);
        }

        public Film_MN[] newArray(int size) {
            return new Film_MN[size];
        }
    }

    protected Film_MN(Parcel in) {
        this.filmId = in.readString();
        this.name = in.readString();
        this.poster = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.filmId);
        dest.writeString(this.name);
        dest.writeString(this.poster);
    }

    public int describeContents() {
        return 0;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getFilmId() {
        return this.filmId;
    }

    public String getName() {
        return this.name;
    }

    public String getPoster() {
        return this.poster;
    }
}
