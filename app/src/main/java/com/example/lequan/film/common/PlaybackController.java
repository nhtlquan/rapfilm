package com.example.lequan.film.common;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSession.Callback;
import android.media.session.PlaybackState.Builder;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;
import com.example.lequan.film.model.EpisodeDetail;
import com.example.lequan.film.model.Movie;
import com.example.lequan.film.model.Video;
import java.util.ArrayList;

public class PlaybackController {
    private static final String MEDIA_SESSION_TAG = "AndroidTVappTutorialSession";
    public static final int MSG_FAST_FORWARD = 6;
    public static final int MSG_PAUSE = 1;
    public static final int MSG_PLAY = 2;
    public static final int MSG_PLAY_FROM_MEDIA_ID = 10;
    public static final int MSG_PLAY_FROM_SEARCH = 11;
    public static final int MSG_PLAY_PAUSE = 9;
    public static final int MSG_REWIND = 3;
    public static final int MSG_SEEK_TO = 8;
    public static final int MSG_SET_RATING = 7;
    public static final int MSG_SKIP_TO_NEXT = 5;
    public static final int MSG_SKIP_TO_PREVIOUS = 4;
    public static final int MSG_SKIP_TO_QUEUE_ITEM = 12;
    public static final int MSG_STOP = 0;
    private static final String TAG = PlaybackController.class.getSimpleName();
    private static ArrayList<Movie> mItems;
    private EpisodeDetail episodeDetail;
    private Activity mActivity;
    private int mCurrentItem;
    private int mCurrentPlaybackState = 0;
    private long mDuration = -1;
    private MediaSessionCallback mMediaSessionCallback;
    private int mPosition = 0;
    private MediaSession mSession;
    private long mStartTimeMillis;
    private VideoView mVideoView;

