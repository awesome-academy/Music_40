package com.framgia.music_40.data.source.remote.fetchjson;

import android.net.Uri;
import android.os.AsyncTask;
import com.framgia.music_40.BuildConfig;
import com.framgia.music_40.utils.Constant;
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.data.source.DataCallBack;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetMusicDataJson extends AsyncTask<String, Void, String> {

    private static final String METHOD_GET = "GET";
    private static final String COLLECTION = "collection";

    private DataCallBack.MusicRemoteDataSource mMusicRemoteDataSource;

    public GetMusicDataJson(DataCallBack.MusicRemoteDataSource musicRemoteDataSource) {
        mMusicRemoteDataSource = musicRemoteDataSource;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        try {
            data = getJsonFromUrl(strings[0]);
        } catch (Exception e) {
            mMusicRemoteDataSource.getDataError(e);
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        mMusicRemoteDataSource.getDataSuccess(parseJsonToData(s));
        super.onPostExecute(s);
    }

    private String getJsonFromUrl(String url) throws Exception {
        URL url1 = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setRequestMethod(METHOD_GET);
        httpURLConnection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url1.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        httpURLConnection.disconnect();
        return stringBuilder.toString();
    }

    private List<Music> parseJsonToData(String a) {
        List<Music> musicList = new ArrayList<>();
        try {
            JSONObject collection = new JSONObject(a);
            JSONArray jsonArray = collection.getJSONArray(COLLECTION);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemCollection = jsonArray.getJSONObject(i);
                Music music = new Music.MusicBuilder().musicId(
                        itemCollection.getInt(Music.MusicEntry.MUSIC_ID))
                        .musicName(itemCollection.getString(Music.MusicEntry.MUSIC_TITLE))
                        .path(itemCollection.getString(Music.MusicEntry.MUSIC_STREAM_URL)
                                + Constant.CLIENT
                                + BuildConfig.API_KEY)
                        .image(Uri.parse(itemCollection.getString(Music.MusicEntry.ARTWORK_URL)))
                        .duration(itemCollection.getInt(Music.MusicEntry.DURATION))
                        .build();
                musicList.add(music);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return musicList;
    }
}
