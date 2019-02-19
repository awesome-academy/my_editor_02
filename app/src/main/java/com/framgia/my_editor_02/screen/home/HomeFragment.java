package com.framgia.my_editor_02.screen.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.databinding.FragmentHomeBinding;
import com.framgia.my_editor_02.utils.Navigator;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private HomeViewModel mHomeViewModel;
    private Navigator mNavigator;
    private FragmentHomeBinding mBinding;

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mNavigator = new Navigator(this);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        setUp();
        mBinding.setViewModel(mHomeViewModel);
        return mBinding.getRoot();
    }

    private void setUp() {
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(
                mBinding.toolBar);
        mBinding.tabLayout.addOnTabSelectedListener(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        mHomeViewModel = new HomeViewModel(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mHomeViewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHomeViewModel.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSearch:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mBinding.viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}
