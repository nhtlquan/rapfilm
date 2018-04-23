package com.example.lequan.film.player;

import android.content.Context;
import android.os.Handler;
import com.example.lequan.film.player.DemoPlayer.RendererBuilder;
import com.google.android.exoplayer.DefaultLoadControl;
import com.google.android.exoplayer.LoadControl;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer.EventListener;
import com.google.android.exoplayer.MediaCodecSelector;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.SampleSource;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.chunk.ChunkSampleSource;
import com.google.android.exoplayer.chunk.FormatEvaluator.AdaptiveEvaluator;
import com.google.android.exoplayer.drm.DrmSessionManager;
import com.google.android.exoplayer.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer.drm.MediaDrmCallback;
import com.google.android.exoplayer.drm.StreamingDrmSessionManager;
import com.google.android.exoplayer.drm.UnsupportedDrmException;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.exoplayer.smoothstreaming.DefaultSmoothStreamingTrackSelector;
import com.google.android.exoplayer.smoothstreaming.SmoothStreamingChunkSource;
import com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifest;
import com.google.android.exoplayer.smoothstreaming.SmoothStreamingManifestParser;
import com.google.android.exoplayer.text.SubtitleParser;
import com.google.android.exoplayer.text.TextTrackRenderer;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.upstream.TransferListener;
import com.google.android.exoplayer.util.ManifestFetcher;
import com.google.android.exoplayer.util.ManifestFetcher.ManifestCallback;
import com.google.android.exoplayer.util.Util;
import java.io.IOException;

public class SmoothStreamingRendererBuilder implements RendererBuilder {
    private static final int AUDIO_BUFFER_SEGMENTS = 54;
    private static final int BUFFER_SEGMENT_SIZE = 65536;
    private static final int LIVE_EDGE_LATENCY_MS = 30000;
    private static final int TEXT_BUFFER_SEGMENTS = 2;
    private static final int VIDEO_BUFFER_SEGMENTS = 200;
    private final Context context;
    private AsyncRendererBuilder currentAsyncBuilder;
    private final MediaDrmCallback drmCallback;
    private final String url;
    private final String userAgent;

    private static final class AsyncRendererBuilder implements ManifestCallback<SmoothStreamingManifest> {
        private boolean canceled;
        private final Context context;
        private final MediaDrmCallback drmCallback;
        private final ManifestFetcher<SmoothStreamingManifest> manifestFetcher;
        private final DemoPlayer player;
        private final String userAgent;

        public AsyncRendererBuilder(Context context, String userAgent, String url, MediaDrmCallback drmCallback, DemoPlayer player) {
            this.context = context;
            this.userAgent = userAgent;
            this.drmCallback = drmCallback;
            this.player = player;
            this.manifestFetcher = new ManifestFetcher(url, new DefaultHttpDataSource(userAgent, null), new SmoothStreamingManifestParser());
        }

        public void init() {
            this.manifestFetcher.singleLoad(this.player.getMainHandler().getLooper(), this);
        }

        public void cancel() {
            this.canceled = true;
        }

        public void onSingleManifestError(IOException exception) {
            if (!this.canceled) {
                this.player.onRenderersError(exception);
            }
        }

        public void onSingleManifest(SmoothStreamingManifest manifest) {
            if (!this.canceled) {
                Handler mainHandler = this.player.getMainHandler();
                LoadControl defaultLoadControl = new DefaultLoadControl(new DefaultAllocator(65536));
                DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter(mainHandler, this.player);
                DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
                if (manifest.protectionElement != null) {
                    if (Util.SDK_INT < 18) {
                        this.player.onRenderersError(new UnsupportedDrmException(1));
                        return;
                    }
                    try {
                        drmSessionManager = StreamingDrmSessionManager.newFrameworkInstance(manifest.protectionElement.uuid, this.player.getPlaybackLooper(), this.drmCallback, null, this.player.getMainHandler(), this.player);
                    } catch (Exception e) {
                        this.player.onRenderersError(e);
                        return;
                    }
                }
                TrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(this.context, new ChunkSampleSource(new SmoothStreamingChunkSource(this.manifestFetcher, DefaultSmoothStreamingTrackSelector.newVideoInstance(this.context, true, false), new DefaultUriDataSource(this.context, (TransferListener) defaultBandwidthMeter, this.userAgent), new AdaptiveEvaluator(defaultBandwidthMeter), 30000), defaultLoadControl, 13107200, mainHandler, this.player, 0), MediaCodecSelector.DEFAULT, 1, HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS, drmSessionManager, true, mainHandler, this.player, 50);
                TrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer((SampleSource) new ChunkSampleSource(new SmoothStreamingChunkSource(this.manifestFetcher, DefaultSmoothStreamingTrackSelector.newAudioInstance(), new DefaultUriDataSource(this.context, (TransferListener) defaultBandwidthMeter, this.userAgent), null, 30000), defaultLoadControl, 3538944, mainHandler, this.player, 1), MediaCodecSelector.DEFAULT, (DrmSessionManager) drmSessionManager, true, mainHandler, (EventListener) this.player, AudioCapabilities.getCapabilities(this.context), 3);
                TrackRenderer textTrackRenderer = new TextTrackRenderer((SampleSource) new ChunkSampleSource(new SmoothStreamingChunkSource(this.manifestFetcher, DefaultSmoothStreamingTrackSelector.newTextInstance(), new DefaultUriDataSource(this.context, (TransferListener) defaultBandwidthMeter, this.userAgent), null, 30000), defaultLoadControl, 131072, mainHandler, this.player, 2), this.player, mainHandler.getLooper(), new SubtitleParser[0]);
                TrackRenderer[] renderers = new TrackRenderer[4];
                renderers[0] = videoRenderer;
                renderers[1] = audioRenderer;
                renderers[2] = textTrackRenderer;
                this.player.onRenderers(renderers, defaultBandwidthMeter);
            }
        }
    }

    public SmoothStreamingRendererBuilder(Context context, String userAgent, String url, MediaDrmCallback drmCallback) {
        this.context = context;
        this.userAgent = userAgent;
        if (!Util.toLowerInvariant(url).endsWith("/manifest")) {
            url = url + "/Manifest";
        }
        this.url = url;
        this.drmCallback = drmCallback;
    }

    public void buildRenderers(DemoPlayer player) {
        this.currentAsyncBuilder = new AsyncRendererBuilder(this.context, this.userAgent, this.url, this.drmCallback, player);
        this.currentAsyncBuilder.init();
    }

    public void cancel() {
        if (this.currentAsyncBuilder != null) {
            this.currentAsyncBuilder.cancel();
            this.currentAsyncBuilder = null;
        }
    }
}
