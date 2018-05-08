package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.example.rapfilm.common.FilmPreferences;
import com.example.rapfilm.common.Language;

public class DownloadTask implements Parcelable {
    public static final Creator<DownloadTask> CREATOR = new C05641();
    private int currentPartSize1;
    private int currentPartSize2;
    private int currentPartSize3;
    private int currentPartSize4;
    private int currentSize = 0;
    private String episodeId;
    private String episodeName;
    private String filmId;
    private boolean isMultiSeason;
    private boolean isSeries;
    private String name;
    private String nameVi;
    private String path;
    private String poster;
    private String season;
    private int state;
    private int token;
    private int totalPartSize1;
    private int totalPartSize2;
    private int totalPartSize3;
    private int totalPartSize4;
    private int totalSize = 0;
    private String url;
    private String year;

    static class C05641 implements Creator<DownloadTask> {
        C05641() {
        }

        public DownloadTask createFromParcel(Parcel source) {
            return new DownloadTask(source);
        }

        public DownloadTask[] newArray(int size) {
            return new DownloadTask[size];
        }
    }

    public int getTotalPartSize1() {
        return this.totalPartSize1;
    }

    public int getTotalPartSize2() {
        return this.totalPartSize2;
    }

    public int getTotalPartSize3() {
        return this.totalPartSize3;
    }

    public int getTotalPartSize4() {
        return this.totalPartSize4;
    }

    public void setTotalPartSize1(int totalPartSize1) {
        this.totalPartSize1 = totalPartSize1;
    }

    public void setTotalPartSize2(int totalPartSize2) {
        this.totalPartSize2 = totalPartSize2;
    }

    public void setTotalPartSize3(int totalPartSize3) {
        this.totalPartSize3 = totalPartSize3;
    }

    public void setTotalPartSize4(int totalPartSize4) {
        this.totalPartSize4 = totalPartSize4;
    }

    public int getCurrentPartSize1() {
        return this.currentPartSize1;
    }

    public int getCurrentPartSize2() {
        return this.currentPartSize2;
    }

    public int getCurrentPartSize3() {
        return this.currentPartSize3;
    }

    public int getCurrentPartSize4() {
        return this.currentPartSize4;
    }

    public void setCurrentPartSize1(int currentPartSize1) {
        this.currentPartSize1 = currentPartSize1;
    }

    public void setCurrentPartSize2(int currentPartSize2) {
        this.currentPartSize2 = currentPartSize2;
    }

    public void setCurrentPartSize3(int currentPartSize3) {
        this.currentPartSize3 = currentPartSize3;
    }

    public void setCurrentPartSize4(int currentPartSize4) {
        this.currentPartSize4 = currentPartSize4;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEpisodeId() {
        return this.episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public String getFilmId() {
        return this.filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameVi() {
        return this.nameVi;
    }

    public void setNameVi(String nameVi) {
        this.nameVi = nameVi;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpisodeName() {
        return this.episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public boolean isSeries() {
        return this.isSeries;
    }

    public void setIsSeries(boolean isSeries) {
        this.isSeries = isSeries;
    }

    public boolean isMultiSeason() {
        return this.isMultiSeason;
    }

    public void setIsMultiSeason(boolean isMultiSeason) {
        this.isMultiSeason = isMultiSeason;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getToken() {
        return this.token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getCurrentSize() {
        return this.currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public String getTitle() {
        return (FilmPreferences.getInstance().getLanguage() != Language.VI || this.nameVi.length() <= 0) ? this.name : this.nameVi;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b = (byte) 1;
        dest.writeString(this.episodeId);
        dest.writeString(this.filmId);
        dest.writeString(this.name);
        dest.writeString(this.nameVi);
        dest.writeString(this.poster);
        dest.writeString(this.season);
        dest.writeString(this.episodeName);
        dest.writeByte(this.isSeries ? (byte) 1 : (byte) 0);
        if (!this.isMultiSeason) {
            b = (byte) 0;
        }
        dest.writeByte(b);
        dest.writeInt(this.state);
        dest.writeInt(this.token);
        dest.writeString(this.year);
        dest.writeInt(this.totalSize);
        dest.writeInt(this.currentSize);
        dest.writeString(this.url);
        dest.writeInt(this.currentPartSize1);
        dest.writeInt(this.currentPartSize2);
        dest.writeInt(this.currentPartSize3);
        dest.writeInt(this.currentPartSize4);
        dest.writeInt(this.totalPartSize1);
        dest.writeInt(this.totalPartSize2);
        dest.writeInt(this.totalPartSize3);
        dest.writeInt(this.totalPartSize4);
        dest.writeString(this.path);
    }

    protected DownloadTask(Parcel in) {
        boolean z = true;
        this.episodeId = in.readString();
        this.filmId = in.readString();
        this.name = in.readString();
        this.nameVi = in.readString();
        this.poster = in.readString();
        this.season = in.readString();
        this.episodeName = in.readString();
        this.isSeries = in.readByte() != (byte) 0;
        if (in.readByte() == (byte) 0) {
            z = false;
        }
        this.isMultiSeason = z;
        this.state = in.readInt();
        this.token = in.readInt();
        this.year = in.readString();
        this.totalSize = in.readInt();
        this.currentSize = in.readInt();
        this.url = in.readString();
        this.currentPartSize1 = in.readInt();
        this.currentPartSize2 = in.readInt();
        this.currentPartSize3 = in.readInt();
        this.currentPartSize4 = in.readInt();
        this.totalPartSize1 = in.readInt();
        this.totalPartSize2 = in.readInt();
        this.totalPartSize3 = in.readInt();
        this.totalPartSize4 = in.readInt();
        this.path = in.readString();
    }
}
