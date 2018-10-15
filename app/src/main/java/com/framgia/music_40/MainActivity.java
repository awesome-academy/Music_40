package com.framgia.music_40;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mActionBar = getSupportActionBar();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.screen_home:
                mActionBar.setTitle(R.string.title_home);
                return true;
            case R.id.screen_search:
                mActionBar.setTitle(R.string.title_search);
                return true;
            case R.id.screen_download:
                mActionBar.setTitle(R.string.title_download);
                return true;
        }
        return false;
    }
}
