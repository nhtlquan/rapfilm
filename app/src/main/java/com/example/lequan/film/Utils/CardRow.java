package com.example.lequan.film.Utils;

import com.example.lequan.film.model.Film;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CardRow {
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_DIVIDER = 2;
    public static final int TYPE_SECTION_HEADER = 1;
    @SerializedName("cards")
    private List<Film> mCards;
    @SerializedName("shadow")
    private boolean mShadow = true;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("type")
    private int mType = 0;

    public int getType() {
        return this.mType;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public boolean useShadow() {
        return this.mShadow;
    }

    public List<Film> getCards() {
        return this.mCards;
    }
}
