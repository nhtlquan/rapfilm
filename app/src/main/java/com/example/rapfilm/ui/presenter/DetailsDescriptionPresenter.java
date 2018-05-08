package com.example.rapfilm.ui.presenter;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.text.Html;

import com.example.rapfilm.R;
import com.example.rapfilm.common.Utils;
import com.example.rapfilm.model.FilmDetail;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        if (item instanceof FilmDetail) {
            FilmDetail movie = (FilmDetail) item;
            if (movie != null) {
                viewHolder.getTitle().setText(movie.getTitle());
                viewHolder.getSubtitle().setTextColor(viewHolder.getSubtitle().getResources().getColor(R.color.white_60));
                viewHolder.getSubtitle().setText(Html.fromHtml("<br><font color=\"#FFFFFF\">Đạo diễn : </font>" + Utils.stringArrayToString(movie.getWriter(), ", ") + "<br><font color=\"#FFFFFF\">Ngày phát hành : </font>" + movie.getReleased() + "<br><font color=\"#FFFFFF\">Thời lượng : </font>" + movie.getRuntime()));
                viewHolder.getBody().setTextColor(viewHolder.getBody().getResources().getColor(R.color.white_60));
                viewHolder.getBody().setText(Html.fromHtml(movie.getDescription()));
            }
        } else if (item instanceof String) {
            String title = (String) item;
            if (title != null) {
                viewHolder.getTitle().setText(title);
            }
        }
    }
}
