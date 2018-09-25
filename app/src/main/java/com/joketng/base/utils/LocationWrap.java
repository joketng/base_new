package com.joketng.base.utils;

import android.content.Context;

import com.joketng.base.bean.LocateCity;


/**
 * @author THC
 * @Title: LocationWrap
 * @Package com.jointem.zyb.util
 * @Description:
 * @date 2016/5/24 14:49
 */
public class LocationWrap {
    private static LocationWrap locationWrap;
//    private static LocationServiceUtil locationServiceUtil;
    private Context context;
    private LocateCity mLocateCity;
    private OnLocationFinishListener onLocationFinishListener;


    public static LocationWrap getInstance() {
        if (null == locationWrap) {
//            locationWrap = new LocationWrap(WYCApplication.getContextFromApplication());
//            locationServiceUtil = new LocationServiceUtil(WYCApplication.getContextFromApplication());
        }
        return locationWrap;
    }

    private LocationWrap(Context context) {
        this.context = context;
    }

    public LocationWrap initLocation() {
//        locationServiceUtil.registerListener(dbLocationListener);
//        locationServiceUtil.setLocationOption(locationServiceUtil.getDefaultLocationClientOption());
//        locationServiceUtil.start();
        return locationWrap;
    }

//    public BDLocationListener dbLocationListener = new BDLocationListener() {
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            locationServiceUtil.unregisterListener(dbLocationListener); //注销掉监听
//            locationServiceUtil.stop();
//
//            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation ||
//                    bdLocation.getLocType() == BDLocation.TypeNetWorkLocation ||
//                    bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {//
//                // GPS定位结果 || 网络定位结果  || 离线定位结果
//                mLocateCity = new LocateCity(bdLocation.getDistrict(), bdLocation.getCityCode(),
//                        bdLocation.getCity(), bdLocation.getAddrStr(), bdLocation.getLatitude(),
//                        bdLocation.getLongitude(), bdLocation.getRadius());
//                mLocateCity.setProvince(bdLocation.getProvince());
//                mLocateCity.setStreet(bdLocation.getStreet() + bdLocation.getStreetNumber());
//            }
//
//            if (onLocationFinishListener != null) {
//                onLocationFinishListener.locationFinish();
//            }
////            if (mLocateCity != null) {
////                Utils.saveObject(context, CacheRecoveryPersistentData.PREF_KEY_LOCATE_CITY, mLocateCity);
////            }
//        }
//    };

    public interface OnLocationFinishListener {
        void locationFinish();
    }

    public void setOnLocationFinishListener(OnLocationFinishListener onLocationFinishListener) {
        this.onLocationFinishListener = onLocationFinishListener;
    }

    public void stopLocation() {
//        if (locationServiceUtil != null) {
//            locationServiceUtil.unregisterListener(dbLocationListener);
//            locationServiceUtil.stop();
//        }
    }

    public LocateCity getLocateCity() {
        return mLocateCity;
    }
}
