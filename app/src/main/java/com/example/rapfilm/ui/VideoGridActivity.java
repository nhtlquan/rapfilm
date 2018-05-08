package com.example.rapfilm.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rapfilm.CategoryItemFragment;
import com.example.rapfilm.Debug;
import com.example.rapfilm.OnItemClickListener;
import com.example.rapfilm.OnItemFocusListener;
import com.example.rapfilm.R;
import com.example.rapfilm.Utils.Utils;
import com.example.rapfilm.VideoGridContentAdapter;
import com.example.rapfilm.model.Film;
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

public class VideoGridActivity extends AppCompatActivity implements OnItemClickListener, OnItemFocusListener {
    private CircularProgressBar circularProgressBar;
    private TextView title;
    private VerticalGridView verticalGridView;
    private String type, item_type;
    private VideoGridContentAdapter videoGridContentAdapter;
    private Disposable mSubscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_grid);
        circularProgressBar = findViewById(R.id.loading);
        title = findViewById(R.id.title);
        verticalGridView = findViewById(R.id.trv);
        verticalGridView.setNumColumns(4);
        videoGridContentAdapter = new VideoGridContentAdapter(new ArrayList<>(), VideoGridActivity.this, VideoGridActivity.this);
        verticalGridView.setAdapter(videoGridContentAdapter);
        getData();
    }

    private void getData() {
        this.type = getIntent().getStringExtra("type");
        title.setText(getIntent().getStringExtra("title"));
        this.item_type = getIntent().getStringExtra("itemType");
        switch (type) {
            case "top_newest":
                getDataTop();
                break;
            case "collection":
                getDataCollection();
                break;
            case "discover":
                break;
            case "local":
                break;
        }
        switch (item_type) {
            case "recent":
                initView(Utils.getHistory(this));
                break;
            case "favourite":
                initView(Utils.getFavorite(this));
                break;
        }
    }


    private void getDataTop() {
        this.mSubscription = FilmApi.newestCategory(this, null, item_type, videoGridContentAdapter.getItemCount(), 16).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new parserData());
    }

    private void getDataCollection() {
        this.mSubscription = FilmApi.newestCategory(this, null, item_type, videoGridContentAdapter.getItemCount(), 16).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new parserData());
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
        if (!type.equals("local")) {
            switch (type) {
                case "top_newest":
                    getDataTop();
                    break;
                case "collection":
                    getDataCollection();
                    break;
            }
        }
    }

    class parserData implements Consumer<JsonElement> {

        parserData() {

        }


        @Override
        public void accept(JsonElement jsonElement) throws Exception {
            parserJson(jsonElement);
        }
    }

    public void parserJson(JsonElement jsonElement) {
        if (jsonElement.getAsJsonObject().get("status").getAsBoolean()) {
            JsonElement dataElement = jsonElement.getAsJsonObject().get("data");
            initView((ArrayList) new Gson().fromJson(dataElement, new C06772().getType()));
        }

    }

    private void initView(ArrayList<Film> films) {
        circularProgressBar.setVisibility(View.GONE);
        videoGridContentAdapter.addAll(films);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

class C06772 extends TypeToken<List<Film>> {
    C06772() {
    }
}
}
