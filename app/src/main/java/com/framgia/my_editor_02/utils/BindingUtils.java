package com.framgia.my_editor_02.utils;

import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class BindingUtils {
    @BindingAdapter("pagerAdapter")
    public static void setPagerAdapter(ViewPager viewPager, FragmentPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter("setUpViewPager")
    public static void setPagerAdapter(TabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
    }
}
