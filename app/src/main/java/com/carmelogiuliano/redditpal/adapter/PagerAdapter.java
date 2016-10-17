package com.carmelogiuliano.redditpal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.carmelogiuliano.redditpal.activity.CommentsFragment;
import com.carmelogiuliano.redditpal.activity.WebFragment;

/**
 * Enables switching of tabs in DetailActivity.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumTabs;

    public PagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        mNumTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CommentsFragment();
            case 1:
                return new WebFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumTabs;
    }
}
