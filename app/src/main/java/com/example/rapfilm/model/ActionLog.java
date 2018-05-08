package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ActionLog implements Parcelable {
    public static final Creator<ActionLog> CREATOR = new C05551();
    private String link;
    private String name;
    private String title;

    public ActionLog() {
    }

    static class C05551 implements Creator<ActionLog> {
        C05551() {
        }

        public ActionLog createFromParcel(Parcel in) {
            return new ActionLog(in);
        }

        public ActionLog[] newArray(int size) {
            return new ActionLog[size];
        }
    }

    protected ActionLog(Parcel in) {
        this.title = in.readString();
        this.link = in.readString();
        this.name = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeString(this.name);
    }

    public int describeContents() {
        return 0;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return this.link;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }
}
