package com.framgia.music_40.data.source.remote;

import com.framgia.music_40.data.MusicDataSource;
import com.framgia.music_40.data.source.DataCallBack;
import com.framgia.music_40.data.source.remote.fetchjson.GetMusicDataJson;

public class MusicRemoteDataSource implements MusicDataSource.RemoteDataSource {

    private static MusicRemoteDataSource mInstance;

    private MusicRemoteDataSource() {
    }

    private static MusicRemoteDataSource getInstance() {
        if (mInstance == null) {
            mInstance = new MusicRemoteDataSource();
        }
        return mInstance;
    }

    @Override
    public void getData(DataCallBack.MusicRemoteDataSource musicRemoteDataSource, String url) {
        new GetMusicDataJson(musicRemoteDataSource).execute(url);
    }
}
