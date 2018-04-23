package com.example.lequan.film.common;

import android.content.Context;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

public class FilmPreferences {
    private static FilmPreferences filmPreference;
    private final String KEY_ACCESS_TOKEN = "dont_edit_0";
    private final String KEY_ADS_CUSTOM = "dont_edit_44";
    private final String KEY_ADS_PRIORITY = "dont_edit_43";
    private final String KEY_AVATAR = "dont_edit_6";
    private final String KEY_BIRTHDAY = "dont_edit_13";
    private final String KEY_CHECK_LOGOUT = "dont_edit_57";
    private final String KEY_CONFIG_ADCOLONY_ID = "dont_edit_37";
    private final String KEY_CONFIG_ADCOLONY_ZONE_ID = "dont_edit_38";
    private final String KEY_CONFIG_HEYZAP_ID = "dont_edit_33";
    private final String KEY_CONFIG_SHOW_ADCOLONY = "dont_edit_36";
    private final String KEY_CONFIG_SHOW_ADS = "dont_edit_31";
    private final String KEY_CONFIG_SHOW_HEYZAP = "dont_edit_32";
    private final String KEY_CONFIG_SHOW_UNITY = "dont_edit_34";
    private final String KEY_CONFIG_UNITY_ID = "dont_edit_35";
    private final String KEY_CURRENT_SHOWADS = "dont_edit_28";
    private final String KEY_DECR_VIP = "dont_edit_54";
    private final String KEY_DETAILS_IS_FIRST = "dont_edit_53";
    private final String KEY_DETAILS_IS_FIRST_DOWNLOAD = "dont_edit_56";
    private final String KEY_EMAIL = "dont_edit_4";
    private final String KEY_ENDPOINT_SEARCH = "dont_edit_14";
    private final String KEY_EXIT_FILM_REPEAT = "dont_edit_29";
    private final String KEY_EXPIRE_DATE = "dont_edit_3";
    private final String KEY_FIRST_SHOW_TEXT_ADS = "dont_edit_49";
    private final String KEY_FULL_NAME = "dont_edit_7";
    private final String KEY_IS_NEW_TYPE_LOGIN = "dont_edit_11";
    private final String KEY_LANGUAGE = "dont_edit_9";
    private final String KEY_LAST_DEVICE_CAST = "dont_edit_61";
    private final String KEY_LAST_SHOW_TUT = "dont_edit_62";
    private final String KEY_LOGIN_FIRST = "dont_edit_19";
    private final String KEY_NOTIFY_3G = "dont_edit_10";
    private final String KEY_PHONE = "dont_edit_22";
    private final String KEY_REALTIME_RUN = "dont_edit_58";
    private final String KEY_REFRESH_TOKEN = "dont_edit_5";
    private final String KEY_SAVE_REVIEW = "dont_edit_18";
    private final String KEY_SHOW_ADS = "dont_edit_21";
    private final String KEY_SHOW_COMPLETE = "dont_edit_41";
    private final String KEY_SHOW_DIALOG_DETAILS = "dont_edit_39";
    private final String KEY_SHOW_DIALOG_OFFLINE = "dont_edit_30";
    private final String KEY_SHOW_DURATION = "dont_edit_40";
    private final String KEY_SHOW_TEXT_ADS = "dont_edit_48";
    private final String KEY_SHOW_TUTORIAL_CAST = "dont_edit_60";
    private final String KEY_SWIPE_CHANGE_BRIGHTNESS = "dont_edit_16";
    private final String KEY_SWIPE_CHANGE_VOLUMN = "dont_edit_15";
    private final String KEY_SWIPE_SEEK = "dont_edit_17";
    private final String KEY_SYN_LOGIN = "dont_edit_8";
    private final String KEY_TEXT_ADS_DURATION = "dont_edit_47";
    private final String KEY_TEXT_ADS_START = "dont_edit_46";
    private final String KEY_TOTAL_VIEW_VIP = "dont_edit_50";
    private final String KEY_USER_ID = "dont_edit_2";
    private final String KEY_USER_INVITE = "dont_edit_24";
    private final String KEY_USER_ISVIP = "dont_edit_25";
    private final String KEY_USER_NAME = "dont_edit_1";
    private final String KEY_USER_PASS = "dont_edit_26";
    private final String KEY_USER_TYPE = "dont_edit_55";
    private final String KEY_USER_VERIFY = "dont_edit_23";
    private final String KEY_USING_CODE = "dont_edit_27";
    private final String KEY_VIDEO_CUSTOM = "dont_edit_45";
    private final String KEY_VIEW_FIRST = "dont_edit_20";
    private final String KEY_VOLUME_CAST = "dont_edit_59";
    private final String TAG = FilmPreferences.class.getSimpleName();
    private final String TOTAL_FAVOURITE = "dont_edit_52";
    private final String TOTAL_RECENT = "dont_edit_51";

