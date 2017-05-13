package com.safelocation.HomePage.Group;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.trace.LBSTraceService;
import com.bumptech.glide.Glide;
import com.safelocation.Entity.Userdata;
import com.safelocation.HomePage.HomeFragment;
import com.safelocation.R;

import com.safelocation.Register.Verification_Fragment;
import com.safelocation.Trace.TrackService;
import com.safelocation.Utils.ACache;
import com.safelocation.login.LoginActivity;


public class InfoActivity extends AppCompatActivity implements InfoFragment.GotoAlertPage{

    private AlertFragment alertFragment;
    private InfoFragment infoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_layout);

        infoFragment = new InfoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, infoFragment).commit();

    }


    @Override
    public void alertInfo() {
        alertFragment = new AlertFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, alertFragment).commit();
    }
}
