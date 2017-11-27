package com.immymemine.kevin.skillshare.exo_player;

import android.net.Uri;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;

/**
 * Created by quf93 on 2017-11-26.
 */

public class Player {
    private MediaSource buildMediaSource(Uri uri, String userAgent) {
//        return new DashMediaSource(uri, new DefaultDataSourceFactory(this, null, new DefaultHttpDataSourceFactory(userAgent, null)),
//                new DefaultDashChunkSource.Factory(mediaDataSourceFactory), null, null);
        return null;
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return null;
    }

    public static void initiatePlayer(SimpleExoPlayerView simpleExoPlayerView, SimpleExoPlayer player) {
        // =========================================================================================
        // Create a default LoadControl
        // LoadControl loadControl = new DefaultLoadControl();
        // =========================================================================================

        // =========================================================================================
        // I. ADJUST HERE:
        // CHOOSE CONTENT: LiveStream / SdCard

        // LIVE STREAM
        Uri uri =Uri.parse("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_2mb.mp4");

        //FROM SD CARD: (2 steps. set up file and path, then change videoSource to get the file)
        // String urimp4 = "path/FileName.mp4"; //upload file to device and add path/name.mp4
        // Uri mp4VideoUri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+urimp4);
        // =========================================================================================

        // =========================================================================================
        // 재생 중 available bandwidth 를 추정해서 제공
        // >>> bandwidth 에 따라 smooth 하게 보여지는 정도가 결정된다
        //     bandwidth 가 크면 클수록 화질이 좋아진다

        // Produces Extractor instances for parsing the media data.
        // ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // =========================================================================================

        // II. ADJUST HERE:

        //This is the MediaSource representing the media to be played:
        //FOR SD CARD SOURCE:
        //        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);

        // FOR LIVESTREAM LINK:
        // ExoPlayer 는 쪼개져있는 segments 들을 MediaSource 형태로 받아온다
        // MediaSource 를 세팅하고 player 에 넘겨준다

        // Dynamic Adaptive Streaming over Http
        // DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayer"));
        // DashMediaSource dashMediaSource = new DashMediaSource(uri, dataSourceFactory, new DefaultDashChunkSource.Factory(dataSourceFactory), null, null);

        // player.prepare(dashMediaSource);


        // Http Live Streaming
        // Produces DataSource instances through which media data is loaded.
        // DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
        // DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayer"), bandwidthMeterA);

        // MediaSource videoSource = new HlsMediaSource(uri, dataSourceFactory, 1, null, null);
        // final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
        // player.prepare(loopingSource); // Prepare the player with the source.
    }
}
