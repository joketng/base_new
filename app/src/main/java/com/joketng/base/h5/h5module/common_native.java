package com.joketng.base.h5.h5module;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

import com.blankj.utilcode.util.AppUtils;
import com.jointem.base.BaseActivity;
import com.jointem.base.util.AlertDialogHelper;
import com.jointem.base.util.IDialogCallBack;
import com.jointem.base.util.IntentUtil;
import com.jointem.plugin.request.util.GsonUtils;
import com.joketng.base.R;
import com.joketng.base.WYCApplication;
import com.joketng.base.activity.MainActivity;
import com.joketng.base.h5.CallBack4H5;
import com.joketng.base.h5.ant.JCallBack;
import com.joketng.base.h5.ant.JContext;
import com.joketng.base.h5.ant.JWebView;
import com.joketng.base.h5.ant.Param;
import com.joketng.base.utils.LocationWrap;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;


/**
 * @author THC
 * @Title: common_native
 * @Package com.jointem.hgp.h5.h5module
 * @Description:
 * @date 2017/5/8 16:39
 */
public class common_native {

    public static void getAppLocation(@JContext Context context, @Param("realTime") String realTime, @JCallBack final CallBack4H5 callBack4H5) {
        final BaseActivity baseActivity = (BaseActivity) context;
        if (WYCApplication.locateCity != null) {
            if (TextUtils.equals(realTime, "0")) {
                callBack4H5.onSuccess(buildAppLocationJsonStr(true));
            } else if (TextUtils.equals(realTime, "1")) {
                LocationWrap.getInstance().setOnLocationFinishListener(new LocationWrap.OnLocationFinishListener() {
                    @Override
                    public void locationFinish() {
                        if (WYCApplication.locateCity != null) {
                            callBack4H5.onSuccess(buildAppLocationJsonStr(true));
                        } else {
                            callBack4H5.onFailed(buildAppLocationJsonStr(false));
                        }
                    }
                });
                LocationWrap.getInstance().initLocation();

            }
        } else {
            RxPermissions rxPermissions = new RxPermissions(baseActivity);
            rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                LocationWrap.getInstance().setOnLocationFinishListener(new LocationWrap.OnLocationFinishListener() {
                                    @Override
                                    public void locationFinish() {
                                        if (WYCApplication.locateCity != null) {
                                            callBack4H5.onSuccess(buildAppLocationJsonStr(true));
                                        } else {
                                            callBack4H5.onFailed(buildAppLocationJsonStr(false));
                                        }
                                    }
                                });
                                LocationWrap.getInstance().initLocation();
                            } else {
                                callBack4H5.onFailed(buildAppLocationJsonStr(false));
                                AlertDialogHelper.getInstance().buildSimpleDialog(baseActivity, baseActivity.getString(R.string.permission_custom_content) + "定位", new IDialogCallBack() {
                                    @Override
                                    public void onSure(String tag) {
                                        IntentUtil.gotoPermissionSettings(baseActivity);
                                    }

                                    @Override
                                    public void onCancel(String tag) {

                                    }
                                });
                                AlertDialogHelper.getInstance().setSureText(baseActivity.getString(R.string.setting));
                            }
                        }
                    });
        }
    }

    public static void getAppVersion(@JContext Context context, @JCallBack CallBack4H5 callBack4H5) {
        String version = buildAppVersionJsonStr(context);
        if (!TextUtils.isEmpty(version)) {
            callBack4H5.onSuccess(version);
        } else {
            callBack4H5.onFailed(version);
        }
    }

    public static void toHomePage(@JContext Context context) {
        Intent homepageIntent = new Intent(context, MainActivity.class);
        context.startActivity(homepageIntent);
    }

    public static void toForwardLink(@JContext Context context, @JWebView WebView webView) {
        Activity activity = (Activity) context;
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            activity.finish();
        }
    }

    public static void closeCurrentPage(@JContext Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            activity.finish();
        }
    }

    public static void hideSoftInputFromWindow(@JContext Context context) {
        Activity activity = (Activity) context;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    private static String buildAppVersionJsonStr(Context context) {
        Map<String, String> map = new HashMap<>();
        String version = "0";
        String jsStr = "";
        try {
            version = AppUtils.getAppVersionName();
            map.put("appVersion", version);
            jsStr = GsonUtils.getInstance().toJsonString(map);
        } catch (Exception e) {
            e.printStackTrace();
            jsStr = null;
        }
        return jsStr;
    }


    private static String buildAppLocationJsonStr(boolean isSuccess) {
        if (isSuccess) {
            Map<String, String> map = new HashMap<>();
            map.put("longitude", String.valueOf(WYCApplication.locateCity.getLongitude()));
            map.put("latitude", String.valueOf(WYCApplication.locateCity.getLatitude()));
            map.put("addressName", WYCApplication.locateCity.getAddress());
            map.put("province", WYCApplication.locateCity.getProvince());
            map.put("city", WYCApplication.locateCity.getCityName());
            map.put("county", WYCApplication.locateCity.getCountyName());
            map.put("street", WYCApplication.locateCity.getStreet());
            return GsonUtils.getInstance().toJsonString(map);
        } else {
            return null;
        }
    }

    private static String buildSwitchCityJsonStr(String cityName, String cityCode) {
        HashMap<String, String> stringHashMap = new HashMap<>();
        if (!TextUtils.isEmpty(cityName)) {
            stringHashMap.put("cityName", cityName);
        }
        if (!TextUtils.isEmpty(cityCode)) {
            stringHashMap.put("cityCode", cityCode);
        }
        String jsonString = null;
        if (!stringHashMap.isEmpty()) {
            jsonString = GsonUtils.getInstance().toJsonString(stringHashMap);
        }
        return jsonString;
    }
}
