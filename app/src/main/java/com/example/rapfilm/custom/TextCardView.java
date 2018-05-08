package com.example.rapfilm.custom;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v4.internal.view.SupportMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rapfilm.model.ActionLog;
import com.example.rapfilm.model.Category;
import com.example.rapfilm.model.Episode;
import com.example.rapfilm.model.Favourite;
import com.example.rapfilm.model.Film;
import com.example.rapfilm.model.Recent;
import com.example.rapfilm.R;

public class TextCardView extends BaseCardView {
    public TextCardView(Context context) {
        super(context, null, R.style.TextCardStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.text_icon_card, this);
    }

    public void updateUi(Object card) {
        View root = findViewById(R.id.root);
        View vQuality = findViewById(R.id.vQuality);
        View vImdb = findViewById(R.id.vImdb);
        TextView tvTitleAction = (TextView) findViewById(R.id.tvTitleAction);
        TextView primaryText = (TextView) findViewById(R.id.primary_text);
        ImageView imgAvatar = (CircleImageView) findViewById(R.id.imgAvatar);
        TextView tvCategory = (TextView) findViewById(R.id.tvCategory);
        ImageView mainImage = (ImageView) findViewById(R.id.imgMain);
        mainImage.setVisibility(GONE);
        TextView tvQuality = (TextView) findViewById(R.id.tvQuality);
        TextView tvSecondText = (TextView) findViewById(R.id.second_text);
        ImageView imgPlay = (ImageView) findViewById(R.id.imgPlay);
        imgPlay.setVisibility(GONE);
        TextView tvAddMore = (TextView) findViewById(R.id.tvAddMore);
        TextView tvImdb = (TextView) findViewById(R.id.tvImdb);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        if (card instanceof Episode) {
            primaryText.setText(((Episode) card).getName());
            mainImage.setVisibility(8);
            tvAddMore.setVisibility(8);
            progressBar.setVisibility(8);
            vQuality.setVisibility(8);
            tvCategory.setVisibility(8);
            tvSecondText.setVisibility(8);
            imgPlay.setVisibility(8);
            vImdb.setVisibility(8);
            tvTitleAction.setVisibility(8);
            imgAvatar.setVisibility(8);
        }
        if (card instanceof ActionLog) {
            ActionLog actionLog = (ActionLog) card;
            root.setBackgroundColor(getResources().getColor(R.color.transparent_black));
            mainImage.setVisibility(8);
            primaryText.setVisibility(8);
            tvSecondText.setVisibility(8);
            tvQuality.setVisibility(8);
            progressBar.setVisibility(8);
            vImdb.setVisibility(8);
            tvCategory.setVisibility(8);
            vQuality.setVisibility(8);
            if (actionLog.getTitle().equals("Đăng xuất")) {
                if (TextUtils.isEmpty(actionLog.getLink())) {
                    imgAvatar.setVisibility(8);
                    imgPlay.setVisibility(0);
                    imgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_label_outline_white_48dp));
                } else {
                    imgAvatar.setVisibility(0);
                    imgPlay.setVisibility(8);
                    Glide.with(getContext()).load(actionLog.getLink()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgAvatar);
                }
            } else if (actionLog.getTitle().equals("Đăng nhập")) {
                imgPlay.setVisibility(0);
                imgAvatar.setVisibility(8);
                imgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_input_white_48dp));
            }
            tvTitleAction.setVisibility(0);
            tvTitleAction.setText(actionLog.getTitle());
            tvAddMore.setVisibility(0);
            tvAddMore.setText(actionLog.getName());
        }
        if (card instanceof Film) {
            Film film = (Film) card;
            if (film.getId().equals("more_more")) {
                root.setBackgroundColor(0);
                mainImage.setVisibility(8);
                progressBar.setVisibility(8);
                primaryText.setVisibility(8);
                tvSecondText.setVisibility(8);
                tvCategory.setVisibility(8);
                imgAvatar.setVisibility(8);
                vImdb.setVisibility(8);
                vQuality.setVisibility(8);
                tvTitleAction.setVisibility(8);
                tvQuality.setVisibility(8);
                imgPlay.setVisibility(0);
                imgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white_36dp));
                tvAddMore.setVisibility(0);
            } else {
                root.setBackgroundColor(getResources().getColor(R.color.default_card_footer_background_color));
                primaryText.setVisibility(0);
                progressBar.setVisibility(8);
                tvCategory.setVisibility(8);
                imgPlay.setVisibility(8);
                imgAvatar.setVisibility(8);
                tvSecondText.setVisibility(0);
                tvTitleAction.setVisibility(8);
                if (film.getView() != 0) {
                    tvSecondText.setVisibility(0);
                    tvSecondText.setText(film.getView() + " lượt xem");
                } else {
                    tvSecondText.setVisibility(8);
                }
                if (TextUtils.isEmpty(film.getYear())) {
                    primaryText.setText(film.getName_vi() + " - " + film.getName());
                } else {
                    primaryText.setText(film.getName_vi() + " - " + film.getName() + " ( " + film.getYear() + " ) ");
                }
                if (TextUtils.isEmpty(film.getQuality())) {
                    vQuality.setVisibility(8);
                } else {
                    tvQuality.setText(film.getQuality());
                    vQuality.setVisibility(0);
                }
                if (film.getImdb_rate() != 0.0d) {
                    vImdb.setVisibility(0);
                    tvImdb.setText(film.getImdb_rate() + "");
                } else {
                    vImdb.setVisibility(8);
                }
                mainImage.setVisibility(0);
                tvQuality.setVisibility(0);
                tvAddMore.setVisibility(8);
                if (film.getCover() == null) {
                    mainImage.setBackgroundColor(SupportMenu.CATEGORY_MASK);
                } else if (!TextUtils.isEmpty(film.getCover().getS960())) {
                    Glide.with(getContext()).load(film.getCover().getS960()).into(mainImage);
                } else if (TextUtils.isEmpty(film.getCover().getS640())) {
                    mainImage.setBackgroundColor(SupportMenu.CATEGORY_MASK);
                } else {
                    Glide.with(getContext()).load(film.getCover().getS640()).into(mainImage);
                }
            }
        }
        if (card instanceof Recent) {
            Recent film2 = (Recent) card;
            progressBar.setVisibility(0);
            if (film2.getDuration() > 0) {
                progressBar.setProgress((film2.getLastDuration() * 100) / film2.getDuration());
            }
            mainImage.setVisibility(0);
            tvSecondText.setVisibility(8);
            tvAddMore.setVisibility(8);
            imgPlay.setVisibility(0);
            imgAvatar.setVisibility(8);
            vQuality.setVisibility(8);
            tvTitleAction.setVisibility(8);
            tvCategory.setVisibility(8);
            vImdb.setVisibility(8);
            primaryText.setText(film2.getName());
            imgPlay.setBackground(getResources().getDrawable(R.drawable.circle_border_white));
            if (film2.getThumb() == null) {
                mainImage.setBackgroundColor(SupportMenu.CATEGORY_MASK);
            } else if (TextUtils.isEmpty(film2.getThumb())) {
                mainImage.setBackgroundColor(SupportMenu.CATEGORY_MASK);
            } else {
                Glide.with(getContext()).load(film2.getThumb()).into(mainImage);
            }
        }
        if (card instanceof Category) {
            Category category = (Category) card;
            progressBar.setVisibility(8);
            root.setBackgroundColor(getResources().getColor(R.color.default_card_footer_background_color));
            mainImage.setVisibility(8);
            tvSecondText.setVisibility(8);
            vImdb.setVisibility(8);
            tvTitleAction.setVisibility(8);
            imgAvatar.setVisibility(8);
            imgPlay.setVisibility(8);
            vQuality.setVisibility(8);
            primaryText.setVisibility(8);
            tvAddMore.setVisibility(8);
            tvCategory.setText(category.getName());
        }
        if (card instanceof Favourite) {
            Favourite favourite = (Favourite) card;
            progressBar.setVisibility(8);
            mainImage.setVisibility(0);
            tvSecondText.setVisibility(8);
            imgAvatar.setVisibility(8);
            tvAddMore.setVisibility(8);
            vImdb.setVisibility(8);
            imgPlay.setVisibility(8);
            tvTitleAction.setVisibility(8);
            tvCategory.setVisibility(8);
            vQuality.setVisibility(8);
            primaryText.setText(favourite.getName());
            if (TextUtils.isEmpty(favourite.getThumb())) {
                mainImage.setBackgroundColor(SupportMenu.CATEGORY_MASK);
            } else if (TextUtils.isEmpty(favourite.getThumb())) {
                mainImage.setBackgroundColor(SupportMenu.CATEGORY_MASK);
            } else {
                Glide.with(getContext()).load(favourite.getThumb()).into(mainImage);
            }
        }
    }
}
