package com.example.lequan.film.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.example.lequan.film.common.FilmPreferences;
import com.example.lequan.film.common.Language;

public class Favourite implements Parcelable {
    public static final Creator<Favourite> CREATOR = new C05671();
    private transient ActionSyn actionSyn = ActionSyn.NONE;
    private String id_film;
    private String name = "";
    private String name_vi = "";
    private transient boolean selected;
    private String thumb;

    public Favourite() {
    }

    static class C05671 implements Creator<Favourite> {
        C05671() {
        }

        public Favourite createFromParcel(Parcel source) {
            return new Favourite(source);
        }

        public Favourite[] newArray(int size) {
            return new Favourite[size];
        }
    }

    public String getTitle() {
        if (this.name.length() > 0 && this.name_vi.length() > 0) {
            return FilmPreferences.getInstance().getLanguage() == Language.VI ? this.name_vi : this.name;
        } else {
            if (this.name.length() > 0) {
                return this.name;
            }
            return this.name_vi;
        }
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ActionSyn getActionSyn() {
        return this.actionSyn;
    }

    public void setActionSyn(ActionSyn actionSyn) {
        this.actionSyn = actionSyn;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_vi() {
        return this.name_vi;
    }

    public void setName_vi(String name_vi) {
        this.name_vi = name_vi;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getId_film() {
        return this.id_film;
    }

    public void setId_film(String id_film) {
        this.id_film = id_film;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.actionSyn == null ? -1 : this.actionSyn.ordinal());
        dest.writeString(this.name);
        dest.writeString(this.name_vi);
        dest.writeString(this.thumb);
        dest.writeString(this.id_film);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected Favourite(Parcel in) {
        int tmpActionSyn = in.readInt();
        this.actionSyn = tmpActionSyn == -1 ? null : ActionSyn.values()[tmpActionSyn];
        this.name = in.readString();
        this.name_vi = in.readString();
        this.thumb = in.readString();
        this.id_film = in.readString();
        this.selected = in.readByte() != (byte) 0;
    }
}
