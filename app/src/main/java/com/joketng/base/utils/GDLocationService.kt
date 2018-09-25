package com.joketng.base.utils

import android.Manifest
import android.app.Activity
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.jointem.base.util.showToast
import com.joketng.base.WYCApplication
import com.joketng.base.bean.LocateCity
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * @Description:
 * @Author:  joketng
 * @Email:  joketng@163.com
 * @Time:  2018/6/7
 */
object GDLocationService : AMapLocationListener {
    private lateinit var mAMapLocationClient: AMapLocationClient
    private lateinit var mLocationClientOption: AMapLocationClientOption
    private var rxPermission: RxPermissions? = null
    var isStarted = false
    var mListener: ((aMapLocation: AMapLocation?) -> Unit)? = null


    fun initLocationOption(isOnceLocation: Boolean) {
        mAMapLocationClient = AMapLocationClient(WYCApplication.getContextFromApplication())
        mAMapLocationClient.setLocationListener(this)

        mLocationClientOption = AMapLocationClientOption()
        mLocationClientOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationClientOption.isNeedAddress = true
        mLocationClientOption.isOnceLocation = isOnceLocation
        mAMapLocationClient.setLocationOption(mLocationClientOption)
    }

    fun startLocation(activity: Activity, listener: (aMapLocation: AMapLocation?) -> Unit) {
        rxPermission = RxPermissions(activity)
        mListener = listener
        rxPermission?.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)?.subscribe {
            if (it) {
                mAMapLocationClient.startLocation()
                isStarted = true
            } else {
                activity.showToast("请开启定位权限")
            }
        }
    }

    fun stopLocation() {
        mAMapLocationClient.stopLocation()
        mAMapLocationClient.onDestroy()
        isStarted = false
    }

    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        if (aMapLocation != null) {
            if (WYCApplication.locateCity == null) {
                WYCApplication.locateCity = LocateCity(aMapLocation.cityCode, aMapLocation.city, aMapLocation.address, aMapLocation.latitude, aMapLocation.longitude)
            } else {
                WYCApplication.locateCity.cityCode = aMapLocation.cityCode
                WYCApplication.locateCity.address = aMapLocation.address
                WYCApplication.locateCity.cityName = aMapLocation.city
                WYCApplication.locateCity.latitude = aMapLocation.latitude
                WYCApplication.locateCity.longitude = aMapLocation.longitude
            }
            mListener?.invoke(aMapLocation)
        }
    }


}