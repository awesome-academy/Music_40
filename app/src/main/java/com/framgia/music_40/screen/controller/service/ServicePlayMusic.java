package com.framgia.music_40.screen.controller.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.utils.Constant;
import java.io.IOException;
import java.util.List;

public class ServicePlayMusic extends Service {

    public static final String EXTRA_LIST_MUSIC = "EXTRA_LIST_MUSIC";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";

    private IBinder mIBinder;
    private MediaPlayer mMediaPlayer;
    private List<Music> mMusicList;
    private int mPosition;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMusicList = intent.getParcelableArrayListExtra(EXTRA_LIST_MUSIC);
        mPosition = intent.getIntExtra(EXTRA_POSITION, 0);
        musicPlay();
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        mIBinder = new serviceBinder();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        mMediaPlayer = new MediaPlayer();
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    class serviceBinder extends Binder {
        ServicePlayMusic getService() {
            return ServicePlayMusic.this;
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void musicPlay() {
        resetMediaPlayer();
        startMediaPlayer();
    }

    public int musicNext() {
        resetMediaPlayer();
        mPosition++;
        if (mPosition == mMusicList.size()) {
            mPosition = Constant.ZERO;
        }
        startMediaPlayer();
        return mPosition;
    }

    public int musicPrevious() {
        resetMediaPlayer();
        mPosition--;
        if (mPosition == Constant.MINUS_ONE) {
            mPosition = mMusicList.size() - Constant.ONE;
        }
        startMediaPlayer();
        return mPosition;
    }

    public void musicPause() {
        mMediaPlayer.pause();
    }

    public void musicReset() {
        mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition());
        mMediaPlayer.start();
    }

    private void startMediaPlayer() {
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(mMusicList.get(mPosition).getPath());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetMediaPlayer() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
    }
}
