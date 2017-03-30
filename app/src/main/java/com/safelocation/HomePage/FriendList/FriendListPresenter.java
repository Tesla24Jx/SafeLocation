package com.safelocation.HomePage.FriendList;


import com.safelocation.Entity.HttpRequest;
import com.safelocation.HttpUtil.SubscriberOnNextListener;

/**
 * Created by Juliet on 2017/1/28.
 */

public class FriendListPresenter implements FriendListContract.Presenter {
    private FriendListModel loginModel;
    private FriendListContract.View loginView;

    public FriendListPresenter(FriendListModel loginModel, FriendListContract.View loginView){
        this.loginModel = loginModel;
        this.loginView = loginView;
        loginView.setPresent(this);
    }


    @Override
    public void login(SubscriberOnNextListener getOnNext) {
          //  loginModel.login(getOnNext, loginView.getPhone(), loginView.getPwd());
    }

    @Override
    public void saveDatatoDB(HttpRequest httpRequest) {
        loginModel.saveDatatoDB(httpRequest);
    }
}
