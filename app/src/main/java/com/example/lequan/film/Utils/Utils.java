package com.example.lequan.film.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.example.lequan.film.model.Film;
import com.example.lequan.film.model.ListFilm;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
    public final static String SHARED_PREFERENCES_FAVORITE = "SHARED_PREFERENCES_FAVORITE";
    public final static String SHARED_PREFERENCES_KEY_FAVORITE = "SHARED_PREFERENCES_KEY_FAVORITE";
    public final static String SHARED_PREFERENCES_HISTORY = "SHARED_PREFERENCES_HISTORY";
    public final static String SHARED_PREFERENCES_KEY_HISTORY = "SHARED_PREFERENCES_KEY_HISTORY";

    public static int convertDpToPixel(Context ctx, int dp) {
        return Math.round(((float) dp) * ctx.getResources().getDisplayMetrics().density);
    }

    public static String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            return new String(bytes);
        } catch (IOException e) {
            return null;
        }
    }

    public static void saveFavorite(Context context, ListFilm film) {
        Gson gson = new Gson();
        String userInfoListJsonString = gson.toJson(film);
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FAVORITE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_KEY_FAVORITE, userInfoListJsonString);
        editor.commit();
    }

    public static ArrayList<Film> getFavorite(Context context) {
        ArrayList<Film> films = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FAVORITE, MODE_PRIVATE);
        String userInfoListJsonString = sharedPreferences.getString(SHARED_PREFERENCES_KEY_FAVORITE, "");
        Gson gson = new Gson();
        ListFilm listFilm = gson.fromJson(userInfoListJsonString, ListFilm.class);
        if (listFilm != null)
            films = listFilm.getFilms();
        return films;
    }
    public static void saveHistory(Context context, ListFilm film) {
        Gson gson = new Gson();
        String userInfoListJsonString = gson.toJson(film);
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_HISTORY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_KEY_HISTORY, userInfoListJsonString);
        editor.commit();
    }

    public static ArrayList<Film> getHistory(Context context) {
        ArrayList<Film> films = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_HISTORY, MODE_PRIVATE);
        String userInfoListJsonString = sharedPreferences.getString(SHARED_PREFERENCES_KEY_HISTORY, "");
        Gson gson = new Gson();
        ListFilm listFilm = gson.fromJson(userInfoListJsonString, ListFilm.class);
        if (listFilm != null)
            films = listFilm.getFilms();
        return films;
    }

    public static Uri getResourceUri(Context context, int resID) {
        return Uri.parse("android.resource://" + context.getResources().getResourcePackageName(resID) + '/' + context.getResources().getResourceTypeName(resID) + '/' + context.getResources().getResourceEntryName(resID));
    }
}
