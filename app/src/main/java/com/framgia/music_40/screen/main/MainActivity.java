package com.framgia.music_40.screen.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.framgia.music_40.R;
import com.framgia.music_40.screen.controller.service.ServicePlayMusic;
import com.framgia.music_40.screen.controller.service.ServicePlayMusicManager;
import com.framgia.music_40.screen.home.ListGenresFragment;
import com.framgia.music_40.utils.Navigator;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
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
                return true;
        }
        return false;
    }
}
