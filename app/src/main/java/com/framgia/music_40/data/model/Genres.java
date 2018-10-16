package com.framgia.music_40.data.model;

public class Genres {

    private String mGenreName;
    private String mGenreImage;

    public Genres(String genreName, String genreImage) {
        mGenreName = genreName;
        mGenreImage = genreImage;
    }

    public String getGenreName() {
        return mGenreName;
    }

    public void setGenreName(String genreName) {
        mGenreName = genreName;
    }

    public String getGenreImage() {
        return mGenreImage;
    }

    public void setGenreImage(String genreImage) {
        mGenreImage = genreImage;
    }
}
