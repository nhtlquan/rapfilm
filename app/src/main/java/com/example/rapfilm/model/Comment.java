package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Comment implements Parcelable {
    public static final Creator<Comment> CREATOR = new C05601();
    private String avatar;
    private String comment;
    private String id;
    private boolean is_like;
    private int likes;
    private int mark;
    private long published_time;
    private int state = 0;
    private int total_reply;
    private String username;

    static class C05601 implements Creator<Comment> {
        C05601() {
        }

        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getPublished_time() {
        return this.published_time;
    }

    public void setPublished_time(long published_time) {
        this.published_time = published_time;
    }

    public int getMark() {
        return this.mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public int getTotal_reply() {
        return this.total_reply;
    }

    public void setTotal_reply(int total_reply) {
        this.total_reply = total_reply;
    }

    public boolean is_like() {
        return this.is_like;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.comment);
        dest.writeString(this.avatar);
        dest.writeLong(this.published_time);
        dest.writeInt(this.mark);
        dest.writeByte(this.is_like ? (byte) 1 : (byte) 0);
        dest.writeInt(this.likes);
        dest.writeInt(this.total_reply);
        dest.writeInt(this.state);
    }

    protected Comment(Parcel in) {
        boolean z = false;
        this.id = in.readString();
        this.username = in.readString();
        this.comment = in.readString();
        this.avatar = in.readString();
        this.published_time = in.readLong();
        this.mark = in.readInt();
        if (in.readByte() != (byte) 0) {
            z = true;
        }
        this.is_like = z;
        this.likes = in.readInt();
        this.total_reply = in.readInt();
        this.state = in.readInt();
    }
}
