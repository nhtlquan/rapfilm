package com.example.lequan.film.ui.presenter;

import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter.ViewHolder;
import android.util.Log;

public class CustomFullWidthDetailsOverviewRowPresenter extends FullWidthDetailsOverviewRowPresenter {
    private static final String TAG = CustomFullWidthDetailsOverviewRowPresenter.class.getSimpleName();

    public CustomFullWidthDetailsOverviewRowPresenter(Presenter presenter) {
        super(presenter);
    }

    protected void onRowViewAttachedToWindow(ViewHolder vh) {
        Log.v(TAG, "onRowViewAttachedToWindow");
        super.onRowViewAttachedToWindow(vh);
    }

    protected void onBindRowViewHolder(ViewHolder holder, Object item) {
        Log.v(TAG, "onBindRowViewHolder");
        super.onBindRowViewHolder(holder, item);
    }

    protected void onLayoutOverviewFrame(FullWidthDetailsOverviewRowPresenter.ViewHolder viewHolder, int oldState, boolean logoChanged) {
        Log.v(TAG, "onLayoutOverviewFrame");
        setState(viewHolder, 0);
        super.onLayoutOverviewFrame(viewHolder, oldState, logoChanged);
    }
}
