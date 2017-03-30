package com.safelocation.Entity;

import org.litepal.crud.DataSupport;

/**
 * Created by Juliet on 2017/2/23.
 */

public class FriendInfo extends DataSupport{

    private int fid;
    private String fname;
    private String fphone;
    private String fsex;
    private int fage;
    private String fimg;
    private int getpermission;
    private int forpermission;
    private String fremarks;
    private String flastlogintime;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFphone() {
        return fphone;
    }

    public void setFphone(String fphone) {
        this.fphone = fphone;
    }

    public String getFsex() {
        return fsex;
    }

    public void setFsex(String fsex) {
        this.fsex = fsex;
    }

    public int getFage() {
        return fage;
    }

    public void setFage(int fage) {
        this.fage = fage;
    }

    public String getFimg() {
        return fimg;
    }

    public void setFimg(String fimg) {
        this.fimg = fimg;
    }

    public int getGetpermission() {
        return getpermission;
    }

    public void setGetpermission(int getpermission) {
        this.getpermission = getpermission;
    }

    public int getForpermission() {
        return forpermission;
    }

    public void setForpermission(int forpermission) {
        this.forpermission = forpermission;
    }

    public String getFremarks() {
        return fremarks;
    }

    public void setFremarks(String fremarks) {
        this.fremarks = fremarks;
    }

    public String getFlastlogintime() {
        return flastlogintime;
    }

    public void setFlastlogintime(String flastlogintime) {
        this.flastlogintime = flastlogintime;
    }
}
