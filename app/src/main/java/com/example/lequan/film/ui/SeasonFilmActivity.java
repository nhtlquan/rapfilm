package com.example.lequan.film.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lequan.film.Debug;
import com.example.lequan.film.OnEpisodeClickListener;
import com.example.lequan.film.OnItemClickListener;
import com.example.lequan.film.R;
import com.example.lequan.film.SeasonAdapter;
import com.example.lequan.film.SugggessContentAdapter;
import com.example.lequan.film.model.Episode;
import com.example.lequan.film.model.Film;
import com.example.lequan.film.model.FilmDetail;
import com.example.lequan.film.model.Poster;
import com.example.lequan.film.network.FilmApi;
import com.example.lequan.film.player.LoadingFragment;
import com.example.lequan.film.player.PlayerCustomActivity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SeasonFilmActivity extends AppCompatActivity implements View.OnClickListener {
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
    private ImageView btn_back;
    private int currentPlaySeason = 0;
    private ConstraintLayout constraintLayout;
    private CircularProgressBar loading;
    private RecyclerView seasons;
    private Film film;
    private ArrayList<Episode> episodes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        seasons = findViewById(R.id.seasons);
        loading = findViewById(R.id.loading);
        constraintLayout = findViewById(R.id.root);
        btn_back = findViewById(R.id.btn_back);
        imgPoster = findViewById(R.id.poster);
        tvTitle = findViewById(R.id.title);
        tvSubname = findViewById(R.id.description1);
        tvReleaseDate = findViewById(R.id.year);
        tvDes = findViewById(R.id.description2);
        quality = findViewById(R.id.quality);
        ibmrate = findViewById(R.id.ibmrate);
        yourVote = findViewById(R.id.rating);
        daodien = findViewById(R.id.daodien);
        dienvien = findViewById(R.id.dienvien);
        mTvRecyclerView = findViewById(R.id.trv);
        btn_back.setOnClickListener(this);
        getData();
    }

    public void getData() {
        this.mFilmID = getIntent().getStringExtra("film");
        this.film = (Film) getIntent().getSerializableExtra("item_film");
        this.loadDetailSub = FilmApi.detailFilm(this, this.mFilmID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C05411());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    class C05411 implements Consumer<JsonElement> {
        C05411() {
        }
        @Override
        public void accept(JsonElement jsonElement) throws Exception {
            Log.e("jsonElement", "jsonElement detailsFilm= " + jsonElement);
            if (jsonElement.getAsJsonObject().get("status").getAsBoolean()) {
                JsonElement poster;
                Gson gson = new Gson();
                mFilmDetail = (FilmDetail) gson.fromJson(jsonElement.getAsJsonObject().get("data"), FilmDetail.class);
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
                    mFilmDetail.setCoverFilm((Poster) gson.fromJson(poster, Poster.class));
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
        SeasonAdapter seasonAdapter = new SeasonAdapter(mFilmDetail.getEpisodes(), new OnEpisodeClickListener() {
            @Override
            public void onItemClicked(Episode episode) {
                intentToplayer(episode);
            }
        });
        seasons.setLayoutManager(new LinearLayoutManager(this));
        seasons.setAdapter(seasonAdapter);
        constraintLayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    public void intentToplayer(Episode episode) {
        Intent intent = new Intent(this, PlayerCustomActivity.class);
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
