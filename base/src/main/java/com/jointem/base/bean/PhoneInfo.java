package com.jointem.base.bean;/**
 * Created by jointem on 2016/8/4.
 */

import java.io.Serializable;

/**
 * @author THC
 * @Title: PhoneInfo
 * @Package com.jointem.zyb.bean
 * @Description:
 * @date 2016/8/4 11:21
 */
public class PhoneInfo implements Serializable {
    private String appVersionName;
    private String imei;
    private String model;
    private String systemVersion;

    public PhoneInfo(String appVersionName, String imei, String model, String systemVersion) {
        this.appVersionName = appVersionName;
        this.imei = imei;
        this.model = model;
        this.systemVersion = systemVersion;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }
}
