package com.framgia.music_40.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.framgia.music_40.R;

public class Navigator {
    public void loadFragment(FragmentActivity fragmentActivity, Fragment fragment, int layoutId) {
        FragmentTransaction transaction =
                fragmentActivity.getSupportFragmentManager().beginTransaction();
        switch (layoutId) {
            case R.id.frame_container_screen:
                transaction.replace(layoutId, fragment);
                transaction.commitAllowingStateLoss();
                break;
            case R.id.frame_container:
                transaction.add(layoutId, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
}
