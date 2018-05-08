package com.example.rapfilm.ui.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;
import com.example.rapfilm.model.ActionLog;
import com.example.rapfilm.model.Category;
import com.example.rapfilm.model.Episode;
import com.example.rapfilm.model.Favourite;
import com.example.rapfilm.model.Film;
import com.example.rapfilm.model.Recent;

public abstract class AbstractCardPresenter<T extends BaseCardView> extends Presenter {
    private static final String TAG = "AbstractCardPresenter";
    private final Context mContext;

    public abstract void onBindViewHolder(Object obj, T t);

    protected abstract T onCreateView();

    public AbstractCardPresenter(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }

    public final ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(onCreateView());
    }

    public final void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if (item instanceof Film) {
            onBindViewHolder((Film) item, (T) viewHolder.view);
        } else if (item instanceof Category) {
            onBindViewHolder((Category) item, (T) viewHolder.view);
        } else if (item instanceof String) {
            onBindViewHolder((String) item, (T) viewHolder.view);
        } else if (item instanceof Episode) {
            onBindViewHolder((Episode) item, (T) viewHolder.view);
        } else if (item instanceof Recent) {
            onBindViewHolder((Recent) item, (T) viewHolder.view);
        } else if (item instanceof Favourite) {
            onBindViewHolder((Favourite) item, (T) viewHolder.view);
        } else if (item instanceof ActionLog) {
            onBindViewHolder((ActionLog) item, (T) viewHolder.view);
        }
    }


    public final void onUnbindViewHolder(ViewHolder viewHolder) {
        onUnbindViewHolder((T) viewHolder.view);
    }

    public void onUnbindViewHolder(T t) {
    }
}
