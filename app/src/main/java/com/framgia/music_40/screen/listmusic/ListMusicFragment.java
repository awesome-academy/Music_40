package com.framgia.music_40.screen.listmusic;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_40.R;
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class ListMusicFragment extends Fragment implements OnItemRecyclerViewClickListener {

    private static final String ARGUMENT_LIST_MUSIC = "ARGUMENT_LIST_MUSIC";

    private ListMusicAdapter mListMusicAdapter;

    public static ListMusicFragment newInstance(List<Music> musicList) {
        ListMusicFragment listMusicFragmentScreen = new ListMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGUMENT_LIST_MUSIC,
                (ArrayList<? extends Parcelable>) musicList);
        listMusicFragmentScreen.setArguments(bundle);
        return listMusicFragmentScreen;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_list_screen, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_music_list_screen);
        mListMusicAdapter = new ListMusicAdapter(getContext(), this);
        recyclerView.setAdapter(mListMusicAdapter);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<Music> musicList = bundle.getParcelableArrayList(ARGUMENT_LIST_MUSIC);
            mListMusicAdapter.updateListMusic(musicList);
        }
    }

    @Override
    public void onItemClickListener(int position) {
    }
}
