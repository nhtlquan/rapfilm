package com.example.rapfilm.Utils;

import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ObjectAdapter;
import com.example.rapfilm.model.Favourite;
import java.util.ArrayList;

public class FavouriteListRow extends ListRow {
    private ArrayList<Favourite> mCardRow;

    public FavouriteListRow(HeaderItem header, ObjectAdapter adapter, ArrayList<Favourite> cardRow) {
        super(header, adapter);
        setCardRow(cardRow);
    }

    public ArrayList<Favourite> getCardRow() {
        return this.mCardRow;
    }

    public void setCardRow(ArrayList<Favourite> cardRow) {
        this.mCardRow = cardRow;
    }
}
