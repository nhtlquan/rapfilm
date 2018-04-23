package com.example.lequan.film.player;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class LoadingFragment extends Fragment {
    private static final int SPINNER_HEIGHT = 130;
    private static final int SPINNER_WIDTH = 130;
    private static final String TAG = LoadingFragment.class.getSimpleName();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CircularProgressBar progressBar = new CircularProgressBar(container.getContext());
        progressBar.setIndeterminate(true);
        if (container instanceof FrameLayout) {
            progressBar.setLayoutParams(new LayoutParams(130, 130, 17));
        }
        return progressBar;
    }
}
