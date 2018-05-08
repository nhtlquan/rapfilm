package com.example.rapfilm;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/**
 * Created by LeQuan on 4/26/2018.
 */

public class ForceUpdateChecker {

    private static final String TAG = ForceUpdateChecker.class.getSimpleName();

    public static final String KEY_UPDATE_REQUIRED = "force_update_required";
    public static final String KEY_CURRENT_VERSION = "force_update_current_version";
    public static final String KEY_UPDATE_URL = "force_update_store_url";
    public static final String KEY_UPDATE_DES = "force_update_des";

    private OnUpdateNeededListener onUpdateNeededListener;
    private Context context;

    public interface OnUpdateNeededListener {
        void onUpdateNeeded(String updateUrl, String des, String appVersion);
    }

    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    public ForceUpdateChecker(@NonNull Context context,
                              OnUpdateNeededListener onUpdateNeededListener) {
        this.context = context;
        this.onUpdateNeededListener = onUpdateNeededListener;
    }

    public void check() {
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.fetch(0) // fetch every minutes
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Debug.e("remote config is fetched.");
                            remoteConfig.activateFetched();
                            Debug.e("remoteConfig" + remoteConfig.getString(KEY_UPDATE_DES));
                            Debug.e("remoteConfig" + remoteConfig.getString(KEY_CURRENT_VERSION));
                            Debug.e("remoteConfig" + remoteConfig.getString(KEY_UPDATE_URL));
                            Debug.e("remoteConfig" + String.valueOf(remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)));
                            if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
                                String currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION);
                                String appVersion = getAppVersion(context);
                                String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);
                                String des = remoteConfig.getString(KEY_UPDATE_DES);

                                if (!TextUtils.equals(currentVersion, appVersion)
                                        && onUpdateNeededListener != null) {
                                    onUpdateNeededListener.onUpdateNeeded(updateUrl, des, currentVersion);
                                }
                            }
                        }
                        else {
                            Debug.e("Lỗi");
                        }
                    }
                });
    }

    private String getAppVersion(Context context) {
        String result = "";

        try {
            result = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public static class Builder {

        private Context context;
        private OnUpdateNeededListener onUpdateNeededListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateNeeded(OnUpdateNeededListener onUpdateNeededListener) {
            this.onUpdateNeededListener = onUpdateNeededListener;
            return this;
        }

        public ForceUpdateChecker build() {
            return new ForceUpdateChecker(context, onUpdateNeededListener);
        }

        public ForceUpdateChecker check() {
            ForceUpdateChecker forceUpdateChecker = build();
            forceUpdateChecker.check();

            return forceUpdateChecker;
        }
    }
}
