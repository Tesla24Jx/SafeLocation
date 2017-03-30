package com.safelocation.HomePage.Group;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.trace.LBSTraceService;
import com.bumptech.glide.Glide;
import com.safelocation.Entity.Userdata;
import com.safelocation.R;
import com.safelocation.Trace.TrackReceiver;
import com.safelocation.Trace.TrackService;
import com.safelocation.login.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView topbar_title;
    private ImageView fillup_head;
    private EditText fillup_username;
    private EditText fillup_password;
    private EditText fillup_cfnpwd;
    private EditText fillup_age;
    private EditText fillup_phone;
    private RadioGroup fillup_radiogroup;
    private RadioButton radio_male;
    private RadioButton radio_female;
    private Button btn_submitInfo;
    private String sex = "男";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_fillupinfo);
        
        initView();
    }

    private void initView() {
        topbar_title = (TextView) findViewById(R.id.topbar_title);
        fillup_head = (ImageView) findViewById(R.id.fillup_head);
        fillup_username = (EditText) findViewById(R.id.fillup_username);
        fillup_password = (EditText) findViewById(R.id.fillup_pwd);
        fillup_cfnpwd = (EditText) findViewById(R.id.fillup_cfnpwd);
        fillup_phone = (EditText) findViewById(R.id.fillup_phone);
        fillup_age = (EditText) findViewById(R.id.fillup_age);
        fillup_radiogroup = (RadioGroup) findViewById(R.id.fillup_radioGroup);
        radio_male = (RadioButton)findViewById(R.id.radioMale);
        radio_female = (RadioButton)findViewById(R.id.radioFemale);
        btn_submitInfo = (Button) findViewById(R.id.btn_submitInfo);


        fillup_head.setOnClickListener(this);
        btn_submitInfo.setOnClickListener(this);

        Glide.with(this).load(Userdata.img).placeholder(R.drawable.default_userhead).into(fillup_head);
        topbar_title.setText("个人信息");
        fillup_username.setText(Userdata.uname);
        fillup_password.setVisibility(View.GONE);
        fillup_cfnpwd.setVisibility(View.GONE);
        fillup_phone.setText(Userdata.uphone);
        fillup_age.setText(Userdata.sex);
        if(Userdata.sex.equals("男")){
            radio_male.setChecked(true);
            radio_female.setChecked(false);
        }else{
            radio_female.setChecked(true);
            radio_male.setChecked(false);
        }

        btn_submitInfo.setText("注销账户");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submitInfo:
                startActivity(new Intent(this, LoginActivity.class));
                stopService(new Intent(this, TrackService.class));
                stopService(new Intent(this, LBSTraceService.class));
                break;
        }
    }
}
