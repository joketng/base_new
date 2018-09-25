package com.joketng.base.bean;

import java.io.Serializable;


public final class LocateCity implements Serializable {
    private String countyId;// 对应数据库中的地区码
    private String countyName;
    private String cityId;// 对应数据库中的地区码
    private String cityCode;
    private String cityName;
    private String address; // 详细地址信息
    private double latitude;// 经度
    private double longitude;// 纬度
    private float radius;
    private String province;
    private String street;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public LocateCity(String cityCode, String cityName, String address,
                      double latitude, double longitude) {
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocateCity(String countyName, String cityCode, String cityName, String address,
                      double latitude, double longitude, float radius) {
        this(cityCode, cityName, address, latitude, longitude);
        this.countyName = countyName;
        this.radius = radius;
    }



    public float getRadius() {
        return radius;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
