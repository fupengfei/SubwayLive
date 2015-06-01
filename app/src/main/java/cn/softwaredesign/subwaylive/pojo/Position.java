package cn.softwaredesign.subwaylive.pojo;

import java.io.Serializable;

/**
 * Created by Think on 2015/5/30.
 */
public class Position implements Serializable{
    private String poi="";
    private String cityCode="";
    private String areaCode ="";
    private String country="";
    private String province="";
    private String city="";
    private String counry="";
    private String road="";
    private double latitude=0; // 经度
    private double longitude=0; // 纬度

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCounry(String counry) {
        this.counry = counry;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPoi() {
        return poi;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getCounry() {
        return counry;
    }

    public String getRoad() {
        return road;
    }

    public double getLatitude() {
        return latitude;
    }
}
