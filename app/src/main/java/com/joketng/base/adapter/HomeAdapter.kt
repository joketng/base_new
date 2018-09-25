package com.joketng.base.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.handmark.pulltorefresh.library.rv.BaseRecyclerAdapter
import com.joketng.base.R
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/3/2
 */
class HomeAdapter(context: Context, itemList: List<String>) : BaseRecyclerAdapter<String, HomeAdapter.MyViewHolder>(context, itemList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(layoutInflater.inflate(R.layout.item_home, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.textView.text = itemList[position]
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        init {
            textView = itemView.tv_test
        }
    }
}