package com.safelocation.HomePage.FriendList;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.safelocation.Entity.StrJson;
import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.R;

public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ImageView topbar_left;
    private ImageView topbar_right;
    private TextView topbar_title;
    private EditText ed_searchFriend;
    private Button btn_searchFriend;
    private ImageView fhead;
    private TextView fname;
    private Button btn_add;
    private EditText ed_mime;
    private Switch aSwitch_get;
    private Switch aSwitch_give;
    private LinearLayout l1;
    private LinearLayout l2;
    private int getpermission=0;
    private int forpermission=0;
    private String fid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        initView();
    }

    private void initView() {
        topbar_left = (ImageView)findViewById(R.id.topbar_left);
        topbar_right = (ImageView)findViewById(R.id.topbar_right);
        topbar_title = (TextView) findViewById(R.id.topbar_title);
        ed_searchFriend = (EditText)findViewById(R.id.ed_searchFriend);
        btn_searchFriend = (Button)findViewById(R.id.btn_searchFriend);
        fhead = (ImageView)findViewById(R.id.iv_f_img);
        fname = (TextView)findViewById(R.id.tv_f_name);
        l1 = (LinearLayout)findViewById(R.id.l1);
        l2 = (LinearLayout)findViewById(R.id.l2);
        ed_mime = (EditText)findViewById(R.id.ed_mime);
        aSwitch_get = (Switch)findViewById(R.id.switch_get);
        aSwitch_give = (Switch)findViewById(R.id.switch_give);
        btn_add = (Button)findViewById(R.id.btn_add);
        topbar_title.setText("添加好友");
        topbar_left.setImageResource(R.drawable.ic_back);
        btn_searchFriend.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        topbar_left.setOnClickListener(this);

        aSwitch_give.setOnCheckedChangeListener(this);
        aSwitch_get.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_searchFriend:
                new FriendListModel().searchFriend(new SubscriberOnNextListener<StrJson>() {
                    @Override
                    public void onNext(StrJson f) {
                        String str = new Gson().toJson(f);
                        Log.d("###json",str);
                        fid=f.getId();
                        showfriend(f.getCode(),f.getData(),f.getName(),f.getImg());
                    }
                },ed_searchFriend.getText().toString().trim());
                break;
            case R.id.topbar_left:
                    finish();
                break;
            case R.id.btn_add:
                new FriendListModel().addfriend(new SubscriberOnNextListener<StrJson>() {
                    @Override
                    public void onNext(StrJson o) {
                        if(o.getCode().equals("000111")){
                            Snackbar.make(btn_add, o.getMsg(), Snackbar.LENGTH_SHORT).show();
                        }else {
                            Snackbar.make(btn_add, o.getMsg(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                },fid,forpermission,getpermission,ed_mime.getText().toString().trim());
                break;
        }
    }

    void showfriend(String code,String msg,String name,String img){
        Log.d("###showfriend","");
        if(code.equals("000000")) {
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.VISIBLE);
            fname.setText(name);
            Glide.with(this)
                    .load(img)
                    .into(fhead);
        }else{
            Snackbar.make(btn_add,msg,Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch_give:
                if (isChecked){
                    getpermission=1;
                    forpermission=1;
                } else{
                    getpermission=0;
                    forpermission=0;
                }
                break;
        }
    }
}
