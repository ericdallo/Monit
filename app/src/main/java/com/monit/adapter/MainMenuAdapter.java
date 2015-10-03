package com.monit.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.monit.fragment.ConfigFragment;
import com.monit.fragment.GraphFragment;

public class MainMenuAdapter extends FragmentPagerAdapter{

    public static final int NUMBER_FRAGMENTS = 2;

    public MainMenuAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new GraphFragment();
            case 1:
                return new ConfigFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_FRAGMENTS;
    }
}
