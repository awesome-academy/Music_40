package com.framgia.music_40.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.framgia.music_40.R;
import com.framgia.music_40.data.model.Genres;
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.data.source.MusicRepository;
import com.framgia.music_40.data.source.remote.MusicRemoteDataSource;
import com.framgia.music_40.utils.GenresId;
import java.util.ArrayList;
import java.util.List;

public class ListGenresFragment extends Fragment
        implements ListGenresContract.View, OnItemListGenresClick {

    private ListGenresContract.Presenter mListGenresPresenter;
    private List<Music> mMusicList;

    public static ListGenresFragment newInstance() {
        return new ListGenresFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_home_screen);
        recyclerView.setAdapter(new ListGenresAdapter(getContext(), getGenresList(), this));
        initPresenter();
        return view;
    }

    private List<Genres> getGenresList() {
        List<Genres> list = new ArrayList<>();
        list.add(new Genres(getString(R.string.genres_all_music),
                getString(R.string.image_all_music)));
        list.add(new Genres(getString(R.string.genres_all_audio),
                getString(R.string.image_all_audio)));
        list.add(new Genres(getString(R.string.genres_alternative_rock),
                getString(R.string.image_alternative_rock)));
        list.add(new Genres(getString(R.string.genres_classical),
                getString(R.string.image_classical)));
        list.add(new Genres(getString(R.string.genres_ambient), getString(R.string.image_ambient)));
        list.add(new Genres(getString(R.string.genres_country), getString(R.string.image_country)));
        return list;
    }

    @Override
    public void onSuccess(List<Music> musicList) {
        if (musicList != null) {
            mMusicList = musicList;
        }
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int position) {
        switch (position) {
            case GenresId.GENRE_ALL_MUSIC:
                mListGenresPresenter.getListSongByGenres(getString(R.string.genres_all_music));
                break;
            case GenresId.GENRE_ALL_AUDIO:
                mListGenresPresenter.getListSongByGenres(getString(R.string.genres_audio));
                break;
            case GenresId.GENRE_ALTERNATIVE_ROCK:
                mListGenresPresenter.getListSongByGenres(
                        getString(R.string.genres_alternative_rock));
                break;
            case GenresId.GENRE_CLASSICAL:
                mListGenresPresenter.getListSongByGenres(getString(R.string.genres_classical));
                break;
            case GenresId.GENRE_AMBIENT:
                mListGenresPresenter.getListSongByGenres(getString(R.string.genres_ambient));
                break;
            case GenresId.GENRE_COUNTRY:
                mListGenresPresenter.getListSongByGenres(getString(R.string.genres_country));
                break;
        }
    }

    private void initPresenter() {
        MusicRemoteDataSource musicRemoteDataSource = MusicRemoteDataSource.getInstance();
        MusicRepository musicRepository = MusicRepository.getInstance(musicRemoteDataSource);
        mListGenresPresenter = new ListGenresPresenter(musicRepository);
        mListGenresPresenter.setView(this);
    }
}
