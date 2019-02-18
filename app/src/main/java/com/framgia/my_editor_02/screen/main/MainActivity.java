package com.framgia.my_editor_02.screen.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.databinding.ActivityMainBinding;
import com.framgia.my_editor_02.screen.home.HomeFragment;
import com.framgia.my_editor_02.utils.Navigator;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainViewModel = new MainViewModel();
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(mMainViewModel);
        Navigator navigator = new Navigator(this);
        navigator.goNextChildFragment(getSupportFragmentManager(), R.id.layoutContainer,
                HomeFragment.getInstance(), false, HomeFragment.TAG);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainViewModel.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainViewModel.onStop();
    }
}
