package com.jointem.base.view.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * @author THC
 * @Title: OnItemTouchViewHoldListener
 * @Package com.jointem.zyb.view.widget.recyclerview
 * @Description:
 * @date 2016/3/17 17:52
 */
public interface OnItemTouchViewHoldListener {

    void onItemSelected(RecyclerView.ViewHolder viewHolder, int actionState);

    void onItemClear(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder);
}
