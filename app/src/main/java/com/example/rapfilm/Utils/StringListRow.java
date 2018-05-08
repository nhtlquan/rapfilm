package com.example.rapfilm.Utils;

import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ObjectAdapter;
import java.util.ArrayList;

public class StringListRow extends ListRow {
    private ArrayList<String> mCardRow;

    public StringListRow(HeaderItem header, ObjectAdapter adapter, ArrayList<String> string) {
        super(header, adapter);
        setCardRow(string);
    }

    public ArrayList<String> getCardRow() {
        return this.mCardRow;
    }

    public void setCardRow(ArrayList<String> cardRow) {
        this.mCardRow = cardRow;
    }
}
