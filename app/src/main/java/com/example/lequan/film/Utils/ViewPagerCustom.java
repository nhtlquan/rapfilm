package com.example.lequan.film.Utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class ViewPagerCustom extends ViewPager {

    public ViewPagerCustom(@NonNull Context context) {
        super(context);
    }

    public ViewPagerCustom(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }

    @Override
    public View focusSearch(int direction) {
        return super.focusSearch(direction);
    }

}
