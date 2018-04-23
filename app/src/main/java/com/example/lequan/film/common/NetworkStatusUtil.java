package com.example.lequan.film.common;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetworkStatusUtil {
    private static final String TAG = NetworkStatusUtil.class.getSimpleName();

    public static boolean isMobileAvailable(Context con) {
        try {
            if (((ConnectivityManager) con.getSystemService("connectivity")).getNetworkInfo(0).isConnected()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isWifiAvaiable(Context context) {
        if (context == null) {
            return false;
        }
        return ((WifiManager) context.getSystemService("wifi")).isWifiEnabled();
    }

    public static boolean is3gAvaiable(Context context) {
        NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (netInfo == null || netInfo.getType() != 0) {
            return false;
        }
        return true;
    }

    public static boolean isGPSEnabale(Context context) {
        return ((LocationManager) context.getSystemService("location")).isProviderEnabled("gps");
    }

    public static boolean isNetworkAvaiable(Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }
}
