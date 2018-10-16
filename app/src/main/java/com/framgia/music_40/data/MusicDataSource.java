package com.framgia.music_40.data;

import com.framgia.music_40.data.source.DataCallBack;

public interface MusicDataSource {
    interface RemoteDataSource {
        void getData(DataCallBack.MusicRemoteDataSource musicRemoteDataSource, String url);
    }
}
