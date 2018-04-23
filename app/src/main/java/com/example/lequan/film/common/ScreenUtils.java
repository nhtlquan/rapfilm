package com.example.lequan.film.common;

import android.content.Context;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils {
    public static int getWidthScreen(Context context) {
        Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (VERSION.SDK_INT < 17) {
            return display.getWidth();
        }
        Point point = new Point();
        display.getRealSize(point);
        return point.x;
    }

    public static int getHeightScreen(Context context) {
        Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (VERSION.SDK_INT < 17) {
            return display.getWidth();
        }
        Point point = new Point();
        display.getRealSize(point);
        return point.y;
    }
}
