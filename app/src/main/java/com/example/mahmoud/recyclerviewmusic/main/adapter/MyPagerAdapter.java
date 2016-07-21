package com.example.mahmoud.recyclerviewmusic.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mahmoud.recyclerviewmusic.R;
import com.example.mahmoud.recyclerviewmusic.main.fragment.CoverFragment;

/**
 * Created by mahmoud on 19-Jul-16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return CoverFragment.newInstance(R.drawable.cover);
            case 1: return CoverFragment.newInstance(R.drawable.album7);
            case 2: return CoverFragment.newInstance(R.drawable.album6);
            case 3: return CoverFragment.newInstance(R.drawable.album9);
            case 4: return CoverFragment.newInstance(R.drawable.album5);
            default: return CoverFragment.newInstance(R.drawable.cover);
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}