package com.example.lequan.film.Utils;

import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ObjectAdapter;
import com.example.lequan.film.model.Recent;
import java.util.ArrayList;

public class RecentListRow extends ListRow {
    private ArrayList<Recent> mCardRow;

    public RecentListRow(HeaderItem header, ObjectAdapter adapter, ArrayList<Recent> cardRow) {
        super(header, adapter);
        setCardRow(cardRow);
    }

    public ArrayList<Recent> getCardRow() {
        return this.mCardRow;
    }

    public void setCardRow(ArrayList<Recent> cardRow) {
        this.mCardRow = cardRow;
    }
}
