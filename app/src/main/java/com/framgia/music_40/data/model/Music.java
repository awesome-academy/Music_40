package com.framgia.music_40.data.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Music implements Parcelable {
    private int mMusicId;
    private String mMusicName;
    private String mPath;
    private Uri mImage;
    private int mDuration;

    private Music(MusicBuilder musicBuilder) {
        mMusicId = musicBuilder.mMusicId;
        mMusicName = musicBuilder.mMusicName;
        mPath = musicBuilder.mPath;
        mImage = musicBuilder.mImage;
        mDuration = musicBuilder.mDuration;
    }

    private Music(Parcel in) {
        mMusicId = in.readInt();
        mMusicName = in.readString();
        mPath = in.readString();
        mImage = in.readParcelable(Uri.class.getClassLoader());
        mDuration = in.readInt();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public int getMusicId() {
        return mMusicId;
    }

    public void setMusicId(int musicId) {
        mMusicId = musicId;
    }

    public String getMusicName() {
        return mMusicName;
    }

    public void setMusicName(String musicName) {
        mMusicName = musicName;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public Uri getImage() {
        return mImage;
    }

    public void setImage(Uri image) {
        mImage = image;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMusicId);
        dest.writeString(mMusicName);
        dest.writeString(mPath);
        dest.writeParcelable(mImage, flags);
        dest.writeInt(mDuration);
    }

    public static class MusicBuilder {
        private int mMusicId;
        private String mMusicName;
        private String mPath;
        private Uri mImage;
        private int mDuration;

        public MusicBuilder(int musicId, String musicName, String path, Uri image, int duration) {
            mMusicId = musicId;
            mMusicName = musicName;
            mPath = path;
            mImage = image;
            mDuration = duration;
        }

        public MusicBuilder() {
        }

        public MusicBuilder musicId(int musicId) {
            mMusicId = musicId;
            return this;
        }

        public MusicBuilder musicName(String musicName) {
            mMusicName = musicName;
            return this;
        }

        public MusicBuilder path(String path) {
            mPath = path;
            return this;
        }

        public MusicBuilder image(Uri image) {
            mImage = image;
            return this;
        }

        public MusicBuilder duration(int duration) {
            mDuration = duration;
            return this;
        }

        public Music build() {
            return new Music(this);
        }
    }

    public final class MusicEntry {
        public static final String MUSIC_ID = "id";
        public static final String MUSIC_TITLE = "title";
        public static final String MUSIC_STREAM_URL = "stream_url";
        public static final String ARTWORK_URL = "artwork_url";
        public static final String DURATION = "duration";
    }
}
