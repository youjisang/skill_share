package com.immymemine.kevin.skillshare.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
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
import com.immymemine.kevin.skillshare.adapter.fragment_adapter.FragmentAdapter;
import com.immymemine.kevin.skillshare.fragment.AboutFragment;
import com.immymemine.kevin.skillshare.fragment.DiscussionsFragment;
import com.immymemine.kevin.skillshare.fragment.LessonsFragment;

import java.util.ArrayList;
import java.util.List;

public class ClassesActivity extends AppCompatActivity {

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

                    // code refactoring...
                    start_button.setVisibility(View.GONE);
                    findViewById(R.id.button_share2).setVisibility(View.GONE);
                    findViewById(R.id.button_back2).setVisibility(View.GONE);
                    findViewById(R.id.button_subscribe2).setVisibility(View.GONE);

                    // controller
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

    private static final String TAG = "Player on classes_activity";
    private static final String URL = "http://yt-dash-mse-test.commondatastorage.googleapis.com/media/oops-20120802-manifest.mpd";

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
        simpleExoPlayerView.setUseController(false); //Set media controller

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
            case C.TYPE_DASH:
                return new DashMediaSource(uri, new DefaultDataSourceFactory(this, null, new DefaultHttpDataSourceFactory(userAgent, null)),
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory), null, null);
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
    protected void onStop() {
        releasePlayer();
        super.onStop();
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