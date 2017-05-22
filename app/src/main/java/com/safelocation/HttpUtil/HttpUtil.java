package com.safelocation.HttpUtil;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.safelocation.Entity.HttpRequest;
import com.safelocation.base.BaseURL;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Juliet on 2017/1/28.
 */

public class HttpUtil {
    private Retrofit retrofit;
    private HttpServer httpServer;

    //构造方法私有
    private HttpUtil() {


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BaseURL.Base_URL)
                .client(okHttpClient)
                .build();

        httpServer = retrofit.create(HttpServer.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    //获取单例
    public static HttpUtil getInstance(){
        return SingletonHolder.INSTANCE;
    }


    /**
     * 用于获取的数据
     * @param
     */
    public void  getJSON(SubscriberOnNextListener getOnNext, String strJson,String type){

        switch(type){
            case "login":
                httpServer.getLogin_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                Log.d("###httpsend_login","httpsend");
                break;
            case "register":
                httpServer.getRegData_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                Log.d("###httpsend_register","httpsend");
                break;
            case "uploadHead":
                httpServer.getheadurl_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                Log.d("###httpsend_register","httpsend");
                break;
            case "checkAccount":
                httpServer.getcheckAccountResult_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                Log.d("###httpsend_register","checkAccount");
                break;
             case "alertpwd":
                httpServer.getAlertPwdResult_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                Log.d("###httpsend_register","alertpwd");
                break;
            case "searchfriend":
                httpServer.getfriend_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                Log.d("###httpsend_register","httpsend");
                break;
            case "submitCID":
                Log.d("###json",strJson);
                httpServer.submitCID_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                break;
            case "addfriend":
                Log.d("###json",strJson);
                httpServer.addfriend_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                break;
            case "add_agree":
                Log.d("###json",strJson);
                httpServer.add_f_agree_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                break;
            case "alertInfo":
                Log.d("###json",strJson);
                httpServer.alertInfo_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                break;
            case "delfriend":
                Log.d("###json",strJson);
                httpServer.delfriend_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                break;
            case "alertPermission":
                Log.d("###json",strJson);
                httpServer.alertPermission_post(strJson)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber(getOnNext));
                break;
        }

    }
    /**
     * 用于获取的数据
     * @param subscriber 由调用者传过来的观察者对象
     */
//    public void getJSON(Subscriber<HttpRequest> subscriber, String strJson){
//
//        httpServer.getNewsData_post2(strJson)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//        Log.d("###httpsend","httpsend");
//    }

}
