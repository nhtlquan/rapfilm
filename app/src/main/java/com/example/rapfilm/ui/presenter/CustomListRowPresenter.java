package com.example.rapfilm.ui.presenter;

import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.RowPresenter.ViewHolder;
import com.example.rapfilm.model.CustomListRow;

public class CustomListRowPresenter extends ListRowPresenter {
    private static final String TAG = CustomListRowPresenter.class.getSimpleName();

    protected void onBindRowViewHolder(ViewHolder holder, Object item) {
        ((ListRowPresenter.ViewHolder) holder).getGridView().setNumRows(((CustomListRow) item).getNumRows());
        super.onBindRowViewHolder(holder, item);
    }

    protected void initializeRowViewHolder(ViewHolder holder) {
        super.initializeRowViewHolder(holder);
    }
}
