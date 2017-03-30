package com.safelocation.Register;


import android.util.Log;
import android.widget.Toast;

import com.safelocation.Entity.UserInfo;
import com.safelocation.HttpUtil.SubscriberOnNextListener;


/**
 * Created by Juliet on 2017/1/28.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterModel registerModel;
    private RegisterContract.View registerView;

    public RegisterPresenter(RegisterModel registerModel, RegisterContract.View loginView){
        this.registerModel = registerModel;
        this.registerView = loginView;
        loginView.setPresent(this);
    }


    @Override
    public void submitInfo(SubscriberOnNextListener getOnNext) {
        //校验输入
        if(checkNoNUll(registerView.getUserName())) {
            Log.d("###用户名","OK");
        }else{
            registerView.showTip("用户名不能为空");    //提示用户名为空
            return;
        }


        //检验密码
        if(checkNoNUll(registerView.getPassword())) {
            Log.d("###密码","OK");
        }else{
            registerView.showTip("密码不能为空");
            return;
        }

        if (registerView.getPassword().equals(registerView.getCfnpwd())) {
            //提交信息
            Log.d("###密码","OK");
        } else {
            registerView.showTip("两次密码不一致");
            return;
        }


        if (checkNoNUll(registerView.getAge()) && checkNoNUll(registerView.getSex())){
            Log.d("###age","OK");
        }else{
            Log.d("###age","no");
                return;//提示年龄有误
        }

        Log.d("###封装userINfo","userInfo");
        UserInfo userInfo = new UserInfo();
        userInfo.setUname(registerView.getUserName());
        userInfo.setUpwd(registerView.getPassword());
        userInfo.setUphone(registerView.getPhone());
        userInfo.setAge(Integer.valueOf(registerView.getAge()));
        userInfo.setSex(registerView.getSex());
        userInfo.setImg(registerView.getHeadUrl());

        registerModel.sublinInfo(getOnNext,userInfo);
    }

    @Override
    public void uploadHead(SubscriberOnNextListener getOnNext, String img) {
        registerModel.uploadhead(getOnNext,img);
    }


    public boolean checkNoNUll(String str){
        if(str!=null && !str.equals("")){
            return true;
        }
        return false;
    }
}
