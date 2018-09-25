package com.joketng.base;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;
import com.joketng.base.bean.LocateCity;
import com.joketng.base.utils.LocationWrap;
import com.jointem.base.BaseApplication;
import com.jointem.base.util.PreferenceHelper;
import com.jointem.base.util.SharedPreferencesHelper;
import com.jointem.dbhelper.UserInfo2;
import com.jointem.plugin.request.GetInterfaceConfig;
import com.jointem.plugin.request.RetrofitClient;
import com.jointem.plugin.request.util.DBRequestUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


public class WYCApplication extends BaseApplication {
    public static final int REQUEST_RE_LOGIN = 0x08;
    private static Context applicationContext;
    public static LocateCity locateCity;// 当前定位到的城市
    private static UserInfo2 userInfo2;//用户信息
    public static boolean isDownloadingApk;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        LocationWrap.getInstance();
//        SDKInitializer.initialize(getApplicationContext());
        initLog();
        RetrofitClient.initContext(getContextFromApplication(), !GetInterfaceConfig.isReleaseEnvironment);
        Utils.init(this);


    }

    public static Context getContextFromApplication() {
        return applicationContext;
    }

    private void initLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodOffset(0)
                .tag("car")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return !GetInterfaceConfig.isReleaseEnvironment;
            }
        });
    }

    public static void deleteUserInfo(){
        setUserInfo(null);
        DBRequestUtil.deleteUserInfo2(applicationContext);
    }


    public static void setUserInfo(UserInfo2 tempUserInfo) {
        userInfo2 = tempUserInfo;
    }

    public static UserInfo2 getUserInfo() {
        if (userInfo2 == null) {
            String userId = PreferenceHelper.readString(applicationContext, SharedPreferencesHelper.FILENAME, SharedPreferencesHelper.KEY_USER_ID, "");
            if (TextUtils.isEmpty(userId)) return null;
            return userInfo2 = DBRequestUtil.getUserInfo2(applicationContext, userId);
        } else {
            return userInfo2;
        }
    }
}
