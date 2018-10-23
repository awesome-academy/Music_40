package com.framgia.music_40.screen.listmusic;

import android.content.Context;
import android.content.Intent;
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
import com.framgia.music_40.screen.controller.ControllerFragment;
import com.framgia.music_40.screen.controller.service.ServicePlayMusic;
import com.framgia.music_40.utils.Navigator;
import com.framgia.music_40.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class ListMusicFragment extends Fragment implements OnItemRecyclerViewClickListener {

    private static final String ARGUMENT_LIST_MUSIC = "ARGUMENT_LIST_MUSIC";

    private ListMusicAdapter mListMusicAdapter;
    private Navigator mNavigator;
    private List<Music> mMusicList;

    private Intent getServicePlayMusic(Context context, int position) {
        Intent intent = new Intent(context, ServicePlayMusic.class);
        intent.putExtra(ServicePlayMusic.EXTRA_POSITION, position);
        intent.putParcelableArrayListExtra(ServicePlayMusic.EXTRA_LIST_MUSIC,
                (ArrayList<? extends Parcelable>) mMusicList);
        return intent;
    }

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
        mNavigator = new Navigator();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mMusicList = bundle.getParcelableArrayList(ARGUMENT_LIST_MUSIC);
            mListMusicAdapter.updateListMusic(mMusicList);
        }
    }

    @Override
    public void onItemClickListener(int position) {
        if (getActivity() != null) {
            getActivity().startService(getServicePlayMusic(getActivity(), position));
            mNavigator.loadFragment(getActivity(),
                    ControllerFragment.newInstance(mMusicList, position), R.id.frame_container);
        }
    }
}
