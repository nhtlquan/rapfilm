package com.example.rapfilm.custom;

import android.support.v17.leanback.widget.HeaderItem;
import com.example.rapfilm.model.Collection;

public class HeaderItemString extends HeaderItem {
    private Collection collection;

    public HeaderItemString(long id, String name) {
        super(id, name);
    }

    public HeaderItemString(String name) {
        super(name);
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Collection getCollection() {
        return this.collection;
    }
}
