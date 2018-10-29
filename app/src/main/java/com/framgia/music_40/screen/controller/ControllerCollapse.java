package com.framgia.music_40.screen.controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_40.R;
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.screen.controller.service.ServicePlayMusicManager;
import com.framgia.music_40.utils.Navigator;
import java.util.ArrayList;
import java.util.List;

public class ControllerCollapse extends Fragment
        implements View.OnClickListener, MediaPlayer.OnCompletionListener {

    private static final String ARGUMENT_LIST_MUSIC = "ARGUMENT_LIST_MUSIC";
    private static final String ARGUMENT_POSITION = "ARGUMENT_POSITION";

    private ImageView mMusicImage, mPlayButton;
    private int mPosition;
    private List<Music> mMusicList;
    private ServicePlayMusicManager mServicePlayMusicManager;
    private Navigator mNavigator;
    private TextView mMusicName;

    public static ControllerCollapse newInstance(List<Music> musicList, int position) {
        ControllerCollapse controllerCollapse = new ControllerCollapse();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_POSITION, position);
        bundle.putParcelableArrayList(ARGUMENT_LIST_MUSIC,
                (ArrayList<? extends Parcelable>) musicList);
        controllerCollapse.setArguments(bundle);
        return controllerCollapse;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(ARGUMENT_POSITION);
            mMusicList = bundle.getParcelableArrayList(ARGUMENT_LIST_MUSIC);
            mServicePlayMusicManager = ServicePlayMusicManager.getInstance();
            MediaPlayer mediaPlayer = mServicePlayMusicManager.getMediaPlayer();
            mediaPlayer.setOnCompletionListener(this);
            mNavigator = new Navigator();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music_collapse, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mMusicName = view.findViewById(R.id.music_name);
        mMusicImage = view.findViewById(R.id.image_cover_collapse);
        mPlayButton = view.findViewById(R.id.image_play_collapse);
        mPlayButton.setOnClickListener(this);
        view.findViewById(R.id.image_previous_collapse).setOnClickListener(this);
        view.findViewById(R.id.image_next_collapse).setOnClickListener(this);
        view.findViewById(R.id.fragment_container).setOnClickListener(this);
    }

    private void initData() {
        if (getActivity() != null) {
            mMusicName.setText(mMusicList.get(mPosition).getMusicName());
            Glide.with(getActivity())
                    .load(mMusicList.get(mPosition).getImage())
                    .apply(new RequestOptions().placeholder(R.drawable.no_image).circleCrop())
                    .into(mMusicImage);
            if (mServicePlayMusicManager.isPlaying()) {
                mPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);
            } else {
                mPlayButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_previous_collapse:
                previousMusic();
                break;
            case R.id.image_play_collapse:
                playOrPause();
                break;
            case R.id.image_next_collapse:
                nextMusic();
                break;
            case R.id.fragment_container:
                mNavigator.loadFragment(getActivity(),
                        ControllerFragment.newInstance(mMusicList, mPosition),
                        R.id.frame_container);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (ControllerFragment.mIsRepeat) {
            mServicePlayMusicManager.repeatMusic();
        } else {
            nextMusic();
        }
    }

    private void nextMusic() {
        if (ControllerFragment.mIsShuffler) {
            mPosition = mServicePlayMusicManager.shufflerMusic();
        } else {
            mPosition = mServicePlayMusicManager.nextMusic();
        }
        initData();
    }

    private void previousMusic() {
        if (ControllerFragment.mIsShuffler) {
            mPosition = mServicePlayMusicManager.shufflerMusic();
        } else {
            mPosition = mServicePlayMusicManager.previousMusic();
        }
        initData();
    }

    private void playOrPause() {
        if (mServicePlayMusicManager.isPlaying()) {
            mPlayButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            mServicePlayMusicManager.pauseMusic();
        } else {
            mPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);
            mServicePlayMusicManager.resetMusic();
        }
    }
}
