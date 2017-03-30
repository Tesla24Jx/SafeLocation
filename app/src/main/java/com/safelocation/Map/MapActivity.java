package com.safelocation.Map;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.OnEntityListener;
import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.Trace;
import com.baidu.trace.TraceLocation;
import com.google.gson.Gson;
import com.safelocation.Entity.StrJson;
import com.safelocation.Entity.Userdata;
import com.safelocation.Entity.locEntities;
import com.safelocation.HomePage.Location.LocationFragment;
import com.safelocation.R;
import com.safelocation.Utils.MyOrientationListener;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int UPLOAD_LOCATION = 2;
    LBSTraceClient client;
    private EditText ed_serviceID;
    private TextView tv_result;


    //定位
    boolean isFirstLoc = true;
    LocationClient mLocClient;
//    public MyLocationListenner myListener;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    //自定义定位图标
    private BitmapDescriptor mLocationIcon;
    private MyOrientationListener myOrientationListener;
    private float currentX;
    private MyLocationConfiguration.LocationMode mLocationMode;
    private String fphone;

    boolean tag = true;
    BaiduMap baiduMap;
    BDLocation loc;


    private MapView mMapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



        Bundle bundle = getIntent().getBundleExtra("fdata");
        fphone = bundle.getString("fphone");
        String fname = bundle.getString("fname");
        Log.d("###fphone:",fphone);

        mMapView = (MapView)findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        baiduMap.setMapStatus( MapStatusUpdateFactory.zoomTo(15.0f));//设置缩放级别
        findViewById(R.id.btn_traffic).setOnClickListener(this);
        findViewById(R.id.type_normal).setOnClickListener(this);
        findViewById(R.id.type_SATELLITE).setOnClickListener(this);
        findViewById(R.id.btn_compass).setOnClickListener(this);
        findViewById(R.id.btn_location).setOnClickListener(this);
        // 初始化轨迹服务客户端
        client = new LBSTraceClient(this.getApplicationContext());
        //client.queryRealtimeLoc(fphone,onEntityListener);
        client.queryEntityList(131714, fphone, null, 0, 60, 10, 1, onEntityListener);

    }



    //实体监听
    OnEntityListener onEntityListener = new OnEntityListener() {
        @Override
        public void onRequestFailedCallback(String s) {
            tv_result.setText("失败");
        }

        @Override
        public void onQueryEntityListCallback(String s) {
            Log.d("###json",s);

            locEntities locEntities = new Gson().fromJson(s,locEntities.class);
            double longitude = locEntities.getEntities().get(0).getRealtime_point().getLocation().get(0);
            double latitude = locEntities.getEntities().get(0).getRealtime_point().getLocation().get(1);
            String lasttime = locEntities.getEntities().get(0).getModify_time();

            LatLng point = new LatLng(latitude, longitude);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            baiduMap.addOverlay(option);
            //移动到point位置
            MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(point);
            baiduMap.animateMapStatus(msu);
            Toast.makeText(MapActivity.this,"更新时间:"+lasttime,Toast.LENGTH_LONG).show();
            //Snackbar.make(mMapView,"位置更新时间："+lasttime,Snackbar.LENGTH_SHORT);
        }

//        @Override
//        public void onReceiveLocation(TraceLocation traceLocation) {
//            super.onReceiveLocation(traceLocation);
//            double latitude = traceLocation.getLatitude();//纬度
//            double longitude = traceLocation.getLongitude();//经度
//            loc = new BDLocation();
//            loc.setLatitude(latitude);
//            loc.setLongitude(longitude);
//            LatLng point = new LatLng(latitude, longitude);
//            //构建Marker图标
//            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
//            //构建MarkerOption，用于在地图上添加Marker
//            OverlayOptions option = new MarkerOptions()
//                    .position(point)
//                    .icon(bitmap);
//            //在地图上添加Marker，并显示
//            baiduMap.addOverlay(option);
//            //移动到point位置
//            MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(point);
//            baiduMap.animateMapStatus(msu);
//        }
    };

//
//    private void initLocation() {
//
//        loc=new BDLocation();
//        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
//
//        mLocClient = new LocationClient(getApplicationContext());
//        myListener = new MyLocationListenner();
//        mLocClient.registerLocationListener(myListener);
//
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setIsNeedAddress(true);
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);   //每隔1000毫秒执行一次请求
//        mLocClient.setLocOption(option);
//
//        //初始化自定义的定位图标
//        mLocationIcon = BitmapDescriptorFactory.fromResource(R.mipmap.arrow);
//
//        myOrientationListener = new MyOrientationListener(this);
//
//        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
//            @Override
//            public void OnOrientationChanged(float x) {
//                currentX=x;
//            }
//        });
//
//    }


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
//    private class MyLocationListenner implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//
//            MyLocationData locData = new MyLocationData.Builder()//
//                    .direction(currentX)//设置方向
//                    .accuracy(location.getRadius())//此处设置开发者获取到的方向信息，顺时针0-360
//                    .latitude(location.getLatitude())//
//                    .longitude(location.getLongitude())//
//                    .build();
//
//            baiduMap.setMyLocationData(locData);
//            //设置自定义图标
//            MyLocationConfiguration config = new
//                    MyLocationConfiguration(mLocationMode,true,mLocationIcon);
//            baiduMap.setMyLocationConfigeration(config);
//
//            loc=location;
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//                MapStatusUpdate msu= MapStatusUpdateFactory.newLatLng(ll);
//                baiduMap.animateMapStatus(msu);
//                //  Snackbar.make(btnCompass,location.getAddrStr(),Snackbar.LENGTH_SHORT).show();
//
//            }
//        }
//
//        @Override
//        public void onConnectHotSpotMessage(String s, int i) {
//
//        }
//    }

    /*
    **定位到我的位置
     */
    public void centerToMyLocation(){
//        LatLng ll = new LatLng(loc.getLatitude(), loc.getLongitude());
//        MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(ll);
//        baiduMap.animateMapStatus(msu);
        client.queryEntityList(131714, fphone, null, 0, 10000, 10, 1, onEntityListener);

        //baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));//设置缩放级别
        // Snackbar.make(btnLocation,loc.getAddrStr(),Snackbar.LENGTH_SHORT).show();
    }




    @Override
    public void onStop() {
        super.onStop();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);

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
