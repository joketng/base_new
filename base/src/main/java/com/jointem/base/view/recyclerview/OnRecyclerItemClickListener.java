package com.jointem.base.view.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * @author THC
 * @Title: OnRecyclerItemClickListener
 * @Package com.jointem.zyb.view.widget.recyclerview
 * @Description:
 * @date 2016/3/25 16:40
 */
public interface OnRecyclerItemClickListener {
    void onItemClick(RecyclerView.ViewHolder viewHolder, int position);
}
