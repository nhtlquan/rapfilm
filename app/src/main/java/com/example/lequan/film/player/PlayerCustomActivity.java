package com.example.lequan.film.player;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.BuildCompat;
import com.example.lequan.film.R;
import com.example.lequan.film.Utils.Utils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PlayerCustomActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_play);
        playLinkVideo();
    }

    public void playLinkVideo() {
        PlayerFragment playerFragment = PlayerFragment.newInstance();
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        ft1.replace(R.id.videoFragment, playerFragment, "tag");
        ft1.commit();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static boolean supportsPictureInPicture(Context context) {
        return BuildCompat.isAtLeastN() && context.getPackageManager().hasSystemFeature("android.software.picture_in_picture");
    }
}
