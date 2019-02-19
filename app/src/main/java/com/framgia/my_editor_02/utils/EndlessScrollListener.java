package com.framgia.my_editor_02.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private static final int DEFAULT_VISIBLE_THRESHOLD = 6;
    private static final int DEFAULT_CURRENT_PAGE = 1;
    private static final int DEFAULT_VALUE = 0;
    private int mVisibleThreshold;
    private int mCurrentPage;
    private int mPreviousTotalItemCount;
    private boolean mIsLoading;

    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        mVisibleThreshold = DEFAULT_VISIBLE_THRESHOLD;
        mCurrentPage = DEFAULT_CURRENT_PAGE;
    }

    public EndlessScrollListener(GridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        mVisibleThreshold = DEFAULT_VISIBLE_THRESHOLD * layoutManager.getSpanCount();
        mCurrentPage = DEFAULT_CURRENT_PAGE;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = DEFAULT_VALUE;
        int totalItemCount = mLayoutManager.getItemCount();
        if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition =
                    ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition =
                    ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }
        if (totalItemCount < mPreviousTotalItemCount) {
            mPreviousTotalItemCount = totalItemCount;
            if (totalItemCount == DEFAULT_VALUE) {
                mIsLoading = true;
            }
        }
        if (mIsLoading && (totalItemCount > mPreviousTotalItemCount)) {
            mIsLoading = false;
            mPreviousTotalItemCount = totalItemCount;
        }
        if (!mIsLoading && (lastVisibleItemPosition + mVisibleThreshold) > totalItemCount) {
            mCurrentPage++;
            onLoadMore(mCurrentPage, view);
            mIsLoading = true;
        }
    }

    public abstract void onLoadMore(int page, RecyclerView view);
}
