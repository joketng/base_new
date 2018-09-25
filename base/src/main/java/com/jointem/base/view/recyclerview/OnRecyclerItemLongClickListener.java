package com.jointem.base.view.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * @author THC
 * @Title: OnRecyclerItemLongClickListener
 * @Package com.jointem.zyb.view.widget.recyclerview
 * @Description:
 * @date 2016/3/25 16:42
 */
public interface OnRecyclerItemLongClickListener {
    void onItemLongClick(RecyclerView.ViewHolder viewHolder, int position);
}
