package com.safelocation.HomePage.Location;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.OnStartTraceListener;
import com.safelocation.Entity.Userdata;
import com.safelocation.R;
import com.safelocation.Utils.MyOrientationListener;
import com.safelocation.Utils.ToastUtils;

/**
 * Created by Juliet on 2017/2/25.
 */

public class LocationFragment extends Fragment implements View.OnClickListener {
    OnStartTraceListener startTraceListener;
    private Intent serviceIntent = null;
    private TextView topbar_title;
    private String address;


    //定位
    boolean isFirstLoc = true;
    LocationClient mLocClient;
    public MyLocationListenner myListener;

    //自定义定位图标
    private BitmapDescriptor mLocationIcon;
    private MyOrientationListener myOrientationListener;
    private float currentX;
    private MyLocationConfiguration.LocationMode mLocationMode;


    boolean tag = true;
    BaiduMap baiduMap;
    BDLocation loc;


    private MapView mMapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_1_location, container, false);

        //startBDLbs();

        topbar_title = (TextView) view.findViewById(R.id.topbar_title);
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15.0f));//设置缩放级别
        initLocation();     //初始化我的位置

        view.findViewById(R.id.btn_traffic).setOnClickListener(this);
        view.findViewById(R.id.type_normal).setOnClickListener(this);
        view.findViewById(R.id.type_SATELLITE).setOnClickListener(this);
        view.findViewById(R.id.btn_compass).setOnClickListener(this);
        view.findViewById(R.id.btn_location).setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        topbar_title.setText("首页");
        baiduMap.setTrafficEnabled(true);
    }


    private void initLocation() {

        loc=new BDLocation();
        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;

        mLocClient = new LocationClient(getActivity().getApplicationContext());
        myListener = new MyLocationListenner();
        mLocClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);   //每隔1000毫秒执行一次请求
        mLocClient.setLocOption(option);

        //初始化自定义的定位图标
        mLocationIcon = BitmapDescriptorFactory.fromResource(R.mipmap.arrow);

        myOrientationListener = new MyOrientationListener(getActivity());

        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void OnOrientationChanged(float x) {
                currentX=x;
            }
        });


    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.type_normal:
                //普通地图
                mMapView.getMap().setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.type_SATELLITE:
                //卫星地图
                mMapView.getMap().setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.btn_traffic:
                //实时交通图
                if (baiduMap.isTrafficEnabled()) {//baiduMap.isTrafficEnabled()默认是false
                    baiduMap.setTrafficEnabled(false);
                    //typeTraffic.setBackgroundResource(R.drawable.button_theme);
                } else {
                    baiduMap.setTrafficEnabled(true);
                    // typeTraffic.setBackgroundResource(R.drawable.button_theme2);
                }
                break;

            case R.id.btn_location:
                //定位
                centerToMyLocation();
                break;
            case R.id.btn_compass:
                //罗盘模式
                if (tag) {//tag默认是false
                    mLocationMode = MyLocationConfiguration.LocationMode.COMPASS;
                    //btnCompass.setBackgroundResource(R.drawable.button_theme2);
                    tag=false;
                } else {
                    mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
                    //btnCompass.setBackgroundResource(R.drawable.button_theme);
                    tag=true;
                }

        }
    }

    /**
     * 定位SDK监听函数
     */
    private class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            MyLocationData locData = new MyLocationData.Builder()//
                    .direction(currentX)//设置方向
                    .accuracy(location.getRadius())//此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude())//
                    .build();

            baiduMap.setMyLocationData(locData);
            //设置自定义图标
            MyLocationConfiguration config = new
                    MyLocationConfiguration(mLocationMode,true,mLocationIcon);
            baiduMap.setMyLocationConfigeration(config);
            address = location.getAddrStr();
            loc=location;
            Userdata.loc=location;
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(ll);
                baiduMap.animateMapStatus(msu);
                Snackbar.make(mMapView,location.getAddrStr(),Snackbar.LENGTH_LONG).show();

            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /*
    **定位到我的位置
     */
    public void centerToMyLocation(){
        LatLng ll = new LatLng(loc.getLatitude(), loc.getLongitude());
        MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.animateMapStatus(msu);
        //baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));//设置缩放级别
        // Snackbar.make(btnLocation,loc.getAddrStr(),Snackbar.LENGTH_SHORT).show();
    }



    @Override
    public void onStart() {
        super.onStart();
        //开启定位
        baiduMap.setMyLocationEnabled(true);
        if (!mLocClient.isStarted()){
            mLocClient.start();
        }
        //开启方向传感器
        myOrientationListener.start();

    }

    @Override
    public void onStop() {
        super.onStop();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        //销毁定位
        mLocClient.stop();
        //关闭方向传感器
        myOrientationListener.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


}
