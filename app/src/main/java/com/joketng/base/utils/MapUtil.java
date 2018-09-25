package com.joketng.base.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * @Description:
 * @Author: joketng
 * @Email: joketng@163.com
 * @Time: 2018/6/27
 */
public class MapUtil {


    /**
     * 百度转高德
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    /**
     * 高德地图转百度[暂时不用]
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    private double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }
    /*
     * 打开腾讯地图
     * params 参考http://lbs.qq.com/uri_v1/guide-route.html
            *
            * @param context
     * @param dqAddress
     * @param gotoAddress
     * @param gotoLatitude
     * @param gotoLongitude
     * 驾车：type=drive，policy有以下取值
    0：较快捷
    1：无高速
    2：距离
            policy的取值缺省为0
     * &from=" + dqAddress + "&fromcoord=" + dqLatitude + "," + dqLongitude + "
            */
    public static void openTencentMap(Context context, String dqAddress, String gotoAddress, String gotoLatitude, String gotoLongitude) {
        try {
            if (context != null && !TextUtils.isEmpty(dqAddress) && !TextUtils.isEmpty(gotoAddress) && !TextUtils.isEmpty(gotoLatitude) && !TextUtils.isEmpty(gotoLongitude)) {
                //double[] gotoLang=AMAPUtils.getInstance().bdToGaoDe(Double.parseDouble(gotoLatitude),Double.parseDouble(gotoLongitude));
                //gotoLatitude=String.valueOf(gotoLang[0]);gotoLongitude=String.valueOf(gotoLang[1]);
                String url1 = "qqmap://map/routeplan?type=drive&to=" + gotoAddress + "&tocoord=" + gotoLatitude + "," + gotoLongitude + "&policy=2&referer=myapp";
                Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(url1));
                context.startActivity(intent);
            }
        } catch (Exception e) {

        }
    }

    /**
     * openGaoDeMap void 调用高德地图apk
     * http://lbs.amap.com/api/uri-api/guide/travel/route
     */
    public static void openGaoDeMap(Context context, String dqAddress, String gotoAddress, String gotoLatitude, String gotoLongitude) {
        try {
            if (context != null && !TextUtils.isEmpty(gotoAddress) && !TextUtils.isEmpty(gotoLatitude) && !TextUtils.isEmpty(gotoLongitude)) {
                //double[] gotoLang=AMAPUtils.getInstance().bdToGaoDe(Double.parseDouble(gotoLatitude),Double.parseDouble(gotoLongitude));
                //gotoLatitude=String.valueOf(gotoLang[0]);gotoLongitude=String.valueOf(gotoLang[1]);
                String url = "androidamap://navi?sourceApplication=" + dqAddress + "&poiname=" + gotoAddress + "&lat=" + gotoLatitude + "&lon=" + gotoLongitude + "&dev=0&style=1";
                Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(url));
                intent.setPackage("com.autonavi.minimap");
                context.startActivity(intent);
            }
        } catch (Exception e) {

        }
    }

    /***
     * 百度地图[传参过来的值可以判断为百度]
     * http://lbsyun.baidu.com/index.php?title=uri/api/android
     */
    public static void openBaiduMap(Context context, String dqAddress, String gotoAddress, String gotoLatitude, String gotoLongitude) {
        try {
            if (context != null && !TextUtils.isEmpty(dqAddress) && !TextUtils.isEmpty(gotoAddress) && !TextUtils.isEmpty(gotoLatitude) && !TextUtils.isEmpty(gotoLongitude)) {
//                double[] gotoLang=AMAPUtils.getInstance().gaoDeToBaidu(Double.parseDouble(gotoLatitude),Double.parseDouble(gotoLongitude));
//                gotoLatitude=String.valueOf(gotoLang[0]);gotoLongitude=String.valueOf(gotoLang[1]);
                String url_map = "intent://map/direction?" +
                        //"origin=latlng:" + dqLatitude + "," + dqLongitude +   //起点  此处不传值默认选择当前位置如果传参则提示参数错误
                        "destination=latlng:" + gotoLatitude + "," + gotoLongitude + "|name:" + gotoAddress +
                        "&mode=driving&" +          //导航路线方式
                        "region=" + dqAddress +           //
                        "&src=" + dqAddress + "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
                Intent intent = Intent.getIntent(url_map);
                context.startActivity(intent); // 启动调用
            } else {
                Log.d(TAG, "openBaiduMap is failed ,failed msg is have params is null");
            }
        } catch (Exception e) {

        }
    }
    //com.baidu.BaiduMap
    //com.autonavi.minimap
    //com.tencent.map

    public static final String BD_PACKAGE = "com.baidu.BaiduMap";
    public static final String GD_PACKAGE = "com.autonavi.minimap";
    public static final String TX_PACKAGE = "com.tencent.map";
    public static boolean isInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
        final List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        if (pinfo != null) {
            for (PackageInfo info : pinfo) {
                if (info.packageName.equals(packageName)) {
                    return true;
                }
                //pName.add(pn);
            }
        }
        return false;
    }

}
