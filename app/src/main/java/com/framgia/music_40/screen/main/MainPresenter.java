package com.framgia.music_40.screen.main;

import android.content.Context;
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.data.source.DataCallBack;
import com.framgia.music_40.data.source.MusicRepository;
import java.util.List;

public class MainPresenter implements MainConstract.Presenter {

    private MusicRepository mMusicRepository;
    private MainConstract.View mView;

    MainPresenter(MusicRepository musicRepository) {
        mMusicRepository = musicRepository;
    }

    @Override
    public void setView(MainConstract.View view) {
        mView = view;
    }

    @Override
    public void getListSongLocal() {
        mMusicRepository.getDataFromLocal(new DataCallBack.MusicLocalDataSource() {
            @Override
            public void getDataSuccess(List<Music> musicList) {
                mView.onSuccess(musicList);
            }

            @Override
            public void getDataError(Exception e) {

            }
        });
    }
}
