package com.example.rapfilm.common;

import android.text.TextUtils;
import android.util.Log;

import com.example.rapfilm.TtmlNode;
import com.example.rapfilm.model.Cover;
import com.example.rapfilm.model.Favourite;
import com.example.rapfilm.model.Film;
import com.example.rapfilm.model.Poster;
import com.example.rapfilm.model.Recent;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static boolean checkJson(JsonElement jsonElement) {
        return jsonElement == null ? false : jsonElement.getAsJsonObject().get("status").getAsBoolean();
    }

    public static List<Recent> parserRecents(JsonElement jsonElement) {
        List<Recent> recents = new ArrayList();
        JsonArray arrRecentJson = jsonElement.getAsJsonObject().get("data").getAsJsonArray();
        for (int i = 0; i < arrRecentJson.size(); i++) {
            JsonObject jsonObject = arrRecentJson.get(i).getAsJsonObject();
            JsonElement filmJsonElement = jsonObject.get("data");
            JsonElement recentElement = jsonObject.get("ext");
            Gson gson = new Gson();
            Film film = (Film) gson.fromJson(filmJsonElement, Film.class);
            Recent recent = (Recent) gson.fromJson(recentElement, Recent.class);
            recent.setPoster(film.getPoster().getLink());
            recent.setName(film.getName());
            recent.setName_vi(film.getName_vi());
            if (recent.getThumb() == null) {
                recent.setThumb("empty");
            }
            recents.add(recent);
        }
        return recents;
    }

    public static List<Film> parserSearch(JsonElement jsonElement) {
        List<Film> films = new ArrayList();
        JsonArray arrFilms = jsonElement.getAsJsonObject().get("items").getAsJsonArray();
        Log.e("arr", "arr search size = " + arrFilms.size());
        for (int i = 0; i < arrFilms.size(); i++) {
            try {
                JsonObject jsonObject = arrFilms.get(i).getAsJsonObject();
                Film film = new Film();
                if (jsonObject != null) {
                    if (jsonObject.has("type") && !TextUtils.isEmpty(jsonObject.get("type").getAsString())) {
                        film.setType(jsonObject.get("type").getAsString());
                    }
                    if (jsonObject.has("year") && !TextUtils.isEmpty(jsonObject.get("year").getAsString())) {
                        film.setYear(jsonObject.get("year").getAsString());
                    }
                    if (jsonObject.has(TtmlNode.ATTR_ID) && !TextUtils.isEmpty(jsonObject.get(TtmlNode.ATTR_ID).getAsString())) {
                        film.setId(jsonObject.get(TtmlNode.ATTR_ID).getAsString());
                    }
                    if (jsonObject.has("name") && !TextUtils.isEmpty(jsonObject.get("name").getAsString())) {
                        film.setName(jsonObject.get("name").getAsString());
                    }
                    if (jsonObject.has("name_vi") && !TextUtils.isEmpty(jsonObject.get("name_vi").getAsString())) {
                        film.setName_vi(jsonObject.get("name_vi").getAsString());
                    }
                    if (jsonObject.has("poster")) {
                        JsonObject jsonPoster = jsonObject.get("poster").getAsJsonObject();
                        Poster poster = new Poster();
                        if (jsonPoster != null) {
                            if (jsonPoster.has("original") && !TextUtils.isEmpty(jsonPoster.get("original").getAsString())) {
                                poster.setOriginal(jsonPoster.get("original").getAsString());
                            }
                            if (jsonPoster.has("160") && !TextUtils.isEmpty(jsonPoster.get("160").getAsString())) {
                                poster.setS160(jsonPoster.get("160").getAsString());
                            }
                            if (jsonPoster.has("320") && !TextUtils.isEmpty(jsonPoster.get("320").getAsString())) {
                                poster.setS320(jsonPoster.get("320").getAsString());
                            }
                            if (jsonPoster.has("480") && !TextUtils.isEmpty(jsonPoster.get("480").getAsString())) {
                                poster.setS480(jsonPoster.get("480").getAsString());
                            }
                            if (jsonPoster.has("640") && !TextUtils.isEmpty(jsonPoster.get("640").getAsString())) {
                                poster.setS640(jsonPoster.get("640").getAsString());
                            }
                        }
                        film.setPoster(poster);
                    }
                    if (jsonObject.has("cover")) {
                        JsonObject jsonCover = jsonObject.get("cover").getAsJsonObject();
                        Cover poster2 = new Cover();
                        if (jsonCover != null) {
                            if (jsonCover.has("original") && !TextUtils.isEmpty(jsonCover.get("original").getAsString())) {
                                poster2.setOriginal(jsonCover.get("original").getAsString());
                            }
                            if (jsonCover.has("320") && !TextUtils.isEmpty(jsonCover.get("320").getAsString())) {
                                poster2.setS320(jsonCover.get("320").getAsString());
                            }
                            if (jsonCover.has("640") && !TextUtils.isEmpty(jsonCover.get("640").getAsString())) {
                                poster2.setS640(jsonCover.get("640").getAsString());
                            }
                            if (jsonCover.has("960") && !TextUtils.isEmpty(jsonCover.get("960").getAsString())) {
                                poster2.setS960(jsonCover.get("960").getAsString());
                            }
                        }
                        film.setCover(poster2);
                    }
                }
                films.add(film);
            } catch (Exception e) {
                Log.e("exeption", "exeption = " + e.toString());
            }
        }
        return films;
    }

    public static List<Favourite> parserBookmarks(JsonElement jsonElement) {
        List<Favourite> bookmarks = new ArrayList();
        JsonArray arrBookmarkJson = jsonElement.getAsJsonObject().get("data").getAsJsonArray();
        for (int i = 0; i < arrBookmarkJson.size(); i++) {
            JsonObject jsonObject = arrBookmarkJson.get(i).getAsJsonObject();
            JsonElement filmJsonElement = jsonObject.get("data");
            JsonElement bookmarkElement = jsonObject.get("ext");
            Film film = (Film) new Gson().fromJson(filmJsonElement, Film.class);
            Favourite bookmark = new Favourite();
            bookmark.setId_film(film.getId());
            bookmark.setName(film.getName());
            bookmark.setThumb(film.getPoster().getLink());
            bookmark.setName_vi(film.getName_vi());
            bookmarks.add(bookmark);
        }
        return bookmarks;
    }
}
