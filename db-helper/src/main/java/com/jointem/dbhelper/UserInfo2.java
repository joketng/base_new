package com.jointem.dbhelper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:
 * @Author: joketng
 * @Email: joketng@163.com
 * @Time: 2018/6/29
 */
@Entity
public class UserInfo2 {
    @Id
    private String mobile;
    private String name;
    private String token;
    private String cityCode;
    private String driverId;
    private String refreshToken;
    private String driverApproveStatus;//审核结果
    private String driverType;//司机类型
    @Generated(hash = 1510277722)
    public UserInfo2(String mobile, String name, String token, String cityCode,
            String driverId, String refreshToken, String driverApproveStatus,
            String driverType) {
        this.mobile = mobile;
        this.name = name;
        this.token = token;
        this.cityCode = cityCode;
        this.driverId = driverId;
        this.refreshToken = refreshToken;
        this.driverApproveStatus = driverApproveStatus;
        this.driverType = driverType;
    }
    @Generated(hash = 812928854)
    public UserInfo2() {
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getCityCode() {
        return this.cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getDriverApproveStatus() {
        return this.driverApproveStatus;
    }
    public void setDriverApproveStatus(String driverApproveStatus) {
        this.driverApproveStatus = driverApproveStatus;
    }
    public String getDriverType() {
        return this.driverType;
    }
    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }
    public String getRefreshToken() {
        return this.refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getDriverId() {
        return this.driverId;
    }
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }



}
