package com.example.rapfilm.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rapfilm.Debug;
import com.example.rapfilm.OnItemClickListener;
import com.example.rapfilm.R;
import com.example.rapfilm.SugggessContentAdapter;
import com.example.rapfilm.Utils.Utils;
import com.example.rapfilm.model.Episode;
import com.example.rapfilm.model.Film;
import com.example.rapfilm.model.FilmDetail;
import com.example.rapfilm.model.ListFilm;
import com.example.rapfilm.model.Poster;
import com.example.rapfilm.network.FilmApi;
import com.example.rapfilm.newplayer.PlayerActivity;
import com.example.rapfilm.player.LoadingFragment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.reactivestreams.Subscription;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivityFilm extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_CARD = "card";
    public static final String TRANSITION_NAME = "t_for_transition";
    private ArrayObjectAdapter actionAdapter;
    private Action bookmarkAction;
    CircularProgressBar circularProgressBar;
    private Disposable loadDetailSub;
    private LoadingFragment loadingFragment;
    private BackgroundManager mBackgroundManager;
    private FilmDetail mFilmDetail;
    private String mFilmID;
    private DisplayMetrics mMetrics;
    private ArrayObjectAdapter mRowsAdapter;
    private Action playAction;
    private Subscription subCribe;
    private Action subcribeAction;
    private Subscription unSubCribe;
    private Bitmap theBitmap;

    private ImageView imgPoster;
    private TextView tvTitle;
    private TextView tvSubname;
    private TextView tvReleaseDate;
    private TextView tvDes;
    private TextView quality;
    private TextView ibmrate;
    private TextView daodien;
    private TextView dienvien;
    private HorizontalGridView mTvRecyclerView;
    private RatingBar yourVote;
    private Button watch;
    private Button browse;
    private Button watchlater;
    private ImageView btn_back;
    private int currentPlaySeason = 0;
    private ConstraintLayout constraintLayout;
    private CircularProgressBar loading;
    private Film film;
    private boolean isWatchlater = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        loading = findViewById(R.id.loading);
        constraintLayout = findViewById(R.id.root);
        watchlater = findViewById(R.id.watchlater);
        btn_back = findViewById(R.id.btn_back);
        imgPoster = findViewById(R.id.poster);
        tvTitle = findViewById(R.id.title);
        tvSubname = findViewById(R.id.description1);
        tvReleaseDate = findViewById(R.id.year);
        tvDes = findViewById(R.id.description2);
        quality = findViewById(R.id.quality);
        watch = findViewById(R.id.watch);
        browse = findViewById(R.id.browse);
        watchlater = findViewById(R.id.watchlater);
        ibmrate = findViewById(R.id.ibmrate);
        yourVote = findViewById(R.id.rating);
        daodien = findViewById(R.id.daodien);
        dienvien = findViewById(R.id.dienvien);
        mTvRecyclerView = findViewById(R.id.trv);
        btn_back.setOnClickListener(this);
        watch.setOnClickListener(this);
        browse.setOnClickListener(this);
        watchlater.setOnClickListener(this);
        getData();
    }

    public void getData() {
        this.mFilmID = getIntent().getStringExtra("film");
        this.film = (Film) getIntent().getSerializableExtra("film_ojb");
        this.loadDetailSub = FilmApi.detailFilm(this, this.mFilmID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C05411());
        for (int i = 0; i < Utils.getFavorite(this).size(); i++) {
            if (mFilmID.equals(Utils.getFavorite(this).get(i).getId())) {
                watchlater.setText("Bỏ yêu thích");
                isWatchlater = false;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.watch:
                if (mFilmDetail.getEpisodes().get(currentPlaySeason).getContents().size() > 0)
                    intentToplayer(mFilmDetail.getEpisodes().get(currentPlaySeason).getContents().get(0));
                else
                    Toast.makeText(this,"Nội dung đang được upload", Toast.LENGTH_LONG).show();
                break;
            case R.id.browse:
                Intent intent = new Intent(this, SeasonFilmActivity.class);
                intent.putExtra("film", mFilmDetail.getId());
                intent.putExtra("item_film", film);
                Debug.e("Size" + Utils.getFavorite(this).size());
                startActivity(intent);
                break;
            case R.id.watchlater:
                if (isWatchlater) {
                    isWatchlater = false;
                    ArrayList<Film> films = Utils.getFavorite(this);
                    films.add(0, film);
                    ListFilm listFilm = new ListFilm(films);
                    Utils.saveFavorite(this, listFilm);
                    watchlater.setText("Bỏ yêu thích");
                    Toast.makeText(this, "Thêm vào yêu thích thành công", Toast.LENGTH_LONG).show();
                } else {
                    for (int i = 0; i < Utils.getFavorite(this).size(); i++) {
                        if (mFilmID.equals(Utils.getFavorite(this).get(i).getId())) {
                            watchlater.setText("Yêu thích");
                            isWatchlater = true;
                            Toast.makeText(this, "Xóa yêu thích thành công", Toast.LENGTH_LONG).show();
                            ArrayList<Film> films = Utils.getFavorite(this);
                            films.remove(i);
                            ListFilm listFilm = new ListFilm(films);
                            Utils.saveFavorite(this, listFilm);
                        }
                    }
                }
                break;
        }
    }

    class C05411 implements Consumer<JsonElement> {
        C05411() {
        }


        @Override
        public void accept(JsonElement jsonElement) {
            Log.e("jsonElement", "jsonElement detailsFilm= " + jsonElement);
            if (jsonElement.getAsJsonObject().get("status").getAsBoolean()) {
                JsonElement poster;
                Gson gson = new Gson();
                mFilmDetail = gson.fromJson(jsonElement.getAsJsonObject().get("data"), FilmDetail.class);
                Log.e("mFillmDetails", "filmDetails slug = " + mFilmDetail.getSlug());
                if (mFilmDetail.isRate()) {
                    mFilmDetail.setMark(jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("mark").getAsInt());
                }
                if (jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("season").getAsString().equals("false")) {
                    mFilmDetail.setSeason(false);
                    Debug.e("season");
                } else {
                    mFilmDetail.setSeason(true);
                    Debug.e("season");
                }
                if (mFilmDetail.getLink_trailer().size() > 0) {
                    poster = jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("cover");
                } else {
                    poster = jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("cover");
                }
                if (poster.isJsonObject()) {
                    mFilmDetail.setCoverFilm(gson.fromJson(poster, Poster.class));
                } else {
                    mFilmDetail.setCoverFilm(null);
                }
                setupUi();
            }
        }
    }

    private void setupUi() {
        tvTitle.setText(mFilmDetail.getName_vi());
        tvSubname.setText(mFilmDetail.getName());
        tvReleaseDate.setText(mFilmDetail.getYear());
        quality.setText(mFilmDetail.getQuality());
        StringBuilder director = new StringBuilder();
        for (int i = 0; i < mFilmDetail.getDirector().length; i++) {
            director.append(mFilmDetail.getDirector()[i]);
            if (mFilmDetail.getDirector().length - 1 != i)
                director.append(", ");
        }
        StringBuilder actor = new StringBuilder();
        for (int i = 0; i < mFilmDetail.getActors().length; i++) {
            actor.append(mFilmDetail.getActors()[i]);
            if (mFilmDetail.getActors().length - 1 != i)
                actor.append(", ");
        }
        daodien.setText(director);
        dienvien.setText(actor);
        ibmrate.setText(String.valueOf("IMDB " + mFilmDetail.getImdb_rate()));
        tvDes.setText(Html.fromHtml(mFilmDetail.getDescription()));
        yourVote.setRating(mFilmDetail.getRateInfo().getMark());
        Glide.with(this).load(mFilmDetail.getCoverFilm().getLink()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgPoster);
        mFilmDetail.getSuggestion().remove(0);
        SugggessContentAdapter sugggessContentAdapter = new SugggessContentAdapter(mFilmDetail.getSuggestion(), new OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                film = mFilmDetail.getSuggestion().get(position);
                mFilmID = mFilmDetail.getSuggestion().get(position).getId();
                loadDetailSub = FilmApi.detailFilm(DetailActivityFilm.this, mFilmID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C05411());
            }
        });
        mTvRecyclerView.setNumRows(1);
        mTvRecyclerView.setAdapter(sugggessContentAdapter);
        constraintLayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    public void intentToplayer(Episode episode) {
        Debug.e(this.mFilmDetail.getName_vi());
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("film", film);
        intent.putExtra("episode", episode);
        intent.putExtra("list_episode", this.mFilmDetail.getEpisodes().get(this.currentPlaySeason));
        intent.putExtra("list_season", this.mFilmDetail.getEpisodes());
        intent.putExtra("currentplayseason", this.currentPlaySeason);
        intent.putExtra("filmID", this.mFilmDetail.getId());
        intent.putExtra("suggess", this.mFilmDetail.getSuggestion());
        intent.putExtra("filmName", this.mFilmDetail.getName());
        intent.putExtra("filmName_vi", this.mFilmDetail.getName_vi());
        intent.putExtra("year", this.mFilmDetail.getYear());
        intent.putExtra("cover", this.mFilmDetail.getCoverFilm());
        intent.putExtra("episodeID", episode.getId());
        intent.putExtra("fromrecent", false);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
