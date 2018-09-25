package com.handmark.pulltorefresh.library.rv;

import android.support.v7.widget.RecyclerView;

/**
 * Created by wuht on 2017/5/23.
 */
public abstract class OnRvRefreshListener {
    public abstract void onPullDownToRefresh(RecyclerView refreshView, int curPageNo);

    public void onPullUpToRefresh(RecyclerView refreshView, int curPageNo) {
    }
}
