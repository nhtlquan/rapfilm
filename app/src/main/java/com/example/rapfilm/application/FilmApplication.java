package com.example.rapfilm.application;

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
import com.example.rapfilm.R;
import com.example.rapfilm.common.FilmPreferences;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.BuildConfig;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.orhanobut.hawk.Hawk;

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
    private String userAgent;


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
        userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
        setLanguage();
    }

    /** Returns a {@link DataSource.Factory}. */
    public DataSource.Factory buildDataSourceFactory(TransferListener<? super DataSource> listener) {
        return new DefaultDataSourceFactory(this, listener, buildHttpDataSourceFactory(listener));
    }

    /** Returns a {@link HttpDataSource.Factory}. */
    public HttpDataSource.Factory buildHttpDataSourceFactory(
            TransferListener<? super DataSource> listener) {
        return new DefaultHttpDataSourceFactory(userAgent, listener);
    }

    public boolean useExtensionRenderers() {
        return BuildConfig.FLAVOR.equals("withExtensions");
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
