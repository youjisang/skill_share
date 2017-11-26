package com.immymemine.kevin.skillshare.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.FragmentAdapter;
import com.immymemine.kevin.skillshare.fragment.AboutFragment;
import com.immymemine.kevin.skillshare.fragment.DiscussionsFragment;
import com.immymemine.kevin.skillshare.fragment.LessonsFragment;

import java.util.ArrayList;
import java.util.List;

public class ClassesActivity extends AppCompatActivity implements VideoRendererEventListener{

    // views
    ViewPager tabPager;
    TabLayout tabLayout;
    ImageButton start_button;

    // fragments
    AboutFragment aboutfragment;
    DiscussionsFragment discussionsfragment;
    LessonsFragment lessonsfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        // 1. Intent 값을 통해 넘어온 data 를 이용해서 서버와 통신

        // 2. model object 에 담아주고

        // 3. view 에서 model object 를 사용

        initiateView();
        setTabLayout();
        setTabPager();
        connectTabAndPager();

        initiatePlayer();
    }

    private void initiateView() {
        tabLayout = findViewById(R.id.tabLayout);
        tabPager = findViewById(R.id.tabPager);
        start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(
                v -> {
                    player.setPlayWhenReady(true);
                    start_button.setVisibility(View.GONE);
                    findViewById(R.id.button_share2).setVisibility(View.GONE);
                    findViewById(R.id.button_back2).setVisibility(View.GONE);
                    findViewById(R.id.button_subscribe2).setVisibility(View.GONE);
                    simpleExoPlayerView.setUseController(true); //Set media controller
                    simpleExoPlayerView.showController();
        });
    }

    private void setTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Lessons"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Discussion"));
    }

    private void setTabPager() {
        List<Fragment> fragmentList = new ArrayList<>();

        aboutfragment = new AboutFragment();
        discussionsfragment = new DiscussionsFragment();
        lessonsfragment = new LessonsFragment();

        fragmentList.add(lessonsfragment);
        fragmentList.add(aboutfragment);
        fragmentList.add(discussionsfragment);

        tabPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));
    }

    // 탭 레이아웃과 뷰페이저를 연결한다.
    private void connectTabAndPager() {
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(tabPager));
        tabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    // share button 클릭 리스너
    public void share(View view) {

    }

    // back button 클릭 리스너
    public void back(View view) {

        finish();
    }

    // subscribe 버튼 클릭 리스너
    public void subscribe(View view) {

    }
    public void student_profile(View view) {
        int id = view.getId();

    }
    // Exo Player -----------------------------------------------------------------------
    // video play step : 1. networking  2. buffering  3. extraction  4. decoding  5. rendering

    private static final String TAG = "ClassesActivity";

    private SimpleExoPlayerView simpleExoPlayerView; // view
    private SimpleExoPlayer player; // player

    private void initiatePlayer() {
        // =========================================================================================

        // view
        simpleExoPlayerView = new SimpleExoPlayerView(this);
        simpleExoPlayerView = findViewById(R.id.simple_exo_player_view);
        simpleExoPlayerView.requestFocus(); // ( ? )
        simpleExoPlayerView.setUseController(false); //Set media controller

        // Create a default TrackSelector <<< 화질 선택 ( ? )
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // Create the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        // Bind the player to the view.
        simpleExoPlayerView.setPlayer(player);

        // Create a default LoadControl
        // LoadControl loadControl = new DefaultLoadControl();
        // =========================================================================================

        // =========================================================================================
        // I. ADJUST HERE:
        // CHOOSE CONTENT: LiveStream / SdCard

        // LIVE STREAM

        // Uri mp4VideoUri =Uri.parse("http://81.7.13.162/hls/ss1/index.m3u8"); //random 720p source
        Uri mp4VideoUri =Uri.parse("http://54.255.155.24:1935//Live/_definst_/amlst:sweetbcha1novD235L240P/playlist.m3u8"); //Radnom 540p indian channel
        // Uri mp4VideoUri =Uri.parse("FIND A WORKING LINK ABD PLUg INTO HERE"); //PLUG INTO HERE<------------------------------------------


        //FROM SD CARD: (2 steps. set up file and path, then change videoSource to get the file)
        // String urimp4 = "path/FileName.mp4"; //upload file to device and add path/name.mp4
        // Uri mp4VideoUri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+urimp4);
        // =========================================================================================

        // =========================================================================================
        // 재생 중 available bandwidth 를 추정해서 제공
        // >>> bandwidth 에 따라 smooth 하게 보여지는 정도가 결정된다
        //     bandwidth 가 크면 클수록 화질이 좋아진다
        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
        //Produces DataSource instances through which media data is loaded.
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoplayer2example"), bandwidthMeterA);
        //Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // =========================================================================================

        // II. ADJUST HERE:

        //This is the MediaSource representing the media to be played:
        //FOR SD CARD SOURCE:
        //        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);

        // FOR LIVESTREAM LINK:
        // ExoPlayer 는 쪼개져있는 segments 들을 MediaSource 형태로 받아온다
        // MediaSource 를 세팅하고 player 에 넘겨준다
        MediaSource videoSource = new HlsMediaSource(mp4VideoUri, dataSourceFactory, 1, null, null);
        final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);

        // Prepare the player with the source.
        player.prepare(loopingSource);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                Log.v(TAG, "Listener-onTimelineChanged...");
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.v(TAG, "Listener-onTracksChanged...");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.v(TAG, "Listener-onLoadingChanged...isLoading:"+isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.v(TAG, "Listener-onPlayerStateChanged..." + playbackState);
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                Log.v(TAG, "Listener-onRepeatModeChanged...");
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG, "Listener-onPlayerError...");
                player.stop();
                player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity() {
                Log.v(TAG, "Listener-onPositionDiscontinuity...");
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                Log.v(TAG, "Listener-onPlaybackParametersChanged...");
            }
        });

        player.setPlayWhenReady(false); //run file/link when ready to play.
        player.setVideoDebugListener(this); //for listening to resolution change and  outputing the resolution
    }

    @Override
    public void onVideoEnabled(DecoderCounters counters) {

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

    }

    @Override
    public void onVideoInputFormatChanged(Format format) {

    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        Log.v(TAG, "onVideoSizeChanged ["  + " width: " + width + " height: " + height + "]");
    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {

    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {

    }
}