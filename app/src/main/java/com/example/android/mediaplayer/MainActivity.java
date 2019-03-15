package com.example.android.mediaplayer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.mediaplayer.Base.BaseActivity;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView{
    SimpleExoPlayerView simpleExoPlayerView;

    Long musicPosition;
    boolean playerState;

    @NonNull
    @Override
    protected MainPresenter createPresenter(@NonNull Context context) {
        return new MainPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        simpleExoPlayerView = findViewById(R.id.exoPlayer_recipeVideo);
        simpleExoPlayerView.setDefaultArtwork( BitmapFactory.decodeResource
                (getResources(), R.drawable.question_mark));
        if (savedInstanceState == null) {
            musicPosition = 0L;
            playerState = false;
        } else {
            musicPosition = savedInstanceState.getLong("musicPosition");
            playerState = savedInstanceState.getBoolean("playerState");
        }
        mPresenter.initComponent();

        if (mPresenter.isNetworkConnected()){
            Toast.makeText( this, "Connected", Toast.LENGTH_SHORT ).show();
        }else {
            Toast.makeText( this, "check connection", Toast.LENGTH_SHORT ).show();
            mPresenter.displayPromptForEnablingGPS();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_action_bar, menu );
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.initialize();
    }
}
