package com.joketng.base.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.joketng.base.R
import com.joketng.base.adapter.DialogBottomAdapter
import com.jointem.base.view.recydivider.ItemDecorations


/**
 * @Description:
 * @Author:  joketng
 * @Email:  joketng@163.com
 * @Time:  2018/5/22
 */
class DialogRecycleView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {
    lateinit var recyclerAdapter: DialogBottomAdapter

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        addItemDecoration(ItemDecorations.vertical(context).type(0, R.drawable.recycler_item_divider).create())
    }

    fun initItems(list: List<String>, callBack: (String) -> Unit) {
        recyclerAdapter = DialogBottomAdapter(context, list)
        adapter = recyclerAdapter
        recyclerAdapter.setOnItemClickListener {
            callBack.invoke(list[it.layoutPosition])
        }
    }

    fun initItems2(list: List<String>, callBack: (String, Int) -> Unit) {
        recyclerAdapter = DialogBottomAdapter(context, list)
        adapter = recyclerAdapter
        recyclerAdapter.setOnItemClickListener {
            callBack.invoke(list[it.layoutPosition], it.layoutPosition)
        }
    }
}