package com.jointem.base.util

import com.bumptech.glide.request.RequestOptions
import com.jointem.base.R

/**
 * @Description:
 * @Author:  joketng
 * @Email:  joketng@163.com
 * @Time:  2018/9/18
 */
object GlideHelper {
    val requestOptions: RequestOptions = RequestOptions().placeholder(R.drawable.shape_loading).error(R.drawable.shape_loading).centerCrop()

}