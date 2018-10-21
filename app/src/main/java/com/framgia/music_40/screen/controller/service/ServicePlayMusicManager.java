package com.framgia.music_40.screen.controller.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.widget.SeekBar;
import android.widget.TextView;
import com.framgia.music_40.utils.Constant;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ServicePlayMusicManager {

    private static final String FORMAT_TIME = "%02d:%02d";

    private ServicePlayMusic mServicePlayMusic;
    private MediaPlayer mMediaPlayer;
    private Runnable mRunnable;
    private Handler mHandler;
    private static ServicePlayMusicManager sInstance;

    public static ServicePlayMusicManager getInstance() {
        if (sInstance == null) {
            sInstance = new ServicePlayMusicManager();
        }
        return sInstance;
    }

    public void serviceConnect(Context context, Intent intent) {
        ServiceConnection connection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ServicePlayMusic.serviceBinder Binder = (ServicePlayMusic.serviceBinder) service;
                mServicePlayMusic = Binder.getService();
                mMediaPlayer = mServicePlayMusic.getMediaPlayer();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public int previousMusic() {
        mHandler.removeCallbacks(mRunnable);
        return mServicePlayMusic.musicPrevious();
    }

    public void pauseMusic() {
        mHandler.removeCallbacks(mRunnable);
        mServicePlayMusic.musicPause();
    }

    public void resetMusic() {
        mHandler.post(mRunnable);
        mServicePlayMusic.musicReset();
    }

    public int nextMusic() {
        mHandler.removeCallbacks(mRunnable);
        return mServicePlayMusic.musicNext();
    }

    public void repeatMusic() {
        mServicePlayMusic.musicPlay();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public MediaPlayer getMediaPlayer() {
        return mServicePlayMusic.getMediaPlayer();
    }

    public void initSeekBar(SeekBar seekBar) {
        mHandler.post(mRunnable);
        seekBar.setMax(getDuration() / Constant.MILLIS_SECONDS);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mMediaPlayer.seekTo(progress * Constant.MILLIS_SECONDS);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void getRunnable(final SeekBar seekBar, final TextView current) {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mMediaPlayer.getCurrentPosition() / Constant.MILLIS_SECONDS);
                current.setText(parseDurationToStringTime(mMediaPlayer.getCurrentPosition()));
                mHandler.postDelayed(mRunnable, Constant.MILLIS_SECONDS);
            }
        };
    }

    public String parseDurationToStringTime(long duration) {
        return String.format(Locale.getDefault(), FORMAT_TIME,
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}