    class C05381 implements OnErrorListener {
        C05381() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            PlaybackController.this.mVideoView.stopPlayback();
            PlaybackController.this.mCurrentPlaybackState = 0;
            return false;
        }
    }

    class C05392 implements OnPreparedListener {
        C05392() {
        }

        public void onPrepared(MediaPlayer mp) {
            if (PlaybackController.this.mCurrentPlaybackState == 3) {
                PlaybackController.this.mVideoView.start();
            }
        }
    }

    class C05403 implements OnCompletionListener {
        C05403() {
        }

        public void onCompletion(MediaPlayer mp) {
            PlaybackController.this.mCurrentPlaybackState = 0;
        }
    }

    private class MediaSessionCallback extends Callback {
        private MediaSessionCallback() {
        }

        public void onPlay() {
            PlaybackController.this.playPause(true);
        }

        public void onPause() {
            PlaybackController.this.playPause(false);
        }

        public void onSkipToNext() {
            boolean z = false;
            if (PlaybackController.access$304(PlaybackController.this) >= PlaybackController.mItems.size()) {
                PlaybackController.this.mCurrentItem = 0;
            }
            Log.d(PlaybackController.TAG, "onSkipToNext: " + PlaybackController.this.mCurrentItem);
            Movie movie = (Movie) PlaybackController.mItems.get(PlaybackController.this.mCurrentItem);
            if (movie != null) {
                PlaybackController.this.setVideoPath(movie.getVideoUrl());
                PlaybackController.this.updateMetadata();
                PlaybackController playbackController = PlaybackController.this;
                if (PlaybackController.this.mCurrentPlaybackState == 3) {
                    z = true;
                }
                playbackController.playPause(z);
                return;
            }
            Log.e(PlaybackController.TAG, "onSkipToNext movie is null!");
        }

        public void onSkipToPrevious() {
            if (PlaybackController.access$306(PlaybackController.this) < 0) {
                PlaybackController.this.mCurrentItem = PlaybackController.mItems.size() - 1;
            }
            Movie movie = (Movie) PlaybackController.mItems.get(PlaybackController.this.mCurrentItem);
            if (movie != null) {
                PlaybackController.this.setVideoPath(movie.getVideoUrl());
                PlaybackController.this.updateMetadata();
                PlaybackController.this.playPause(PlaybackController.this.mCurrentPlaybackState == 3);
                return;
            }
            Log.e(PlaybackController.TAG, "onSkipToPrevious movie is null!");
        }

        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            PlaybackController.this.mCurrentItem = Integer.parseInt(mediaId);
            Movie movie = (Movie) PlaybackController.mItems.get(PlaybackController.this.mCurrentItem);
            if (movie != null) {
                PlaybackController.this.setVideoPath(movie.getVideoUrl());
                PlaybackController.this.updateMetadata();
                PlaybackController.this.playPause(PlaybackController.this.mCurrentPlaybackState == 3);
            }
        }

        public void onSeekTo(long pos) {
            PlaybackController.this.setPosition((int) pos);
            PlaybackController.this.mVideoView.seekTo(PlaybackController.this.mPosition);
            PlaybackController.this.updatePlaybackState();
        }

        public void onFastForward() {
            PlaybackController.this.fastForward();
        }

        public void onRewind() {
            PlaybackController.this.rewind();
        }
    }

    static /* synthetic */ int access$304(PlaybackController x0) {
        int i = x0.mCurrentItem + 1;
        x0.mCurrentItem = i;
        return i;
    }

    static /* synthetic */ int access$306(PlaybackController x0) {
        int i = x0.mCurrentItem - 1;
        x0.mCurrentItem = i;
        return i;
    }

    public int getCurrentPlaybackState() {
        return this.mCurrentPlaybackState;
    }

    public void setCurrentPlaybackState(int currentPlaybackState) {
        this.mCurrentPlaybackState = currentPlaybackState;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public int getCurrentItem() {
        return this.mCurrentItem;
    }

    public PlaybackController(Activity activity) {
        this.mActivity = activity;
        createMediaSession(this.mActivity);
    }

    public PlaybackController(Activity activity, int currentItemIndex, ArrayList<Movie> items) {
        this.mActivity = activity;
        setPlaylist(currentItemIndex, items);
        createMediaSession(this.mActivity);
    }

    public void setPlaylist(int currentItemIndex, ArrayList<Movie> items) {
        this.mCurrentItem = currentItemIndex;
        mItems = items;
        if (mItems == null) {
            Log.e(TAG, "mItems null!!");
        }
    }

    private void createMediaSession(Activity activity) {
        if (this.mSession == null) {
            this.mSession = new MediaSession(activity, MEDIA_SESSION_TAG);
            this.mMediaSessionCallback = new MediaSessionCallback();
            this.mSession.setCallback(this.mMediaSessionCallback);
            this.mSession.setFlags(3);
            this.mSession.setActive(true);
            activity.setMediaController(new MediaController(activity, this.mSession.getSessionToken()));
        }
    }

    public MediaSessionCallback getMediaSessionCallback() {
        return this.mMediaSessionCallback;
    }

    public void setVideoView(VideoView videoView) {
        this.mVideoView = videoView;
        setupCallbacks();
    }

    public void setMovie(EpisodeDetail episodeDetail) {
        this.episodeDetail = episodeDetail;
    }

    public void setVideoPath(String videoUrl) {
        setPosition(0);
        this.mVideoView.setVideoPath(videoUrl);
        this.mStartTimeMillis = 0;
        this.mDuration = Utils.getDuration(videoUrl);
    }

    public void setPosition(int position) {
        if (((long) position) > this.mDuration) {
            Log.d(TAG, "position: " + position + ", mDuration: " + this.mDuration);
            this.mPosition = (int) this.mDuration;
        } else if (position < 0) {
            this.mPosition = 0;
            this.mStartTimeMillis = System.currentTimeMillis();
        } else {
            this.mPosition = position;
        }
        this.mStartTimeMillis = System.currentTimeMillis();
        Log.d(TAG, "position set to " + this.mPosition);
    }

    public int getPosition() {
        return this.mPosition;
    }

    public int getCurrentPosition() {
        return this.mVideoView.getCurrentPosition();
    }

    private void updatePlaybackState() {
        Builder stateBuilder = new Builder().setActions(getAvailableActions());
        int state = 3;
        if (this.mCurrentPlaybackState == 2 || this.mCurrentPlaybackState == 0) {
            state = 2;
        }
        stateBuilder.setState(state, (long) getCurrentPosition(), 1.0f);
        this.mSession.setPlaybackState(stateBuilder.build());
    }

    private long getAvailableActions() {
        return 3710;
    }

    public void finishPlayback() {
        if (this.mVideoView != null) {
            this.mVideoView.stopPlayback();
            this.mVideoView.suspend();
            this.mVideoView.setVideoURI(null);
        }
        releaseMediaSession();
    }

    public int getBufferPercentage() {
        return this.mVideoView.getBufferPercentage();
    }

    public int calcBufferedTime(int currentTime) {
        return currentTime + (((int) ((this.mDuration - ((long) currentTime)) * ((long) getBufferPercentage()))) / 100);
    }

    private void setupCallbacks() {
        this.mVideoView.setOnErrorListener(new C05381());
        this.mVideoView.setOnPreparedListener(new C05392());
        this.mVideoView.setOnCompletionListener(new C05403());
    }

    public void setCurrentItem(int currentItem) {
        Log.v(TAG, "setCurrentItem: " + currentItem);
        this.mCurrentItem = currentItem;
    }

    public void updateMetadata() {
        Log.i(TAG, "updateMetadata: getCurrentItem" + getCurrentItem());
        this.mDuration = Utils.getDuration(((Video) this.episodeDetail.getDownload().get(0)).getUrl());
    }

    public void releaseMediaSession() {
        if (this.mSession != null) {
            this.mSession.release();
        }
    }

    public void playPause(boolean doPlay) {
        if (this.mCurrentPlaybackState == 0) {
            setupCallbacks();
        }
        if (doPlay) {
            Log.d(TAG, "playPause: play");
            if (this.mCurrentPlaybackState != 3) {
                this.mCurrentPlaybackState = 3;
                this.mVideoView.start();
                this.mStartTimeMillis = System.currentTimeMillis();
            } else {
                return;
            }
        }
        Log.d(TAG, "playPause: pause");
        if (this.mCurrentPlaybackState != 2) {
            this.mCurrentPlaybackState = 2;
            setPosition(this.mVideoView.getCurrentPosition());
            this.mVideoView.pause();
        } else {
            return;
        }
        updatePlaybackState();
    }

    public void fastForward() {
        if (this.mDuration != -1) {
            setPosition(getCurrentPosition() + 10000);
            this.mVideoView.seekTo(this.mPosition);
        }
    }

    public void rewind() {
        setPosition(getCurrentPosition() - 10000);
        this.mVideoView.seekTo(this.mPosition);
    }
}
