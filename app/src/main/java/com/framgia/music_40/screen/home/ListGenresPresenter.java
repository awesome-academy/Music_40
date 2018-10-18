package com.framgia.music_40.screen.home;

import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.data.source.DataCallBack;
import com.framgia.music_40.data.source.MusicRepository;
import java.util.List;

public class ListGenresPresenter implements ListGenresContract.Presenter {
    private ListGenresContract.View mView;
    private MusicRepository mRepository;

    ListGenresPresenter(MusicRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(ListGenresContract.View view) {
        mView = view;
    }

    @Override
    public void getListSongByGenres(String genre) {
        mRepository.getDataFromUrl(new DataCallBack.MusicRemoteDataSource() {
            @Override
            public void getDataSuccess(List<Music> musicList) {
                mView.onSuccess(musicList);
            }

            @Override
            public void getDataError(Exception e) {
                mView.onError(e);
            }
        }, genre);
    }
}
