package com.example.rapfilm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class VideoAds implements Parcelable {
    public static final Creator<VideoAds> CREATOR = new C06091();
    private String banner;
    private String icon;
    private String id;
    private String link_install;
    @SerializedName("link_play")
    private ArrayList<LinkPlay> link_play;
    private String link_video;
    private String name;
    private int skip;

    static class C06091 implements Creator<VideoAds> {
        C06091() {
        }

        public VideoAds createFromParcel(Parcel in) {
            return new VideoAds(in);
        }

        public VideoAds[] newArray(int size) {
            return new VideoAds[size];
        }
    }

    protected VideoAds(Parcel in) {
        this.name = in.readString();
        this.link_video = in.readString();
        this.link_install = in.readString();
        this.skip = in.readInt();
        this.banner = in.readString();
        this.icon = in.readString();
        this.link_play = in.createTypedArrayList(LinkPlay.CREATOR);
        this.id = in.readString();
    }

    public ArrayList<LinkPlay> getLink_play() {
        return this.link_play;
    }

    public void setLink_play(ArrayList<LinkPlay> link_play) {
        this.link_play = link_play;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink_video() {
        return this.link_video;
    }

    public void setLink_video(String link_video) {
        this.link_video = link_video;
    }

    public String getLink_install() {
        return this.link_install;
    }

    public void setLink_install(String link_install) {
        this.link_install = link_install;
    }

    public int getSkip() {
        return this.skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public String getBanner() {
        return this.banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.link_video);
        dest.writeString(this.link_install);
        dest.writeInt(this.skip);
        dest.writeString(this.banner);
        dest.writeString(this.icon);
        dest.writeTypedList(this.link_play);
        dest.writeString(this.id);
    }
}
