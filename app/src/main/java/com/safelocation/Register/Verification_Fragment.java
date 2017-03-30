package com.safelocation.Register;

import android.Manifest;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.safelocation.*;
import com.safelocation.Utils.CountDownTimerUtils;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Juliet on 2017/2/24.
 */

public class Verification_Fragment extends Fragment implements View.OnClickListener {

    //APPKEY
    private static String APPKEY = "1a6d5a7dd8f8c";
    //从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "3ca473ef5d31a905d7ea88d63b4a0f43";
    Thread thread;
    private GotoNewxtPage mCallback;
    private EventHandler eh;
    private TextView topbar_title;
    private Button btnRegist;
    private TextView btnGetCode;
    private EditText edPhoneNum;
    private EditText edVerification;
    boolean flag = false;//倒计时标记

    private int r_time;

    public interface GotoNewxtPage{
        void gotoCompleteInfo(String str);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_reg,container,false);
        topbar_title = (TextView) view.findViewById(R.id.topbar_title);
        btnGetCode = (TextView) view.findViewById(R.id.btn_sendCode);
        btnRegist = (Button)view.findViewById(R.id.btn_register);
        edPhoneNum = (EditText)view.findViewById(R.id.ed_phoneNum);
        edVerification = (EditText)view.findViewById(R.id.ed_verificationCode);

        btnGetCode.setOnClickListener(this);
        btnRegist.setOnClickListener(this);

        topbar_title.setText("注册");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //版本解决
        versionsolve();
        //初始化短信验证码SDK
        initSDK();
    }

    private void initSDK() {
        // 初始化短信SDK
        SMSSDK.initSDK(getActivity(), APPKEY, APPSECRET);
        //短信回调监听
        eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {//校验成功
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //验证码校验成功
                        @SuppressWarnings("unchecked")
                        HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                        String country = (String) phoneMap.get("country");
                        String phone = (String) phoneMap.get("phone");
                        Log.d("###短信验证成功","手机号码为："+phone+"  国家为："+country);
                        Snackbar.make(btnGetCode,"验证成功",Snackbar.LENGTH_SHORT).show();
                        mCallback.gotoCompleteInfo(phone);
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功  true为智能验证，false为普通下发短信
                        if((boolean)data){
                            Snackbar.make(btnGetCode, "手机号已注册", Snackbar.LENGTH_SHORT).show();
                        }else {
                            Snackbar.make(btnGetCode, "验证码已发送至手机" + edPhoneNum.getText().toString(), Snackbar.LENGTH_SHORT).show();
                            flag = true;
                        }
                        Log.d("###验证码已发送","验证码已发送");
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                        Log.d("###返回支持发送验证码的国家列表","智能验证成功");
                    }
                }else if(result == SMSSDK.RESULT_ERROR){
                    //验证失败，验证码错误，获取验证码频繁
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        Log.d("###错误代码","  "+status);
                        Log.d("###错误描述","  "+des);
                        Snackbar.make(btnGetCode,des,Snackbar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendCode:     //发送验证码
                SMSSDK.getVerificationCode("86", edPhoneNum.getText().toString());
                btnGetCode.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        starttime(flag);
                    }
                },10000);
                break;
            case R.id.btn_register:     //注册
                SMSSDK.submitVerificationCode("86",edPhoneNum.getText().toString().trim(),edVerification.getText().toString().trim());
                break;
        }
    }
    private RegisterFragment registerFragment;


    private void versionsolve() {
        if (Build.VERSION.SDK_INT >= 23) {
            int readPhone = getActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int receiveSms = getActivity().checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            int readSms = getActivity().checkSelfPermission(Manifest.permission.READ_SMS);
            int readContacts = getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS);
            int readSdcard = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            int requestCode = 0;
            ArrayList<String> permissions = new ArrayList<String>();
            if (readPhone != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 0;
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (receiveSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 1;
                permissions.add(Manifest.permission.RECEIVE_SMS);
            }
            if (readSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 2;
                permissions.add(Manifest.permission.READ_SMS);
            }
            if (readContacts != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 3;
                permissions.add(Manifest.permission.READ_CONTACTS);
            }
            if (readSdcard != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 4;
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (requestCode > 0) {
                String[] permission = new String[permissions.size()];
                this.requestPermissions(permissions.toArray(permission), requestCode);
                return;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //注销回调监听
        SMSSDK.unregisterEventHandler(eh);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (GotoNewxtPage) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString());
        }
    }

    public void starttime(boolean flag){
        if (flag) {
            CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(btnGetCode, 10000, 1000);
            mCountDownTimerUtils.start();
            flag=false;
        }
    }
}
