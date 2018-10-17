package com.framgia.music_40.data.source.remote;

import com.framgia.music_40.BuildConfig;
import com.framgia.music_40.data.source.DataCallBack;
import com.framgia.music_40.data.source.MusicDataSource;
import com.framgia.music_40.data.source.remote.fetchjson.GetMusicDataJson;
import com.framgia.music_40.utils.Constant;

public class MusicRemoteDataSource implements MusicDataSource.RemoteDataSource {

    private static MusicRemoteDataSource mInstance;

    private MusicRemoteDataSource() {
    }

    public static MusicRemoteDataSource getInstance() {
        if (mInstance == null) {
            mInstance = new MusicRemoteDataSource();
        }
        return mInstance;
    }

    @Override
    public void getData(DataCallBack.MusicRemoteDataSource musicRemoteDataSource, String genre) {
        String url = Constant.HTTP_API
                + Constant.CLIENT
                + BuildConfig.API_KEY
                + Constant.LINK_PARTITION
                + Constant.GENRES_MUSIC
                + genre;
        new GetMusicDataJson(musicRemoteDataSource).execute(url);
    }
}
