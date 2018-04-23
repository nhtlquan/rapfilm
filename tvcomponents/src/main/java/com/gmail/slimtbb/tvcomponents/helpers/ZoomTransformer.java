package com.gmail.slimtbb.tvcomponents.helpers;

import android.support.v4.view.ViewPager;
import android.view.View;


public class ZoomTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private ViewPager mViewPager;
    private Pager mAdapter;
    private float mLastOffset;

    public ZoomTransformer(ViewPager viewPager, Pager adapter) {
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        mAdapter = adapter;
    }

    @Override
    public void transformPage(View page, float position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realCurrentPosition;
        int nextPosition;
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        // Avoid crash on overscroll
        if (nextPosition > mAdapter.getCount() - 1
                || realCurrentPosition > mAdapter.getCount() - 1) {
            return;
        }

        View currentCard = mAdapter.getViewAt(realCurrentPosition);

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            float scaleFactor = (float) (1 + 0.1 * (1 - realOffset));
//            currentCard.animate().withLayer().scaleX(scaleFactor).scaleY(scaleFactor).setDuration(150).start();
            currentCard.setScaleX(scaleFactor);
            currentCard.setScaleY(scaleFactor);
        }

        View nextCard = mAdapter.getViewAt(nextPosition);

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            float scaleFactor = (float) (1 + 0.1 * (realOffset));
//            nextCard.animate().withLayer().scaleX(scaleFactor).scaleY(scaleFactor).setDuration(150).start();
            nextCard.setScaleX(scaleFactor);
            nextCard.setScaleY(scaleFactor);
        }

        mLastOffset = positionOffset;
    }


    @Override
    public void onPageSelected(int position) {
        // skip fake page (first), go to last page
        if (position == 0) {
            mViewPager.setCurrentItem(mAdapter.getCount()-2, false);
        }
        // skip fake page (last), go to first page
        if (position == mAdapter.getCount()-1) {
            mViewPager.setCurrentItem(1, false); //notice how this jumps to position 1, and not position 0. Position 0 is the fake page!
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
