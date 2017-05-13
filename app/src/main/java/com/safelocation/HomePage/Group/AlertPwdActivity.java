package com.safelocation.HomePage.Group;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.safelocation.Entity.StrJson;
import com.safelocation.Entity.Userdata;
import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.R;
import com.safelocation.Utils.ToastUtils;
/*
**修改密码
 */
public class AlertPwdActivity extends AppCompatActivity {

    private SubscriberOnNextListener getOnNext;
    private TextView topbar_title;
    private ImageView topbar_left;
    private EditText ed_oldpwd;
    private EditText ed_newpwd;
    private EditText ed_cfnpwd;
    private Button btn_submit;
    private boolean tag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_pwd);

        Bundle bundle = getIntent().getExtras();//从activity传过来的Bundle
        if(bundle!=null){
            tag = true;
        }

        getOnNext = new SubscriberOnNextListener<StrJson>() {
            @Override
            public void onNext(StrJson json) {
                Log.d("###json",json.getMsg());
                ToastUtils.snackbar_short(btn_submit,json.getMsg());
                if (json.getMsg().equals("旧密码不正确")){
                    return;
                }
                btn_submit.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },2000);

            }
        };
        initView();
    }

    void initView(){
        topbar_title = (TextView)findViewById(R.id.topbar_title);
        topbar_left = (ImageView) findViewById(R.id.topbar_left);
        topbar_title.setText("修改密码");
        topbar_left.setImageResource(R.drawable.ic_back);
        ed_oldpwd = (EditText)findViewById(R.id.old_pwd);
        ed_newpwd = (EditText)findViewById(R.id.fillup_pwd);
        ed_cfnpwd = (EditText)findViewById(R.id.fillup_cfnpwd);
        btn_submit = (Button)findViewById(R.id.btn_submitInfo);
        if(tag){
            ed_oldpwd.setVisibility(View.GONE);
            topbar_title.setText("找回密码");
        }
        topbar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpwd = "";
                String type = "0";
                if(!tag){
                    oldpwd = ed_oldpwd.getText().toString().trim();
                    type = "1";
                }
                String newpwd = ed_newpwd.getText().toString().trim();
                String cfnpwd = ed_cfnpwd.getText().toString().trim();
                if(newpwd.equals(cfnpwd)){
                    GroupModel.alertPwd(getOnNext, Userdata.uphone,oldpwd,newpwd,type);
                }else{
                    ToastUtils.snackbar_short(btn_submit,"两次密码不一致");
                }
            }
        });
    }

}
