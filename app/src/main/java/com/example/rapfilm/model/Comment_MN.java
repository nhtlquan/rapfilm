package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Comment_MN implements Parcelable {
    public static final Creator<Comment_MN> CREATOR = new C05611();
    private String comment;
    private String comment_id;
    private Film_MN film;
    private long timestamp;
    private User_MN user;

    static class C05611 implements Creator<Comment_MN> {
        C05611() {
        }

        public Comment_MN createFromParcel(Parcel in) {
            return new Comment_MN(in);
        }

        public Comment_MN[] newArray(int size) {
            return new Comment_MN[size];
        }
    }

    protected Comment_MN(Parcel in) {
        this.comment_id = in.readString();
        this.comment = in.readString();
        this.timestamp = in.readLong();
        this.film = (Film_MN) in.readParcelable(Film_MN.class.getClassLoader());
        this.user = (User_MN) in.readParcelable(User_MN.class.getClassLoader());
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public void setFilm(Film_MN film) {
        this.film = film;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUser(User_MN user) {
        this.user = user;
    }

    public Film_MN getFilm() {
        return this.film;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getComment() {
        return this.comment;
    }

    public String getComment_id() {
        return this.comment_id;
    }

    public User_MN getUser() {
        return this.user;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.comment_id);
        dest.writeString(this.comment);
        dest.writeLong(this.timestamp);
        dest.writeParcelable(this.film, flags);
        dest.writeParcelable(this.user, flags);
    }
}
