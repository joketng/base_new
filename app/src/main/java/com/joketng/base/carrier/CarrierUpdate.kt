package com.joketng.base.carrier
import com.google.gson.annotations.SerializedName


/**
 * @Description:
 * @Author:  joketng
 * @Email:  joketng@163.com
 * @Time:  2018/7/12
 */

data class CarrierUpdate(
    @SerializedName("download") val download: String = "",
    @SerializedName("forceUpdate") val forceUpdate: String = "",
    @SerializedName("lastVersion") val lastVersion: String = "",
    @SerializedName("message") val message: String = ""
)