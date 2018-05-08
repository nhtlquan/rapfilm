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
import com.example.rapfilm.OnItemClickListener;
import com.example.rapfilm.R;
import com.example.rapfilm.model.Category;

import java.util.List;


/**
 * Created by Tu NM on 4/21/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final List<Category> mValues;
    private Context context;
    private OnItemClickListener listener;
    private FindNextFocusAtLastPositionListener findNextFocusAtLastPositionListener;

    public CategoryAdapter(List<Category> items, OnItemClickListener listener) {
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
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnFocusChangeListener((view, b) -> {
            if (b) {
                holder.itemView.animate().withLayer().scaleX(1.2f).scaleY(1.2f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(500).start();
                holder.tvTitle.setSelected(true);
            } else {
                holder.itemView.animate().withLayer().scaleX(1.0f).scaleY(1.0f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(150).start();
                holder.tvTitle.setSelected(false);
            }
        });
        final Category item = mValues.get(position);
        holder.tvTitle.setText(item.getName_vi());
        Glide.with(context).load(item.getBanner())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_2_3)
                .into(holder.mContentView);

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
        ImageView mContentView;
        TextView tvTitle;

        public ViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.imageCover);
            tvTitle = view.findViewById(R.id.tag_name);
        }

    }

    public interface FindNextFocusAtLastPositionListener {
        void onFocusChanged();
    }
}
