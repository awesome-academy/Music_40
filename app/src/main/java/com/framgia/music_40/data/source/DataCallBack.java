package com.framgia.music_40.data.source;

import com.framgia.music_40.data.model.Music;
import java.util.List;

public interface DataCallBack {
    interface MusicRemoteDataSource {
        void getDataSuccess(List<Music> musicList);

        void getDataError(Exception e);
    }

    interface MusicLocalDataSource {
        void getDataSuccess(List<Music> musicList);

        void getDataError(Exception e);
    }
}
