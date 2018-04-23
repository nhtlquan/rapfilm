package com.example.lequan.film.receiver;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.parse.ParsePushBroadcastReceiver;
import org.json.JSONException;
import org.json.JSONObject;

public class PushReceiver extends ParsePushBroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String pushDataStr = intent.getStringExtra(ParsePushBroadcastReceiver.KEY_PUSH_DATA);
        Log.e("pushData", "pushData = " + pushDataStr);
        String token = "";
        try {
            token = new JSONObject(pushDataStr).getString("access_token");
            Log.e("pushData", "pushData = " + token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(token)) {
            Intent intent1 = new Intent();
            intent1.setAction("Login");
            intent1.putExtra("token", token);
            context.sendBroadcast(intent1);
        }
    }
}
