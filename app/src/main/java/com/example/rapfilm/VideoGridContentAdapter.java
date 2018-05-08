package com.example.rapfilm;

import android.content.Context;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rapfilm.model.Film;
import com.example.rapfilm.ui.VideoGridActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tu NM on 4/21/2017.
 */

public class VideoGridContentAdapter extends RecyclerView.Adapter<VideoGridContentAdapter.ViewHolder> {
    private final List<Film> mValues;
    private Context context;
    private OnItemClickListener listener;
    private OnItemFocusListener onItemFocusListener;
    private FindNextFocusAtLastPositionListener findNextFocusAtLastPositionListener;
    private View itemViewFirt;


    public VideoGridContentAdapter(List<Film> items, OnItemClickListener listener, OnItemFocusListener onItemFocusListener) {
        mValues = items;
        this.listener = listener;
        this.onItemFocusListener = onItemFocusListener;
    }
    public View getItemView(){
        return itemViewFirt;
    }

    public void addAll(List<Film> items) {
        Debug.e(items.size());
        mValues.addAll(items);
        notifyItemRangeInserted(mValues.size() - items.size(), mValues.size());
    }

    public Film getItemByPos(int pos) {
        return mValues.get(pos);
    }


    public void setListener(FindNextFocusAtLastPositionListener findNextFocusAtLastPositionListener) {
        this.findNextFocusAtLastPositionListener = findNextFocusAtLastPositionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_grid_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Film item = mValues.get(position);
        holder.itemView.setOnFocusChangeListener((view, b) -> {
            if (b) {
                holder.imagePlay.setVisibility(View.VISIBLE);
                holder.imageBlur.setVisibility(View.VISIBLE);
                if (mValues.size() - 5 < position && onItemFocusListener != null)
                    onItemFocusListener.onItemFocus();
                holder.tvTitle.setTextColor(context.getResources().getColor(R.color.white));
                holder.itemView.animate().withLayer().scaleX(1.1f).scaleY(1.1f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(500).start();
                holder.tvTitle.setSelected(true);
            } else {
                holder.imagePlay.setVisibility(View.GONE);
                holder.imageBlur.setVisibility(View.GONE);
                holder.tvTitle.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.itemView.animate().withLayer().scaleX(1.0f).scaleY(1.0f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(150).start();
                holder.tvTitle.setSelected(false);
            }
        });
        if (position ==0)
            holder.itemView.requestFocus(View.FOCUS_DOWN);
        itemViewFirt = holder.itemView;
        if (item.getType().equals("movie")) {
            holder.quality.setVisibility(View.VISIBLE);
            holder.episode.setVisibility(View.GONE);
            holder.quality.setText(item.getQuality());
        } else if (item.getType().equals("series")) {
            holder.quality.setVisibility(View.GONE);
            holder.episode.setVisibility(View.VISIBLE);
            holder.episode.setText("T-" + item.getTotal_episode());
        }
        holder.tvTitle.setText(item.getName_vi());
        if (item.getCover() != null && item.getCover().getLink() != null)
            Glide.with(context).load(item.getCover().getLink())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder_16_9)
                    .into(holder.mContentView);
        holder.title_sub.setText(item.getName());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClicked(position);
        });


    }


    @Override
    public int getItemCount() {
        if (mValues == null)
            return 0;
        else
            return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mContentView, imageBlur, imagePlay;
        TextView tvTitle;
        TextView title_sub;
        TextView  quality, episode;

        public ViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.imageView);
            imageBlur = view.findViewById(R.id.imageBlur);
            imagePlay = view.findViewById(R.id.imagePlay);
            quality = view.findViewById(R.id.quality);
            episode = view.findViewById(R.id.episode);
            tvTitle = view.findViewById(R.id.title);
            title_sub = view.findViewById(R.id.title_sub);
        }

    }

    public interface FindNextFocusAtLastPositionListener {
        void onFocusChanged();
    }
}
