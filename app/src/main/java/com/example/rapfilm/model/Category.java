package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Category implements Parcelable {
    public static final Creator<Category> CREATOR = new C05581();
    private String id;
    private boolean isCheck;
    private String name;
    private String name_vi;
    private String banner;
    private String thumbnail;

    public String getName_vi() {
        return name_vi;
    }

    public void setName_vi(String name_vi) {
        this.name_vi = name_vi;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    static class C05581 implements Creator<Category> {
        C05581() {
        }

        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category() {
        this.isCheck = false;
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public void setCheck(boolean check) {
        this.isCheck = check;
    }

    public Category(String id, String name, boolean isCheck) {
        this.isCheck = false;
        this.id = id;
        this.name = name;
        this.isCheck = isCheck;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
    }

    private Category(Parcel in) {
        this.isCheck = false;
        this.name = in.readString();
        this.id = in.readString();
    }
}
