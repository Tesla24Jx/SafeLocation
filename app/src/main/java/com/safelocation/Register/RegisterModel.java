package com.safelocation.Register;

import android.util.Log;

import com.google.gson.JsonObject;
import com.safelocation.Entity.HttpRequest;
import com.safelocation.Entity.StrJson;
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
     private String flag="";

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

    //检查手机号是否已注册
    public String checkAccount(String phone){
        //获取数据
        SubscriberOnNextListener getOnNext = new SubscriberOnNextListener<StrJson>() {
            @Override
            public void onNext(StrJson json) {
                    //手机号已存在 data为0时表示手机号未注册,为1时表示手机号已注册
                    flag = json.getData();

                Log.d("###flag---",flag);

            }
        };
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strJson = jsonObject.toString();
        String type = "checkAccount";
        HttpUtil.getInstance().getJSON(getOnNext,strJson,type);
        return flag;
    }

}
