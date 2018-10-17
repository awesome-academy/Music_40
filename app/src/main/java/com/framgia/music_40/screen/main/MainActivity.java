package com.framgia.music_40.screen.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.framgia.music_40.R;
import com.framgia.music_40.data.source.MusicRepository;
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.data.source.remote.MusicRemoteDataSource;
import com.framgia.music_40.screen.home.ListGenresFragment;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        loadFragment(ListGenresFragment.newInstance());
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setVisibility(View.GONE);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.screen_home:
                mToolbar.setVisibility(View.GONE);
                loadFragment(ListGenresFragment.newInstance());
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

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}
