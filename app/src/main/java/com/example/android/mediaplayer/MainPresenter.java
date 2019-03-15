package com.example.android.mediaplayer;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;

import com.example.android.mediaplayer.Base.BasePresenter;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainPresenter extends BasePresenter implements Player.EventListener {
    private MainActivity context;
    private MainView view;
    private SimpleExoPlayer player;
    private String music = "https://cdn.dhad.sa/books/1/samples/starbucks_Intro_00.mp3";
    MainPresenter(MainActivity context, MainView view) {
        this.context = context;
        this.view = view;
    }

    public void initialize() {
        if (!(music.equals("") && music.isEmpty())) {
            if (player != null) {
                player.release();
                player = null;
            }
            context.simpleExoPlayerView.setVisibility( View.VISIBLE);
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            player.setPlayWhenReady(context.playerState);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    context, Util.getUserAgent(context, context.getString(R.string.app_name)), null);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource( Uri.parse(music));
            player.prepare(videoSource);
            player.seekTo( context.musicPosition );

            context.simpleExoPlayerView.setPlayer(player);

        } else {
            context.simpleExoPlayerView.setVisibility( View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState( outState );
        if (player != null) {
            outState.putLong("musicPosition", player.getCurrentPosition());
            outState.putBoolean("playState", player.getPlayWhenReady());
        }
    }
    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        //mNotificationManager.cancelAll();
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void initComponent(){
         BottomNavigationView navigationView;
        navigationView = context.findViewById( R.id.navigation );
        navigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.m1:
                        return true;
                    case R.id.m2:
                        return true;
                    case R.id.m3:
                        return true;
                    case R.id.m4:
                        return true;
                    default:
                        return false;
                }


            }
        } );
    }

    public void displayPromptForEnablingGPS() {
        final String action = Settings.ACTION_WIRELESS_SETTINGS;
        context.startActivity(new Intent(action));
    }
     boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
