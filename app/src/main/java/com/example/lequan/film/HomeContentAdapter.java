package com.example.lequan.film;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * Created by Tu NM on 4/21/2017.
 */

public class HomeContentAdapter extends RecyclerView.Adapter<HomeContentAdapter.ViewHolder> {
    private final List<Film> mValues;
    private Context context;
    private OnItemClickListener listener;
    private FindNextFocusAtLastPositionListener findNextFocusAtLastPositionListener;

    public HomeContentAdapter(List<Film> items, OnItemClickListener listener) {
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
                .inflate(R.layout.item_main_content2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnFocusChangeListener((view, b) -> {
            if (b) {
                holder.itemView.animate().withLayer().scaleX(1.1f).scaleY(1.1f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(500).start();
                holder.tvTitle.setSelected(true);
            } else {
                holder.itemView.animate().withLayer().scaleX(1.0f).scaleY(1.0f).setInterpolator(new LinearOutSlowInInterpolator()).setDuration(150).start();
                holder.tvTitle.setSelected(false);
            }
        });
        final Film item = mValues.get(position);


        holder.tvTitle.setText(item.getName_vi());
        Glide.with(context).load(item.getPoster().getLink())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_2_3)
                .into(holder.mContentView);
        holder.title_sub.setText(item.getName());

        holder.itemView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (position == mValues.size() - 1 && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    findNextFocusAtLastPositionListener.onFocusChanged();
                }
            }
            return false;
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClicked(position);
        });
        holder.tvCoundownTime.setText(item.getView() + " lượt xem");
        holder.tv_quality.setText(item.getQuality());

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
        TextView title_sub,tv_quality;
        View icLive;
        LinearLayout iconLive;
        TextView tvCoundownTime;

        public ViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.imageView);
            tv_quality = view.findViewById(R.id.tv_quality);
            tvTitle = view.findViewById(R.id.title);
            icLive = view.findViewById(R.id.iconLive);
            title_sub = view.findViewById(R.id.title_sub);
            iconLive = view.findViewById(R.id.iconLive);
            tvCoundownTime = view.findViewById(R.id.tv_countdown);
        }

    }

    public interface FindNextFocusAtLastPositionListener {
        void onFocusChanged();
    }
}
