package com.framgia.my_editor_02.screen.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.databinding.ActivityMainBinding;
import com.framgia.my_editor_02.screen.brightness.BrightnessFragment;
import com.framgia.my_editor_02.screen.draw.DrawFragment;
import com.framgia.my_editor_02.screen.edit.EditPhotoFragment;
import com.framgia.my_editor_02.screen.filter.FilterFragment;
import com.framgia.my_editor_02.screen.home.HomeFragment;
import com.framgia.my_editor_02.utils.Navigator;
import com.framgia.my_editor_02.utils.OnAttachEditFeatureListener;

public class MainActivity extends AppCompatActivity implements OnAttachEditFeatureListener {

    private static final int FLAGS = 0;
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

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof DrawFragment) {
            DrawFragment drawFragment = (DrawFragment) fragment;
            drawFragment.setOnAttachDrawFragmentListener(this);
        } else if (fragment instanceof FilterFragment) {
            FilterFragment filterFragment = (FilterFragment) fragment;
            filterFragment.setOnAttachFilterFragment(this);
        }
        else if (fragment instanceof BrightnessFragment){
            BrightnessFragment brightnessFragment = (BrightnessFragment) fragment;
            brightnessFragment.setOnAttachBrightnessListener(this);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    view.clearFocus();
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), FLAGS);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void saveEditResult(Bitmap bitmap) {
        EditPhotoFragment editPhotoFragment =
                (EditPhotoFragment) getSupportFragmentManager().findFragmentByTag(
                        EditPhotoFragment.TAG);
        if (editPhotoFragment != null) {
            editPhotoFragment.saveBitmap(bitmap);
        }
    }
}
