package com.joketng.base.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bigkoo.pickerview.TimePickerView
import com.bumptech.glide.Glide
import com.handmark.pulltorefresh.library.rv.PullToRefreshRecyclerView
import com.jointem.base.util.GlideHelper
import com.jointem.base.util.getScreenW
import com.joketng.base.R
import kotlinx.android.synthetic.main.dialog_recycler_bottom.view.*
import org.jetbrains.anko.layoutInflater
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Description:
 * @Author:  joketng
 * @Time:  2018/6/26
 */

fun View.singleClick(l: (android.view.View?) -> Unit) {
    setOnClickListener(l)
}

fun View.visible(show: Boolean = true) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return context.layoutInflater.inflate(layoutRes, this, attachToRoot)
}

fun PullToRefreshRecyclerView.isPullDown(currentPage: String, totalPage: String): Boolean {
    setMaxPageNo(totalPage.toInt())
    setCurPageNo(currentPage.toInt())
    onRefreshComplete()
    return isClearData
}

fun View.ripple(){
    setBackgroundResource(com.jointem.base.R.drawable.ripple_item_clicked_with_mask);
}

fun View.initTimeView(context: Context,callBack:((Date, View)->Unit)? = null): TimePickerView {
    val selectedDate = Calendar.getInstance()
    return TimePickerView.Builder(context, TimePickerView.OnTimeSelectListener { date, v ->
        if(v is EditText){
            (v as? EditText)?.setText(SimpleDateFormat("yyyy-MM-dd").format(date))
        } else if(v is TextView){
            (v as? TextView)?.text = SimpleDateFormat("yyyy-MM-dd").format(date)
        }
        callBack?.invoke(date,v)

    })
            //年月日时分秒 的显示与否，不设置则默认全部显示
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setLabel("年", "月", "日", "", "", "")
            .isCenterLabel(false)
            .setDividerColor(Color.DKGRAY)
            .setContentSize(21)
            .setDate(selectedDate)
            //                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
            .setDecorView(null)
            .build()

}

fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).apply(GlideHelper.requestOptions).into(this)
//    Glide.with(this.context)
//            .load(url)
//            .placeholder(R.mipmap.ic_hgp_loading_rectangle)
//            .error(R.mipmap.ic_hgp_loading_rectangle)
//            .centerCrop()
//            .crossFade()
//            .into(this)
}

fun View.showCenterDialog(dialog: AlertDialog) {
    val window = dialog.getWindow()
    window.setGravity(Gravity.CENTER)
    window.setWindowAnimations(R.style.dialog)
//    val lp = window.attributes
//    lp.width =
//    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//    window.setAttributes(lp)
    setBackgroundColor(Color.WHITE)
    dialog.setCanceledOnTouchOutside(true)
    dialog.show()
    dialog.setContentView(this)
    dialog.window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    window.setLayout((context.getScreenW() * 0.8f +0.5).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
}

fun View.showBottomDialog(dialog: AlertDialog = AlertDialog.Builder(context, R.style.dialog1).create(), width:Int = WindowManager.LayoutParams.MATCH_PARENT, height : Int = WindowManager.LayoutParams.WRAP_CONTENT) {
    val window = dialog.getWindow()
    window.setGravity(Gravity.BOTTOM)
    window.setWindowAnimations(R.style.popupWindowAnimationBottom)
//    val lp = window.attributes
//    lp.width =
//    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//    window.setAttributes(lp)
    setBackgroundColor(Color.WHITE)
    dialog.setCanceledOnTouchOutside(true)
    dialog.show()
    dialog.setContentView(this)
    dialog.window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    window.setLayout(width, height)
    this.tv_dialog_cancel?.singleClick { dialog.dismiss() }
}