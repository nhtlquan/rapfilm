package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class User_MN implements Parcelable {
    public static final Creator<User_MN> CREATOR = new C06071();
    private String avatar;
    private String username;

    static class C06071 implements Creator<User_MN> {
        C06071() {
        }

        public User_MN createFromParcel(Parcel in) {
            return new User_MN(in);
        }

        public User_MN[] newArray(int size) {
            return new User_MN[size];
        }
    }

    protected User_MN(Parcel in) {
        this.username = in.readString();
        this.avatar = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.avatar);
    }

    public int describeContents() {
        return 0;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getUsername() {
        return this.username;
    }
}
