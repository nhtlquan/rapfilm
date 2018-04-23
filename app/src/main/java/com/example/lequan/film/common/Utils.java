package com.example.lequan.film.common;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Utils {
    private Utils() {
    }

    public static Point getDisplaySize(Context context) {
        Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static String stringArrayToString(String[] strs, String betweenStrs) {
        StringBuilder builder = new StringBuilder();
        if (strs.length <= 0) {
            return "";
        }
        for (String str : strs) {
            builder.append(str + betweenStrs);
        }
        if (builder.toString().length() <= 0) {
            return "";
        }
        return builder.toString().substring(0, builder.toString().lastIndexOf(betweenStrs));
    }

    public static byte[] download(URL url) throws IOException {
        InputStream input = null;
        ByteArrayOutputStream output = null;
        HttpURLConnection connection = null;
        byte[] data = new byte[4096];
        try {
            int fileLength;
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != 200) {
                fileLength = connection.getContentLength();
                input = connection.getInputStream();
            } else {
                fileLength = connection.getContentLength();
                input = connection.getInputStream();
            }
            if (fileLength != -1) {
                output = new ByteArrayOutputStream(fileLength);
            }
            long total = 0;
            while (true) {
                int count = input.read(data);
                if (count == -1) {
                    break;
                }
                total += (long) count;
                output.write(data, 0, count);
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                }
            }
            if (input != null) {
                input.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        } catch (Exception e2) {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e3) {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    return data;
                }
            }
            if (input != null) {
                input.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        } catch (Throwable th) {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e4) {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
            if (input != null) {
                input.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return data;
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, 1).show();
    }

    public static void showToast(Context context, int resourceId) {
        Toast.makeText(context, context.getString(resourceId), 1).show();
    }

    public static int convertDpToPixel(Context ctx, int dp) {
        return Math.round(((float) dp) * ctx.getResources().getDisplayMetrics().density);
    }

    public static String formatMillis(int millis) {
        String result = "";
        int hr = millis / 3600000;
        millis %= 3600000;
        int min = millis / 60000;
        int sec = (millis % 60000) / 1000;
        if (hr > 0) {
            result = result + hr + ":";
        }
        if (min >= 0) {
            if (min > 9) {
                result = result + min + ":";
            } else {
                result = result + "0" + min + ":";
            }
        }
        if (sec > 9) {
            return result + sec;
        }
        return result + "0" + sec;
    }

    public static long getDuration(String videoUrl) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if (VERSION.SDK_INT >= 14) {
            mmr.setDataSource(videoUrl, new HashMap());
        } else {
            mmr.setDataSource(videoUrl);
        }
        return Long.parseLong(mmr.extractMetadata(9));
    }

    public static boolean hasPermission(Context context, String permission) {
        return context.getPackageManager().checkPermission(permission, context.getPackageName()) == 0;
    }
}
