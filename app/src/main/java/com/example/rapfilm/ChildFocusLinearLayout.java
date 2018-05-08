package com.example.rapfilm;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Tu NM on 029, 06/29.
 */

public class ChildFocusLinearLayout extends LinearLayout {
    public ChildFocusLinearLayout(Context context) {
        super(context);
    }

    public ChildFocusLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildFocusLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private OnRequestChildFocusListener listener;

    public void setOnRequestChildFocusListener(OnRequestChildFocusListener listener) {
        this.listener = listener;
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        View nextFocusedView = null;
        if (listener != null)
            nextFocusedView = listener.onRequestChildFocus(child, focused);
        if (nextFocusedView == null)
            super.requestChildFocus(child, focused);
        else
            super.requestChildFocus(nextFocusedView, focused);
    }

    public interface OnRequestChildFocusListener {
        View onRequestChildFocus(View child, View focused);
    }
}
