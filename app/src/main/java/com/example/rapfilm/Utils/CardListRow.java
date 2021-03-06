package com.example.rapfilm.Utils;

import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ObjectAdapter;

import com.example.rapfilm.Debug;
import com.example.rapfilm.model.Film;
import java.util.ArrayList;

public class CardListRow extends ListRow {
    private ArrayList<Film> mCardRow;

    public CardListRow(HeaderItem header, ObjectAdapter adapter, ArrayList<Film> cardRow) {
        super(header, adapter);
        setCardRow(cardRow);
    }

    public ArrayList<Film> getCardRow() {
        return this.mCardRow;
    }

    public void setCardRow(ArrayList<Film> cardRow) {
        this.mCardRow = cardRow;
    }
}
