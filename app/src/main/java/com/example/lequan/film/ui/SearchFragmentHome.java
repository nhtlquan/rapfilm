package com.example.lequan.film.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.widget.SearchEditText;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lequan.film.Debug;
import com.example.lequan.film.R;
import com.example.lequan.film.common.FilmPreferences;

public class SearchFragmentHome extends Fragment {
    private SearchEditText btn_search;
    private String mQuery;

    public static SearchFragmentHome newInstance() {
        SearchFragmentHome fragment = new SearchFragmentHome();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_video_grid, container, false);
        btn_search = view.findViewById(R.id.edt_search);
        btn_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (!FilmPreferences.getInstance().isReview()) {
                            mQuery = btn_search.getText().toString();
                            Intent intent = new Intent(getActivity(), VideoSearchActivity.class);
                            intent.putExtra("keyword", mQuery);
                            startActivity(intent);
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        return view;
    }

}
