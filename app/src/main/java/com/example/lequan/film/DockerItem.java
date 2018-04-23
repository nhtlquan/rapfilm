package com.example.lequan.film;

import java.util.ArrayList;


/**
 * Created by Tu NM on 5/8/2017.
 */

public class DockerItem {
    public static final int UNKNOWN = 0;
    public static final String TV = "live_channels";
    public static final String VOD = "videos";
    public static final String SERIES = "series";
    public static final String EVENTS = "events";
    public static final String FILM = "movies";
    public static final String BOOKMARK = "bookmark";
    public static final String HISTORY = "history";
    public static final String APP = "app";
    public static final String ACCOUNT = "account";
    public static final String SETTINGS = "setting";

    private String title;
    private int iconUrl;
    private int id;
    private String itemType;
    private int id_hover;

    public DockerItem(String title, int iconUrl, int id, String itemType, int id_hover) {
        this.title = title;
        this.iconUrl = iconUrl;
        this.id = id;
        this.itemType = itemType;
        this.id_hover = id_hover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(int iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getId_hover() {
        return id_hover;
    }

    public void setId_hover(int id_hover) {
        this.id_hover = id_hover;
    }
}