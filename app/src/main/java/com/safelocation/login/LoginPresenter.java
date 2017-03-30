package com.safelocation.login;


import android.util.Log;

import com.safelocation.Entity.HttpRequest;
import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.Utils.Common;

/**
 * Created by Juliet on 2017/1/28.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginModel loginModel;
    private LoginContract.View loginView;

    public LoginPresenter(LoginModel loginModel, LoginContract.View loginView){
        this.loginModel = loginModel;
        this.loginView = loginView;
        loginView.setPresent(this);
    }


    @Override
    public void login(SubscriberOnNextListener getOnNext) {
            loginModel.login(getOnNext, loginView.getPhone(), loginView.getPwd());
    }

    @Override
    public void saveDatatoDB(HttpRequest httpRequest) {
        loginModel.saveDatatoDB(httpRequest);
    }
}
