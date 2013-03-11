package com.example.videoaudio;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyVideoPlayer extends Activity implements
OnBufferingUpdateListener, OnCompletionListener,
OnPreparedListener, OnVideoSizeChangedListener, SurfaceHolder.Callback {

    private static final String TAG = "AudioVideo";
    private int mVideoWidth;
    private int mVideoHeight;
    private MediaPlayer mp;
    private SurfaceView mPreview;
    private SurfaceHolder holder;
    private String path;
    private Bundle extras;

    private static final String AUDIOVIDEO = "videoaudio";

    private static final int LOCAL_VIDEO = 1;
    private static final int STREAM_VIDEO = 2;
    private static final int RESOURCES_VIDEO = 3;

    private boolean mIsVideoSizeKnown = false;
    private boolean mIsVideoReadyToBePlayed = false;

    /**
     * Called when the activity is first created.
     */@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.media_player);
        mPreview = (SurfaceView) findViewById(R.id.surface);
        holder = mPreview.getHolder();
        holder.addCallback(this);
        extras = getIntent().getExtras();
    }

    private void playVideo(Integer AUDIOVIDEO) {
        doCleanUp();
        try {

            switch (AUDIOVIDEO) {
                case LOCAL_VIDEO:
                    path = Environment.getExternalStorageDirectory().getPath();

                    // Create a new media player and set the listeners
                    mp = new MediaPlayer();
                    mp.setDataSource(path + "/sample.3gp");
                    mp.setDisplay(holder);
                    mp.prepare();
                    mp.setOnBufferingUpdateListener(this);
                    mp.setOnCompletionListener(this);
                    mp.setOnPreparedListener(this);
                    mp.setOnVideoSizeChangedListener(this);
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    break;
                case RESOURCES_VIDEO:
                	
                    // Upload a video file to res/raw folder 
                    mp = MediaPlayer.create(this, R.raw.sample_mpeg4);
                    mp.start();
                    
                case STREAM_VIDEO:
                    path = "http://www.tools4movies.com/dvd_catalyst_profile_samples/The%20Amazing%20Spiderman%20bionic%20hq.mp4";

                    // Create a new media player and set the listeners
                    mp = new MediaPlayer();
                    mp.setDataSource(path);
                    mp.setDisplay(holder);
                    mp.prepare();
                    mp.setOnBufferingUpdateListener(this);
                    mp.setOnCompletionListener(this);
                    mp.setOnPreparedListener(this);
                    mp.setOnVideoSizeChangedListener(this);
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
    }

    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
        Log.d(TAG, "onBufferingUpdate percent:" + percent);

    }

    public void onCompletion(MediaPlayer arg0) {
        Log.d(TAG, "onCompletion called");
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.v(TAG, "onVideoSizeChanged called");
        if (width == 0 || height == 0) {
            Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
            return;
        }
        mIsVideoSizeKnown = true;
        mVideoWidth = width;
        mVideoHeight = height;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
    }

    public void onPrepared(MediaPlayer mediaplayer) {
        Log.d(TAG, "onPrepared called");
        mIsVideoReadyToBePlayed = true;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.d(TAG, "surfaceChanged called");

    }

    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.d(TAG, "surfaceDestroyed called");
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated called");
        playVideo(extras.getInt(AUDIOVIDEO));
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
        doCleanUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
        doCleanUp();
    }

    private void releaseMediaPlayer() {
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    private void doCleanUp() {
        mVideoWidth = 0;
        mVideoHeight = 0;
        mIsVideoReadyToBePlayed = false;
        mIsVideoSizeKnown = false;
    }

    private void startVideoPlayback() {
        Log.v(TAG, "startVideoPlayback");
        holder.setFixedSize(mVideoWidth, mVideoHeight);
        mp.start();
    }
}