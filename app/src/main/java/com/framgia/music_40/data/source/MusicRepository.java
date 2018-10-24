package com.framgia.music_40.data.source;

public class MusicRepository {

    private static MusicRepository sInstance;
    private MusicDataSource.RemoteDataSource mRemoteDataSource;
    private MusicDataSource.LocalDataSource mLocalDataSource;

    private MusicRepository(MusicDataSource.RemoteDataSource remoteDataSource,
            MusicDataSource.LocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static MusicRepository getInstance(MusicDataSource.RemoteDataSource remoteDataSource,
            MusicDataSource.LocalDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new MusicRepository(remoteDataSource, localDataSource);
        }
        return sInstance;
    }

    public void getDataFromUrl(DataCallBack.MusicRemoteDataSource musicRemoteDataSource,
            String genre) {
        mRemoteDataSource.getData(musicRemoteDataSource, genre);
    }

    public void getDataFromLocal(DataCallBack.MusicLocalDataSource musicLocalDataSource) {
        mLocalDataSource.getData(musicLocalDataSource);
    }
}
