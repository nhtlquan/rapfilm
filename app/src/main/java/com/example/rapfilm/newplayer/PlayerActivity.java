/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.rapfilm.newplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rapfilm.Debug;
import com.example.rapfilm.OnEpisodeClickListener;
import com.example.rapfilm.R;
import com.example.rapfilm.SeasonAdapter;
import com.example.rapfilm.Utils.Utils;
import com.example.rapfilm.application.FilmApplication;
import com.example.rapfilm.common.FilmPreferences;
import com.example.rapfilm.common.JsonUtils;
import com.example.rapfilm.model.Episode;
import com.example.rapfilm.model.EpisodeDetail;
import com.example.rapfilm.model.Film;
import com.example.rapfilm.model.FilmDetail;
import com.example.rapfilm.model.ListFilm;
import com.example.rapfilm.model.Poster;
import com.example.rapfilm.model.Season;
import com.example.rapfilm.model.Video;
import com.example.rapfilm.network.FilmApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.commons.lang3.StringEscapeUtils;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * An activity that plays media using {@link SimpleExoPlayer}.
 */
public class PlayerActivity extends Activity
        implements OnClickListener, PlaybackPreparer {


    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private Handler mainHandler;
    private EventLogger eventLogger;
    private PlayerView playerView;

    private DataSource.Factory mediaDataSourceFactory;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private TrackSelectionHelper trackSelectionHelper;
    private DebugTextViewHelper debugViewHelper;
    private boolean inErrorState;
    private TrackGroupArray lastSeenTrackGroupArray;

    private boolean shouldAutoPlay;
    private int resumeWindow;
    private long resumePosition;

    // Fields used only for ad playback. The ads loader is loaded via reflection.


    // Activity lifecycle

    private Episode mEpisode;
    private String mFilmId;
    private Film mFilm;
    private String mFilmName;
    private String mFilmName_vi;
    private Handler mPlayerHandler = new Handler();
    private String mType;
    private String mUrl;
    private int currentIndexQuality = 0;
    private int currentplayseason = 0;
    private long duraton = 0;
    private boolean enableBackgroundAudio;
    private String episodeID;
    private boolean fromRecent = false;
    private boolean isSeason;
    private boolean isTvShow;
    private boolean lastStatePlay = false;
    private ArrayList<Season> seasons = new ArrayList();
    private String year;
    private ArrayList<Film> suggess = new ArrayList();
    private Poster cover;
    private long playerPosition;
    private Disposable mSubscriptionGetDetailFilm;
    private int countLink = 0;
    private EpisodeDetail mCurrentEpisode;
    private AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
    private CircularProgressBar circularProgressBar;
    private SeekBar seekBar;
    private TextView tv_english_title, tv_vietnam_title, tv_total_duration, tv_duration;
    private ImageButton bt_play, bt_rewind, bt_forward, bt_previous, bt_next, bt_setting;
    private Button bt_episode;
    private RecyclerView cv_vod_episode;
    private RelativeLayout rl_episode_container, rl_navigation_button_container;
    private boolean is_showepisode = false;
    private boolean isPause;
    private int elapsedTime;
    private RelativeLayout controler;
    private Handler handler;
    private CountDownTimer countDownTimer;
    private boolean ishowHistory = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shouldAutoPlay = true;
        clearResumePosition();
        mediaDataSourceFactory = buildDataSourceFactory(true);
        mainHandler = new Handler();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
        handler = new Handler();
        setContentView(R.layout.player_activity);
        View rootView = findViewById(R.id.root);
        rootView.setOnClickListener(this);
        circularProgressBar = findViewById(R.id.loading);
        playerView = findViewById(R.id.player_view);

        rl_navigation_button_container = findViewById(R.id.rl_navigation_button_container);
        rl_episode_container = findViewById(R.id.rl_episode_container);
        cv_vod_episode = findViewById(R.id.cv_vod_episode);
        controler = findViewById(R.id.controler);
        seekBar = findViewById(R.id.sb_duration);
        tv_english_title = findViewById(R.id.tv_english_title);
        tv_vietnam_title = findViewById(R.id.tv_vietnam_title);
        tv_total_duration = findViewById(R.id.tv_total_duration);
        tv_duration = findViewById(R.id.tv_duration);
        bt_play = findViewById(R.id.bt_play);
        bt_rewind = findViewById(R.id.bt_rewind);
        bt_forward = findViewById(R.id.bt_forward);
        bt_previous = findViewById(R.id.bt_previous);
        bt_next = findViewById(R.id.bt_next);
        bt_setting = findViewById(R.id.bt_setting);
        bt_episode = findViewById(R.id.bt_episode);

        playerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                showControls();
            }
            return true;
        });

        bt_forward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null)
                    player.seekTo(player.getCurrentPosition() + 6000);
            }
        });
        bt_rewind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null)
                    player.seekTo(player.getCurrentPosition() - 6000);
            }
        });
        bt_play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player == null)
                    return;
                if (player.getPlaybackState() == Player.STATE_ENDED) {
                    elapsedTime = 0;
                    player.seekTo(0);
                    isPause = true;
                    bt_play.setImageDrawable(getResources().getDrawable(R.drawable.icon_play_media));
                    player.setPlayWhenReady(true);
                    return;
                }
                if (player.getPlayWhenReady()) {
                    isPause = true;
                    bt_play.setImageDrawable(getResources().getDrawable(R.drawable.icon_play_media));
                    player.setPlayWhenReady(false);
                } else {
                    isPause = false;
                    bt_play.setImageDrawable(getResources().getDrawable(R.drawable.icon_pause_media));
                    player.setPlayWhenReady(true);
                }
            }
        });
        bt_episode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                is_showepisode = true;
                rl_episode_container.setVisibility(View.VISIBLE);
                rl_navigation_button_container.setVisibility(View.GONE);
            }
        });
        seekBar.setProgress(0);
        seekBar.setOnKeyListener((v, keyCode, event) -> {
            countDownTime.cancel();
            countDownTime.start();
            return false;
        });


        seekBar.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
