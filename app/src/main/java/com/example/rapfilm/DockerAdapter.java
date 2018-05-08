package com.example.rapfilm;


import android.content.Context;
import android.os.Handler;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gmail.slimtbb.tvcomponents.helpers.CategoryAdapter;
import com.gmail.slimtbb.tvcomponents.helpers.OnCategoryListener;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DockerAdapter extends CategoryAdapter<DockerAdapter.ViewHolder> {
    private final List<DockerItem> mValues;
    private Context context;
    private final int DELAY = 350; //milliseconds of delay for timer
    private ScheduledFuture sf;
    private ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
    private int old_position = 0;

    private FindNextFocusAtLastPositionListener findNextFocusAtLastPositionListener;


    public void setListener(FindNextFocusAtLastPositionListener findNextFocusAtLastPositionListener) {
        this.findNextFocusAtLastPositionListener = findNextFocusAtLastPositionListener;
    }

    public DockerAdapter(List<DockerItem> DockerItems, OnCategoryListener listener) {
        super(listener);
        mValues = DockerItems;

    }

    public DockerAdapter(List<DockerItem> DockerItems) {
        super(null);
        mValues = DockerItems;
    }

    public void removeData() {
        if (mValues == null) return;
        mValues.clear();
        notifyDataSetChanged();
    }

    public void addItem(DockerItem DockerItem) {
        mValues.add(DockerItem);
        notifyDataSetChanged();
    }

    public void setPosition(int position) {
        notifyItemChanged(old_position);
        notifyItemChanged(position);
        this.old_position = position;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_dock, parent, false);
        return new ViewHolder(view);
    }

    public DockerItem getCategoryByPosition(int pos) {
        if (mValues.get(pos) != null)
            return mValues.get(pos);
        else return null;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onBindingViewHolder(final ViewHolder holder, final int position) {
        DockerItem item = mValues.get(position);
        holder.textView.setText(item.getTitle());
        Glide.with(context).load(item.getIconUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mContentView);

        holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                Glide.with(context).load(item.getId_hover())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mContentView);
                holder.textView.setTextColor(context.getResources().getColor(R.color.yellow));
                holder.itemView.animate().withLayer().scaleX(1.2f).scaleY(1.2f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(500).start();
            } else {
                Glide.with(context).load(item.getIconUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mContentView);
                holder.textView.setTextColor(context.getResources().getColor(R.color.white));
                holder.itemView.animate().withLayer().scaleX(1.0f).scaleY(1.0f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(150).start();
            }
            if (listener == null) return;
            listener.onCategoryInstantSelectChange(holder, position, hasFocus);
            if (sf != null)
                sf.cancel(true);
            sf = scheduledPool.schedule(new Runnable() {
                @Override
                public void run() {
//                    setPosition(position);
                    listener.onCategoryDebounceSelectChange(holder, position, hasFocus);
                }
            }, DELAY, TimeUnit.MILLISECONDS);

        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mContentView;
        public final TextView textView;

        public ViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.imageView);
            textView = view.findViewById(R.id.text);
        }

    }

    public interface FindNextFocusAtLastPositionListener {
        void onFocusChanged();
    }
}
