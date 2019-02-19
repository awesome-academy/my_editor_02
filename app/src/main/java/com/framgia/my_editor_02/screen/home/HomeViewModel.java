package com.framgia.my_editor_02.screen.home;

import com.framgia.my_editor_02.screen.BaseViewModel;

public class HomeViewModel extends BaseViewModel {

    private ViewPagerAdapter mViewPagerAdapter;

    HomeViewModel(ViewPagerAdapter viewPagerAdapter) {
        mViewPagerAdapter = viewPagerAdapter;
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }

    public ViewPagerAdapter getViewPagerAdapter() {
        return mViewPagerAdapter;
    }
}
