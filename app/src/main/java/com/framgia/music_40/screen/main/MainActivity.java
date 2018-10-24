package com.framgia.music_40.screen.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.framgia.music_40.R;
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.data.source.MusicRepository;
import com.framgia.music_40.data.source.local.MusicLocalDataSource;
import com.framgia.music_40.data.source.remote.MusicRemoteDataSource;
import com.framgia.music_40.screen.controller.service.ServicePlayMusic;
import com.framgia.music_40.screen.controller.service.ServicePlayMusicManager;
import com.framgia.music_40.screen.home.ListGenresFragment;
import com.framgia.music_40.screen.listmusic.ListMusicFragment;
import com.framgia.music_40.utils.Navigator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, MainConstract.View {

    private static final int REQUEST_PERMISSION_STORAGE = 101;
    private Toolbar mToolbar;
    private Navigator mNavigator;
    private MainConstract.Presenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setVisibility(View.GONE);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        mNavigator = new Navigator();
        ServicePlayMusicManager servicePlayMusicManager = ServicePlayMusicManager.getInstance();
        servicePlayMusicManager.serviceConnect(this, new Intent(this, ServicePlayMusic.class));
        mNavigator.loadFragment(this, ListGenresFragment.newInstance(),
                R.id.frame_container_screen);
        initMainPresenter();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.screen_home:
                mToolbar.setVisibility(View.GONE);
                mNavigator.loadFragment(this, ListGenresFragment.newInstance(),
                        R.id.frame_container_screen);
                return true;
            case R.id.screen_search:
                mToolbar.setVisibility(View.VISIBLE);
                mToolbar.setTitle(R.string.title_search);
                return true;
            case R.id.screen_download:
                mToolbar.setVisibility(View.VISIBLE);
                mToolbar.setTitle(R.string.title_download);
                mMainPresenter.getListSongLocal();
                return true;
        }
        return false;
    }

    @Override
    public void onSuccess(List<Music> musicList) {
        if (musicList != null) {
            mNavigator.loadFragment(this, ListMusicFragment.newInstance(musicList),
                    R.id.frame_container_screen);
        }
    }

    private void initMainPresenter() {
        MusicRemoteDataSource musicRemoteDataSource = MusicRemoteDataSource.getInstance();
        MusicLocalDataSource musicLocalDataSource =
                MusicLocalDataSource.getInstance(getContentResolver());
        MusicRepository musicRepository =
                MusicRepository.getInstance(musicRemoteDataSource, musicLocalDataSource);
        mMainPresenter = new MainPresenter(musicRepository);
        mMainPresenter.setView(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initView();
                } else {
                    finish();
                }
                break;
        }
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                        REQUEST_PERMISSION_STORAGE);
            } else {
                initView();
            }
        }
    }
}
