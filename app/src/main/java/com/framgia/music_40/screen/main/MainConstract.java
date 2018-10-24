package com.framgia.music_40.screen.main;

import com.framgia.music_40.data.model.Music;
import java.util.List;

public interface MainConstract {
    interface View {
        void onSuccess(List<Music> musicList);
    }

    interface Presenter {
        void setView(MainConstract.View view);

        void getListSongLocal();
    }
}
