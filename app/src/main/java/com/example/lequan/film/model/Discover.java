package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Discover implements Parcelable {
    public static final Creator<Discover> CREATOR = new C05631();
    private boolean ads;
    @SerializedName("contents")
    private ArrayList<Collection> contents;
    private String cover;
    private String id;
    private String name;
    private int style;
    private String type;

    static class C05631 implements Creator<Discover> {
        C05631() {
        }

        public Discover createFromParcel(Parcel source) {
            return new Discover(source);
        }

        public Discover[] newArray(int size) {
            return new Discover[size];
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStyle() {
        return this.style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public boolean isAds() {
        return this.ads;
    }

    public void setAds(boolean ads) {
        this.ads = ads;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Collection> getContents() {
        return this.contents;
    }

    public void setContents(ArrayList<Collection> contents) {
        this.contents = contents;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeInt(this.style);
        dest.writeByte(this.ads ? (byte) 1 : (byte) 0);
        dest.writeString(this.cover);
        dest.writeString(this.id);
        dest.writeSerializable(this.contents);
    }

    private Discover(Parcel in) {
        this.name = in.readString();
        this.type = in.readString();
        this.style = in.readInt();
        this.ads = in.readByte() != (byte) 0;
        this.cover = in.readString();
        this.id = in.readString();
        this.contents = (ArrayList) in.readSerializable();
    }
}
