package com.safelocation.HomePage.FriendList;

import android.util.Log;

import com.safelocation.Entity.HttpRequest;
import com.safelocation.Entity.UserInfo;
import com.safelocation.Entity.Userdata;
import com.safelocation.HttpUtil.HttpUtil;
import com.safelocation.HttpUtil.SubscriberOnNextListener;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Juliet on 2017/1/28.
 */

public class FriendListModel {

    void searchFriend(SubscriberOnNextListener getOnNext,String phone){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strJson = jsonObject.toString();
        String type = "searchfriend";
        Log.d("###SendJson",""+strJson);
        HttpUtil.getInstance().getJSON(getOnNext,strJson,type);
    }
    void addfriend(SubscriberOnNextListener getOnNext,String fid,int forpermission,int getpermission,String mime){
            Log.d("###fid=",fid);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("uid", Userdata.uid);
                jsonObject.put("fid", fid);
                jsonObject.put("forpermission", forpermission);
                jsonObject.put("getpermission", getpermission);
                jsonObject.put("mime", mime);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String strJson = jsonObject.toString();
            String type = "addfriend";
            Log.d("###SendJson",""+strJson);
            HttpUtil.getInstance().getJSON(getOnNext,strJson,type);
        }

    void  saveDatatoDB(HttpRequest httpRequest){
        Log.d("###saveDatatoDB","_model");
        UserInfo userInfo = httpRequest.getMydata();
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setUname("jin");
        userInfo2.setImg("qqq");
        userInfo2.setSex("male");
        userInfo2.setAge(1);
        userInfo2.setUphone("123213123");
        userInfo2.setUpwd("qq123456");

        Log.d("###username",userInfo.getUname());


        if (userInfo2.save()) {
            Log.d("###Data save DB","success");
        } else {
            Log.d("###Data save DB","fail");
        }

    }

}
