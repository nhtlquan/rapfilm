package com.gmail.slimtbb.tvcomponents.helpers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Tu NM on 5/6/2017.
 */

public class OnAddFocusablesHelper {
    public static boolean forLeftList(RecyclerView recyclerView, ArrayList<View> views, int direction, PositionHolder positionHolder) {
//        Log.d("OnAddFocusablesHelper", "forLeftList");
        if (recyclerView.hasFocus() && direction == View.FOCUS_RIGHT) {
            return savePosition(recyclerView, positionHolder);
        } else if (!recyclerView.hasFocus() && direction == View.FOCUS_LEFT) {
            return setPosition(recyclerView, views, positionHolder);
        } else if (!recyclerView.hasFocus() && (direction == View.FOCUS_DOWN || direction == View.FOCUS_UP)) {
            return setPosition(recyclerView, views, positionHolder);
        }
        return false;
    }

    public static boolean forRightList(RecyclerView recyclerView, ArrayList<View> views, int direction, PositionHolder positionHolder) {
        if (recyclerView.hasFocus() && direction == View.FOCUS_LEFT) {
            return savePosition(recyclerView, positionHolder);
        } else if (!recyclerView.hasFocus() && direction == View.FOCUS_RIGHT) {
            return setPosition(recyclerView, views, positionHolder);
        } else if (!recyclerView.hasFocus() && (direction == View.FOCUS_DOWN || direction == View.FOCUS_UP)) {
            return setPosition(recyclerView, views, positionHolder);
        }
        return false;
    }

    public static boolean forRightGrid(RecyclerView recyclerView, ArrayList<View> views, int direction, PositionHolder holder) {
        if (recyclerView.hasFocus() && direction == View.FOCUS_LEFT) {
            return savePosition(recyclerView, holder);
        } else if (!recyclerView.hasFocus() && direction == View.FOCUS_RIGHT) {
            return setPosition(recyclerView, views, holder);
        }
        //To avoid change focus when the current position of this recyclerview = coordinate of the last position of the left recyclerview
        else if ((direction == View.FOCUS_DOWN || direction == View.FOCUS_UP) && views.size() > 0) {
            views.clear();
            return true;
        }
        return false;
    }


    public static boolean forTopGrid(RecyclerView recyclerView, ArrayList<View> views, int direction, PositionHolder holder) {
        if (recyclerView.hasFocus() && direction == View.FOCUS_DOWN) {
            return savePosition(recyclerView, holder);
        } else if (!recyclerView.hasFocus() && direction == View.FOCUS_UP) {
            return setPosition(recyclerView, views, holder);
        }
        //To avoid change focus when the current position of this recyclerview = coordinate of the last position of the left recyclerview
        else if ((direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT) && views.size() > 0) {
            views.clear();
            return true;
        }
        //return false will lose focus, return true will gain focus
        return false;
    }

    public static boolean forBottomList(RecyclerView recyclerView, ArrayList<View> views, int direction, PositionHolder holder) {
        if (!recyclerView.hasFocus() && direction == View.FOCUS_DOWN) {
            return setPosition(recyclerView, views, holder);
        }
        //To avoid change focus when the current position of this recyclerview = coordinate of the last position of the left recyclerview
        else if ((direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT) && views.size() > 0) {
            views.clear();
            return true;
        }
        return false;
    }

    public static boolean forBottomSimpleList(RecyclerView recyclerView, ArrayList<View> views, int direction, PositionHolder holder) {
        if (!recyclerView.hasFocus() && direction == View.FOCUS_DOWN) {
            return setPosition(recyclerView, views, holder);
        }
        //To avoid change focus when the current position of this recyclerview = coordinate of the last position of the left recyclerview
        else if ((direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT) && views.size() > 0) {
            views.clear();
            return true;
        }
        return false;
    }

    private static boolean savePosition(RecyclerView recyclerView, PositionHolder holder) {
        View focusedChild = recyclerView.getFocusedChild();
        if (focusedChild != null)
            holder.pos = recyclerView.getLayoutManager().getPosition(recyclerView.getFocusedChild());
        return false;
    }

    private static boolean setPosition(RecyclerView recyclerView, ArrayList<View> views, PositionHolder holder) {
        View view = recyclerView.getLayoutManager().findViewByPosition(holder.pos);
        if (view != null)
            views.add(view);
        return true;
    }

    public static class PositionHolder {
        public int pos;

        public PositionHolder() {
            pos = 0;
        }
    }

}
