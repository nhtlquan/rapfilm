package com.example.rapfilm.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.rapfilm.Debug;
import com.example.rapfilm.OnItemClickListener;
import com.example.rapfilm.OnItemFocusListener;
import com.example.rapfilm.R;
import com.example.rapfilm.VideoGridContentAdapter;
import com.example.rapfilm.common.ConvertCharacter;
import com.example.rapfilm.common.JsonUtils;
import com.example.rapfilm.custom.TextCardPresenter;
import com.example.rapfilm.model.Film;
import com.example.rapfilm.model.Type;
import com.example.rapfilm.network.FilmApi;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VideoSearchActivity extends AppCompatActivity implements OnItemClickListener, OnItemFocusListener {
    private CircularProgressBar circularProgressBar;
    private TextView title;
    private VerticalGridView verticalGridView;
    private String type;
    private VideoGridContentAdapter videoGridContentAdapter;
    private Disposable mSubscription;
    private String mQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_grid);
        circularProgressBar = findViewById(R.id.loading);
        title = findViewById(R.id.title);
        verticalGridView = findViewById(R.id.trv);
        verticalGridView.setNumColumns(4);
        videoGridContentAdapter = new VideoGridContentAdapter(new ArrayList<>(), VideoSearchActivity.this, VideoSearchActivity.this);
        verticalGridView.setAdapter(videoGridContentAdapter);
        getData();
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        mQuery =  getIntent().getStringExtra("keyword");
        title.setText("Tìm kiếm " + "\"" +mQuery + "\"");
        loadData();
    }



    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, DetailActivityFilm.class);
        intent.putExtra("film", videoGridContentAdapter.getItemByPos(position).getId());
        intent.putExtra("film_ojb", videoGridContentAdapter.getItemByPos(position));
        startActivity(intent);
    }

    @Override
    public void onItemFocus() {
        loadData();
    }

    private void loadData() {
        mSubscription = FilmApi.search(this, ConvertCharacter.convertToEnglish(mQuery), "", Type.NONE, videoGridContentAdapter.getItemCount(), 16).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<JsonElement>() {
            @Override
            public void accept(JsonElement jsonElement) throws Exception {
                if (jsonElement.getAsJsonObject().get("status").getAsBoolean()) {
                    initView(JsonUtils.parserSearch(jsonElement));
                }
            }
        });
    }



    private void initView(List<Film> films) {
        circularProgressBar.setVisibility(View.GONE);
        videoGridContentAdapter.addAll(films);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
