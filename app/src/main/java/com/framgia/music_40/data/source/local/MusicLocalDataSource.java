package com.framgia.music_40.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.data.source.DataCallBack;
import com.framgia.music_40.data.source.MusicDataSource;
import java.util.ArrayList;
import java.util.List;

public class MusicLocalDataSource implements MusicDataSource.LocalDataSource {

    private static final String ALBUM_ART = "content://media/external/audio/albumart";

    private static MusicLocalDataSource sInstance;
    private ContentResolver mContentResolver;

    private MusicLocalDataSource(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    public static MusicLocalDataSource getInstance(ContentResolver contentResolver) {
        if (sInstance == null) {
            sInstance = new MusicLocalDataSource(contentResolver);
        }
        return sInstance;
    }

    @Override
    public void getData(DataCallBack.MusicLocalDataSource musicLocalDataSource) {
        getDataFromLocal(musicLocalDataSource);
    }

    private void getDataFromLocal(DataCallBack.MusicLocalDataSource musicLocalDataSource) {
        List<Music> mMusicList = new ArrayList<>();
        Cursor cursor =
                mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                        null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                Music music = new Music.MusicBuilder().musicId(
                        cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)))
                        .musicName(cursor.getString(
                                cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)))
                        .path(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)))
                        .image(ContentUris.withAppendedId(Uri.parse(ALBUM_ART), cursor.getInt(
                                cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))))
                        .duration(cursor.getInt(
                                cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)))
                        .build();
                mMusicList.add(music);
            }
            cursor.close();
        }
        musicLocalDataSource.getDataSuccess(mMusicList);
    }
}
