package com.safelocation.Register;


import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.base.BasePresenter;
import com.safelocation.base.BaseView;

/**
 * Created by Juliet on 2017/1/28.
 */

public class RegisterContract {
    interface View extends BaseView<Presenter> {
        void goto_homePage();
        String getUserName();
        String getPassword();
        String getCfnpwd();
        String getPhone();
        String getAge();
        String getSex();
        String getHeadUrl();
        void showTip(String tip);
    }
    interface Presenter extends BasePresenter {
       void submitInfo(SubscriberOnNextListener getOnNext);
       void uploadHead(SubscriberOnNextListener getOnNext,String img);
    }

}
