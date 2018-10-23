package com.framgia.music_40.screen.controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_40.R;
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.screen.controller.service.ServicePlayMusicManager;
import com.framgia.music_40.utils.Constant;
import java.util.ArrayList;
import java.util.List;

public class ControllerFragment extends Fragment
        implements View.OnClickListener, MediaPlayer.OnCompletionListener {

    private static final String ARGUMENT_LIST_MUSIC = "ARGUMENT_LIST_MUSIC";
    private static final String ARGUMENT_POSITION = "ARGUMENT_POSITION";

    private List<Music> mMusicList;
    private int mPosition;
    private ImageView mMusicImage, mPlayButton;
    private SeekBar mControllerSeekBar;
    private TextView mCurrent, mDuration, mMusicTitle;
    private ServicePlayMusicManager mServicePlayMusicManager;
    private boolean mIsRepeat;
    private int mCountRepeat;

    public static ControllerFragment newInstance(List<Music> musicList, int position) {
        ControllerFragment controllerFragment = new ControllerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_POSITION, position);
        bundle.putParcelableArrayList(ARGUMENT_LIST_MUSIC,
                (ArrayList<? extends Parcelable>) musicList);
        controllerFragment.setArguments(bundle);
        return controllerFragment;
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
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music_screen, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mMusicImage = view.findViewById(R.id.image_music_artwork);
        mPlayButton = view.findViewById(R.id.image_play);
        mControllerSeekBar = view.findViewById(R.id.seek_bar);
        mCurrent = view.findViewById(R.id.text_current);
        mDuration = view.findViewById(R.id.text_duration);
        mMusicTitle = view.findViewById(R.id.text_music_title);
        mPlayButton.setOnClickListener(this);
        view.findViewById(R.id.image_back).setOnClickListener(this);
        view.findViewById(R.id.image_download).setOnClickListener(this);
        view.findViewById(R.id.image_previous).setOnClickListener(this);
        view.findViewById(R.id.image_next).setOnClickListener(this);
        view.findViewById(R.id.image_repeat).setOnClickListener(this);
        view.findViewById(R.id.image_shuffler).setOnClickListener(this);
    }

    private void initData() {
        if (getActivity() != null) {
            Glide.with(getActivity())
                    .load(mMusicList.get(mPosition).getImage())
                    .apply(new RequestOptions().placeholder(R.drawable.no_image).circleCrop())
                    .into(mMusicImage);
            mPlayButton.setImageResource(R.drawable.ic_pause_black_24dp);
            mDuration.setText(mServicePlayMusicManager.parseDurationToStringTime(
                    mServicePlayMusicManager.getDuration()));
            mMusicTitle.setText(mMusicList.get(mPosition).getMusicName());
            mServicePlayMusicManager.getRunnable(mControllerSeekBar, mCurrent);
            mServicePlayMusicManager.initSeekBar(mControllerSeekBar);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.image_download:
                break;
            case R.id.image_previous:
                previousMusic();
                break;
            case R.id.image_play:
                playOrPause();
                break;
            case R.id.image_next:
                nextMusic();
                break;
            case R.id.image_repeat:
                mIsRepeat = !mIsRepeat;
                mCountRepeat = Constant.ZERO;
                break;
            case R.id.image_shuffler:
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mIsRepeat && mCountRepeat != Constant.ONE) {
            mCountRepeat++;
            mServicePlayMusicManager.repeatMusic();
        } else {
            nextMusic();
            mCountRepeat = Constant.ZERO;
        }
    }

    private void nextMusic() {
        mPosition = mServicePlayMusicManager.nextMusic();
        initData();
    }

    private void previousMusic() {
        mPosition = mServicePlayMusicManager.previousMusic();
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
