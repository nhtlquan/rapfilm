package com.example.lequan.film.Utils;

import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ObjectAdapter;
import com.example.lequan.film.model.Episode;
import java.util.ArrayList;

public class EpisodeListRow extends ListRow {
    private ArrayList<Episode> mCardRow;

    public EpisodeListRow(HeaderItem header, ObjectAdapter adapter, ArrayList<Episode> cardRow) {
        super(header, adapter);
        setCardRow(cardRow);
    }

    public ArrayList<Episode> getCardRow() {
        return this.mCardRow;
    }

    public void setCardRow(ArrayList<Episode> cardRow) {
        this.mCardRow = cardRow;
    }
}
