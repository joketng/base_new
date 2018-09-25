package com.joketng.base.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.handmark.pulltorefresh.library.rv.BaseRecyclerAdapter
import com.joketng.base.R
import com.joketng.base.utils.inflate


import kotlinx.android.synthetic.main.item_pop_window_bottom.view.*

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/2/1
 */
class DialogBottomAdapter(ctx: Context, listContent: List<String>): BaseRecyclerAdapter<String, DialogBottomAdapter.PopViewHolder>(ctx, listContent) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopViewHolder {
        return PopViewHolder(parent.inflate(R.layout.item_pop_window_bottom)!!)
    }

    override fun onBindViewHolder(holder: PopViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        with(holder!!){
//            if(position == 0){
//                itemView.tv_pop_window_bottom.textColor = ContextCompat.getColor(mContext, R.color.deep_blue)
//            } else {
//                itemView.tv_pop_window_bottom.textColor = ContextCompat.getColor(mContext, R.color.home_detail_color)
//            }
            itemView.tv_pop_window_bottom.text = itemList[position]
        }
    }

    class PopViewHolder(view: View): RecyclerView.ViewHolder(view)
}