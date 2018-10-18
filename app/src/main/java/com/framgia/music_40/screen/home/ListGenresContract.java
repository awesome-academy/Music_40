package com.framgia.music_40.screen.home;

import com.framgia.music_40.data.model.Music;
import java.util.List;

public interface ListGenresContract {
    interface View {
        void onSuccess(List<Music> musicList);

        void onError(Exception e);
    }

    interface Presenter {
        void setView(View view);

        void getListSongByGenres(String genre);
    }
}
