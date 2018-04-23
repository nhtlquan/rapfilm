package com.gmail.slimtbb.tvcomponents.helpers;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tu NM on 022, 06/22.
 */

public abstract class ViewPagerAdapter extends PagerAdapter implements Pager {
    private List<View> mViews;

    private SparseArray<ToDestroy> mToDestroy = new SparseArray<>();


    @Override
    public View getViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public final int getCount() {
        if (mViews == null) {
            int count = getItemCount();
            mViews = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                mViews.add(null);
            }
            mToDestroy = new SparseArray<>();
        }
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ToDestroy toDestroy = mToDestroy.get(position);
        if (toDestroy != null) {
            mToDestroy.remove(position);
            return toDestroy.object;
        }
        View view = getItem(container, position);
        container.addView(view);
        mViews.set(position, view);
        return view;
    }

    public abstract View getItem(ViewGroup container, int position);

    public abstract int getItemCount();

    public void clearData(){
        mViews.clear();
        mViews = null;
        mToDestroy.clear();
        mToDestroy = null;
    };

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int lastPos = getItemCount()-3;
        if (position == 1 || position == lastPos) {
            mToDestroy.put(position, new ToDestroy(container, lastPos,
                    object));
        } else {
            container.removeView((View) object);
            mViews.set(position, null);
        }
    }

    static class ToDestroy {
        ViewGroup container;
        int position;
        Object object;

        public ToDestroy(ViewGroup container, int position, Object object) {
            this.container = container;
            this.position = position;
            this.object = object;
        }
    }
}
