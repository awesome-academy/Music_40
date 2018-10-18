package com.framgia.music_40.data.source;

public class MusicRepository {

    private static MusicRepository mInstance;
    private MusicDataSource.RemoteDataSource mRemoteDataSource;

    private MusicRepository(MusicDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public static MusicRepository getInstance(MusicDataSource.RemoteDataSource remoteDataSource) {
        if (mInstance == null) {
            mInstance = new MusicRepository(remoteDataSource);
        }
        return mInstance;
    }

    public void getDataFromUrl(DataCallBack.MusicRemoteDataSource musicRemoteDataSource,
            String genre) {
        mRemoteDataSource.getData(musicRemoteDataSource, genre);
    }
}
