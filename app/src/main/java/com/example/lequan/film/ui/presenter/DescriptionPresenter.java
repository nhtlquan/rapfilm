package com.example.lequan.film.ui.presenter;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter.ViewHolder;
import com.example.lequan.film.model.Movie;

public class DescriptionPresenter extends AbstractDetailsDescriptionPresenter {
    private static final String TAG = DescriptionPresenter.class.getSimpleName();

    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        viewHolder.getTitle().setText(((Movie) item).getTitle());
        viewHolder.getSubtitle().setText(((Movie) item).getStudio());
    }
}
