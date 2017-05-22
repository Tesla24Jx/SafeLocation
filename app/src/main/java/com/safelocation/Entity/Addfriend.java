package com.safelocation.Entity;

import org.litepal.crud.DataSupport;

/**
 * Created by Juliet on 2017/3/8.
 */

public class Addfriend extends DataSupport{
    private String uid;
    private String fid;
    private String fphone;
    private String fname;
    private String fimg;
    private String mime;
    private String forpermission;
    private String getpermission;
    private String type;
    private String already_add;

    public String getAlready_add() {
        return already_add;
    }

    public void setAlready_add(String already_add) {
        this.already_add = already_add;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFphone() {
        return fphone;
    }

    public void setFphone(String fphone) {
        this.fphone = fphone;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFimg() {
        return fimg;
    }

    public void setFimg(String fimg) {
        this.fimg = fimg;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getForpermission() {
        return forpermission;
    }

    public void setForpermission(String forpermission) {
        this.forpermission = forpermission;
    }

    public String getGetpermission() {
        return getpermission;
    }

    public void setGetpermission(String getpermission) {
        this.getpermission = getpermission;
    }
}
