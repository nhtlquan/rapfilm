package com.example.rapfilm.common;

import android.content.Context;
import android.net.wifi.WifiManager;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.regex.Pattern;

public class DeviceUtils {
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    public static String getDeviceImei(Context context) {
        if (context == null) {
            return "film";
        }
        String macAddr = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        if (macAddr == null) {
            return "film";
        }
        return macAddr;
    }

    public static String getTypeDevice(Context context) {
        if (context == null) {
            return "phone";
        }
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3 ? "tablet" : "phone";
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            for (NetworkInterface intf : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress addr : Collections.list(intf.getInetAddresses())) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = IPV4_PATTERN.matcher(sAddr).matches();
                        if (useIPv4) {
                            if (isIPv4) {
                                return sAddr;
                            }
                        } else if (!isIPv4) {
                            int delim = sAddr.indexOf(37);
                            if (delim >= 0) {
                                return sAddr.substring(0, delim);
                            }
                            return sAddr;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }
}
