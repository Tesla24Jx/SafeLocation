package com.safelocation.HomePage.Group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.trace.LBSTraceService;
import com.bumptech.glide.Glide;
import com.safelocation.Entity.Userdata;
import com.safelocation.R;
import com.safelocation.Register.Verification_Fragment;
import com.safelocation.Trace.TrackService;
import com.safelocation.Utils.ACache;
import com.safelocation.login.LoginActivity;


/**
 * Created by Juliet on 2017/2/25.
 */

public class InfoFragment extends Fragment implements View.OnClickListener {


    private TextView topbar_title;
    private ImageView topbar_right;
    private ImageView topbar_left;
    private ImageView fillup_head;
    private TextView fillup_username;
    private TextView fillup_age;
    private TextView fillup_phone;
    private TextView fillup_sex;

    private Button btn_submitInfo;
    private String sex = "男";
    private ACache mCache;
    private GotoAlertPage mCallback;

    public interface GotoAlertPage{
        void alertInfo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_info,container,false);
        topbar_title = (TextView) view.findViewById(R.id.topbar_title);
        topbar_left = (ImageView) view.findViewById(R.id.topbar_left);
        topbar_right = (ImageView) view.findViewById(R.id.topbar_right);
        fillup_head = (ImageView) view.findViewById(R.id.fillup_head);
        fillup_username = (TextView) view.findViewById(R.id.fillup_username);
        fillup_sex = (TextView) view.findViewById(R.id.fillup_sex);
        fillup_phone = (TextView) view.findViewById(R.id.fillup_phone);
        fillup_age = (TextView) view.findViewById(R.id.fillup_age);

        btn_submitInfo = (Button) view.findViewById(R.id.btn_submitInfo);

        topbar_left.setOnClickListener(this);
        topbar_right.setOnClickListener(this);
        btn_submitInfo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(this).load(Userdata.img).placeholder(R.drawable.default_userhead).into(fillup_head);

        topbar_title.setText("个人信息");
        topbar_left.setImageResource(R.drawable.ic_back);
        topbar_right.setImageResource(R.drawable.ic_bianji);

        fillup_username.setText(Userdata.uname);
        fillup_phone.setText(Userdata.uphone);
        fillup_age.setText(""+Userdata.age);
        fillup_sex.setText(Userdata.sex);

        btn_submitInfo.setText("注销账户");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_submitInfo:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                mCache = ACache.get(getActivity());
                mCache.remove("isLogin");
                getActivity().stopService(new Intent(getActivity(), TrackService.class));
                getActivity().stopService(new Intent(getActivity(), LBSTraceService.class));
                getActivity().finish();
                break;
            case R.id.topbar_left:
                getActivity().finish();
                break;

            case R.id.topbar_right:
                mCallback.alertInfo();
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (GotoAlertPage) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString());
        }
    }
}