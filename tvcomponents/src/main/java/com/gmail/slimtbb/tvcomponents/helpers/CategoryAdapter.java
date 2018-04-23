package com.gmail.slimtbb.tvcomponents.helpers;

import android.os.Handler;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class CategoryAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    protected OnCategoryListener listener;
    private boolean isScrolling;
    private final int DELAY = 300; //milliseconds of delay for timer
    private ScheduledFuture sf;
    private ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
    private Handler handler;

    public CategoryAdapter(OnCategoryListener listener) {
        this.listener = listener;
        isScrolling = false;
        handler = new Handler();
    }

    public void setOnCategoryListener(OnCategoryListener listener) {
        this.listener = listener;
    }

    public void removeOnCategoryListener() {
        this.listener = null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (!isScrolling)
                        listener.onCategoryClick(position);
                }
            }
        });
        onBindingViewHolder((VH) holder, position);
    }

    public void getViewByPosition(int pos) {

    }

    public abstract void onBindingViewHolder(VH holder, int position);

}