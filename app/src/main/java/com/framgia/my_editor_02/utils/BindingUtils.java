package com.framgia.my_editor_02.utils;

import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class BindingUtils {
    private static final float THUMBNAIL_SIZE_MULTIPLIER = 0.1f;

    @BindingAdapter("pagerAdapter")
    public static void setPagerAdapter(ViewPager viewPager, FragmentPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter("setUpViewPager")
    public static void setPagerAdapter(TabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
    }

    @BindingAdapter("setAdapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("imgUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .thumbnail(THUMBNAIL_SIZE_MULTIPLIER)
                .into(imageView);
    }
}
