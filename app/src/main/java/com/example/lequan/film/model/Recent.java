package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.example.lequan.film.common.FilmPreferences;
import com.google.gson.annotations.SerializedName;

public class  Recent implements Parcelable {
    public static final Creator<Recent> CREATOR = new C05881();
    private transient ActionSyn actionSyn;
    private String id_episode;
    private String id_film;
    private String id_season;
    private transient boolean isSelected;
    @SerializedName("name_en")
    private String name;
    private String name_episode;
    private String name_season;
    private String name_vi;
    private transient String poster;
    @SerializedName("duration")
    private String strDuration;
    @SerializedName("lastDuration")
    private String strLastDuration;
    private String thumb;
    private long timeSave;
    private String year;

    static class C05881 implements Creator<Recent> {
        C05881() {
        }

        public Recent createFromParcel(Parcel source) {
            return new Recent(source);
        }

        public Recent[] newArray(int size) {
            return new Recent[size];
        }
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getTitle() {
        if (this.name_vi != null && this.name_vi.length() > 0 && this.name.length() > 0) {
            return FilmPreferences.getInstance().getLanguage().toString().equals("vi") ? this.name_vi : this.name;
        } else {
            if (this.name_vi == null || this.name_vi.length() <= 0) {
                return this.name;
            }
            return this.name_vi;
        }
    }

    public String getTotalName() {
        String title = getTitle();
        if (!this.name_season.equals("0")) {
            return title + " - S" + getName_season() + ", " + getName_episode();
        }
        return title + (getName_episode() != null ? " - " + getName_episode() : "");
    }

    public String getName_vi() {
        return this.name_vi;
    }

    public void setName_vi(String name_vi) {
        this.name_vi = name_vi;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName_season() {
        return this.name_season;
    }

    public void setName_season(String name_season) {
        this.name_season = name_season;
    }

    public String getName_episode() {
        return this.name_episode;
    }

    public void setName_episode(String name_episode) {
        this.name_episode = name_episode;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public ActionSyn getActionSyn() {
        return this.actionSyn;
    }

    public void setActionSyn(ActionSyn actionSyn) {
        this.actionSyn = actionSyn;
    }

    public String getId_season() {
        return this.id_season;
    }

    public void setId_season(String id_season) {
        this.id_season = id_season;
    }

    public String getId_film() {
        return this.id_film;
    }

    public void setId_film(String id_film) {
        this.id_film = id_film;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLastDuration() {
        int index = this.strLastDuration.indexOf(".");
        if (index >= 0) {
            this.strLastDuration = this.strLastDuration.substring(0, index);
        }
        return Integer.parseInt(this.strLastDuration) * 1000;
    }

    public void setLastDuration(long lastDuration) {
        this.strLastDuration = (lastDuration / 1000) + "";
    }

    public int getDuration() {
        if (this.strDuration.equals("NaN")) {
            return 0;
        }
        try {
            int index = this.strDuration.indexOf(".");
            if (index >= 0) {
                this.strDuration = this.strDuration.substring(0, index);
            }
            return Integer.parseInt(this.strDuration) * 1000;
        } catch (NumberFormatException e) {
            Log.e("e", "e = " + e.toString());
            return 0;
        }
    }

    public void setDuration(long duration) {
        this.strDuration = (duration / 1000) + "";
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getId_episode() {
        return this.id_episode;
    }

    public void setId_episode(String id_episode) {
        this.id_episode = id_episode;
    }

    public long getTimeSave() {
        return this.timeSave;
    }

    public void setTimeSave(long timeSave) {
        this.timeSave = timeSave;
    }

    public Recent() {
        this.name = "";
        this.name_vi = "";
        this.actionSyn = ActionSyn.ADD;
        this.isSelected = false;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_film);
        dest.writeString(this.name);
        dest.writeString(this.name_vi);
        dest.writeString(this.year);
        dest.writeString(this.name_season);
        dest.writeString(this.name_episode);
        dest.writeString(this.strLastDuration);
        dest.writeString(this.strDuration);
        dest.writeString(this.thumb);
        dest.writeString(this.id_episode);
        dest.writeString(this.id_season);
        dest.writeLong(this.timeSave);
        dest.writeString(this.poster);
        dest.writeInt(this.actionSyn == null ? -1 : this.actionSyn.ordinal());
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    private Recent(Parcel in) {
        boolean z;
        this.name = "";
        this.name_vi = "";
        this.actionSyn = ActionSyn.ADD;
        this.isSelected = false;
        this.id_film = in.readString();
        this.name = in.readString();
        this.name_vi = in.readString();
        this.year = in.readString();
        this.name_season = in.readString();
        this.name_episode = in.readString();
        this.strLastDuration = in.readString();
        this.strDuration = in.readString();
        this.thumb = in.readString();
        this.id_episode = in.readString();
        this.id_season = in.readString();
        this.timeSave = in.readLong();
        this.poster = in.readString();
        int tmpActionSyn = in.readInt();
        this.actionSyn = tmpActionSyn == -1 ? null : ActionSyn.values()[tmpActionSyn];
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.isSelected = z;
    }
}
