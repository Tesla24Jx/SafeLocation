package com.safelocation.login;

import android.util.Log;

import com.safelocation.Entity.HttpRequest;
import com.safelocation.Entity.UserInfo;
import com.safelocation.HttpUtil.HttpUtil;
import com.safelocation.HttpUtil.ProgressSubscriber;
import com.safelocation.HttpUtil.SubscriberOnNextListener;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Juliet on 2017/1/28.
 */

public class LoginModel {

    void login(SubscriberOnNextListener getOnNext,String phone,String pwd){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phone);
            jsonObject.put("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strJson = jsonObject.toString();
        String type = "login";
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
