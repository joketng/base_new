package com.joketng.base.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.joketng.base.R
import org.jetbrains.anko.find

/**
 * Created by thc on 2017/9/25.
 */
class CustomToast {

    var toast: Toast? = null
    lateinit var ctx: Context
    var str: String? = null
    var x = 0
    var y = 0

    constructor(ctx: Context, str: String?) {
        this.ctx = ctx
        this.str = str
        x = Gravity.LEFT
        y = Gravity.TOP + 100
    }


    constructor(ctx: Context, id: Int) {
        this.ctx = ctx
        this.str = ctx.resources.getString(id)
        x = Gravity.LEFT + 50
        y = Gravity.TOP + 100
    }

    fun show(time: Int = Toast.LENGTH_LONG) {
        if (toast == null) {
            toast = Toast(ctx)
        }
        val contentView = LayoutInflater.from(ctx).inflate(R.layout.toast, null)
        val drawable = ContextCompat.getDrawable(ctx, R.drawable.bg_toast)
        drawable?.alpha = 190
        ViewCompat.setBackground(contentView, drawable)
        val textView = contentView.find<TextView>(R.id.tv_toast)
        textView.text = str
        toast?.view = contentView
        toast?.duration = time
        toast?.show()
    }
}