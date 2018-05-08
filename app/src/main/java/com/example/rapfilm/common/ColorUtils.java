package com.example.rapfilm.common;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import java.util.Random;

public class ColorUtils {
    public static int brightenColor(@ColorInt int color, @IntRange(from = 0, to = 100) int percent) {
        if (percent < 0) {
            percent = 0;
        }
        if (percent > 100) {
            percent = 100;
        }
        float factor = ((float) percent) / 100.0f;
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb((int) (((float) red) + (((float) (255 - red)) * factor)), (int) (((float) green) + (((float) (255 - green)) * factor)), (int) (((float) blue) + (((float) (255 - blue)) * factor)));
    }

    public static int darkenColor(@ColorInt int color, @IntRange(from = 0, to = 100) int percent) {
        if (percent < 0) {
            percent = 0;
        }
        if (percent > 100) {
            percent = 100;
        }
        float factor = 1.0f - (((float) percent) / 100.0f);
        return Color.rgb((int) (((float) Color.red(color)) * factor), (int) (((float) Color.green(color)) * factor), (int) (((float) Color.blue(color)) * factor));
    }

    public static int alphaColor(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        if (alpha < 0 || alpha > 255) {
            alpha = 255;
        }
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    public static int getRandomDarkColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(100), random.nextInt(100), random.nextInt(100));
    }

    private static int getIntRamdom(int start, int end) {
        return (end - start) + (new Random().nextInt() % (end - start));
    }
}
