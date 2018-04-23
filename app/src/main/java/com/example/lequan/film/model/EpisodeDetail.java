package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class EpisodeDetail implements Parcelable {
    public static final Creator<EpisodeDetail> CREATOR = new C05661();
    private List<Video> download;
    private String[] servers;
//    @SerializedName("english_subtitle")
//    private String subtitleEnglish;
//    @SerializedName("vietnamese_subtitle")
//    private String subtitleVietnamese;
    private int total_server;
    private String type;

    static class C05661 implements Creator<EpisodeDetail> {
        C05661() {
        }

        public EpisodeDetail createFromParcel(Parcel source) {
            return new EpisodeDetail(source);
        }

        public EpisodeDetail[] newArray(int size) {
            return new EpisodeDetail[size];
        }
    }

    public int getTotal_server() {
        return this.total_server;
    }

    public void setTotal_server(int total_server) {
        this.total_server = total_server;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Video> getDownload() {
        return this.download;
    }

    public void setDownload(List<Video> download) {
        this.download = download;
    }

    public String[] getServers() {
        return this.servers;
    }

    public void setServers(String[] servers) {
        this.servers = servers;
    }

//    public String getSubtitleVietnamese() {
//        return this.subtitleVietnamese;
//    }
//
//    public void setSubtitleVietnamese(String subtitleVietnamese) {
//        this.subtitleVietnamese = subtitleVietnamese;
//    }
//
//    public String getSubtitleEnglish() {
//        return this.subtitleEnglish;
//    }
//
//    public void setSubtitleEnglish(String subtitleEnglish) {
//        this.subtitleEnglish = subtitleEnglish;
//    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeTypedList(this.download);
        dest.writeStringArray(this.servers);
        dest.writeInt(this.total_server);
    }

    protected EpisodeDetail(Parcel in) {
        this.type = in.readString();
        this.download = in.createTypedArrayList(Video.CREATOR);
        this.servers = in.createStringArray();
        this.total_server = in.readInt();
    }
}
