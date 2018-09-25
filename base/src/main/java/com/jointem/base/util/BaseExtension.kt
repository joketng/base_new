package com.jointem.base.util

import android.content.Context
import android.widget.Toast
import com.jointem.base.view.CustomToast

/**
 * @Description:
 * @Author:  joketng
 * @Email:  joketng@163.com
 * @Time:  2018/9/21
 */
fun Context.showToast(resId: Int) {
    val toast = CustomToast(this, resId)
    toast.show()
}

@JvmOverloads fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    val toast = CustomToast(this, message)
    toast.show(duration)
}

fun <T> isNotEmptyAny(list: Collection<T>?): Boolean {
    return list?.isNotEmpty() ?: false
}


fun Context.getScreenH(): Int {
    val dm = resources.displayMetrics
    return dm.heightPixels
}

fun Context.getScreenW(): Int {
    val dm = resources.displayMetrics
    return dm.widthPixels
}