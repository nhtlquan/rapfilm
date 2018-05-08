package com.example.rapfilm.network;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.rapfilm.TtmlNode;
import com.example.rapfilm.common.Const;
import com.example.rapfilm.common.DeviceUtils;
import com.example.rapfilm.common.FilmPreferences;
import com.example.rapfilm.model.Page;
import com.example.rapfilm.model.Section;
import com.example.rapfilm.model.Type;
import com.google.gson.JsonElement;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import io.reactivex.Observable;
import retrofit2.Callback;

public class FilmApi {
    public static Observable<JsonElement> genres(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().genres(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> discover(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().discover(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> topVote(Context context, String categoryId, Type type, int start, int limit) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put(TtmlNode.START, Integer.toString(start));
        fields.put("limit", Integer.toString(limit));
        if (categoryId != null && categoryId.length() > 0) {
            fields.put("genre_id", categoryId);
        }
        if (type != Type.NONE) {
            fields.put("type", type.toString());
        }
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().topVote(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> getPackage(Context context, int isBank) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("isBank", String.valueOf(isBank));
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().getPackage(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> register(Context context, String userName, String email, String password) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("username", userName);
        fields.put("email", email);
        fields.put("password", password);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().registerUser(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> newestCategory(Context context, String categoryId, String type, int start, int limit) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put(TtmlNode.START, Integer.toString(start));
        fields.put("limit", Integer.toString(limit));
        if (categoryId != null && categoryId.length() > 0) {
            fields.put("genre_id", categoryId);
        }
        if (!type.equals("none")) {
            fields.put("type", type);
        }
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().newestCategory(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> topDownload(Context context, String categoryId, Type type, int start, int limit) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put(TtmlNode.START, Integer.toString(start));
        fields.put("limit", Integer.toString(limit));
        if (categoryId != null && categoryId.length() > 0) {
            fields.put("genre_id", categoryId);
        }
        if (type != Type.NONE) {
            fields.put("type", type.toString());
        }
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().topDownload(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> topIMDb(Context context, String categoryId, Type type, int start, int limit) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put(TtmlNode.START, Integer.toString(start));
        fields.put("limit", Integer.toString(limit));
        if (categoryId != null && categoryId.length() > 0) {
            fields.put("genre_id", categoryId);
        }
        if (type != Type.NONE) {
            fields.put("type", type.toString());
        }
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().topIMDb(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> search(Context context, @NonNull String keyword, String categoryId, Type type, int start, int limit) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("keyword", keyword);
        fields.put(TtmlNode.START, Integer.toString(start));
        fields.put("limit", Integer.toString(limit));
        if (categoryId != null && categoryId.length() > 0) {
            fields.put("genre_id", categoryId);
        }
        if (type != Type.NONE) {
            fields.put("type", type.toString());
        }
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getSearchRequest().search(keyword);
    }

    public static Observable<JsonElement> detailFilm(Context context, @NonNull String filmId) {
        HashMap<String, String> fields = getBaseParams(context);
        FilmPreferences preferences = FilmPreferences.getInstance();
        fields.put("movie_id", filmId);
        fields.put("referrer", "search");
        fields.put("hash", getHash(fields));
        int versionCode = 1;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return FilmApiRequest.getInstance().detailFilm(getToken(), FilmPreferences.getInstance().getLanguage().toString(), 13, fields);
    }

    public static  Observable<JsonElement> collection(Context context, String collectionId, int start, int limit) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("collection_id", collectionId);
        fields.put(TtmlNode.START, Integer.toString(start));
        fields.put("limit", Integer.toString(limit));
        fields.put("hash", getHash(fields));
     return   FilmApiRequest.getInstance().collection(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static void reportError(Context context, String filmId, String episodeId, String des, Callback<JsonElement> callback) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("fid", filmId);
        fields.put("episodesId", episodeId);
        fields.put("description", des);
        fields.put("hash", getHash(fields));
        FilmApiRequest.getInstance().reportError(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields, callback);
    }

    public static Observable<JsonElement> download(Context context, @NonNull String accessToken, String filmId, String episodeId, boolean isView, int order) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("movie_id", filmId);
        fields.put("order", String.valueOf(order));
        fields.put("type", isView ? "view" : "download");
        if (episodeId != null) {
            fields.put("episode_id", episodeId);
        }
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().download(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> downloadFilm(Context context, @NonNull String accessToken, String filmId, String episodeId, boolean isView, String server) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("movie_id", filmId);
        fields.put("type", isView ? "view" : "download");
        if (!TextUtils.isEmpty(server)) {
            fields.put("server", server);
        }
        if (episodeId != null) {
            fields.put("episode_id", episodeId);
        }
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().download(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> getSurvey(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().getSurvey(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> advertise(Context context, Type type, Section section, Page page) {
        HashMap<String, String> fields = getBaseParams(context);
        if (type != Type.NONE) {
            fields.put("type", type.toString());
        }
        fields.put("section", section.toString());
        fields.put("page", page.toString());
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().advertise(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> getListFavourite(Context context, int start, int limit) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put(TtmlNode.START, Integer.toString(start));
        fields.put("limit", Integer.toString(limit));
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().getListFavourite(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> getUserRecent(Context context, int start, int limit) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put(TtmlNode.START, Integer.toString(start));
        fields.put("limit", Integer.toString(limit));
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().getUserRecent(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> comment(Context context, String filmId, String comment) {
        HashMap<String, String> fields = getBaseParams(context);
        FilmPreferences preferences = FilmPreferences.getInstance();
        fields.put("movie_id", filmId);
        fields.put(ClientCookie.COMMENT_ATTR, comment);
        fields.put("user_id", preferences.getUserId());
        fields.put("username", preferences.getUserName());
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().comment(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> getComments(Context context, String filmId, int start, int limit) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("movie_id", filmId);
        fields.put(TtmlNode.START, Integer.toString(start));
        fields.put("limit", Integer.toString(limit));
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().getComments(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> rate(Context context, String filmId, int mark) {
        HashMap<String, String> fields = getBaseParams(context);
        FilmPreferences preferences = FilmPreferences.getInstance();
        fields.put("movie_id", filmId);
        fields.put("mark", Integer.toString(mark));
        fields.put("user_id", preferences.getUserId());
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().rate(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static void userSetting(Context context, String userId, String content, boolean isRecent, Callback<JsonElement> callback) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("user_id", userId);
        fields.put("favourite", content);
        fields.put("hash", getHash(fields));
        FilmApiRequest.getInstance().userSetting(FilmPreferences.getInstance().getAccessToken(), FilmPreferences.getInstance().getLanguage().toString(), fields, callback);
    }

    public static void userRecent(Context context, String userId, String content, Callback<JsonElement> callback) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("user_id", userId);
        fields.put("recent", content);
        fields.put("hash", getHash(fields));
        Log.e("user id", "user id = " + userId);
        Log.e("recent", "recent = " + content);
        Log.e("hash", "hash = " + getHash(fields));
        FilmApiRequest.getInstance().userRecent(FilmPreferences.getInstance().getAccessToken(), FilmPreferences.getInstance().getLanguage().toString(), fields, callback);
    }

    public static Observable<JsonElement> recentDetail(Context context, String movieId) {
        HashMap<String, String> fields = getBaseParams(context);
        FilmPreferences preferences = FilmPreferences.getInstance();
        fields.put("user_id", preferences.getUserId());
        fields.put("movie_id", movieId);
        fields.put("hash", getHash(fields));
        Log.e("movieId", "movie_id = " + movieId + "&user_id=" + preferences.getUserId() + "&hash=" + getHash(fields));
        return FilmApiRequest.getInstance().recentDetail(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> genCode(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().genCode(FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static void likeComment(Context context, String commentId, Callback<JsonElement> callback) {
        HashMap<String, String> fields = getBaseParams(context);
        FilmPreferences preferences = FilmPreferences.getInstance();
        fields.put("user_id", preferences.getUserId());
        fields.put("comment_id", commentId);
        fields.put("hash", getHash(fields));
        FilmApiRequest.getInstance().likeComment(preferences.getAccessToken(), FilmPreferences.getInstance().getLanguage().toString(), fields, callback);
    }

    public static void addPackageFree(Context context, Callback<JsonElement> callback) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("user_id", FilmPreferences.getInstance().getUserId());
        fields.put("hash", getHash(fields));
        FilmApiRequest.getInstance().addPackageFree(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields, callback);
    }

    public static void transaction(Context context, String transactionJson, Callback<JsonElement> callback) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("user_id", FilmPreferences.getInstance().getUserId());
        fields.put("transaction_data", transactionJson);
        fields.put("hash", getHash(fields));
        FilmApiRequest.getInstance().transaction(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields, callback);
    }

    public static void getPackages(Context context, Callback<JsonElement> callback) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        FilmApiRequest.getInstance().getPackage(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static void getUserPackageInfo(Context context, Callback<JsonElement> callback) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("user_id", FilmPreferences.getInstance(context).getUserId());
        fields.put("hash", getHash(fields));
        FilmApiRequest.getInstance().getUserPackageInfo(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields, callback);
    }

    public static void historyTransaction(Context context, Callback<JsonElement> callback) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("user_id", FilmPreferences.getInstance(context).getUserId());
        fields.put("hash", getHash(fields));
        FilmApiRequest.getInstance().historyTransaction(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields, callback);
    }

    public static void deleteRecents(Context context, String ids, Callback<JsonElement> callback) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("user_id", FilmPreferences.getInstance(context).getUserId());
        fields.put("movie_ids", FilmPreferences.getInstance(context).getUserId());
        fields.put("hash", getHash(fields));
        FilmApiRequest.getInstance().deleteRecents(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields, callback);
    }

    public static Observable<JsonElement> checkLogout(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().checkOut(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> loginFacebook(Context context, String facebookToken) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("facebook_access_token", facebookToken);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().loginFacebook(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> loginGoogle(Context context, String googleToken) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("google_access_token", googleToken);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().loginGoogle(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> buyPackage(Context context, String amount, String type, String transaction_id, String timestamp) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("amount", amount);
        fields.put("transaction_id", transaction_id);
        fields.put("timestamp", timestamp);
        fields.put("type", type);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().buyPackage(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> login(Context context, String username, String password) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("username", username);
        fields.put("password", password);
        fields.put("hash", getHash(fields));
        Log.e("username", "username = " + username + "&password=" + password + "&hash=" + getHash(fields));
        return FilmApiRequest.getInstance().login(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> getUserInfo(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().getUserInfo(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> updateUserInfo(Context context, HashMap<String, String> maps) {
        HashMap<String, String> fields = getBaseParams(context);
        for (String key : maps.keySet()) {
            fields.put(key, maps.get(key));
        }
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().updateUserInfo(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> textAds(Context context, String filmId) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("movie_id", filmId);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().textAds(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> subscribe(Context context, String filmId) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("movie_id", filmId);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().subscribe(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> sendImpression(Context context, String campaign_id, String type) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("campaign_id", campaign_id);
        fields.put("type", type);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().sendImpression(fields);
    }

    public static Observable<JsonElement> getCampaign(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().getCampaign(getToken(), fields);
    }

    public static Observable<JsonElement> getConfigAds(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().configAds(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> sendPromocode(Context context, String code) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("code", code);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().sendPromocode(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> getHistoryTransition(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().getTransitionHistory(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> unsubscribe(Context context, String filmId) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("movie_id", filmId);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().unsubscribe(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> init(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().init(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> feedback(Context context, String title, String content) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("title", title);
        fields.put("content", content);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().feedback(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> verifyInvite(Context context, String invite_code) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("invite_code", invite_code);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().verifyInvite(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> checkVip(Context context) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().checkVip(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    public static Observable<JsonElement> analyticsAds(Context context, String filmId) {
        HashMap<String, String> fields = getBaseParams(context);
        fields.put("ads_id", filmId);
        fields.put("type", "click");
        fields.put("hash", getHash(fields));
        return FilmApiRequest.getInstance().analyticAds(getToken(), FilmPreferences.getInstance().getLanguage().toString(), fields);
    }

    private static String getToken() {
        String token = FilmPreferences.getInstance().getAccessToken();
        return token.length() == 0 ? "e8051ef21638e78755f0511ad14d8c0c" : token;
    }

    private static HashMap<String, String> getBaseParams(Context context) {
        HashMap<String, String> params = new HashMap();
        params.put("device_id", DeviceUtils.getDeviceImei(context));
        params.put("device_os", "android");
        params.put("device_type", DeviceUtils.getTypeDevice(context));
        params.put("time", Long.toString(System.currentTimeMillis() / 1000));
        params.put("client_ip", DeviceUtils.getIPAddress(true));
        Log.e("device_id", "device_id =" + DeviceUtils.getDeviceImei(context) + "&device_os=android&device_type=" + DeviceUtils.getTypeDevice(context) + "&time=" + Long.toString(System.currentTimeMillis() / 1000) + "&client_ip=" + DeviceUtils.getIPAddress(true));
        return params;
    }

    private static String getMD5(String param) {
        return new String(Hex.encodeHex(DigestUtils.md5(param)));
    }

    private static String getHash(HashMap<String, String> map) {
        String[] params = getKeysSorted(map);
        StringBuilder beforeHash = new StringBuilder();
        beforeHash.append(Const.getKey());
        for (String param : params) {
            beforeHash.append((String) map.get(param));
        }
        return getMD5(beforeHash.toString());
    }

    private static String[] getKeysSorted(HashMap<String, String> map) {
        Set<String> set = map.keySet();
        String[] params = new String[set.size()];
        set.toArray(params);
        Arrays.sort(params);
        return params;
    }
}
