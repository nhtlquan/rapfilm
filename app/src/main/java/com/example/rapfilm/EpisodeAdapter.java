package com.example.rapfilm;

import android.content.Context;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.example.rapfilm.OnItemClickListener;
import com.example.rapfilm.R;
import com.example.rapfilm.model.Episode;

import java.util.List;


/**
 * Created by Tu NM on 4/21/2017.
 */

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {
    private final List<Episode> mValues;
    private Context context;
    private OnEpisodeClickListener listener;
    private FindNextFocusAtLastPositionListener findNextFocusAtLastPositionListener;

    public EpisodeAdapter(List<Episode> items, OnEpisodeClickListener listener) {
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
                .inflate(R.layout.item_episode, parent, false);
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
        final Episode item = mValues.get(position);
        holder.tvTitle.setText(item.getName());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.e("Click  ");
                if (listener != null) listener.onItemClicked(item);
            }
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
        TextView tvTitle;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.text);
        }

    }

    public interface FindNextFocusAtLastPositionListener {
        void onFocusChanged();
    }
}
