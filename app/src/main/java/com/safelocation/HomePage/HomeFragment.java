package com.safelocation.HomePage;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.safelocation.HomePage.FriendList.FriendListFragment;
import com.safelocation.HomePage.Group.GroupFragment;
import com.safelocation.HomePage.Location.LocationFragment;
import com.safelocation.R;

/**
 * Created by Juliet on 2017/2/25.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final int TAB_1 = 1;
    private static final int TAB_2 = 2;
    private static final int TAB_3 = 3;
    private int tabIndex;

    private LocationFragment locationFragment;
    private FriendListFragment friendListFragment;
    private GroupFragment groupFragment;

    private LinearLayout ly_location;
    private LinearLayout ly_flist;
    private LinearLayout ly_group;

    private ImageButton imgBtn_location;
    private ImageButton imgBtn_flist;
    private ImageButton imgBtn_group;

    private TextView tv_location;
    private TextView tv_flist;
    private TextView tv_group;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra("bundle");
            Toast.makeText(context,"收到广播:"+bundle.getString("data"), Toast.LENGTH_SHORT).show();
            friendListFragment.topbar_left.setImageResource(R.drawable.ic_msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home,container,false);

        ly_location = (LinearLayout) view.findViewById(R.id.ly_tab_location);
        ly_flist = (LinearLayout) view.findViewById(R.id.ly_tab_flist);
        ly_group = (LinearLayout) view.findViewById(R.id.ly_tab_group);

        imgBtn_location = (ImageButton) view.findViewById(R.id.imgbtn_location);
        imgBtn_flist = (ImageButton) view.findViewById(R.id.imgbtn_flist);
        imgBtn_group = (ImageButton) view.findViewById(R.id.imgbtn_group);

        tv_location = (TextView) view.findViewById(R.id.tv_location_bottom);
        tv_flist = (TextView) view.findViewById(R.id.tv_flist_bottom);
        tv_group = (TextView) view.findViewById(R.id.tv_group_bottom);

        ly_location.setOnClickListener(this);
        ly_flist.setOnClickListener(this);
        ly_group.setOnClickListener(this);

        imgBtn_location.setOnClickListener(this);
        imgBtn_flist.setOnClickListener(this);
        imgBtn_group.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        IntentFilter filter = new IntentFilter("msg");
        getActivity().registerReceiver(broadcastReceiver, filter);


        initFragment();
        tabIndex=1;
        changeTab(tabIndex);
        changeBottomSelect(tabIndex);
    }

    private void initFragment() {
        locationFragment = new LocationFragment();
        friendListFragment = new FriendListFragment();
        groupFragment = new GroupFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.home_content,locationFragment);
        transaction.hide(locationFragment);
        transaction.add(R.id.home_content,friendListFragment);
        transaction.hide(friendListFragment);
        transaction.add(R.id.home_content,groupFragment);
        transaction.hide(groupFragment);
        transaction.commit();
    }

    private void toChangeTab(int index){

        if (tabIndex == index){
            return;
        }
        changeTab(index);
        changeBottomSelect(index);
        tabIndex = index;
    }

    private void changeTab(int index){
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (index){
            case TAB_1:
                imgBtn_location.setImageResource(R.drawable.ic_location_selected);
                ft.show(locationFragment);
                ft.hide(friendListFragment);
                ft.hide(groupFragment);
                //ft.replace(R.id.home_content,locationFragment);
                break;
            case TAB_2:
                //ft.replace(R.id.home_content,friendListFragment);
                ft.hide(locationFragment);
                ft.show(friendListFragment);
                ft.hide(groupFragment);
                break;
            case TAB_3:
                //ft.replace(R.id.home_content,groupFragment);
                ft.hide(locationFragment);
                ft.hide(friendListFragment);
                ft.show(groupFragment);
                break;
        }
        ft.commit();
    }

    private void changeBottomSelect(int index) {

        switch (index){
            case TAB_1:
                imgBtn_location.setImageResource(R.drawable.ic_location_selected);
                imgBtn_flist.setImageResource(R.drawable.ic_flist_normal);
                imgBtn_group.setImageResource(R.drawable.ic_group_normal);

                tv_location.setTextColor(getResources().getColor(R.color.theme_color));
                tv_flist.setTextColor(getResources().getColor(R.color.bottom_normal));
                tv_group.setTextColor(getResources().getColor(R.color.bottom_normal));

                break;
            case TAB_2:
                imgBtn_location.setImageResource(R.drawable.ic_location_normal);
                imgBtn_flist.setImageResource(R.drawable.ic_flist_selected);
                imgBtn_group.setImageResource(R.drawable.ic_group_normal);

                tv_location.setTextColor(getResources().getColor(R.color.bottom_normal));
                tv_flist.setTextColor(getResources().getColor(R.color.theme_color));
                tv_group.setTextColor(getResources().getColor(R.color.bottom_normal));

                break;
            case TAB_3:
                imgBtn_location.setImageResource(R.drawable.ic_location_normal);
                imgBtn_flist.setImageResource(R.drawable.ic_flist_normal);
                imgBtn_group.setImageResource(R.drawable.ic_group_selected);

                tv_location.setTextColor(getResources().getColor(R.color.bottom_normal));
                tv_flist.setTextColor(getResources().getColor(R.color.bottom_normal));
                tv_group.setTextColor(getResources().getColor(R.color.theme_color));

                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_tab_location:
            case R.id.imgbtn_location:
                toChangeTab(TAB_1);
                break;
            case R.id.ly_tab_flist:
            case R.id.imgbtn_flist:
                toChangeTab(TAB_2);

                break;
            case R.id.ly_tab_group:
            case R.id.imgbtn_group:
                toChangeTab(TAB_3);
                break;

        }
    }
    //销毁时解除广播接收
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
