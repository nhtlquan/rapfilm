package com.example.rapfilm;

import android.content.Context;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rapfilm.model.Film;

import java.util.List;


/**
 * Created by Tu NM on 4/21/2017.
 */

public class SugggessContentAdapter extends RecyclerView.Adapter<SugggessContentAdapter.ViewHolder> {
    private final List<Film> mValues;
    private Context context;
    private OnItemClickListener listener;
    private FindNextFocusAtLastPositionListener findNextFocusAtLastPositionListener;

    public SugggessContentAdapter(List<Film> items, OnItemClickListener listener) {
        mValues = items;
        this.listener = listener;
    }

    public void setListener(FindNextFocusAtLastPositionListener findNextFocusAtLastPositionListener) {
        this.findNextFocusAtLastPositionListener = findNextFocusAtLastPositionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Film item = mValues.get(position);
        holder.itemView.setOnFocusChangeListener((view, b) -> {
            if (b) {
                holder.imagePlay.setVisibility(View.VISIBLE);
                holder.imageBlur.setVisibility(View.VISIBLE);
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
        Glide.with(context).load(item.getCover().getLink())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_16_9)
                .into(holder.mContentView);
        holder.title_sub.setText(item.getName());
//        holder.itemView.setOnKeyListener((v, keyCode, event) -> {
//            if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                if (position == mValues.size() - 1 && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
//                    findNextFocusAtLastPositionListener.onFocusChanged();
//                }
//            }
//            return false;
//        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClicked(position);
        });

//        holder.itemView.setOnHoverListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_HOVER_ENTER) {
//                holder.itemView.requestFocus();
//            }
//            return true;
//        });

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
