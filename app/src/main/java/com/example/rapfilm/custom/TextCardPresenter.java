package com.example.rapfilm.custom;

import android.content.Context;
import com.example.rapfilm.ui.presenter.AbstractCardPresenter;

public class TextCardPresenter extends AbstractCardPresenter<TextCardView> {
    public TextCardPresenter(Context context) {
        super(context);
    }

    protected TextCardView onCreateView() {
        return new TextCardView(getContext());
    }

    public void onBindViewHolder(Object card, TextCardView cardView) {
        cardView.setFocusable(true);
        cardView.setActivated(false);
        cardView.setFocusableInTouchMode(true);
        cardView.setInfoVisibility(1);
        cardView.updateUi(card);
    }
}
