package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Poster implements Serializable {
    public static final Creator<Poster> CREATOR = new C05841();
    private String original;
    @SerializedName("160")
    private String s160;
    @SerializedName("320")
    private String s320;
    @SerializedName("480")
    private String s480;
    @SerializedName("640")
    private String s640;
    @SerializedName("960")
    private String s960;

    public Poster() {
    }

    static class C05841 implements Creator<Poster> {
        C05841() {
        }

        public Poster createFromParcel(Parcel source) {
            return new Poster(source);
        }

        public Poster[] newArray(int size) {
            return new Poster[size];
        }
    }

    public String getS960() {
        return this.s960;
    }

    public void setS960(String s960) {
        this.s960 = s960;
    }

    public String getOriginal() {
        return this.original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getS160() {
        return this.s160;
    }

    public void setS160(String s160) {
        this.s160 = s160;
    }

    public String getS320() {
        return this.s320;
    }

    public void setS320(String s320) {
        this.s320 = s320;
    }

    public String getS480() {
        return this.s480;
    }

    public void setS480(String s480) {
        this.s480 = s480;
    }

    public String getS640() {
        return this.s640;
    }

    public void setS640(String s640) {
        this.s640 = s640;
    }

    public String getLink() {
        return this.s640.length() == 0 ? this.original : this.s640;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.original);
        dest.writeString(this.s160);
        dest.writeString(this.s320);
        dest.writeString(this.s480);
        dest.writeString(this.s640);
        dest.writeString(this.s960);
    }

    private Poster(Parcel in) {
        this.original = in.readString();
        this.s160 = in.readString();
        this.s320 = in.readString();
        this.s480 = in.readString();
        this.s640 = in.readString();
        this.s960 = in.readString();
    }
}
