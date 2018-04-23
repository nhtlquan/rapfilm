package com.example.lequan.film.player;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.CaptioningManager;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lequan.film.Debug;
import com.example.lequan.film.TtmlNode;
import com.example.lequan.film.callback.OnStatePlayBack;
import com.example.lequan.film.common.FilmPreferences;
import com.example.lequan.film.common.JsonUtils;
import com.example.lequan.film.common.Utils;
import com.example.lequan.film.model.ActionSyn;
import com.example.lequan.film.model.Episode;
import com.example.lequan.film.model.EpisodeDetail;
import com.example.lequan.film.model.Film;
import com.example.lequan.film.model.FilmDetail;
import com.example.lequan.film.model.ListFilm;
import com.example.lequan.film.model.Poster;
import com.example.lequan.film.model.Recent;
import com.example.lequan.film.model.Season;
import com.example.lequan.film.model.Video;
import com.example.lequan.film.network.FilmApi;
import com.example.lequan.film.player.DemoPlayer.CaptionListener;
import com.example.lequan.film.player.DemoPlayer.Id3MetadataListener;
import com.example.lequan.film.player.DemoPlayer.Listener;
import com.example.lequan.film.player.DemoPlayer.RendererBuilder;
import com.example.lequan.film.subtitles.Caption;
import com.example.lequan.film.subtitles.FormatSRT;
import com.example.lequan.film.subtitles.TimedTextObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.lequan.film.R;
import com.google.android.exoplayer.AspectRatioFrameLayout;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.MediaCodecTrackRenderer.DecoderInitializationException;
import com.google.android.exoplayer.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioCapabilitiesReceiver;
import com.google.android.exoplayer.drm.UnsupportedDrmException;
import com.google.android.exoplayer.metadata.id3.ApicFrame;
import com.google.android.exoplayer.metadata.id3.GeobFrame;
import com.google.android.exoplayer.metadata.id3.Id3Frame;
import com.google.android.exoplayer.metadata.id3.PrivFrame;
import com.google.android.exoplayer.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer.metadata.id3.TxxxFrame;
import com.google.android.exoplayer.text.CaptionStyleCompat;
import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.text.SubtitleLayout;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.exoplayer.util.Util;
import com.google.android.exoplayer.util.VerboseLogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringEscapeUtils;
import org.mozilla.universalchardet.UniversalDetector;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class PlayerFragment extends Fragment implements Callback, OnClickListener, Listener, CaptionListener, Id3MetadataListener, AudioCapabilitiesReceiver.Listener {
    private static final int ID_OFFSET = 2;
    private static final int MENU_GROUP_TRACKS = 1;
    private static final String TAG = "PlayerFragment";
    private static final CookieManager defaultCookieManager = new CookieManager();
    private AudioCapabilitiesReceiver audioCapabilitiesReceiver;
    private ControlFragment controlFragment;
    private int countLink = 0;
    private Poster cover;
    private int currentIndexQuality = 0;
    private int currentplayseason = 0;
    private long duraton = 0;
    private boolean enableBackgroundAudio;
    private String episodeID;
    private boolean fromRecent = false;
    private boolean isSeason;
    private boolean isTvShow;
    private boolean lastStatePlay = false;
    @BindView(R.id.loading)
    CircularProgressBar loading;
    private AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
    private EpisodeDetail mCuEpisodeDetail;
    private EpisodeDetail mCurrentEpisode;
    private Episode mEpisode;
    private String mFilmId;
    private Film mFilm;
    private String mFilmName;
    private String mFilmName_vi;
    private Handler mPlayerHandler = new Handler();
    private Disposable mSubscriptionGetDetailFilm;
    private SubtitleAsyncTask mTaskParserSubtitle;
    private String mType;
    private String mUrl;
    private OnStatePlayBack onStatePlayBack;
    private DemoPlayer player;
    private boolean playerNeedsPrepare;
    private long playerPosition;
    Runnable playerRunnable = new C06257();
    private ArrayList<Season> seasons = new ArrayList();
    @BindView(R.id.shutter)
    View shutterView;
    private final Runnable subtitle = new SubtitleRunnable();
    private final Handler subtitleDisplayHandler = new Handler();
    private String subtitleInputEncoding = "UTF-8";
    @BindView(R.id.subtitles)
    SubtitleLayout subtitleLayout;
    private TimedTextObject subtitleTimedText;
    @BindView(R.id.subtitle)
    TextView subtitles;
    private boolean subtitlesPlaybackEnabled = true;
    private ArrayList<Film> suggess = new ArrayList();
    @BindView(R.id.surface_view)
    SurfaceView surfaceView;
    private int uiFlags;
    @BindView(R.id.video_frame)
    AspectRatioFrameLayout videoFrame;
    private String year;

    class C06191 implements Consumer<JsonElement> {
        C06191() {
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
                PlayerFragment.this.mType = filmDetail.getType();
                PlayerFragment.this.seasons = filmDetail.getEpisodes();
                PlayerFragment.this.isSeason = filmDetail.isSeason();
                PlayerFragment.this.mFilmName = filmDetail.getName();
                PlayerFragment.this.mFilmName_vi = filmDetail.getName_vi();
                PlayerFragment.this.year = filmDetail.getYear();
                PlayerFragment.this.suggess = filmDetail.getSuggestion();
                PlayerFragment.this.isTvShow = filmDetail.isTv_show();
                PlayerFragment.this.cover = filmDetail.getCoverFilm();
                PlayerFragment.this.getLinkPlay(PlayerFragment.this.episodeID, 0);
                PlayerFragment.this.findEpisode();
            }
        }
    }

    class C06202 implements Consumer<Throwable> {
        C06202() {
        }

        @Override
        public void accept(Throwable throwable) throws Exception {

        }
    }

    class C06213 implements Consumer<JsonElement> {
        C06213() {
        }

        @Override
        public void accept(JsonElement jsonElement) throws Exception {
            if (JsonUtils.checkJson(jsonElement)) {
                try {
                    Debug.e(jsonElement.getAsJsonObject().get("data").toString());
                    PlayerFragment.this.mCurrentEpisode = (EpisodeDetail) new Gson().fromJson(jsonElement.getAsJsonObject().get("data"), EpisodeDetail.class);
                    Log.e("mCurrentEpisode", "mCurrentEpisode = " + PlayerFragment.this.mCurrentEpisode.getServers());
                    if (PlayerFragment.this.mCurrentEpisode.getDownload().size() > 0) {
                        PlayerFragment.this.playVideo();
                    } else {
                        Toast.makeText(PlayerFragment.this.getActivity(), "link error1", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Debug.e(e.toString());
                    Toast.makeText(PlayerFragment.this.getActivity(), "link error2" + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    class C06224 implements Consumer<Throwable> {
        C06224() {
        }


        @Override
        public void accept(Throwable throwable) throws Exception {
            throwable.printStackTrace();
        }
    }

    class C06235 extends AsyncHttpResponseHandler {
        C06235() {
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
                PlayerFragment.this.mCurrentEpisode.setDownload(videos);
                PlayerFragment.this.playLinkVideo(((Video) PlayerFragment.this.mCurrentEpisode.getDownload().get(PlayerFragment.this.currentIndexQuality)).getUrl());
            }
        }

        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        }
    }

    class C06246 implements retrofit2.Callback<JsonElement> {
        C06246() {
        }

        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {

        }
    }

    class C06257 implements Runnable {
        C06257() {
        }

        public void run() {
            if (PlayerFragment.this.player != null) {
                PlayerFragment.this.duraton = PlayerFragment.this.player.getDuration();
                PlayerFragment.this.playerPosition = PlayerFragment.this.player.getCurrentPosition();
                PlayerFragment.this.onStatePlayBack.onProgressChange(PlayerFragment.this.playerPosition, PlayerFragment.this.duraton, PlayerFragment.this.player.getBufferedPercentage());
                PlayerFragment.this.mPlayerHandler.postDelayed(this, 1000);
            }
        }
    }

    class C06279 implements OnMenuItemClickListener {
        C06279() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == 0) {
                VerboseLogUtil.setEnableAllTags(false);
            } else {
                VerboseLogUtil.setEnableAllTags(true);
            }
            return true;
        }
    }

    public class SubtitleAsyncTask extends AsyncTask<Void, Void, Void> {
        private String subtitleURL;

        public SubtitleAsyncTask(String subtitleURL) {
            this.subtitleURL = subtitleURL;
        }

        protected Void doInBackground(Void... params) {
            if (this.subtitleURL != null) {
                Log.e("SubtitleAsyncTask", this.subtitleURL);
                try {
                    URL url = new URL(this.subtitleURL);
                    byte[] bytes = Utils.download(url);
                    String str = "UTF-8";
                    str = getInputEncoding(url);
                    PlayerFragment.this.subtitleTimedText = new FormatSRT().parseFile("", url.openStream(), str);
                    PlayerFragment.this.subtitleDisplayHandler.post(PlayerFragment.this.subtitle);
                } catch (Exception e) {
                    Log.e(getClass().getName(), e.getMessage(), e);
                }
            }
            return null;
        }

        private String getInputEncoding(URL url) {
            InputStream is = null;
            try {
                byte[] buf = new byte[4096];
                is = url.openStream();
                UniversalDetector detector = new UniversalDetector(null);
                while (true) {
                    int nread = is.read(buf);
                    if (nread <= 0 || detector.isDone()) {
                        break;
                    }
                    detector.handleData(buf, 0, nread);
                }
                detector.dataEnd();
                PlayerFragment.this.subtitleInputEncoding = detector.getDetectedCharset();
                detector.reset();
                if (PlayerFragment.this.subtitleInputEncoding != null) {
                    Log.d(getClass().getName(), "Detected encoding = " + PlayerFragment.this.subtitleInputEncoding);
                    String access$1600 = PlayerFragment.this.subtitleInputEncoding;
                    if (is == null) {
                        return access$1600;
                    }
                    try {
                        is.close();
                        return access$1600;
                    } catch (IOException e) {
                        return access$1600;
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e2) {
                    }
                }
                return "";
            } catch (IOException e3) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e4) {
                    }
                }
            } catch (Throwable th) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e5) {
                    }
                }
            }
            return "";
        }
    }

    protected class SubtitleRunnable implements Runnable {
        protected SubtitleRunnable() {
        }

        public void run() {
            if (PlayerFragment.this.player != null) {
                if (PlayerFragment.this.player.getPlaybackState() == 4 && PlayerFragment.this.player.getPlayWhenReady()) {
                    if (hasSubtitles()) {
                        showSubtitles();
                    } else {
                        PlayerFragment.this.subtitlesPlaybackEnabled = false;
                    }
                }
                if (PlayerFragment.this.subtitlesPlaybackEnabled) {
                    PlayerFragment.this.subtitleDisplayHandler.postDelayed(this, 100);
                }
            }
        }

        private void showSubtitles() {
            long start = System.currentTimeMillis();
            int currentPos = (int) PlayerFragment.this.player.getCurrentPosition();
            Collection<Caption> subtitles = PlayerFragment.this.subtitleTimedText.captions.values();
            Log.e("subtitle", "subtitle = " + subtitles.size());
            for (Caption caption : subtitles) {
                if (currentPos >= caption.timeStart && currentPos <= caption.timeEnd) {
                    PlayerFragment.this.onTimedText(caption);
                    return;
                }
            }
            PlayerFragment.this.onTimedText(null);
        }

        protected boolean hasSubtitles() {
            return (PlayerFragment.this.subtitleTimedText == null || PlayerFragment.this.subtitleTimedText.captions == null) ? false : true;
        }
    }

    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    static {
        defaultCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    public void setOnStatePlayBack(OnStatePlayBack onStatePlayBack) {
        Log.e("listener", "setOnStatePlayBack");
        this.onStatePlayBack = onStatePlayBack;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_player, container, false);
        ButterKnife.bind((Object) this, v);
        this.surfaceView.getHolder().addCallback(this);
        if (getActivity().getIntent() != null) {
            Intent bundle = getActivity().getIntent();
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
        Log.e("playerPosition", "playerPositon = " + this.playerPosition);
        if (CookieHandler.getDefault() != defaultCookieManager) {
            CookieHandler.setDefault(defaultCookieManager);
        }
        this.audioCapabilitiesReceiver = new AudioCapabilitiesReceiver(getActivity(), this);
        this.audioCapabilitiesReceiver.register();
        if (checkFilmHistory()) {
            for (int i = 0; i < com.example.lequan.film.Utils.Utils.getHistory(getActivity()).size(); i++) {
                if (this.mFilmId.equals(com.example.lequan.film.Utils.Utils.getHistory(getActivity()).get(i).getId())) {
                    ArrayList<Film> films = com.example.lequan.film.Utils.Utils.getHistory(getActivity());
                    films.remove(i);
                    films.add(0, this.mFilm);
                    ListFilm listFilm = new ListFilm(films);
                    com.example.lequan.film.Utils.Utils.saveHistory(getActivity(), listFilm);
                }
            }
        } else {
            ArrayList<Film> films = com.example.lequan.film.Utils.Utils.getHistory(getActivity());
            films.add(0, this.mFilm);
            ListFilm listFilm = new ListFilm(films);
            com.example.lequan.film.Utils.Utils.saveHistory(getActivity(), listFilm);
        }
        loadDetailFilm();
        return v;
    }

    private boolean checkFilmHistory() {
        boolean isHistory = false;
        for (int i = 0; i < com.example.lequan.film.Utils.Utils.getHistory(getActivity()).size(); i++) {
            if (this.mFilmId.equals(com.example.lequan.film.Utils.Utils.getHistory(getActivity()).get(i).getId())) {
                isHistory = true;
            }
        }
        return isHistory;
    }


    public void onTimedText(Caption text) {
        if (text == null) {
            this.subtitles.setVisibility(View.INVISIBLE);
            return;
        }
        this.subtitles.setText(text.content);
        this.subtitles.setVisibility(View.VISIBLE);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onResume() {
        this.audioCapabilitiesReceiver.register();
        if (this.seasons == null) {
            loadDetailFilm();
        } else if (this.lastStatePlay) {
            playVideo();
            this.lastStatePlay = false;
        }
        super.onResume();
    }

    private void loadDetailFilm() {
        this.mSubscriptionGetDetailFilm = FilmApi.detailFilm(getActivity(), this.mFilmId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C06191(), new C06202());
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

    private void onShown() {
        if (this.player != null) {
            this.player.setBackgrounded(false);
        } else if (!maybeRequestPermission()) {
            preparePlayer(true);
            runSub();
        }
    }

    private void saveRecent() {
        Recent recent = new Recent();
        recent.setId_film(this.mFilmId);
        if (!TextUtils.isEmpty(this.episodeID)) {
            recent.setId_episode(this.episodeID);
        }
        recent.setId_season(this.currentplayseason + "");
        recent.setName(this.mFilmName);
        recent.setName_vi(this.mFilmName_vi);
        if (this.mEpisode == null) {
            recent.setName(this.mFilmName);
        } else if (TextUtils.isEmpty(this.mEpisode.getName())) {
            recent.setName(this.mFilmName);
        } else {
            recent.setName_episode(this.mEpisode.getName());
        }
        if (this.seasons.size() > 0) {
            recent.setName_season((this.currentplayseason + 1) + "");
        } else {
            recent.setName_season("0");
        }
        recent.setYear(this.year);
        recent.setLastDuration(this.playerPosition);
        recent.setDuration(this.duraton);
        recent.setThumb(this.cover.getS960());
        recent.setActionSyn(ActionSyn.ADD);
        JsonObject recentObj = new JsonObject();
        recentObj.addProperty(TtmlNode.ATTR_ID, this.mFilmId);
        recentObj.add("ext", new Gson().toJsonTree(recent));
        recentObj.addProperty("action", recent.getActionSyn().toString());
        saveRecent(recentObj.toString());
    }

    public void onPause() {
        this.audioCapabilitiesReceiver.unregister();
        this.lastStatePlay = true;
        this.mPlayerHandler.removeCallbacks(this.playerRunnable);
        saveRecent();
        super.onPause();
    }

    public void setFromRecent() {
        this.fromRecent = false;
    }

    public void getLinkPlay(String episodeID, int currentSeason) {
        this.currentplayseason = currentSeason;
        this.episodeID = episodeID;
        Log.e("episode", "episode id = " + this.episodeID);
        FilmApi.download(getActivity(), FilmPreferences.getInstance().getAccessToken(), this.mFilmId, episodeID, true, this.countLink).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C06213(), new C06224());
    }

    public void playLinkVideo(String url) {
        this.mUrl = url;
        this.mPlayerHandler.removeCallbacks(this.playerRunnable);
        releasePlayer();
        FragmentTransaction ft2 = getFragmentManager().beginTransaction();
        this.controlFragment = new ControlFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mFilmName", this.mFilmName);
        bundle.putString("mFilmName_vi", this.mFilmName_vi);
        bundle.putParcelable("mCurrentEpisode", this.mCurrentEpisode);
        bundle.putSerializable("seasons", this.seasons);
        bundle.putSerializable("suggess", this.suggess);
        bundle.putInt("currentplayseason", this.currentplayseason);
        bundle.putString("episodeID", this.episodeID);
        this.controlFragment.setArguments(bundle);
        ft2.replace(R.id.videoFragment1, this.controlFragment, "tag123");
        ft2.commit();
        onShown();
    }

    private void playVideo() {
        if (this.mCurrentEpisode != null) {
            if (this.mCurrentEpisode.getType().equals("drive.google") && ((Video) this.mCurrentEpisode.getDownload().get(0)).getUrl().startsWith("https://drive.google.com")) {
                this.mAsyncHttpClient.get(getActivity(), ((Video) this.mCurrentEpisode.getDownload().get(0)).getUrl(), new C06235());
            } else {
                playLinkVideo(((Video) this.mCurrentEpisode.getDownload().get(this.currentIndexQuality)).getUrl());
            }
            Log.wtf("", "PlayLink " + ((Video) this.mCurrentEpisode.getDownload().get(0)).getUrl());
        }
    }

    public void runSub() {
//        if (this.mCurrentEpisode != null && this.mCurrentEpisode.getSubtitleVietnamese() != null && this.mCurrentEpisode.getSubtitleVietnamese().size() > 0 && !TextUtils.isEmpty(((Subtitle) this.mCurrentEpisode.getSubtitleVietnamese().get(0)).getUrl())) {
////            this.mTaskParserSubtitle = new SubtitleAsyncTask(((Subtitle) this.mCurrentEpisode.getSubtitleVietnamese().get(0)).getUrl());
//            this.mTaskParserSubtitle.execute(new Void[0]);
//        }
    }

    private void saveRecent(String content) {
//        FilmApi.userRecent(getActivity(), FilmPreferences.getInstance().getUserId(), content, new C06246());
    }

    public void onStop() {
        if (this.player != null) {
            this.player.setPlayWhenReady(false);
        }
        if (this.mPlayerHandler != null) {
            this.mPlayerHandler.removeCallbacks(this.playerRunnable);
        }
        super.onStop();
    }

    public String getExtension(String url) {
        String extension = "";
        if (url.contains(".")) {
            return url.substring(url.lastIndexOf("."));
        }
        return extension;
    }

    private void onHidden() {
        if (this.enableBackgroundAudio) {
            this.player.setBackgrounded(true);
        } else {
            releasePlayer();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void onDestroyView() {
        if (this.mSubscriptionGetDetailFilm != null) {
            this.mSubscriptionGetDetailFilm.dispose();
        }
        if (this.mAsyncHttpClient != null) {
            this.mAsyncHttpClient.cancelRequests(getActivity(), true);
        }
        super.onDestroyView();
    }

    public void onClick(View view) {
    }

    public void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities) {
        if (this.player != null) {
            boolean backgrounded = this.player.getBackgrounded();
            boolean playWhenReady = this.player.getPlayWhenReady();
            releasePlayer();
            preparePlayer(playWhenReady);
            this.player.setBackgrounded(backgrounded);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length <= 0 || grantResults[0] != 0) {
            Toast.makeText(getActivity(), "ko co permission", Toast.LENGTH_LONG).show();
            getActivity().finish();
            return;
        }
        preparePlayer(true);
    }

    @TargetApi(23)
    private boolean maybeRequestPermission() {
        if (!requiresPermission(Uri.parse(this.mUrl))) {
            return false;
        }
        requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 0);
        return true;
    }

    @TargetApi(23)
    private boolean requiresPermission(Uri uri) {
        return Util.SDK_INT >= 23 && Util.isLocalFileUri(uri) && getActivity().checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_DENIED;
    }

    private RendererBuilder getRendererBuilder(int contentType, Uri contentUri) {
        String userAgent = Util.getUserAgent(getActivity(), "Phim247");
        switch (contentType) {
            case 1:
                return new SmoothStreamingRendererBuilder(getActivity(), userAgent, contentUri.toString(), new SmoothStreamingTestMediaDrmCallback());
            case 2:
                return new HlsRendererBuilder(getActivity(), userAgent, contentUri.toString());
            case 3:
                return new ExtractorRendererBuilder(getActivity(), userAgent, contentUri);
            default:
                throw new IllegalStateException("Unsupported type: " + contentType);
        }
    }

    private void preparePlayer(boolean playWhenReady) {
        if (this.player == null) {
            this.player = new DemoPlayer(getRendererBuilder(inferContentType(Uri.parse(this.mUrl), getExtension(this.mUrl)), Uri.parse(this.mUrl)));
            this.player.addListener(this);
            this.player.setCaptionListener(this);
            this.player.setMetadataListener(this);
            this.player.seekTo(this.playerPosition);
            this.playerNeedsPrepare = true;
        }
        if (this.playerNeedsPrepare) {
            this.player.prepare();
            this.playerNeedsPrepare = false;
        }
        this.player.setSurface(this.surfaceView.getHolder().getSurface());
        this.player.setPlayWhenReady(playWhenReady);
    }

    public void releasePlayer() {
        if (this.player != null) {
            this.player.release();
            if (!this.fromRecent) {
                this.playerPosition = 0;
            }
            this.player = null;
        }
    }

    public void onStateChanged(boolean playWhenReady, int playbackState) {
        String text;
        if (playbackState == 5) {
            text = "playWhenReady=" + playWhenReady + ", playbackState=";
        } else {
            text = "playWhenReady=" + playWhenReady + ", playbackState=";
        }
        switch (playbackState) {
            case 1:
                text = text + "idle";
                this.loading.setVisibility(View.VISIBLE);
                break;
            case 2:
                text = text + "preparing";
                this.loading.setVisibility(View.VISIBLE);
                break;
            case 3:
                text = text + "buffering";
                this.loading.setVisibility(View.VISIBLE);
                break;
            case 4:
                text = text + "ready";
                this.loading.setVisibility(View.GONE);
                this.mPlayerHandler.post(this.playerRunnable);
                break;
            case 5:
                text = text + "ended";
                releasePlayer();
                if (this.controlFragment != null) {
                    int posE = this.controlFragment.getCurrentNextPosEpisode();
                    if (posE != 1000000) {
                        Episode episode = (Episode) ((Season) this.seasons.get(this.currentplayseason)).getContents().get(posE);
                        if (episode != null) {
                            this.episodeID = episode.getId();
                            getLinkPlay(this.episodeID, this.currentplayseason);
                        }
                    }
                }
                this.loading.setVisibility(View.GONE);
                break;
            default:
                this.loading.setVisibility(View.VISIBLE);
                text = text + EnvironmentCompat.MEDIA_UNKNOWN;
                break;
        }
        if (this.onStatePlayBack != null) {
            this.onStatePlayBack.onStatePlayBack(playbackState, playWhenReady);
        }
    }

    public void onError(Exception e) {
        String errorString = null;
        if (e instanceof UnsupportedDrmException) {
            int i = Util.SDK_INT < 18 ? R.string.error_drm_not_supported : ((UnsupportedDrmException) e).reason == 1 ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown;
            errorString = getString(i);
        } else if ((e instanceof ExoPlaybackException) && (e.getCause() instanceof DecoderInitializationException)) {
            DecoderInitializationException decoderInitializationException = (DecoderInitializationException) e.getCause();
            errorString = decoderInitializationException.decoderName == null ? decoderInitializationException.getCause() instanceof DecoderQueryException ? getString(R.string.error_querying_decoders) : decoderInitializationException.secureDecoderRequired ? getString(R.string.error_no_secure_decoder, new Object[]{decoderInitializationException.mimeType}) : getString(R.string.error_no_decoder, new Object[]{decoderInitializationException.mimeType}) : getString(R.string.error_instantiating_decoder, new Object[]{decoderInitializationException.decoderName});
        }
        if (errorString != null) {
            Toast.makeText(getActivity(), errorString, 1).show();
        }
        this.playerNeedsPrepare = true;
        onError();
    }

    public void onError() {
        if (this.mCuEpisodeDetail == null) {
            return;
        }
        if (this.countLink < this.mCuEpisodeDetail.getTotal_server()) {
            this.countLink++;
            getLinkPlay(this.episodeID, 0);
            return;
        }
        Toast.makeText(getActivity(), "Server lỗi, vui lòng chọn tập khác", 0).show();
    }

    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthAspectRatio) {
        this.shutterView.setVisibility(8);
        this.videoFrame.setAspectRatio(height == 0 ? 1.0f : (((float) width) * pixelWidthAspectRatio) / ((float) height));
    }

    private boolean haveTracks(int type) {
        return this.player != null && this.player.getTrackCount(type) > 0;
    }

    public void showVideoPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        configurePopupWithTracks(popup, null, 0);
        popup.show();
    }

    public void showAudioPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        Menu menu = popup.getMenu();
        menu.add(0, 0, 0, R.string.enable_background_audio);
        final MenuItem backgroundAudioItem = menu.findItem(0);
        backgroundAudioItem.setCheckable(true);
        backgroundAudioItem.setChecked(this.enableBackgroundAudio);
        configurePopupWithTracks(popup, new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                boolean z = false;
                if (item != backgroundAudioItem) {
                    return false;
                }
                PlayerFragment playerFragment = PlayerFragment.this;
                if (!item.isChecked()) {
                    z = true;
                }
                playerFragment.enableBackgroundAudio = z;
                return true;
            }
        }, 1);
        popup.show();
    }

    public void showTextPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        configurePopupWithTracks(popup, null, 2);
        popup.show();
    }

    public void showVerboseLogPopup(View v) {
        int i = 0;
        PopupMenu popup = new PopupMenu(getActivity(), v);
        Menu menu = popup.getMenu();
        menu.add(0, 0, 0, R.string.logging_normal);
        menu.add(0, 1, 0, R.string.logging_verbose);
        menu.setGroupCheckable(0, true, true);
        if (VerboseLogUtil.areAllTagsEnabled()) {
            i = 1;
        }
        menu.findItem(i).setChecked(true);
        popup.setOnMenuItemClickListener(new C06279());
        popup.show();
    }

    private void configurePopupWithTracks(PopupMenu popup, final OnMenuItemClickListener customActionClickListener, final int trackType) {
        if (this.player != null) {
            int trackCount = this.player.getTrackCount(trackType);
            if (trackCount != 0) {
                popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        return (customActionClickListener != null && customActionClickListener.onMenuItemClick(item)) || PlayerFragment.this.onTrackItemClick(item, trackType);
                    }
                });
                Menu menu = popup.getMenu();
                menu.add(1, 1, 0, R.string.off);
                for (int i = 0; i < trackCount; i++) {
                    menu.add(1, i + 2, 0, buildTrackName(this.player.getTrackFormat(trackType, i)));
                }
                menu.setGroupCheckable(1, true, true);
                menu.findItem(this.player.getSelectedTrack(trackType) + 2).setChecked(true);
            }
        }
    }

    private static String buildTrackName(MediaFormat format) {
        if (format.adaptive) {
            return "auto";
        }
        String trackName;
        if (MimeTypes.isVideo(format.mimeType)) {
            trackName = joinWithSeparator(joinWithSeparator(buildResolutionString(format), buildBitrateString(format)), buildTrackIdString(format));
        } else if (MimeTypes.isAudio(format.mimeType)) {
            trackName = joinWithSeparator(joinWithSeparator(joinWithSeparator(buildLanguageString(format), buildAudioPropertyString(format)), buildBitrateString(format)), buildTrackIdString(format));
        } else {
            trackName = joinWithSeparator(joinWithSeparator(buildLanguageString(format), buildBitrateString(format)), buildTrackIdString(format));
        }
        if (trackName.length() == 0) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        return trackName;
    }

    private static String buildResolutionString(MediaFormat format) {
        return (format.width == -1 || format.height == -1) ? "" : format.width + "x" + format.height;
    }

    private static String buildAudioPropertyString(MediaFormat format) {
        return (format.channelCount == -1 || format.sampleRate == -1) ? "" : format.channelCount + "ch, " + format.sampleRate + "Hz";
    }

    private static String buildLanguageString(MediaFormat format) {
        return (TextUtils.isEmpty(format.language) || "und".equals(format.language)) ? "" : format.language;
    }

    private static String buildBitrateString(MediaFormat format) {
        if (format.bitrate == -1) {
            return "";
        }
        return String.format(Locale.US, "%.2fMbit", new Object[]{Float.valueOf(((float) format.bitrate) / 1000000.0f)});
    }

    private static String joinWithSeparator(String first, String second) {
        if (first.length() == 0) {
            return second;
        }
        return second.length() == 0 ? first : first + ", " + second;
    }

    private static String buildTrackIdString(MediaFormat format) {
        return format.trackId == null ? "" : " (" + format.trackId + ")";
    }

    private boolean onTrackItemClick(MenuItem item, int type) {
        if (this.player == null || item.getGroupId() != 1) {
            return false;
        }
        this.player.setSelectedTrack(type, item.getItemId() - 2);
        return true;
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

    public void onCues(List<Cue> cues) {
        this.subtitleLayout.setCues(cues);
    }

    public void onId3Metadata(List<Id3Frame> id3Frames) {
        for (Id3Frame id3Frame : id3Frames) {
            if (id3Frame instanceof TxxxFrame) {
                TxxxFrame txxxFrame = (TxxxFrame) id3Frame;
                Log.i(TAG, String.format("ID3 TimedMetadata %s: description=%s, value=%s", new Object[]{txxxFrame.id, txxxFrame.description, txxxFrame.value}));
            } else if (id3Frame instanceof PrivFrame) {
                PrivFrame privFrame = (PrivFrame) id3Frame;
                Log.i(TAG, String.format("ID3 TimedMetadata %s: owner=%s", new Object[]{privFrame.id, privFrame.owner}));
            } else if (id3Frame instanceof GeobFrame) {
                GeobFrame geobFrame = (GeobFrame) id3Frame;
                Log.i(TAG, String.format("ID3 TimedMetadata %s: mimeType=%s, filename=%s, description=%s", new Object[]{geobFrame.id, geobFrame.mimeType, geobFrame.filename, geobFrame.description}));
            } else if (id3Frame instanceof ApicFrame) {
                ApicFrame apicFrame = (ApicFrame) id3Frame;
                Log.i(TAG, String.format("ID3 TimedMetadata %s: mimeType=%s, description=%s", new Object[]{apicFrame.id, apicFrame.mimeType, apicFrame.description}));
            } else if (id3Frame instanceof TextInformationFrame) {
                TextInformationFrame textInformationFrame = (TextInformationFrame) id3Frame;
                Log.i(TAG, String.format("ID3 TimedMetadata %s: description=%s", new Object[]{textInformationFrame.id, textInformationFrame.description}));
            } else {
                Log.i(TAG, String.format("ID3 TimedMetadata %s", new Object[]{id3Frame.id}));
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (this.player != null) {
            this.player.setSurface(holder.getSurface());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.player != null) {
            this.player.blockingClearSurface();
        }
    }

    private void configureSubtitleView() {
        CaptionStyleCompat style;
        float fontScale;
        if (Util.SDK_INT >= 19) {
            style = getUserCaptionStyleV19();
            fontScale = getUserCaptionFontScaleV19();
        } else {
            style = CaptionStyleCompat.DEFAULT;
            fontScale = 1.0f;
        }
        this.subtitleLayout.setStyle(style);
        this.subtitleLayout.setFractionalTextSize(SubtitleLayout.DEFAULT_TEXT_SIZE_FRACTION * fontScale);
    }

    @TargetApi(19)
    private float getUserCaptionFontScaleV19() {
        return ((CaptioningManager) getActivity().getSystemService("captioning")).getFontScale();
    }

    @TargetApi(19)
    private CaptionStyleCompat getUserCaptionStyleV19() {
        return CaptionStyleCompat.createFromCaptionStyle(((CaptioningManager) getActivity().getSystemService("captioning")).getUserStyle());
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

    public void playOrPause(int action) {
        if (action == 0 && this.player != null) {
            if (this.player.getPlayWhenReady()) {
                this.player.setPlayWhenReady(false);
            } else {
                this.player.setPlayWhenReady(true);
            }
        }
    }

    public void nextEpisode() {
    }

    public void prevEpisode() {
    }

    public void fast15s() {
        if (this.player == null) {
            return;
        }
        if (this.player.getCurrentPosition() + 30000 < this.player.getDuration()) {
            this.player.seekTo(this.player.getCurrentPosition() + 30000);
        } else {
            this.player.seekTo(this.player.getDuration());
        }
    }

    public void rewind15s() {
        if (this.player == null) {
            return;
        }
        if (this.player.getCurrentPosition() - 30000 > 0) {
            this.player.seekTo(this.player.getCurrentPosition() - 30000);
        } else {
            this.player.seekTo(0);
        }
    }

    public boolean isPlay() {
        if (this.player != null) {
            return this.player.getPlayWhenReady();
        }
        return true;
    }
}
