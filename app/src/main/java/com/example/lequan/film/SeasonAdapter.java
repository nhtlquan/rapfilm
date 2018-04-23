package com.example.lequan.film;

import android.content.Context;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lequan.film.OnItemClickListener;
import com.example.lequan.film.R;
import com.example.lequan.film.model.Episode;
import com.example.lequan.film.model.Season;

import java.util.List;


/**
 * Created by Tu NM on 4/21/2017.
 */

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.ViewHolder> {
    private final List<Season> mValues;
    private Context context;
    private OnEpisodeClickListener listener;
    private FindNextFocusAtLastPositionListener findNextFocusAtLastPositionListener;

    public SeasonAdapter(List<Season> items, OnEpisodeClickListener listener) {
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
                .inflate(R.layout.item_season, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnFocusChangeListener((view, b) -> {
            if (b) {
                holder.tvTitle.setSelected(true);
            } else {
                holder.tvTitle.setSelected(false);
            }
        });
        final Season item = mValues.get(position);
        int season = position + 1;
        holder.tvTitle.setText("Phần " + season);
        holder.trv.setNumRows(1);
        holder.trv.setAdapter(new EpisodeAdapter(item.getContents(), listener));

    }


    @Override
    public int getItemCount() {
        if (mValues == null)
            return 0;
        else
            return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        HorizontalGridView trv;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.title);
            trv = view.findViewById(R.id.trv);
        }

    }

    public interface FindNextFocusAtLastPositionListener {
        void onFocusChanged();
    }
}
