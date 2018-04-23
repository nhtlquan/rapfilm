package com.example.lequan.film.Utils;

import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ObjectAdapter;
import com.example.lequan.film.model.Category;
import java.util.ArrayList;

public class CategoryListRow extends ListRow {
    private ArrayList<Category> mCardRow;

    public CategoryListRow(HeaderItem header, ObjectAdapter adapter, ArrayList<Category> cardRow) {
        super(header, adapter);
        setCardRow(cardRow);
    }

    public ArrayList<Category> getCardRow() {
        return this.mCardRow;
    }

    public void setCardRow(ArrayList<Category> cardRow) {
        this.mCardRow = cardRow;
    }
}
