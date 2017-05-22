package com.safelocation.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.safelocation.Entity.Userdata;
import com.safelocation.HomePage.HomeActivity;
import com.safelocation.FindPassword.FindPwdActivity;
import com.safelocation.Register.RegisterActivity;
import com.safelocation.Entity.HttpRequest;
import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.R;
import com.safelocation.Utils.ACache;
import com.safelocation.Utils.Common;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Juliet on 2017/1/28.
 */

public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener {
    private LoginContract.Presenter loginPresenter;
    private SubscriberOnNextListener getOnNext;
    private ACache mCache;
    private TextView topbar_title;
    private ImageView topbar_left;
    private ImageView topbar_right;
    private EditText ed_phone;
    private EditText ed_password;
    private TextView tv_gotoreg;
    private TextView tv_findpwd;
    private Button btn_login;
    private CircleImageView img_head;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("###onCreateView()","loginFragment");
        View view = inflater.inflate(R.layout.login,container,false);
        topbar_title = (TextView) view.findViewById(R.id.topbar_title);
        topbar_left = (ImageView) view.findViewById(R.id.topbar_left);
        topbar_right = (ImageView) view.findViewById(R.id.topbar_right);
        ed_phone = (EditText)view.findViewById(R.id.ed_phone);
        ed_password = (EditText)view.findViewById(R.id.ed_password);
        tv_gotoreg = (TextView)view.findViewById(R.id.tv_gotoreg);
        tv_findpwd = (TextView)view.findViewById(R.id.tv_findpwd);
        btn_login = (Button) view.findViewById(R.id.btn_login);
        img_head = (CircleImageView)view.findViewById(R.id.img_head);
        tv_gotoreg.setOnClickListener(this);
        tv_findpwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        topbar_title.setText("登录");
        topbar_left.setVisibility(View.GONE);
        topbar_right.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("###onActivityCreated()","onActivityCreated()");

        mCache = ACache.get(getActivity());
        String str = mCache.getAsString("initdata");
        if(str!=null){
            loadhead(str);
        }
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

                    Userdata.uid=json.getMydata().getUid();
                    Userdata.uname=json.getMydata().getUname();
                    Userdata.uphone=json.getMydata().getUphone();
                    Userdata.age=json.getMydata().getAge();
                    Userdata.sex=json.getMydata().getSex();
                    Userdata.img=json.getMydata().getImg();



                    Gson gson = new Gson();
                    String str = gson.toJson(json);
                    ImageView imageView = new ImageView(getActivity());
                    Glide.with(getActivity()).load(Userdata.img);
                    Log.d("###登录返回的json",str);
                    mCache.put("initdata",str);  //把好友列表信息存储到缓存
                    mCache.put("userphone",json.getMydata().getUphone());//保存用户手机以便服务获取

                    //保存登录信息
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("phone", json.getMydata().getUphone());
                        jsonObject.put("password", json.getMydata().getUpwd());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String logInfo = jsonObject.toString();
                    mCache.put("isLogin",logInfo); //保存登录信息

                    goto_homePage();

                }else{
                    Log.d("###msg:",json.getMsg());
                    ShowTips(json.getMsg());
                    setBtnstyle();
                }
            }
        };
    }

    @Override
    public void setPresent(LoginContract.Presenter presenter) {
        Log.d("###setPresent()","setPresent()");
        loginPresenter = presenter;
    }


    @Override
    public void goto_homePage() {
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }

    @Override
    public String getPhone() {
        return ed_phone.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return ed_password.getText().toString().trim();
    }

    @Override
    public void ShowTips(String tips) {
        Snackbar.make(topbar_left,tips,Snackbar.LENGTH_SHORT).show();
    }


    public void setBtnstyle(){
        btn_login.setClickable(true);
        btn_login.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg));
        btn_login.setTextColor(getResources().getColor(R.color.theme_color));
    }

    public void loadhead(String str){


        Gson gson =new Gson();
        HttpRequest httpRequest = gson.fromJson(str,HttpRequest.class);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(2500);
        img_head.startAnimation(scaleAnimation);

        Glide.with(getActivity())
                .load( httpRequest.getMydata().getImg())
                .animate(scaleAnimation)
                .placeholder(R.drawable.default_userhead)
                .into(img_head);
          ed_phone.setText(httpRequest.getMydata().getUphone());
        ed_password.setText(httpRequest.getMydata().getUpwd());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_gotoreg:
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.tv_findpwd:
                startActivity(new Intent(getActivity(), FindPwdActivity.class));
                break;
            case R.id.btn_login:
                btn_login.setClickable(false);
                btn_login.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg_click));
                btn_login.setTextColor(getResources().getColor(R.color.theme_color_88));
                if(Common.checkNoNUll(getPhone()) && Common.checkNoNUll(getPwd())){
                    loginPresenter.login(getOnNext);
                }else {
                    setBtnstyle();
                    ShowTips("手机号和密码不能为空");
                }
                break;
        }
    }
}
