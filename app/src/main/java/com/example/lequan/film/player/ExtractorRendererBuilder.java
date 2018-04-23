package com.example.lequan.film.player;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import com.example.lequan.film.player.DemoPlayer.RendererBuilder;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer.EventListener;
import com.google.android.exoplayer.MediaCodecSelector;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.SampleSource;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.exoplayer.text.SubtitleParser;
import com.google.android.exoplayer.text.TextRenderer;
import com.google.android.exoplayer.text.TextTrackRenderer;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.upstream.TransferListener;

public class ExtractorRendererBuilder implements RendererBuilder {
    private static final int BUFFER_SEGMENT_COUNT = 256;
    private static final int BUFFER_SEGMENT_SIZE = 65536;
    private final Context context;
    private final Uri uri;
    private final String userAgent;

    public ExtractorRendererBuilder(Context context, String userAgent, Uri uri) {
        this.context = context;
        this.userAgent = userAgent;
        this.uri = uri;
    }

    public void buildRenderers(DemoPlayer player) {
        Allocator allocator = new DefaultAllocator(65536);
        Handler mainHandler = player.getMainHandler();
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter(mainHandler, null);
        SampleSource sampleSource = new ExtractorSampleSource(this.uri, new DefaultUriDataSource(this.context, (TransferListener) defaultBandwidthMeter, this.userAgent), allocator, 16777216, mainHandler, player, 0, new Extractor[0]);
        MediaCodecVideoTrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(this.context, sampleSource, MediaCodecSelector.DEFAULT, 1, HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS, mainHandler, player, 50);
        MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource, MediaCodecSelector.DEFAULT, null, true, mainHandler, (EventListener) player, AudioCapabilities.getCapabilities(this.context), 3);
        TrackRenderer textTrackRenderer = new TextTrackRenderer(sampleSource, (TextRenderer) player, mainHandler.getLooper(), new SubtitleParser[0]);
        TrackRenderer[] renderers = new TrackRenderer[4];
        renderers[0] = videoRenderer;
        renderers[1] = audioRenderer;
        renderers[2] = textTrackRenderer;
        player.onRenderers(renderers, defaultBandwidthMeter);
    }

    public void cancel() {
    }
}
