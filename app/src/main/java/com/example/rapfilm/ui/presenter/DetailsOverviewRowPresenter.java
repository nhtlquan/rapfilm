package com.example.rapfilm.ui.presenter;

import android.support.v17.leanback.widget.DetailsOverviewLogoPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;

public class DetailsOverviewRowPresenter extends FullWidthDetailsOverviewRowPresenter {
    public DetailsOverviewRowPresenter(Presenter detailsPresenter) {
        super(detailsPresenter);
    }

    public DetailsOverviewRowPresenter(Presenter detailsPresenter, DetailsOverviewLogoPresenter logoPresenter) {
        super(detailsPresenter, logoPresenter);
    }
}
