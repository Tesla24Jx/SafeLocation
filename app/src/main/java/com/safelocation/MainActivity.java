package com.safelocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.safelocation.Entity.HttpRequest;
import com.safelocation.Entity.Userdata;
import com.safelocation.HomePage.HomeActivity;
import com.safelocation.HttpUtil.HttpUtil;
import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.Utils.ACache;
import com.safelocation.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private ACache aCache;
    private SubscriberOnNextListener getOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aCache=ACache.get(this);
        String login = aCache.getAsString("isLogin");


        if(getOnNext!=null){
            Log.d("###getOnNext !=","null");
        }
        if(login == null || login.equals("")){
            //没有登陆态缓存，跳到登录页
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }else{
            init();
            String type = "login";
            HttpUtil.getInstance().getJSON(getOnNext,login,type);
        }
    }

    void gotoHomePage(){
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
    void init(){
        //获取数据
        getOnNext = new SubscriberOnNextListener<HttpRequest>() {
            @Override
            public void onNext(HttpRequest json) {

                Log.d("###服务器返回的json","");
                Log.d("###code:",json.getCode());
                if(json.getCode().equals("000000")) {
                    Log.d("###msg:",json.getMsg());
                    Log.d("###uid:",json.getMydata().getUid());
                    Log.d("###Mydata_username:",json.getMydata().getUname());
                    Log.d("###flist_size:",""+json.getFriendlist().size());
                    Log.d("###flist_1:",""+json.getFriendlist().get(0).getFname());
                    Log.d("###flist_1-fid:",""+json.getFriendlist().get(0).getFid());
                    //loginPresenter.saveDatatoDB(json);
                    Userdata.uid=json.getMydata().getUid();
                    Userdata.uname=json.getMydata().getUname();
                    Userdata.uphone=json.getMydata().getUphone();
                    Userdata.age=json.getMydata().getAge();
                    Userdata.sex=json.getMydata().getSex();
                    Userdata.img=json.getMydata().getImg();

                    Gson gson = new Gson();
                    // mCache.put("mydata",json.getMydata().toString());  //把个人信息存储到缓存
                    String str = gson.toJson(json);
                    aCache.put("initdata",str);  //把好友列表信息存储到缓存

                    //保存登录信息
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("phone", json.getMydata().getUphone());
                        jsonObject.put("password", json.getMydata().getUpwd());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String logInfo = jsonObject.toString();
                    aCache.put("isLogin",logInfo); //保存登录信息
                    gotoHomePage();
                }else{
                    Log.d("###msg:",json.getMsg());
                }
            }
        };
    }

}
