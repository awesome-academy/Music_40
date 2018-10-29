package com.framgia.music_40.screen.main;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
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

    private static final String CHECK_INTERNET_CONNECTION = "CHECK_INTERNET_CONNECTION";

    private static final int REQUEST_PERMISSION_STORAGE = 101;
    public static boolean mIsConnected;
    private Toolbar mToolbar;
    private Navigator mNavigator;
    private MainConstract.Presenter mMainPresenter;
    private boolean mIsPermission;
    private BroadcastReceiver mNetworkStatusReceiver;
    private List<Music> mMusicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNetworkStatusReceiver = NetworkStatusReceiver();
        registerReceiver(mNetworkStatusReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        initPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkStatusReceiver);
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
                if (!mIsConnected) {
                    Toast.makeText(getApplicationContext(), CHECK_INTERNET_CONNECTION,
                            Toast.LENGTH_SHORT).show();
                }
                mToolbar.setVisibility(View.GONE);
                mNavigator.loadFragment(this, ListGenresFragment.newInstance(),
                        R.id.frame_container_screen);
                return true;
            case R.id.screen_search:
                mToolbar.setVisibility(View.VISIBLE);
                mToolbar.setTitle(R.string.title_search);
                return true;
            case R.id.screen_download:
                initPermission();
                if (mIsPermission) {
                    mToolbar.setVisibility(View.VISIBLE);
                    mToolbar.setTitle(R.string.title_download);
                    mMainPresenter.getListSongLocal();
                    mNavigator.loadFragment(this, ListMusicFragment.newInstance(mMusicList),
                            R.id.frame_container_screen);
                }
                return true;
        }
        return false;
    }

    @Override
    public void onSuccess(List<Music> musicList) {
        if (musicList != null) {
            mMusicList = musicList;
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
                mIsPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                        REQUEST_PERMISSION_STORAGE);
                initView();
            } else {
                initView();
                mIsPermission = true;
            }
        }
    }

    private static BroadcastReceiver NetworkStatusReceiver() {
        return new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                mIsConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
                if (!mIsConnected) {
                    Toast.makeText(context, CHECK_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
