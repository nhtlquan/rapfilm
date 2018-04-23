package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class RecentOff implements Parcelable {
    public static final Creator<RecentOff> CREATOR = new C05891();
    private String id_episode;
    private String id_film;
    private String id_season;
    private String name;
    private String name_episode;
    private String name_season;
    private long strCurrentDuration;
    private long strTotalDuration;
    private String thumb;
    private String year;

    static class C05891 implements Creator<RecentOff> {
        C05891() {
        }

        public RecentOff createFromParcel(Parcel in) {
            return new RecentOff(in);
        }

        public RecentOff[] newArray(int size) {
            return new RecentOff[size];
        }
    }

    public void setId_film(String id_film) {
        this.id_film = id_film;
    }

    public void setId_episode(String id_episode) {
        this.id_episode = id_episode;
    }

    public void setId_season(String id_season) {
        this.id_season = id_season;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName_episode(String name_episode) {
        this.name_episode = name_episode;
    }

    public void setName_season(String name_season) {
        this.name_season = name_season;
    }

    public void setStrCurrentDuration(long strCurrentDuration) {
        this.strCurrentDuration = strCurrentDuration;
    }

    public void setStrTotalDuration(long strTotalDuration) {
        this.strTotalDuration = strTotalDuration;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId_episode() {
        return this.id_episode;
    }

    public String getId_film() {
        return this.id_film;
    }

    public String getId_season() {
        return this.id_season;
    }

    public String getName() {
        return this.name;
    }

    public String getName_episode() {
        return this.name_episode;
    }

    public String getName_season() {
        return this.name_season;
    }

    public long getStrCurrentDuration() {
        return this.strCurrentDuration;
    }

    public long getStrTotalDuration() {
        return this.strTotalDuration;
    }

    public String getThumb() {
        return this.thumb;
    }

    public String getYear() {
        return this.year;
    }

    protected RecentOff(Parcel in) {
        this.id_film = in.readString();
        this.name = in.readString();
        this.year = in.readString();
        this.name_season = in.readString();
        this.name_episode = in.readString();
        this.strCurrentDuration = in.readLong();
        this.strTotalDuration = in.readLong();
        this.thumb = in.readString();
        this.id_episode = in.readString();
        this.id_season = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_film);
        dest.writeString(this.name);
        dest.writeString(this.year);
        dest.writeString(this.name_season);
        dest.writeString(this.name_episode);
        dest.writeLong(this.strCurrentDuration);
        dest.writeLong(this.strTotalDuration);
        dest.writeString(this.thumb);
        dest.writeString(this.id_episode);
        dest.writeString(this.id_season);
    }

    public int describeContents() {
        return 0;
    }
}
