package com.example.rapfilm.custom;

import android.content.Context;
import com.example.rapfilm.ui.presenter.AbstractCardPresenter;

public class StringPresenter extends AbstractCardPresenter<StringCardView> {
    public StringPresenter(Context context) {
        super(context);
    }

    protected StringCardView onCreateView() {
        return new StringCardView(getContext());
    }

    public void onBindViewHolder(Object card, StringCardView cardView) {
        cardView.updateUi(card);
    }
}
