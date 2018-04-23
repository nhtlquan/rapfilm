package com.example.lequan.film.custom;

import android.content.Context;
import android.support.v17.leanback.widget.BaseCardView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lequan.film.model.Episode;
import com.example.lequan.film.R;

public class StringCardView extends BaseCardView {
    public StringCardView(Context context) {
        super(context, null, R.style.TextCardStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.text_card, this);
        setFocusable(true);
    }

    public void updateUi(Object card) {
        TextView primaryText = (TextView) findViewById(R.id.primary_text);
        ImageView imageView = (ImageView) findViewById(R.id.main_image);
        ImageView imgFooter = (ImageView) findViewById(R.id.footer_icon);
        if (card instanceof Episode) {
            primaryText.setText(((Episode) card).getName());
            imgFooter.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_white_36dp));
        }
    }
}
