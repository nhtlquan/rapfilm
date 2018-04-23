package com.example.lequan.film.Utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import com.google.gson.annotations.SerializedName;
import java.net.URI;
import java.net.URISyntaxException;

public class Card {
    @SerializedName("description")
    private String mDescription = "";
    @SerializedName("extraText")
    private String mExtraText = "";
    @SerializedName("footerColor")
    private String mFooterColor = null;
    @SerializedName("footerIconLocalImageResource")
    private String mFooterResource = null;
    @SerializedName("height")
    private int mHeight;
    @SerializedName("id")
    private int mId;
    @SerializedName("card")
    private String mImageUrl;
    @SerializedName("localImageResource")
    private String mLocalImageResource = null;
    @SerializedName("selectedColor")
    private String mSelectedColor = null;
    @SerializedName("title")
    private String mTitle = "";
    @SerializedName("type")
    private Type mType;
    @SerializedName("width")
    private int mWidth;

    public enum Type {
        MOVIE_COMPLETE,
        MOVIE,
        MOVIE_BASE,
        ICON,
        SQUARE_BIG,
        SINGLE_LINE,
        GAME,
        SQUARE_SMALL,
        DEFAULT,
        SIDE_INFO,
        SIDE_INFO_TEST_1,
        TEXT,
        CHARACTER,
        GRID_SQUARE,
        VIDEO_GRID
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getLocalImageResource() {
        return this.mLocalImageResource;
    }

    public void setLocalImageResource(String localImageResource) {
        this.mLocalImageResource = localImageResource;
    }

    public String getFooterResource() {
        return this.mFooterResource;
    }

    public void setFooterResource(String footerResource) {
        this.mFooterResource = footerResource;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getId() {
        return this.mId;
    }

    public Type getType() {
        return this.mType;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getExtraText() {
        return this.mExtraText;
    }

    public void setExtraText(String extraText) {
        this.mExtraText = extraText;
    }

    public int getFooterColor() {
        if (this.mFooterColor == null) {
            return -1;
        }
        return Color.parseColor(this.mFooterColor);
    }

    public void setFooterColor(String footerColor) {
        this.mFooterColor = footerColor;
    }

    public int getSelectedColor() {
        if (this.mSelectedColor == null) {
            return -1;
        }
        return Color.parseColor(this.mSelectedColor);
    }

    public String getImageUrl() {
        return this.mImageUrl;
    }

    public void setSelectedColor(String selectedColor) {
        this.mSelectedColor = selectedColor;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    public URI getImageURI() {
        if (getImageUrl() == null) {
            return null;
        }
        try {
            return new URI(getImageUrl());
        } catch (URISyntaxException e) {
            Log.d("URI exception: ", getImageUrl());
            return null;
        }
    }

    public int getLocalImageResourceId(Context context) {
        return context.getResources().getIdentifier(getLocalImageResourceName(), "drawable", context.getPackageName());
    }

    public String getLocalImageResourceName() {
        return this.mLocalImageResource;
    }

    public String getFooterLocalImageResourceName() {
        return this.mFooterResource;
    }
}
