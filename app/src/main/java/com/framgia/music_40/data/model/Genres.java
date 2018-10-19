package com.framgia.music_40.data.model;

public class Genres {

    private String mGenreName;
    private int mGenreImage;

    public Genres(String genreName, int genreImage) {
        mGenreName = genreName;
        mGenreImage = genreImage;
    }

    public String getGenreName() {
        return mGenreName;
    }

    public void setGenreName(String genreName) {
        mGenreName = genreName;
    }

    public int getGenreImage() {
        return mGenreImage;
    }

    public void setGenreImage(int genreImage) {
        mGenreImage = genreImage;
    }
}
