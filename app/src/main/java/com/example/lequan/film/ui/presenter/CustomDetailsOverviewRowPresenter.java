package com.example.lequan.film.ui.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.DetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter.ViewHolder;
import android.util.Log;
import com.example.lequan.film.R;

public class CustomDetailsOverviewRowPresenter extends DetailsOverviewRowPresenter {
    private static final String TAG = CustomDetailsOverviewRowPresenter.class.getSimpleName();
    private Context mContext;

    public CustomDetailsOverviewRowPresenter(Presenter presenter, Context context) {
        super(presenter);
        this.mContext = context;
    }

    protected void onRowViewAttachedToWindow(ViewHolder vh) {
        Log.v(TAG, "onRowViewAttachedToWindow");
        super.onRowViewAttachedToWindow(vh);
    }

    protected void onBindRowViewHolder(ViewHolder holder, Object item) {
        Log.v(TAG, "onBindRowViewHolder");
        setBackgroundColor(this.mContext.getResources().getColor(R.color.default_background));
        setStyleLarge(true);
        super.onBindRowViewHolder(holder, item);
    }

    protected void onRowViewExpanded(ViewHolder vh, boolean expanded) {
        Log.v(TAG, "onRowViewExpanded");
        super.onRowViewExpanded(vh, expanded);
    }
}
