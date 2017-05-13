package com.safelocation.HomePage.Group;

import android.util.Log;

import com.safelocation.HttpUtil.HttpUtil;
import com.safelocation.HttpUtil.SubscriberOnNextListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Juliet on 2017/4/19.
 */

public class GroupModel {
    public static void alertPwd(SubscriberOnNextListener getOnNext, String phone, String oldpwd,String newpwd,String flag){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uphone", phone);
            jsonObject.put("oldpwd", oldpwd);
            jsonObject.put("newpwd", newpwd);
            jsonObject.put("type", flag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strJson = jsonObject.toString();
        String type = "alertpwd";
        Log.d("###SendJson",""+strJson);
        HttpUtil.getInstance().getJSON(getOnNext,strJson,type);
    }

    void uploadhead(SubscriberOnNextListener getOnNext, String img){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("head",img);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strJson = jsonObject.toString();
        String type = "uploadHead";
        //Log.d("###SendJson",""+strJson.toString());
        HttpUtil.getInstance().getJSON(getOnNext,strJson,type);
    }
}

