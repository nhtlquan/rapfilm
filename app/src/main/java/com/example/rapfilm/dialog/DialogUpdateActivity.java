package com.example.rapfilm.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v17.leanback.app.GuidedStepFragment;
import com.example.rapfilm.R;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import io.reactivex.disposables.Disposable;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DialogUpdateActivity extends Activity {
    private String content = "";
    private boolean isForce = false;
    private String link = "";
    CircularProgressBar loading;
    private Disposable mSubscriptionGenCode;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(R.layout.activity_dialog);
        this.loading = (CircularProgressBar) findViewById(R.id.loading);
        this.loading.setVisibility(8);
        if (getIntent() != null) {
            this.content = getIntent().getStringExtra("content");
            this.isForce = getIntent().getBooleanExtra("force", false);
            this.link = getIntent().getStringExtra("link");
        }
        GuidedStepFragment fragment = new DialogUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", this.content);
        bundle.putBoolean("force", this.isForce);
        bundle.putString("link", this.link);
        fragment.setArguments(bundle);
        GuidedStepFragment.addAsRoot(this, fragment, R.id.content);
    }

    public void onBackPressed() {
        if (this.isForce) {
            setResult(-1, new Intent());
            finish();
            return;
        }
        super.onBackPressed();
    }

    public void finisActvity() {
        setResult(-1, new Intent());
        finish();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
