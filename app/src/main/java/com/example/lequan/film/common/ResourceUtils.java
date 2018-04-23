package com.example.lequan.film.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;

public class ResourceUtils {
    @TargetApi(23)
    public static int getColor(Context context, int resId) {
        return VERSION.SDK_INT >= 23 ? context.getColor(resId) : context.getResources().getColor(resId);
    }

    public static Drawable getDrawable(Context context, int resId) {
        return VERSION.SDK_INT >= 21 ? context.getDrawable(resId) : context.getResources().getDrawable(resId);
    }
}
