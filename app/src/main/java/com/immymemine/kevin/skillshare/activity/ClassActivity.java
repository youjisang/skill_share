package com.immymemine.kevin.skillshare.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.adapter.class_adapter.FragmentAdapter;
import com.immymemine.kevin.skillshare.adapter.class_adapter.LessonsAdapter;
import com.immymemine.kevin.skillshare.fragment.class_f.AboutFragment;
import com.immymemine.kevin.skillshare.fragment.class_f.DiscussionsFragment;
import com.immymemine.kevin.skillshare.fragment.class_f.LessonsFragment;
import com.immymemine.kevin.skillshare.utility.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity implements LessonsAdapter.InteractionInterface {

    // views
    ViewPager tabPager;
    TabLayout tabLayout;
    ImageButton startButton;

    // fragments
    AboutFragment aboutfragment;
    DiscussionsFragment discussionsfragment;
    LessonsFragment lessonsfragment;

    String classId, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        // 1. Intent 값을 통해 넘어온 data 를 이용해서 서버와 통신
        Intent intent = getIntent();
        classId = intent.getStringExtra(ConstantUtil.ID_FLAG); // class ID
        url = intent.getStringExtra(ConstantUtil.URL_FLAG);

        initiateView();
        setTabLayout();
        setTabPager();

        initiatePlayer();
        initiateFullScreenDialog();
    }

    private void initiateView() {
        tabLayout = findViewById(R.id.tabLayout);
        tabPager = findViewById(R.id.tabPager);
        startButton = findViewById(R.id.start_button);

//        findViewById(R.id.button_share).setVisibility(View.GONE);
//        findViewById(R.id.button_back).setVisibility(View.GONE);
//        findViewById(R.id.button_subscribe).setVisibility(View.GONE);
    }

    private void setTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Lessons"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Discussion"));

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(tabPager));
    }

    private void setTabPager() {
        List<Fragment> fragmentList = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtil.ID_FLAG, classId);

        lessonsfragment = new LessonsFragment();
        lessonsfragment.setArguments(bundle);
        aboutfragment = new AboutFragment();
        aboutfragment.setArguments(bundle);
        discussionsfragment = new DiscussionsFragment();
        discussionsfragment.setArguments(bundle);

        fragmentList.add(lessonsfragment);
        fragmentList.add(aboutfragment);
        fragmentList.add(discussionsfragment);

        tabPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));
        tabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    // share button 클릭 리스너
    public void share(View view) {

    }

    // back button 클릭 리스너
    public void back(View view) {
        // video 상태 저장
        finish();
    }

    // subscribe 버튼 클릭 리스너
    public void subscribe(View view) {

    }

    public void start(View view) {
        player.setPlayWhenReady(true);

        // code refactoring...
        startButton.setVisibility(View.GONE);
        findViewById(R.id.button_share).setVisibility(View.GONE);
        findViewById(R.id.button_back).setVisibility(View.GONE);
        findViewById(R.id.button_subscribe).setVisibility(View.GONE);

        // controller
        simpleExoPlayerView.setUseController(true); //Set media controller
        simpleExoPlayerView.showController();
    }

    Dialog fullScreenDialog;
    boolean isFullScreen;
    private void initiateFullScreenDialog() {
        fullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            @Override
            public void onBackPressed() {
                super.onBackPressed();
                if(isFullScreen)
                    closeFullScreen();
            }
        };
    }

    public void openFullScreen(View view) {
        if(isFullScreen)
            closeFullScreen();

        ((ViewGroup)simpleExoPlayerView.getParent()).removeView(simpleExoPlayerView);
        fullScreenDialog.addContentView(simpleExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        isFullScreen = true;
        fullScreenDialog.show();
    }

    public void closeFullScreen() {
        ((ViewGroup)simpleExoPlayerView.getParent()).removeView(simpleExoPlayerView);
        ((FrameLayout)findViewById(R.id.player_frame)).addView(simpleExoPlayerView);
        isFullScreen = false;
        fullScreenDialog.dismiss();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 세로
            
        } else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        }

        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void play(String url) {
        mediaSource = buildMediaSource(Uri.parse(url));
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);

        // code refactoring...
        startButton.setVisibility(View.GONE);
        findViewById(R.id.button_share).setVisibility(View.GONE);
        findViewById(R.id.button_back).setVisibility(View.GONE);
        findViewById(R.id.button_subscribe).setVisibility(View.GONE);

        // controller
        simpleExoPlayerView.setUseController(true); //Set media controller
        simpleExoPlayerView.showController();
    }

    // Exo Player -----------------------------------------------------------------------
    // video play step : 1. networking  2. buffering  3. extraction  4. decoding  5. rendering

    private static final String TAG = "Player on classes_activity";
    private final String URL = "https://f1.media.brightcove.com/12/3695997568001/3695997568001_4607435803001_4204665411001.mp4?pubId=3695997568001&videoId=4204665411001";
//    private final String URL = "http://yt-dash-mse-test.commondatastorage.googleapis.com/media/oops-20120802-manifest.mpd";

    private String userAgent;
    private SimpleExoPlayerView simpleExoPlayerView; // view
    private SimpleExoPlayer player; // player
    private DefaultTrackSelector trackSelector;
    private DataSource.Factory mediaDataSourceFactory;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    MediaSource mediaSource;

    long resumePosition;

    private void initiatePlayer() {
        userAgent = Util.getUserAgent(this, "Skill Share");

        // view
        simpleExoPlayerView = new SimpleExoPlayerView(this);
        simpleExoPlayerView = findViewById(R.id.simple_exo_player_view);
        simpleExoPlayerView.requestFocus(); // ( ? )
        simpleExoPlayerView.setUseArtwork(true);
        simpleExoPlayerView.setUseController(false); //Set media controller
        simpleExoPlayerView.hideController();

        // renders [ 4, 5 ]
        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this, null);

        // track selector [ 5 ]
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);
        player.addListener(new PlayerEventListener());

        // binding
        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(false);

        // dash
        // data source factory
        mediaDataSourceFactory = buildDataSourceFactory(true);

        // [ 1, 2, 3 ( ? ) ]
        mediaSource = buildMediaSource(Uri.parse(URL));

        if(resumePosition > 0)
            player.seekTo(resumePosition);
        player.prepare(mediaSource);
    }

    private MediaSource buildMediaSource(Uri uri) {
        @C.ContentType int type = Util.inferContentType(uri);
        switch (type) {
            case C.TYPE_SS:
                return new SsMediaSource(uri, buildDataSourceFactory(false),
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), null, null);
            case C.TYPE_DASH:
                return new DashMediaSource(uri, new DefaultDataSourceFactory(this, null, new DefaultHttpDataSourceFactory(userAgent, null)),
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory), null, null);
            case C.TYPE_HLS:
                return new HlsMediaSource(uri, mediaDataSourceFactory, null, null);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(),
                        null, null);
            default:
                throw new IllegalStateException("Unsupported Type : " + type);
        }
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    private DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(this, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
    }

    private HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }

    private void saveResumePosition() {
        // TODO save video list index
        Log.d("window index", player.getCurrentWindowIndex() + "");
        resumePosition = player.getContentPosition();
        resumePosition = (resumePosition > 0) ? resumePosition : 0;
    }


    private void releasePlayer() {
        if(player != null) {
            saveResumePosition();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    @Override
    protected void onPause() {
        player.setPlayWhenReady(false);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    private class PlayerEventListener implements Player.EventListener {
        private static final String TAG = "skill share player";

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
            player.prepare(mediaSource);
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
    }
}