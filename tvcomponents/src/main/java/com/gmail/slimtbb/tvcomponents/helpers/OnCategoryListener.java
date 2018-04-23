package com.gmail.slimtbb.tvcomponents.helpers;

import android.support.v7.widget.RecyclerView;

public interface OnCategoryListener {
    void onCategoryInstantSelectChange(RecyclerView.ViewHolder viewHolder, int pos, boolean selected);
    void onCategoryDebounceSelectChange(RecyclerView.ViewHolder viewHolder, int pos, boolean selected);
    void onCategoryClick(int pos);

}