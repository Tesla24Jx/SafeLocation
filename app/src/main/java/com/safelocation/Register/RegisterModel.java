package com.safelocation.Register;

import android.util.Log;

import com.google.gson.JsonObject;
import com.safelocation.Entity.UserInfo;
import com.safelocation.HttpUtil.HttpUtil;
import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.Utils.Common;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Juliet on 2017/1/28.
 */

public class RegisterModel {

    void sublinInfo(SubscriberOnNextListener getOnNext, UserInfo userInfo){

        String strJson = Common.gson.toJson(userInfo);
        String type = "register";
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
