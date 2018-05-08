package com.example.rapfilm.model;

import android.support.v17.leanback.widget.HeaderItem;

public class IconHeaderItem extends HeaderItem {
    public static final int ICON_NONE = -1;
    private static final String TAG = IconHeaderItem.class.getSimpleName();
    private int mIconResId;

    public IconHeaderItem(long id, String name, int iconResId) {
        super(id, name);
        this.mIconResId = -1;
        this.mIconResId = iconResId;
    }

    public IconHeaderItem(long id, String name) {
        this(id, name, -1);
    }

    public IconHeaderItem(String name) {
        super(name);
        this.mIconResId = -1;
    }

    public int getIconResId() {
        return this.mIconResId;
    }

    public void setIconResId(int iconResId) {
        this.mIconResId = iconResId;
    }
}
