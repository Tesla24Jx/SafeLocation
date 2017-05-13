package com.safelocation.HomePage.FriendList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.safelocation.Map.MapActivity;
import com.safelocation.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransferActivity extends Activity implements View.OnClickListener {
    TextView topbar_title;
    ImageView topbar_left;
    ImageView iv_head;
    TextView tv_username;
    TextView tv_phone;
    Switch aSwitch_get;
    Switch aSwitch_give;
    Button btn_del;
    Button btn_loc;

    String fhead="";
    String fphone="";
    String fname="";
    int fgetPermission;
    int fforPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivity_transfer);

        Bundle bundle = getIntent().getBundleExtra("fdata");
        fhead = bundle.getString("fhead");
        fphone = bundle.getString("fphone");
        fname = bundle.getString("fname");
        fgetPermission = bundle.getInt("fgetPermission");
        fforPermission = bundle.getInt("fforPermission");
        initView();
    }
    void initView(){
        topbar_title = (TextView) findViewById(R.id.topbar_title);
        topbar_left = (ImageView) findViewById(R.id.topbar_left);
        iv_head = (CircleImageView) findViewById(R.id.img_head);
        tv_phone = (TextView) findViewById(R.id.phone);
        tv_username = (TextView) findViewById(R.id.username);
        aSwitch_get = (Switch) findViewById(R.id.switch_get);
        aSwitch_give = (Switch) findViewById(R.id.switch_give);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_loc = (Button) findViewById(R.id.btn_gotoloc);

        Glide.with(this).load(fhead).placeholder(R.drawable.default_userhead).into(iv_head);
        topbar_title.setText(fname);
        topbar_left.setImageResource(R.drawable.ic_back);
        tv_username.setText(fname);
        tv_phone.setText(fphone);
        if(fgetPermission==1){//获得对方的权限
            aSwitch_get.setChecked(true);
        }else {
            aSwitch_get.setChecked(false);
        }
        if(fforPermission==1){//赋予对方权限
            aSwitch_give.setChecked(true);
        }else{
            aSwitch_give.setChecked(false);
        }
//        aSwitch_get.setClickable(false);
        topbar_left.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_loc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.topbar_left:
                finish();
                break;
            case R.id.btn_del:

                break;
            case R.id.btn_gotoloc:
                if(fgetPermission==1){
                    Bundle bundle = new Bundle();
                    bundle.putString("fhead",fhead);
                    bundle.putString("fphone",fphone);
                    bundle.putString("fname",fname);
                    startActivity(new Intent(this,MapActivity.class).putExtra("fdata",bundle));
                }else{
                    Snackbar.make(btn_del," 未获得好友权限！",Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
