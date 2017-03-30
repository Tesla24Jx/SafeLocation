package com.safelocation.Entity;

/**
 * Created by Juliet on 2017/2/28.
 */

public class Flist {
    private String phone;
    private String name;
    private int getPermission;
    private String imgurl;
    private String PinYin;
    private String FirstPinYin;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGetPermission() {
        return getPermission;
    }

    public void setGetPermission(int getPermission) {
        this.getPermission = getPermission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstPinYin() {
        return FirstPinYin;
    }

    public void setFirstPinYin(String firstPinYin) {
        FirstPinYin = firstPinYin;
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String pinYin) {
        PinYin = pinYin;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
