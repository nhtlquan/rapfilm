package com.example.rapfilm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.rapfilm.ui.MainActivity;
import com.example.rapfilm.ui.SearchFragmentHome;

public class SplashActivity extends AppCompatActivity{


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_splash);
        intentMainActivity();
    }

    public void intentMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }


}
