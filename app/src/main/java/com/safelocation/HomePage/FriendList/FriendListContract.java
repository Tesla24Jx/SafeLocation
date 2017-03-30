package com.safelocation.HomePage.FriendList;


import com.safelocation.Entity.HttpRequest;
import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.base.BasePresenter;
import com.safelocation.base.BaseView;

/**
 * Created by Juliet on 2017/1/28.
 */

public class FriendListContract {
    interface View extends BaseView<Presenter> {
        void goto_homePage();
        String getPhone();
        String getPwd();
        void ShowTips(String tips);
    }
    interface Presenter extends BasePresenter {
        void login(SubscriberOnNextListener getOnNext);
        void saveDatatoDB(HttpRequest httpRequest);
    }

}
