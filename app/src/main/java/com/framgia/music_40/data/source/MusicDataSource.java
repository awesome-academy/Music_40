package com.framgia.music_40.data.source;

public interface MusicDataSource {
    interface RemoteDataSource {
        void getData(DataCallBack.MusicRemoteDataSource musicRemoteDataSource, String genre);
    }

    interface LocalDataSource {
        void getData(DataCallBack.MusicLocalDataSource musicLocalDataSource);
    }
}
