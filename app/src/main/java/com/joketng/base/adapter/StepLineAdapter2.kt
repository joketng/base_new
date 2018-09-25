package com.joketng.base.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.handmark.pulltorefresh.library.rv.BaseRecyclerAdapter
import com.joketng.base.R
import com.joketng.base.utils.inflate
import com.orhanobut.logger.Logger


class StepLineAdapter2(ctx: Context, listContent: List<String>): BaseRecyclerAdapter<String, StepLineAdapter2.MyViewHolder>(ctx, listContent) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent.inflate(R.layout.item_step_line2))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        Logger.d(position)
        with(holder){
//            if(position == 0){
//                itemView.img_line_start.visibility = View.INVISIBLE
//                itemView.img_dot.layoutParams.width = context.dip(20)
//                itemView.img_dot.layoutParams.height = context.dip(20)
//                itemView.img_dot.setImageResource(R.mipmap.now)
//                itemView.text_timeline_title.textSize = 16f
//                itemView.text_timeline_title.paint.isFakeBoldText = true
//                itemView.tv_check_state.visible(false)
//
//            } else {
//                itemView.img_line_start.visibility = View.VISIBLE
//                itemView.img_dot.layoutParams.width = context.dip(12)
//                itemView.img_dot.layoutParams.height = context.dip(12)
//                itemView.img_dot.setImageResource(R.drawable.shape_dot_orange)
//                itemView.text_timeline_title.textSize = 14f
//                itemView.text_timeline_title.paint.isFakeBoldText = false
//                itemView.tv_check_state.visible(true)
//            }
//            itemView.text_timeline_title.text = DataUtil.qualificationId2Text(itemList[position].subType)
//            if(itemList[position].approveStatus == "3"){
//                itemView.text_timeline_title.textColor = ContextCompat.getColor(context,R.color.c_deep_red)
//                itemView.tv_check_state.textColor = ContextCompat.getColor(context, R.color.c_deep_red)
//            } else {
//                itemView.text_timeline_title.textColor = ContextCompat.getColor(context,R.color.c_main_orange)
//                itemView.tv_check_state.textColor = ContextCompat.getColor(context, R.color.c_main_orange)
//            }
//            if(itemList[position].remarks.isEmpty()){
//                itemView.tv_check_state.text = "正在审核"+ DataUtil.qualificationId2Text(itemList[position].subType)
//            } else {
//                itemView.tv_check_state.text = itemList[position].remarks
//            }
        }
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
}