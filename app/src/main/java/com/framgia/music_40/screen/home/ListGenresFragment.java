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
import com.framgia.music_40.data.source.local.MusicLocalDataSource;
import com.framgia.music_40.data.source.remote.MusicRemoteDataSource;
import com.framgia.music_40.screen.listmusic.ListMusicFragment;
import com.framgia.music_40.screen.main.MainActivity;
import com.framgia.music_40.utils.GenresId;
import com.framgia.music_40.utils.Navigator;
import com.framgia.music_40.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class ListGenresFragment extends Fragment
        implements ListGenresContract.View, OnItemRecyclerViewClickListener {

    private ListGenresContract.Presenter mListGenresPresenter;
    private List<Music> mMusicList;
    private Navigator mNavigator;

    public static ListGenresFragment newInstance() {
        return new ListGenresFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        initView(view);
        initPresenter();
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_home_screen);
        recyclerView.setAdapter(new ListGenresAdapter(getContext(), getGenresList(), this));
        mNavigator = new Navigator();
    }

    private List<Genres> getGenresList() {
        List<Genres> list = new ArrayList<>();
        list.add(new Genres(getString(R.string.genres_all_music), R.drawable.all_music));
        list.add(new Genres(getString(R.string.genres_all_audio), R.drawable.all_audio));
        list.add(new Genres(getString(R.string.genres_alternative_rock),
                R.drawable.alternative_rock));
        list.add(new Genres(getString(R.string.genres_classical), R.drawable.classical));
        list.add(new Genres(getString(R.string.genres_ambient), R.drawable.ambient));
        list.add(new Genres(getString(R.string.genres_country), R.drawable.country));
        return list;
    }

    @Override
    public void onSuccess(List<Music> musicList) {
        if (musicList != null && getActivity() != null) {
            mMusicList = musicList;
            mNavigator.loadFragment(getActivity(), ListMusicFragment.newInstance(mMusicList),
                    R.id.frame_container);
        }
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickListener(int position) {
        if (MainActivity.mIsConnected) {
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
    }

    private void initPresenter() {
        MusicRemoteDataSource musicRemoteDataSource = MusicRemoteDataSource.getInstance();
        MusicLocalDataSource musicLocalDataSource =
                MusicLocalDataSource.getInstance(getActivity().getContentResolver());
        MusicRepository musicRepository =
                MusicRepository.getInstance(musicRemoteDataSource, musicLocalDataSource);
        mListGenresPresenter = new ListGenresPresenter(musicRepository);
        mListGenresPresenter.setView(this);
    }
}
