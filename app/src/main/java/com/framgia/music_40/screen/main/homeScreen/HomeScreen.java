package com.framgia.music_40.screen.main.homeScreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_40.R;
import com.framgia.music_40.data.model.Genres;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends Fragment {

    public HomeScreen() {
    }

    public static HomeScreen newInstance() {
        return new HomeScreen();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_home_screen);
        recyclerView.setAdapter(new HomeScreenAdapter(getContext(), getGenresList()));
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
}
