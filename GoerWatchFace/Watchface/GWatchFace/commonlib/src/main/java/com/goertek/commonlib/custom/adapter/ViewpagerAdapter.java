package com.goertek.commonlib.custom.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goertek.commonlib.custom.BaseCustomFragment;

import java.util.List;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private List<BaseCustomFragment> mFragments;

    public ViewpagerAdapter(FragmentManager fm, List<BaseCustomFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments != null ? mFragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }
}
