package com.example.rapfilm.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.widget.SearchEditText;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rapfilm.Debug;
import com.example.rapfilm.R;
import com.example.rapfilm.common.FilmPreferences;

public class SearchFragmentHome extends Fragment {
    private SearchEditText edt_search;
    private Button btn_search;
    private String mQuery;

    public static SearchFragmentHome newInstance() {
        SearchFragmentHome fragment = new SearchFragmentHome();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_video_grid, container, false);
        edt_search = view.findViewById(R.id.edt_search);
        btn_search = view.findViewById(R.id.btn_search);
        Debug.e("Quan pro");
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FilmPreferences.getInstance().isReview()) {
                    mQuery = edt_search.getText().toString();
                    Intent intent = new Intent(getActivity(), VideoSearchActivity.class);
                    intent.putExtra("keyword", mQuery);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

}
