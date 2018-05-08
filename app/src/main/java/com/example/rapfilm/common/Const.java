package com.example.rapfilm.common;

import com.example.rapfilm.TtmlNode;

public class Const {
    public static final String ACTION_BANNER = "banner click";
    public static final String ACTION_CLICK_DOWNLOAD = "action_click_download";
    public static final String ACTION_CLICK_SHARE = "action_click_share";
    public static final String ACTION_CLICK_SUBCRIBE = "action_click_subcribe";
    public static final String ACTION_CLICK_TRAILER = "action_click_trailer";
    public static final String ACTION_DOWNLOAD = "download click";
    public static final String ACTION_EXTEND = "action_extend";
    public static final String ACTION_FAVORITE = "favorite click";
    public static final String ACTION_FB_MESS = "facebook mess click";
    public static final String ACTION_FEEDBACK = "action_feedback";
    public static final String ACTION_FILM_RELATED = "related film click";
    public static final String ACTION_INVITE = "action_invite";
    public static final String ACTION_LOGIN = "action_login";
    public static final String ACTION_LOGIN_FACEBOOK = "action_login_facebook";
    public static final String ACTION_LOGIN_GOOGLE = "action_login_google";
    public static final String ACTION_LOGOUT = "action_logout";
    public static final String ACTION_PLAY = "play click";
    public static final String ACTION_PLAY_RECENT = "recent film click";
    public static final String ACTION_SEARCH = "search film click";
    public static final String ACTION_SERVER = "choose server click";
    public static final String ACTION_SERVEY = "action_click_servey";
    public static final String ACTION_SETTING = "action_setting";
    public static final String ACTION_SHARE = "share click";
    public static final String ACTION_SUBCRIBE = "subcribe click";
    public static final String ACTION_TRAILER = "trailer click";
    public static final String ACTION_VIP = "vip red button click";
    public static final String FEATURE_EVENT_BANNER = "feature_click_banner";
    public static final String FEATURE_EVENT_FACEBOOK = "feature_click_facebook";
    public static final String FEATURE_EVENT_MESSENGER = "feature_click_messenger";
    public static final String FEATURE_EVENT_MORE_COLLECTION = "feature_click_more_collecntion ";
    public static final String FEATURE_EVENT_RECENT = "feature_click_recent";
    public static final String FEATURE_EVENT_REQUEST = "feature_click_request";
    public static final String LIBRARY_SCREEN_DOWNLOAD = "screen_library_download";
    public static final String LIBRARY_SCREEN_FAVOURITE = "screen_library_favourite";
    public static final String LIBRARY_SCREEN_NOTIFICATION = "screen_library_notification";
    public static final String LIBRARY_SCREEN_RECENT = "screen_library_recent";
    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MIN_LENGTH_USERNAME = 4;
    public static final String MOVIES_SCREEN_ANIME = "screen_movies_anime";
    public static final String MOVIES_SCREEN_MOVIE = "screen_movies_movie";
    public static final String MOVIES_SCREEN_TVSERIS = "screen_movies_tvserris";
    public static final String MOVIES_SCREEN_TVSHOW = "screen_movies_tvshow";
    public static final int NUMBER_RETRY_WHEN_CALL_API_FAIL = 7;
    public static final String TOPCHART_SCREEN_CATEGORY = "screen_topcharts_category";
    public static final String TOPCHART_SCREEN_IMDB = "screen_topcharts_imdb";
    public static final String TOPCHART_SCREEN_TOPNEW = "screen_topcharts_topnew";
    public static final String TOPCHART_SCREEN_TOPVIEW = "screen_topcharts_topview";
    private static final String[] chars = new String[]{"$", "$", "$", "x", "e", "m", "|", TtmlNode.TAG_P, "h", "i", "m", "|", "c", "a", "t", "|", "t", "r", "y", "m", "*", "*", "1", "2", "3"};

    public static String getKey() {
        StringBuilder builder = new StringBuilder();
        for (String str : chars) {
            builder.append(str);
        }
        return builder.toString();
    }
}
