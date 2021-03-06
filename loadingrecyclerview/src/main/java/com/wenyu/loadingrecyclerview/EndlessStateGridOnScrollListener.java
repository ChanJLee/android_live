package com.wenyu.loadingrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by chan on 17/4/13.
 */

public abstract class EndlessStateGridOnScrollListener extends RecyclerView.OnScrollListener {
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public EndlessStateGridOnScrollListener(StaggeredGridLayoutManager layoutManager) {
        mStaggeredGridLayoutManager = layoutManager;
    }

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 0;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;


    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int firstVisibleItems[] = mStaggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null);
        int visibleItemCount = view.getChildCount();
        int totalItemCount = mStaggeredGridLayoutManager.getItemCount();

        // FIXME: 16/4/27 防止刚开始时进入onLoadMore
        if (firstVisibleItems.length != 0 && firstVisibleItems[0] == 0) {
            reset();
            return;
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && firstVisibleItems.length != 0 && (totalItemCount - visibleItemCount) <= (firstVisibleItems[0] + visibleThreshold)) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page);

    public void reset() {
        visibleThreshold = 0;
        currentPage = 0;
        previousTotalItemCount = 0;
        loading = true;
        startingPageIndex = 0;
    }
}