    private FilmPreferences() {
    }

    public static FilmPreferences getInstance(Context context) {
        if (filmPreference == null) {
            filmPreference = new FilmPreferences();
        }
        return filmPreference;
    }

    public static FilmPreferences getInstance() {
        if (filmPreference == null) {
            filmPreference = new FilmPreferences();
        }
        return filmPreference;
    }

    public void setCheckLogout(boolean checkLogout) {
        Hawk.put("dont_edit_57", Boolean.valueOf(checkLogout));
    }

    public boolean isCheckLogout() {
        try {
            return ((Boolean) Hawk.get("dont_edit_57", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_55", Boolean.valueOf(false));
            return true;
        }
    }

    public void countTut(int count) {
        Hawk.put("dont_edit_62", Integer.valueOf(count));
    }

    public int getCountTut() {
        try {
            return ((Integer) Hawk.get("dont_edit_62", Integer.valueOf(0))).intValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_62", Integer.valueOf(0));
            return 0;
        }
    }

    public void setTypeUser(String typeUSer) {
        Hawk.put("dont_edit_55", typeUSer);
    }

    public String getTypeUser() {
        try {
            return (String) Hawk.get("dont_edit_55", "user");
        } catch (Exception e) {
            Hawk.put("dont_edit_55", "user");
            return "user";
        }
    }

    public void setRealTimePlayVideo(int minutes) {
        Hawk.put("dont_edit_58", Integer.valueOf(minutes));
    }

    public int getRealTimePlayVideo() {
        try {
            return ((Integer) Hawk.get("dont_edit_58", Integer.valueOf(0))).intValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_58", Integer.valueOf(0));
            return 0;
        }
    }

    public void setInviteCode(String invite_code) {
        Hawk.put("dont_edit_24", invite_code);
    }

    public void setReview(boolean review) {
        Hawk.put("dont_edit_18", Boolean.valueOf(review));
    }

    public void setLanguage(Language language) {
        Hawk.put("dont_edit_9", language.toString());
    }

    public void setAccessToken(String accessToken) {
        Hawk.put("dont_edit_0", accessToken);
    }

    public void setRefreshToken(String refreshToken) {
        Hawk.put("dont_edit_5", refreshToken);
    }

    public void setEmail(String email) {
        Hawk.put("dont_edit_4", email);
    }

    public void setExpireDate(String expireDate) {
        Hawk.put("dont_edit_3", expireDate);
    }

    public void setUsingCode(boolean usingCode) {
        Hawk.put("dont_edit_27", Boolean.valueOf(usingCode));
    }

    public void setTotalViewVip(int countView) {
        Hawk.put("dont_edit_50", Integer.valueOf(countView));
    }

    public void setFirstDetails(boolean isFirstDetails) {
        Hawk.put("dont_edit_53", Boolean.valueOf(isFirstDetails));
    }

    public boolean isDetailsFist() {
        try {
            return ((Boolean) Hawk.get("dont_edit_53", Boolean.valueOf(true))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_53", Boolean.valueOf(true));
            return false;
        }
    }

    public void setTotalRecent(int recent) {
        Hawk.put("dont_edit_51", Integer.valueOf(recent));
    }

    public void setTotalFavourite(int favourite) {
        Hawk.put("dont_edit_52", Integer.valueOf(favourite));
    }

    public int getTotalViewVVip() {
        try {
            return ((Integer) Hawk.get("dont_edit_50", Integer.valueOf(0))).intValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_50", Integer.valueOf(0));
            return 0;
        }
    }

    public void saveDecrVipDownload(boolean isDecr) {
        Hawk.put("dont_edit_56", Boolean.valueOf(isDecr));
    }

    public boolean isDecrVipDownload() {
        try {
            return ((Boolean) Hawk.get("dont_edit_56", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_56", Boolean.valueOf(false));
            return false;
        }
    }

    public void saveDecrVip(boolean isDecr) {
        Hawk.put("dont_edit_53", Boolean.valueOf(isDecr));
    }

    public boolean isDecrVip() {
        try {
            return ((Boolean) Hawk.get("dont_edit_53", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_53", Boolean.valueOf(false));
            return false;
        }
    }

    public int getTotalRecent() {
        try {
            return ((Integer) Hawk.get("dont_edit_51", Integer.valueOf(0))).intValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_51", Integer.valueOf(0));
            return 0;
        }
    }

    public int getTotalFavourite() {
        try {
            return ((Integer) Hawk.get("dont_edit_52", Integer.valueOf(0))).intValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_52", Integer.valueOf(0));
            return 0;
        }
    }

    public void setUserId(String userId) {
        Hawk.put("dont_edit_2", userId);
    }

    public void setVerify(boolean verify) {
        Hawk.put("dont_edit_23", Boolean.valueOf(verify));
    }

    public void setUserName(String userName) {
        Hawk.put("dont_edit_1", userName);
    }

    public void setFullName(String fullname) {
        Hawk.put("dont_edit_7", fullname);
    }

    public void setAvatar(String avatar) {
        Hawk.put("dont_edit_6", avatar);
    }

    public void setIsVip(boolean isVip) {
        Hawk.put("dont_edit_25", Boolean.valueOf(isVip));
    }

    public void setPhoneNumber(String phone) {
        Hawk.put("dont_edit_22", phone);
    }

    public void setHeyZapId(String heyzapId) {
        Hawk.put("dont_edit_33", heyzapId);
    }

    public void setUnityId(String unityId) {
        Hawk.put("dont_edit_35", unityId);
    }

    public void setAdcolonyId(String adcolonyId) {
        Hawk.put("dont_edit_37", adcolonyId);
    }

    public void setAdcolonyZoneId(String adcolonyZoneID) {
        Hawk.put("dont_edit_38", adcolonyZoneID);
    }

    public void setPhone(String phone) {
        Hawk.put("dont_edit_22", phone);
    }

    public void setBirthday(String birthday) {
        Hawk.put("dont_edit_13", birthday);
    }

    public void setEndpointSearch(String endpoint) {
        Hawk.put("dont_edit_14", endpoint);
    }

    public void setCustomAds(String customAds) {
        Hawk.put("dont_edit_45", customAds);
    }

    public void setPriority(String priority) {
        Hawk.put("dont_edit_43", priority);
    }

    public void setSynLogin(boolean isSynLogin) {
        Hawk.put("dont_edit_8", Boolean.valueOf(isSynLogin));
    }

    public void setNotifyUse3g(boolean notify3g) {
        Hawk.put("dont_edit_10", Boolean.valueOf(notify3g));
    }

    public void setShowAds(boolean isShow) {
        Hawk.put("dont_edit_21", Boolean.valueOf(isShow));
    }

    public void setShowTextAds(boolean isShow) {
        Hawk.put("dont_edit_48", Boolean.valueOf(isShow));
    }

    public void setFirstShowTextAds(boolean isShow) {
        Hawk.put("dont_edit_49", Boolean.valueOf(isShow));
    }

    public void setConfigShowAdas(boolean configShowAds) {
        Hawk.put("dont_edit_31", Boolean.valueOf(configShowAds));
    }

    public void setTextAdsStart(int start) {
        Hawk.put("dont_edit_46", Integer.valueOf(start));
    }

    public void setTextAdsDuration(int duration) {
        Hawk.put("dont_edit_47", Integer.valueOf(duration));
    }

    public void setCustom(boolean custom) {
        Hawk.put("dont_edit_44", Boolean.valueOf(custom));
    }

    public void setComplete(boolean isComplete) {
        Hawk.put("dont_edit_41", Boolean.valueOf(isComplete));
    }

    public void setShowAdsHeyzap(boolean showHeyzap) {
        Hawk.put("dont_edit_32", Boolean.valueOf(showHeyzap));
    }

    public void setShowAdsUnity(boolean showUnity) {
        Hawk.put("dont_edit_34", Boolean.valueOf(showUnity));
    }

    public void setShowAdsAdcolony(boolean showAdcolony) {
        Hawk.put("dont_edit_36", Boolean.valueOf(showAdcolony));
    }

    public void setIsRepeat(boolean isRepeat) {
        Hawk.put("dont_edit_29", Boolean.valueOf(isRepeat));
    }

    public void setShowDialogOff(boolean isShow) {
        Hawk.put("dont_edit_30", Boolean.valueOf(isShow));
    }

    public void setPassword(String pass) {
        Hawk.put("dont_edit_26", pass);
    }

    public void setSwipeChangeVolumn(boolean swipeChangeVolumn) {
        Hawk.put("dont_edit_15", Boolean.valueOf(swipeChangeVolumn));
    }

    public void setShowTutorialCast(boolean isShowTutorialCast) {
        Hawk.put("dont_edit_60", Boolean.valueOf(isShowTutorialCast));
    }

    public void setSwipeChangeBrightness(boolean swipeChangeBrightness) {
        Hawk.put("dont_edit_16", Boolean.valueOf(swipeChangeBrightness));
    }

    public void setLoginFirst(boolean loginFirst) {
        Hawk.put("dont_edit_19", Boolean.valueOf(loginFirst));
    }

    public void setSwipeSeek(boolean swipeSeek) {
        Hawk.put("dont_edit_17", Boolean.valueOf(swipeSeek));
    }

    public boolean isNotifyUse3g() {
        try {
            return ((Boolean) Hawk.get("dont_edit_10", Boolean.valueOf(true))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_10", Boolean.valueOf(true));
            return true;
        }
    }

    public boolean isComplete() {
        try {
            return ((Boolean) Hawk.get("dont_edit_41", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_41", Boolean.valueOf(false));
            return true;
        }
    }

    public boolean isCustom() {
        try {
            return ((Boolean) Hawk.get("dont_edit_44", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_44", Boolean.valueOf(false));
            return true;
        }
    }

    public boolean isShowDialogOff() {
        try {
            return ((Boolean) Hawk.get("dont_edit_30", Boolean.valueOf(true))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_30", Boolean.valueOf(true));
            return true;
        }
    }

    public boolean isVerify() {
        try {
            return ((Boolean) Hawk.get("dont_edit_23", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_23", Boolean.valueOf(false));
            return true;
        }
    }

    public boolean isRepeat() {
        try {
            return ((Boolean) Hawk.get("dont_edit_29", Boolean.valueOf(true))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_29", Boolean.valueOf(true));
            return true;
        }
    }

    public boolean isVip() {
        try {
            return ((Boolean) Hawk.get("dont_edit_25", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_25", Boolean.valueOf(false));
            return true;
        }
    }

    public String getPhoneNumber() {
        try {
            return (String) Hawk.get("dont_edit_22", "");
        } catch (Exception e) {
            return "";
        }
    }

    public String getPriority() {
        try {
            return (String) Hawk.get("dont_edit_43", "custom");
        } catch (Exception e) {
            return "custom";
        }
    }

    public String getCustomAds() {
        try {
            return (String) Hawk.get("dont_edit_45", "");
        } catch (Exception e) {
            return "";
        }
    }

    public String getPassword() {
        try {
            return (String) Hawk.get("dont_edit_26", "");
        } catch (Exception e) {
            return "";
        }
    }

    public int getTextAdsStart() {
        try {
            return ((Integer) Hawk.get("dont_edit_46", Integer.valueOf(0))).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isShowTutorialCast() {
        try {
            return ((Boolean) Hawk.get("dont_edit_60", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public void saveLastDeviceCast(String lastDevice) {
        Hawk.put("dont_edit_61", lastDevice);
    }

    public String getLastDeviceCast() {
        try {
            return (String) Hawk.get("dont_edit_61", "");
        } catch (Exception e) {
            return "";
        }
    }

    public int getTextAdsDuration() {
        try {
            return ((Integer) Hawk.get("dont_edit_47", Integer.valueOf(0))).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getDuration() {
        try {
            return ((Integer) Hawk.get("dont_edit_40", Integer.valueOf(2))).intValue();
        } catch (Exception e) {
            return 2;
        }
    }

    public void setDuration(int duration) {
        Hawk.put("dont_edit_40", Integer.valueOf(duration));
    }

    public boolean isShowAds() {
        try {
            return ((Boolean) Hawk.get("dont_edit_21", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_21", Boolean.valueOf(false));
            return true;
        }
    }

    public void setVolumeCast(long volume) {
        Hawk.put("dont_edit_59", Long.valueOf(volume));
    }

    public long getVolumecast() {
        long j = 2;
        try {
            j = ((Long) Hawk.get("dont_edit_59", Long.valueOf(2))).longValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_59", Long.valueOf(j));
        }
        return j;
    }

    public boolean isFirstShowTextAds() {
        try {
            return ((Boolean) Hawk.get("dont_edit_49", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_49", Boolean.valueOf(false));
            return true;
        }
    }

    public boolean isShowTextAds() {
        try {
            return ((Boolean) Hawk.get("dont_edit_48", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_48", Boolean.valueOf(false));
            return true;
        }
    }

    public boolean isConfigShowAds() {
        try {
            return ((Boolean) Hawk.get("dont_edit_31", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_31", Boolean.valueOf(false));
            return true;
        }
    }

    public boolean isConfigShowHeyzap() {
        try {
            return ((Boolean) Hawk.get("dont_edit_32", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_32", Boolean.valueOf(false));
            return true;
        }
    }

    public boolean isConfigShowUnity() {
        try {
            return ((Boolean) Hawk.get("dont_edit_34", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_34", Boolean.valueOf(false));
            return true;
        }
    }

    public boolean isConfigShowAdcolony() {
        try {
            return ((Boolean) Hawk.get("dont_edit_36", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_36", Boolean.valueOf(false));
            return true;
        }
    }

    public void saveTimeCurrentShowAds(int time) {
        Hawk.put("dont_edit_28", Integer.valueOf(time));
    }

    public int getTimeCurrentShowAds() {
        try {
            return ((Integer) Hawk.get("dont_edit_28", Integer.valueOf(0))).intValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_28", Integer.valueOf(0));
            return 0;
        }
    }

    public void saveTotalCurrentShowDialogDetail(int i) {
        Hawk.put("dont_edit_39", Integer.valueOf(i));
    }

    public int getTotalShowDialogDetails() {
        try {
            return ((Integer) Hawk.get("dont_edit_39", Integer.valueOf(0))).intValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_39", Integer.valueOf(0));
            return 0;
        }
    }

    public boolean isReview() {
        try {
            return ((Boolean) Hawk.get("dont_edit_18", Boolean.valueOf(true))).booleanValue();
        } catch (Exception e) {
            Hawk.put("dont_edit_18", Boolean.valueOf(true));
            return true;
        }
    }

    public Language getLanguage() {
        try {
            return ((String) Hawk.get("dont_edit_9", Language.VI.toString())).equals(Language.VI.toString()) ? Language.VI : Language.EN;
        } catch (Exception e) {
            Log.e("Looix", e.toString());
            setLanguage(Language.VI);
            return Language.VI;
        }
    }

    public String getAccessToken() {
        try {
            return (String) Hawk.get("dont_edit_0", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public String getInviteCode() {
        try {
            return (String) Hawk.get("dont_edit_24", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public boolean getLoginFirst() {
        try {
            return ((Boolean) Hawk.get("dont_edit_19", Boolean.valueOf(true))).booleanValue();
        } catch (Exception e) {
            Hawk.destroy();
            return true;
        }
    }

    public String getRefreshToken() {
        try {
            return (String) Hawk.get("dont_edit_5", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public String getEmail() {
        try {
            return (String) Hawk.get("dont_edit_4", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public String getUserName() {
        try {
            return (String) Hawk.get("dont_edit_1", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public String getFullName() {
        return (String) Hawk.get("dont_edit_7", "");
    }

    public String getUserId() {
        try {
            return (String) Hawk.get("dont_edit_2", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public String getHeyzapId() {
        try {
            return (String) Hawk.get("dont_edit_33", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public String getUnityId() {
        try {
            return (String) Hawk.get("dont_edit_35", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public String getAdcolonyId() {
        try {
            return (String) Hawk.get("dont_edit_37", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public String getAdcolonyZoneId() {
        try {
            return (String) Hawk.get("dont_edit_38", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public String getExpireDate() {
        try {
            return (String) Hawk.get("dont_edit_3", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public boolean isUsingCode() {
        try {
            return ((Boolean) Hawk.get("dont_edit_27", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.destroy();
            return false;
        }
    }

    public String getAvatar() {
        try {
            return (String) Hawk.get("dont_edit_6", "");
        } catch (Exception e) {
            Hawk.destroy();
            return "";
        }
    }

    public String getPhone() {
        return (String) Hawk.get("dont_edit_22", "");
    }

    public String getBirthday() {
        return (String) Hawk.get("dont_edit_13", "");
    }

    public String getEndpoint() {
        return (String) Hawk.get("dont_edit_14", "http://iplei.com");
    }

    public boolean isSwipeChangeVolumn() {
        return ((Boolean) Hawk.get("dont_edit_15", Boolean.valueOf(true))).booleanValue();
    }

    public boolean isSwipeChangeBrightness() {
        return ((Boolean) Hawk.get("dont_edit_16", Boolean.valueOf(true))).booleanValue();
    }

    public boolean isSwipeSeek() {
        return ((Boolean) Hawk.get("dont_edit_17", Boolean.valueOf(true))).booleanValue();
    }

    public boolean isSynLogin() {
        try {
            return ((Boolean) Hawk.get("dont_edit_8", Boolean.valueOf(false))).booleanValue();
        } catch (Exception e) {
            Hawk.destroy();
            return false;
        }
    }

    public boolean isNewTypeLogin() {
        return ((Boolean) Hawk.get("dont_edit_11", Boolean.valueOf(false))).booleanValue();
    }

    public void setIsNewTypeLogin(boolean isNewTypeLogin) {
        Hawk.put("dont_edit_11", Boolean.valueOf(isNewTypeLogin));
    }

    public void destroy() {
        Hawk.destroy();
    }

    public void logout() {
        Hawk.remove("dont_edit_0", "dont_edit_2", "dont_edit_1", "dont_edit_5", "dont_edit_3", "dont_edit_7", "dont_edit_25", "dont_edit_23", "dont_edit_1", "dont_edit_4", "dont_edit_6");
    }
}
