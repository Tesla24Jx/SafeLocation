package com.safelocation.HomePage.Group;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.safelocation.Activity;
import com.safelocation.Entity.Userdata;
import com.safelocation.R;


/**
 * Created by Juliet on 2017/2/25.
 */

public class GroupFragment extends Fragment implements View.OnClickListener {
    public ImageView topbar_left;
    private ImageView topbar_right;
    private TextView topbar_title;
    private ImageView Uhead;
    private TextView Uname;
    private TextView Uphone;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_3_group,container,false);
        topbar_left = (ImageView) view.findViewById(R.id.topbar_left);
        topbar_title = (TextView) view.findViewById(R.id.topbar_title);
        topbar_right = (ImageView) view.findViewById(R.id.topbar_right);
        Uhead = (ImageView) view.findViewById(R.id.iv_person_head);
        Uname = (TextView) view.findViewById(R.id.tv_person_name);
        Uphone = (TextView) view.findViewById(R.id.tv_person_phone);


        view.findViewById(R.id.personInfo_layout).setOnClickListener(this);
        view.findViewById(R.id.relay).setOnClickListener(this);
        view.findViewById(R.id.sego).setOnClickListener(this);
        topbar_title.setText("功能页");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(this).load(Userdata.img).into(Uhead);
        Uname.setText(Userdata.uname);
        Uphone.setText(Userdata.uphone);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.personInfo_layout:
                startActivity(new Intent(getActivity(), InfoActivity.class));
                break;
            case R.id.relay:
            case R.id.sego:
                startActivity(new Intent(getActivity(), Activity.class));
                break;
        }
    }
}