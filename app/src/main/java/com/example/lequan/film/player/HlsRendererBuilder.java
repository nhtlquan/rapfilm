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
import com.google.android.exoplayer.hls.DefaultHlsTrackSelector;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.exoplayer.hls.HlsMasterPlaylist;
import com.google.android.exoplayer.hls.HlsPlaylist;
import com.google.android.exoplayer.hls.HlsPlaylistParser;
import com.google.android.exoplayer.hls.HlsSampleSource;
import com.google.android.exoplayer.hls.PtsTimestampAdjusterProvider;
import com.google.android.exoplayer.metadata.MetadataTrackRenderer;
import com.google.android.exoplayer.metadata.id3.Id3Frame;
import com.google.android.exoplayer.metadata.id3.Id3Parser;
import com.google.android.exoplayer.text.SubtitleParser;
import com.google.android.exoplayer.text.TextTrackRenderer;
import com.google.android.exoplayer.text.eia608.Eia608TrackRenderer;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.upstream.TransferListener;
import com.google.android.exoplayer.util.ManifestFetcher;
import com.google.android.exoplayer.util.ManifestFetcher.ManifestCallback;
import java.io.IOException;
import java.util.List;

public class HlsRendererBuilder implements RendererBuilder {
    private static final int AUDIO_BUFFER_SEGMENTS = 54;
    private static final int BUFFER_SEGMENT_SIZE = 65536;
    private static final int MAIN_BUFFER_SEGMENTS = 254;
    private static final int TEXT_BUFFER_SEGMENTS = 2;
    private final Context context;
    private AsyncRendererBuilder currentAsyncBuilder;
    private final String url;
    private final String userAgent;

    private static final class AsyncRendererBuilder implements ManifestCallback<HlsPlaylist> {
        private boolean canceled;
        private final Context context;
        private final DemoPlayer player;
        private final ManifestFetcher<HlsPlaylist> playlistFetcher;
        private final String userAgent;

        public AsyncRendererBuilder(Context context, String userAgent, String url, DemoPlayer player) {
            this.context = context;
            this.userAgent = userAgent;
            this.player = player;
            this.playlistFetcher = new ManifestFetcher(url, new DefaultUriDataSource(context, userAgent), new HlsPlaylistParser());
        }

        public void init() {
            this.playlistFetcher.singleLoad(this.player.getMainHandler().getLooper(), this);
        }

        public void cancel() {
            this.canceled = true;
        }

        public void onSingleManifestError(IOException e) {
            if (!this.canceled) {
                this.player.onRenderersError(e);
            }
        }

        public void onSingleManifest(HlsPlaylist manifest) {
            if (!this.canceled) {
                MediaCodecAudioTrackRenderer audioRenderer;
                Handler mainHandler = this.player.getMainHandler();
                LoadControl loadControl = new DefaultLoadControl(new DefaultAllocator(65536));
                DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                PtsTimestampAdjusterProvider timestampAdjusterProvider = new PtsTimestampAdjusterProvider();
                boolean haveSubtitles = false;
                boolean haveAudios = false;
                if (manifest instanceof HlsMasterPlaylist) {
                    HlsMasterPlaylist masterPlaylist = (HlsMasterPlaylist) manifest;
                    haveSubtitles = !masterPlaylist.subtitles.isEmpty();
                    haveAudios = !masterPlaylist.audios.isEmpty();
                }
                HlsSampleSource sampleSource = new HlsSampleSource(new HlsChunkSource(true, new DefaultUriDataSource(this.context, bandwidthMeter, this.userAgent), manifest, DefaultHlsTrackSelector.newDefaultInstance(this.context), bandwidthMeter, timestampAdjusterProvider), loadControl, 16646144, mainHandler, this.player, 0);
                MediaCodecVideoTrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(this.context, sampleSource, MediaCodecSelector.DEFAULT, 1, HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS, mainHandler, this.player, 50);
                MetadataTrackRenderer<List<Id3Frame>> metadataTrackRenderer = new MetadataTrackRenderer(sampleSource, new Id3Parser(), this.player, mainHandler.getLooper());
                if (haveAudios) {
                    HlsSampleSource audioSampleSource = new HlsSampleSource(new HlsChunkSource(false, new DefaultUriDataSource(this.context, bandwidthMeter, this.userAgent), manifest, DefaultHlsTrackSelector.newAudioInstance(), bandwidthMeter, timestampAdjusterProvider), loadControl, 3538944, mainHandler, this.player, 1);
                    audioRenderer = new MediaCodecAudioTrackRenderer(new SampleSource[]{sampleSource, audioSampleSource}, MediaCodecSelector.DEFAULT, null, true, this.player.getMainHandler(), (EventListener) this.player, AudioCapabilities.getCapabilities(this.context), 3);
                } else {
                    audioRenderer = new MediaCodecAudioTrackRenderer((SampleSource) sampleSource, MediaCodecSelector.DEFAULT, null, true, this.player.getMainHandler(), (EventListener) this.player, AudioCapabilities.getCapabilities(this.context), 3);
                }
                TrackRenderer textTrackRenderer;
                if (haveSubtitles) {
                    textTrackRenderer = new TextTrackRenderer((SampleSource) new HlsSampleSource(new HlsChunkSource(false, new DefaultUriDataSource(this.context, bandwidthMeter, this.userAgent), manifest, DefaultHlsTrackSelector.newSubtitleInstance(), bandwidthMeter, timestampAdjusterProvider), loadControl, 131072, mainHandler, this.player, 2), this.player, mainHandler.getLooper(), new SubtitleParser[0]);
                } else {
                    textTrackRenderer = new Eia608TrackRenderer(sampleSource, this.player, mainHandler.getLooper());
                }
                this.player.onRenderers(new TrackRenderer[]{videoRenderer, audioRenderer, metadataTrackRenderer, textTrackRenderer}, bandwidthMeter);
            }
        }
    }

    public HlsRendererBuilder(Context context, String userAgent, String url) {
        this.context = context;
        this.userAgent = userAgent;
        this.url = url;
    }

    public void buildRenderers(DemoPlayer player) {
        this.currentAsyncBuilder = new AsyncRendererBuilder(this.context, this.userAgent, this.url, player);
        this.currentAsyncBuilder.init();
    }

    public void cancel() {
        if (this.currentAsyncBuilder != null) {
            this.currentAsyncBuilder.cancel();
            this.currentAsyncBuilder = null;
        }
    }
}
