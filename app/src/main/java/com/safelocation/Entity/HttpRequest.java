package com.safelocation.Entity;

import java.util.List;

/**
 * Created by Juliet on 2017/2/21.
 */

public class HttpRequest {
    private String code;
    private String msg;
    private UserInfo mydata;
    private List<FriendInfo> friendlist;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfo getMydata() {
        return mydata;
    }

    public void setMydata(UserInfo mydata) {
        this.mydata = mydata;
    }

    public List<FriendInfo> getFriendlist() {
        return friendlist;
    }

    public void setFriendlist(List<FriendInfo> friendlist) {
        this.friendlist = friendlist;
    }
}
