package com.framgia.music_40.utils;

import android.support.annotation.IntDef;

@IntDef({
        GenresId.GENRE_ALL_MUSIC, GenresId.GENRE_ALL_AUDIO, GenresId.GENRE_ALTERNATIVE_ROCK,
        GenresId.GENRE_CLASSICAL, GenresId.GENRE_AMBIENT, GenresId.GENRE_COUNTRY
})
public @interface GenresId {
    int GENRE_ALL_MUSIC = 0;
    int GENRE_ALL_AUDIO = 1;
    int GENRE_ALTERNATIVE_ROCK = 2;
    int GENRE_CLASSICAL = 3;
    int GENRE_AMBIENT = 4;
    int GENRE_COUNTRY = 5;
}
