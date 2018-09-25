package com.jointem.base;

import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDexApplication;

import com.jointem.base.bean.PhoneInfo;
import com.jointem.base.util.SharedPreferencesHelper;
import com.jointem.base.util.SystemTool;


/**
 * Description:
 * Created by Kevin.Li on 2017/4/10.
 */
public class BaseApplication extends MultiDexApplication {
    public static String appVersionName = "";
    public static String imei = "";
    public static String model = "";
    public static String systemVersion = "";
    private static Context applicationContext;
    public static boolean isNetWork = true;//是否有网
    public static boolean isUpdateCityList=true;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        PhoneInfo phoneInfo = (PhoneInfo) SharedPreferencesHelper.readObject(getApplicationContext(), SharedPreferencesHelper.KEY_PHONE_INFO);
        if (phoneInfo != null) {
            appVersionName = phoneInfo.getAppVersionName();
            imei = phoneInfo.getImei();
            model = phoneInfo.getModel();
            systemVersion = phoneInfo.getSystemVersion();
        }
    }


    /**
     * 获取手机基本信息及定位城市编码
     */
    public static void initPhoneMessage(Context context) {
        appVersionName = SystemTool.getAppVersionName(context);
        imei = SystemTool.getPhoneIMEI(context);
        model = Build.MODEL;
        systemVersion = SystemTool.getSystemVersion();
        SharedPreferencesHelper.saveObject(context, SharedPreferencesHelper.KEY_PHONE_INFO, new PhoneInfo(appVersionName, imei, model, systemVersion));
    }

    public static Context getContextFromApplication() {
        return applicationContext;
    }
}
