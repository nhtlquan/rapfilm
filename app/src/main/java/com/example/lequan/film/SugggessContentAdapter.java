package com.example.lequan.film;

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
import com.example.lequan.film.model.Film;

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
        holder.itemView.setOnFocusChangeListener((view, b) -> {
            if (b) {
                holder.tvTitle.setTextColor(context.getResources().getColor(R.color.white));
                holder.itemView.animate().withLayer().scaleX(1.1f).scaleY(1.1f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(500).start();
                holder.tvTitle.setSelected(true);
            } else {
                holder.tvTitle.setTextColor(context.getResources().getColor(R.color.textcolor));
                holder.itemView.animate().withLayer().scaleX(1.0f).scaleY(1.0f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(150).start();
                holder.tvTitle.setSelected(false);
            }
        });
        final Film item = mValues.get(position);

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
        ImageView mContentView;
        TextView tvTitle;
        TextView title_sub;

        public ViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.imageView);
            tvTitle = view.findViewById(R.id.title);
            title_sub = view.findViewById(R.id.title_sub);
        }

    }

    public interface FindNextFocusAtLastPositionListener {
        void onFocusChanged();
    }
}