//                seekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_progress_drawable_focus));
            } else {
//                seekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_progress_drawable));
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    player.seekTo(i * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if (getIntent() != null) {
            Intent bundle = getIntent();
            this.mFilm = (Film) bundle.getSerializableExtra("film");
            this.mFilmId = bundle.getStringExtra("filmID");
            this.mEpisode = (Episode) bundle.getSerializableExtra("episode");
            this.currentplayseason = bundle.getIntExtra("currentplayseason", 0);
            this.seasons = (ArrayList<Season>) bundle.getSerializableExtra("list_season");
            this.mFilmName = bundle.getStringExtra("filmName");
            this.mFilmName_vi = bundle.getStringExtra("filmName_vi");
            this.year = bundle.getStringExtra("year");
            this.suggess = (ArrayList<Film>) bundle.getSerializableExtra("suggess");
            this.cover = (Poster) bundle.getSerializableExtra("cover");
            this.playerPosition = (long) bundle.getIntExtra("currentPlayDuration", 0);
            this.fromRecent = bundle.getBooleanExtra("fromrecent", false);
            this.episodeID = bundle.getStringExtra("episodeID");
        }
        bt_episode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    bt_episode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_list_focus_media, 0, 0, 0);
                    bt_episode.setTextColor(getResources().getColor(R.color.pink));
                } else {
                    bt_episode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_list_media, 0, 0, 0);
                    bt_episode.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
        SeasonAdapter seasonAdapter = new SeasonAdapter(seasons, new OnEpisodeClickListener() {
            @Override
            public void onItemClicked(Episode episode) {
                mEpisode = episode;
                episodeID = episode.getId();
                loadDetailFilm();
                onBackPressed();
            }
        });
        cv_vod_episode.setLayoutManager(new LinearLayoutManager(this));
        cv_vod_episode.setAdapter(seasonAdapter);
        showControls();
    }

    private void updateHistory() {
        if (!player.getPlayWhenReady())
            return;
        if (player != null) {
            mFilm.setCurent_history(player.getCurrentPosition());
            Debug.e(player.getCurrentPosition() + "");
        }
        if (checkFilmHistory()) {
            for (int i = 0; i < Utils.getHistory(this).size(); i++) {
                if (this.mFilmId.equals(Utils.getHistory(this).get(i).getId())) {
                    ArrayList<Film> films = Utils.getHistory(this);
                    films.remove(i);
                    films.add(0, this.mFilm);
                    ListFilm listFilm = new ListFilm(films);
                    Utils.saveHistory(this, listFilm);
                }
            }
        } else {
            ArrayList<Film> films = Utils.getHistory(this);
            films.add(0, this.mFilm);
            ListFilm listFilm = new ListFilm(films);
            Utils.saveHistory(this, listFilm);
        }
    }


    private void creatWachtLog() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(10000, 10000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                updateHistory();
                creatWachtLog();
            }

        }.start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
                if (controler.getVisibility() == View.GONE) {
                    showControls();
                    return false;
                }
            }
            countDownTime.cancel();
            countDownTime.start();
        }
        return super.onKeyDown(keyCode, event);
    }

    private CountDownTimer countDownTime = new CountDownTimer(10000, 1000) {
        public void onTick(long millisUntilFinished) {

        }

        public void onFinish() {
            closeController();
        }
    };

    private void closeController() {
        controler.setVisibility(View.GONE);
    }

    private boolean checkFilmHistory() {
        boolean isHistory = false;
        for (int i = 0; i < Utils.getHistory(this).size(); i++) {
            if (this.mFilmId.equals(Utils.getHistory(this).get(i).getId())) {
                isHistory = true;
            }
        }
        return isHistory;
    }


    private void loadDetailFilm() {
        ishowHistory = false;
        bt_episode.setText(mEpisode.getName());
        tv_english_title.setText(mFilmName);
        tv_vietnam_title.setText(mFilmName_vi);
        this.mSubscriptionGetDetailFilm = FilmApi.detailFilm(this, this.mFilmId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new parseLink());
    }

    class parseLink implements Consumer<JsonElement> {
        parseLink() {
        }

        @Override
        public void accept(JsonElement jsonElement) throws Exception {
            if (JsonUtils.checkJson(jsonElement)) {
                Log.e("loadDetails", "detail = " + jsonElement.toString());
                FilmDetail filmDetail = (FilmDetail) new Gson().fromJson(jsonElement.getAsJsonObject().get("data"), FilmDetail.class);
                if (jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("season").getAsString().equals("false")) {
                    filmDetail.setSeason(false);
                } else {
                    filmDetail.setSeason(true);
                }
                mType = filmDetail.getType();
                seasons = filmDetail.getEpisodes();
                isSeason = filmDetail.isSeason();
                mFilmName = filmDetail.getName();
                mFilmName_vi = filmDetail.getName_vi();
                year = filmDetail.getYear();
                suggess = filmDetail.getSuggestion();
                isTvShow = filmDetail.isTv_show();
                cover = filmDetail.getCoverFilm();
                getLinkPlay(episodeID, 0);
                findEpisode();
            }
        }
    }

    public void getLinkPlay(String episodeID, int currentSeason) {
        this.currentplayseason = currentSeason;
        this.episodeID = episodeID;
        Log.e("episode", "episode id = " + this.episodeID);
        FilmApi.download(this, FilmPreferences.getInstance().getAccessToken(), this.mFilmId, episodeID, true, this.countLink).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new getEpisode());
    }

    class getEpisode implements Consumer<JsonElement> {
        getEpisode() {
        }

        @Override
        public void accept(JsonElement jsonElement) throws Exception {
            if (JsonUtils.checkJson(jsonElement)) {
                try {
                    Debug.e(jsonElement.getAsJsonObject().get("data").toString());
                    mCurrentEpisode = (EpisodeDetail) new Gson().fromJson(jsonElement.getAsJsonObject().get("data"), EpisodeDetail.class);
                    Log.e("mCurrentEpisode", "mCurrentEpisode = " + mCurrentEpisode.getServers());
                    if (mCurrentEpisode.getDownload().size() > 0) {
                        playVideo();
                    } else {
                        Toast.makeText(PlayerActivity.this, "link error1", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Debug.e(e.toString());
                    Toast.makeText(PlayerActivity.this, "link error2" + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void playVideo() {
        if (this.mCurrentEpisode != null) {
            if (this.mCurrentEpisode.getType().equals("drive.google") && ((Video) this.mCurrentEpisode.getDownload().get(0)).getUrl().startsWith("https://drive.google.com")) {
                this.mAsyncHttpClient.get(PlayerActivity.this, ((Video) this.mCurrentEpisode.getDownload().get(0)).getUrl(), new genLink());
            } else {
                playLinkVideo(((Video) this.mCurrentEpisode.getDownload().get(this.currentIndexQuality)).getUrl());
            }
            Log.wtf("", "PlayLink " + ((Video) this.mCurrentEpisode.getDownload().get(0)).getUrl());
        }
    }

    class genLink extends AsyncHttpResponseHandler {
        genLink() {
        }

        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String html = new String(responseBody);
            int startLink = html.indexOf("fmt_stream_map");
            int startLabelLink = html.indexOf("fmt_list");
            if (startLink > 0 && startLabelLink > 0) {
                List<Video> videos = new ArrayList();
                int endLink = html.indexOf("\"", startLink + 17);
                int endLabelLink = html.indexOf("\"", startLabelLink + 11);
                String[] links = html.substring(startLink + 17, endLink).split(",");
                String[] labels = html.substring(startLabelLink + 11, endLabelLink).split(",");
                for (int i = 0; i < links.length; i++) {
                    Log.wtf("link", links[i]);
                    String[] parts = links[i].split("\\|");
                    int itag = Integer.parseInt(parts[0]);
                    String link = StringEscapeUtils.unescapeJava(parts[1]);
                    String quality = labels[i].split("/")[1];
                    Video video = new Video();
                    video.setItag(itag);
                    video.setQuality(quality);
                    video.setUrl(link);
                    videos.add(video);
                }
                mCurrentEpisode.setDownload(videos);
                playLinkVideo((mCurrentEpisode.getDownload().get(currentIndexQuality)).getUrl());
            }
        }

        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        }
    }

    private void playLinkVideo(String url) {
        this.mUrl = url;
        boolean needNewPlayer = player == null;
        if (needNewPlayer) {
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
            trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
            lastSeenTrackGroupArray = null;
            eventLogger = new EventLogger(trackSelector);


            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            player.addListener(new PlayerEventListener());
            player.addListener(eventLogger);
            player.addMetadataOutput(eventLogger);
            player.addAudioDebugListener(eventLogger);
            player.addVideoDebugListener(eventLogger);
            player.setPlayWhenReady(shouldAutoPlay);

            playerView.setPlayer(player);
            playerView.setPlaybackPreparer(this);
//            debugViewHelper = new DebugTextViewHelper(player, debugTextView);
//            debugViewHelper.start();
        }
        Uri uris = Uri.parse(mUrl);
        String extensions = null;
        if (Util.maybeRequestReadExternalStoragePermission(this, uris)) {
            // The player will be reinitialized if the permission is granted.
            return;
        }
        MediaSource mediaSource = buildMediaSource(uris, inferContentType(Uri.parse(this.mUrl), getExtension(this.mUrl)), mainHandler, eventLogger);
        boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
        if (haveResumePosition) {
            player.seekTo(resumeWindow, resumePosition);
        }
        player.prepare(mediaSource, !haveResumePosition, false);
        inErrorState = false;
    }

    private static int inferContentType(Uri uri, String fileExtension) {
        String lastPathSegment;
        if (TextUtils.isEmpty(fileExtension)) {
            lastPathSegment = uri.getLastPathSegment();
        } else {
            lastPathSegment = "." + fileExtension;
        }
        return inferContentType(lastPathSegment);
    }

    @Override
    public void onBackPressed() {
        if (is_showepisode) {
            is_showepisode = false;
            rl_episode_container.setVisibility(View.GONE);
            rl_navigation_button_container.setVisibility(View.VISIBLE);
            bt_episode.requestFocus();
            return;
        }
        if (controler.getVisibility() == View.VISIBLE) {
            controler.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static int inferContentType(String fileName) {
        if (fileName == null) {
            return 3;
        }
        if (fileName.endsWith(".ism")) {
            return 1;
        }
        if (fileName.endsWith(".m3u8")) {
            return 2;
        }
        return 3;
    }

    public String getExtension(String url) {
        String extension = "";
        if (url.contains(".")) {
            return url.substring(url.lastIndexOf("."));
        }
        return extension;
    }

    private void findEpisode() {
        int numberSeason = this.seasons.size();
        for (int i = 0; i < numberSeason; i++) {
            for (int j = 0; j < ((Season) this.seasons.get(i)).getContents().size(); j++) {
                if (((Episode) ((Season) this.seasons.get(i)).getContents().get(j)).getId().equals(this.episodeID)) {
                    this.currentplayseason = i;
                    return;
                }
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        releasePlayer();
        shouldAutoPlay = true;
        clearResumePosition();
        setIntent(intent);
    }


    @Override
    public void onResume() {
        super.onResume();
        loadDetailFilm();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadDetailFilm();
        } else {
            showToast(R.string.storage_permission_denied);
            finish();
        }
    }

    // Activity input

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // See whether the player view wants to handle media or DPAD keys events.
        return playerView.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    // OnClickListener methods

    @Override
    public void onClick(View view) {
    }

    // PlaybackControlView.PlaybackPreparer implementation

    @Override
    public void preparePlayback() {
        loadDetailFilm();
    }

    // PlaybackControlView.VisibilityListener implementation


    // Internal methods


    private MediaSource buildMediaSource(
            Uri uri,
            int overrideExtension,
            @Nullable Handler handler,
            @Nullable MediaSourceEventListener listener) {
        switch (overrideExtension) {
            case 1:
                return new SsMediaSource.Factory(
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory),
                        buildDataSourceFactory(false))
                        .createMediaSource(uri, handler, listener);
            case 2:
                return new HlsMediaSource.Factory(mediaDataSourceFactory)
                        .createMediaSource(uri, handler, listener);
            case 3:
                return new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                        .createMediaSource(uri, handler, listener);
            default: {
                throw new IllegalStateException("Unsupported type: " + overrideExtension);
            }
        }
    }

    private DrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManagerV18(UUID uuid,
                                                                              String licenseUrl, String[] keyRequestPropertiesArray, boolean multiSession)
            throws UnsupportedDrmException {
        HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback(licenseUrl,
                buildHttpDataSourceFactory(false));
        if (keyRequestPropertiesArray != null) {
            for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
                drmCallback.setKeyRequestProperty(keyRequestPropertiesArray[i],
                        keyRequestPropertiesArray[i + 1]);
            }
        }
        return new DefaultDrmSessionManager<>(uuid, FrameworkMediaDrm.newInstance(uuid), drmCallback,
                null, mainHandler, eventLogger, multiSession);
    }

    private void releasePlayer() {
        if (player != null) {
//            debugViewHelper.stop();
//            debugViewHelper = null;
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
            trackSelector = null;
            trackSelectionHelper = null;
            eventLogger = null;
        }
    }

    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = Math.max(0, player.getContentPosition());
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    /**
     * Returns a new DataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return ((FilmApplication) getApplication())
                .buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    /**
     * Returns a new HttpDataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new HttpDataSource factory.
     */
    private HttpDataSource.Factory buildHttpDataSourceFactory(boolean useBandwidthMeter) {
        return ((FilmApplication) getApplication())
                .buildHttpDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }


    private void showControls() {
        countDownTime.cancel();
        countDownTime.start();
        if (controler.getVisibility() == View.GONE) {
            controler.setVisibility(View.VISIBLE);
            bt_episode.requestFocus(View.FOCUS_DOWN);
        }
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (player != null) {
                int currentTimeExoPlayer = (int) player.getCurrentPosition() / 1000;
                tv_duration.setText(Utils.getStringTime(currentTimeExoPlayer));
                seekBar.setProgress(currentTimeExoPlayer);
            }
            handler.postDelayed(this, 1000);
        }
    };

    private class PlayerEventListener extends Player.DefaultEventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_ENDED) {
                showControls();
                bt_play.setImageDrawable(getResources().getDrawable(R.drawable.icon_play_media));
            }
            if (playbackState == Player.STATE_READY) {
                if (player.getPlayWhenReady()) {
                    showControls();
                    handler.post(runnable);
                    circularProgressBar.setVisibility(View.GONE);
                    long duration = player.getDuration();
                    int secNum = (int) (duration / 1000);
                    String textDur = Utils.getStringTime(secNum);
                    tv_total_duration.setText(textDur);
                    seekBar.setMax(secNum);
                    seekBar.setKeyProgressIncrement(6);
                    if (!ishowHistory) {
                        ishowHistory = true;
                        for (Film film : Utils.getHistory(PlayerActivity.this)) {
                            if (film.getId().equals(mFilm.getId())) {
                                if (film.getCurent_history() > 1000) {
                                    player.setPlayWhenReady(false);
                                    Debug.e("Fuck phet");
                                    showDialogHistory(film);
                                }
                                break;
                            }
                        }
                    }
                    if (player.getPlayWhenReady())
                        creatWachtLog();
                }
            }
            if (playbackState == Player.STATE_BUFFERING)

            {
                circularProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPositionDiscontinuity(@Player.DiscontinuityReason int reason) {
            if (inErrorState) {
                // This will only occur if the user has performed a seek whilst in the error state. Update
                // the resume position so that if the user then retries, playback will resume from the
                // position to which they seeked.
                updateResumePosition();
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            String errorString = null;
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                Exception cause = e.getRendererException();
                if (cause instanceof DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    DecoderInitializationException decoderInitializationException =
                            (DecoderInitializationException) cause;
                    if (decoderInitializationException.decoderName == null) {
                        if (decoderInitializationException.getCause() instanceof DecoderQueryException) {
                            errorString = getString(R.string.error_querying_decoders);
                        } else if (decoderInitializationException.secureDecoderRequired) {
                            errorString = getString(R.string.error_no_secure_decoder,
                                    decoderInitializationException.mimeType);
                        } else {
                            errorString = getString(R.string.error_no_decoder,
                                    decoderInitializationException.mimeType);
                        }
                    } else {
                        errorString = getString(R.string.error_instantiating_decoder,
                                decoderInitializationException.decoderName);
                    }
                }
            }
            if (errorString != null) {
                showToast(errorString);
            }
            inErrorState = true;
            if (isBehindLiveWindow(e)) {
                clearResumePosition();
                loadDetailFilm();
            } else {
                updateResumePosition();
                showControls();
            }
            if (errorString != null)
                Toast.makeText(PlayerActivity.this, "Lỗi " + errorString, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(PlayerActivity.this, "Lỗi hệ thống", Toast.LENGTH_LONG).show();
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray
                trackSelections) {
            if (trackGroups != lastSeenTrackGroupArray) {
                MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_VIDEO)
                            == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast(R.string.error_unsupported_video);
                    }
                    if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_AUDIO)
                            == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast(R.string.error_unsupported_audio);
                    }
                }
                lastSeenTrackGroupArray = trackGroups;
            }
        }

    }

    @SuppressLint("DefaultLocale")
    private void showDialogHistory(Film film) {
        String time = String.format("<i>%d phút %d giây</i>",
                TimeUnit.MILLISECONDS.toMinutes(film.getCurent_history()),
                TimeUnit.MILLISECONDS.toSeconds(film.getCurent_history()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(film.getCurent_history()))
        );
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("THÔNG BÁO")
                .setMessage(Html.fromHtml("<i>" + film.getName_vi() + "</i>" + " bạn đang xem " + time + ", bạn có muốn xem tiếp không?"))
                .setPositiveButton("Xem tiếp",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                player.seekTo(film.getCurent_history());
                                player.setPlayWhenReady(true);
                            }
                        })
                .setNegativeButton("Hủy",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                player.setPlayWhenReady(true);
                            }
                        }).create();
        dialog.setCancelable(false);
        dialog.show();

    }

}
