package com.example.lequan.film.application;

import android.annotation.SuppressLint;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.support.v7.view.ActionMode;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.lequan.film.R;
import com.example.lequan.film.Utils.Utils;
import com.example.lequan.film.common.FilmPreferences;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.parse.Parse;
import com.parse.Parse.Configuration.Builder;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.parse.interceptors.ParseLogInterceptor;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import java.io.File;
import java.util.Locale;

public class FilmApplication extends MultiDexApplication implements ComponentCallbacks2 {
    public static String DIR_DOWNLOAD = "/.aPhim/Download/";
    public static final String TAG = FilmApplication.class.getSimpleName();
    public static ActionMode actionMode;
    public static int build;
    public static Context mContext;
    public static FilmApplication mInstance;
    public static String version;

    class C05321 implements SaveCallback {
        C05321() {
        }

        public void done(ParseException e) {
            Log.e("Parse", "done", e);
        }
    }

    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Hawk.init(this).build();
        mInstance = this;
        DIR_DOWNLOAD = Environment.getExternalStorageState() + "/Android" + File.separator + "data" + File.separator + getPackageName() + File.separator + "Download" + File.separator;
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = info.versionName;
            build = info.versionCode;
        } catch (NameNotFoundException e) {
        }

//        try {
//            Parse.setLogLevel(2);
//            Parse.initialize(new Builder(this).applicationId("9071b872c2e7f848bace7f4e0045d1edec042c16").clientKey("937aee152bf1d6921314d5233617af4f3fcdef33").addNetworkInterceptor(new ParseLogInterceptor()).server("http://103.53.170.98:1337/aphim/parse").build());
//            ParsePush.subscribeInBackground("");
//            ParseInstallation parseInstallation = ParseInstallation.getCurrentInstallation();
//            parseInstallation.put("GCMSenderId", "354497433830");
//            parseInstallation.put("deviceID", getDeviceImei(this));
//            parseInstallation.saveInBackground(new C05321());
//        } catch (Exception e2) {
//            Log.wtf("Parse", "error " + e2.getMessage());
//            e2.printStackTrace();
//        }
        setLanguage();
    }

    public static String getDeviceImei(Context context) {
        if (context == null) {
            return "film";
        }
        @SuppressLint("WrongConstant") String macAddr = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        if (macAddr == null) {
            return "film";
        }
        return macAddr;
    }

    public static SharedPreferences getSharedPref() {
        return mInstance.getSharedPreferences("local", 0);
    }

    public void setLanguage() {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(FilmPreferences.getInstance().getLanguage().toString());
        res.updateConfiguration(conf, dm);
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case 5:
                Log.i("TrimMemory", "TRIM_MEMORY_RUNNING_MODERATE");
                break;
            case 10:
                Log.i("TrimMemory", "TRIM_MEMORY_RUNNING_LOW");
                break;
            case 15:
                Log.i("TrimMemory", "TRIM_MEMORY_RUNNING_CRITICAL");
                break;
            case 20:
                Log.i("TrimMemory", "TRIM_MEMORY_UI_HIDDEN");
                break;
            case 40:
                Log.i("TrimMemory", "TRIM_MEMORY_BACKGROUND");
                break;
            case 60:
                Log.i("TrimMemory", "TRIM_MEMORY_MODERATE");
                break;
            case 80:
                Log.i("TrimMemory", "TRIM_MEMORY_COMPLETE");
                break;
        }
        Glide.get(this).trimMemory(level);
    }

    public void onLowMemory() {
        super.onLowMemory();
//        Glide.with((Context) this).onLowMemory();
    }
}
