package com.safelocation.Map;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.IllegalNaviArgumentException;
import com.baidu.mapapi.navi.NaviParaOption;
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
import com.safelocation.MainActivity;
import com.safelocation.R;
import com.safelocation.Utils.MyOrientationListener;
import com.safelocation.Utils.ToastUtils;


public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int UPLOAD_LOCATION = 2;
    LBSTraceClient client;
    private EditText ed_serviceID;
    private TextView tv_result;
    private TextView topbar_title;
    private ImageView topbar_left;


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
    double longitude;
    double latitude;

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
        topbar_title = (TextView) findViewById(R.id.topbar_title);
        topbar_left = (ImageView)findViewById(R.id.topbar_left);
        topbar_left.setImageResource(R.drawable.ic_back);
        topbar_title.setText(fname+"的位置");
        topbar_left.setOnClickListener(this);
        findViewById(R.id.btn_traffic).setOnClickListener(this);
        findViewById(R.id.type_normal).setOnClickListener(this);
        findViewById(R.id.type_SATELLITE).setOnClickListener(this);
        findViewById(R.id.btn_compass).setOnClickListener(this);
        findViewById(R.id.btn_location).setOnClickListener(this);
        findViewById(R.id.nav).setOnClickListener(this);
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
            longitude = locEntities.getEntities().get(0).getRealtime_point().getLocation().get(0);
            latitude = locEntities.getEntities().get(0).getRealtime_point().getLocation().get(1);
            String lasttime = locEntities.getEntities().get(0).getModify_time();
            Log.d("###经度：",""+longitude);
            Log.d("###纬度：",""+latitude);
            Log.d("###时间",lasttime);
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
            ToastUtils.snackbar_long(topbar_title,"最近更新时间："+lasttime);
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
                break;
            case R.id.topbar_left:
                finish();
                break;
            case R.id.nav:
                openBaiduMapToNav();
                break;

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

    void openBaiduMapToNav(){

        Log.d("### 起点经纬度:",Userdata.loc.getLongitude()+" ，"+Userdata.loc.getLatitude());
        Log.d("### 终点经纬度:",longitude+" ，"+latitude);
        //LatLng pt1 = new LatLng(Userdata.loc.getLongitude(),Userdata.loc.getLatitude());
//        LatLng pt1 = new LatLng(113.95702897715,22.5489522252);
//        LatLng pt2 = new LatLng(longitude,latitude);
        LatLng pt1 = new LatLng(114.026939,22.542482);
        LatLng pt2 = new LatLng(114.027007,22.539052);

        NaviParaOption para = new NaviParaOption();
        para.startPoint(pt1);
        para.startName("从这里开始");
        para.endPoint(pt2);
        para.endName("到这里结束");

        try {

            BaiduMapNavigation.openBaiduMapNavi(para, this);
            Log.d("###导行参数","导行参数");

        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
            builder.setTitle("提示");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //BaiduMapNavigation.GetLatestBaiduMapApp(MapActivity.this);
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.create().show();
        } catch (IllegalNaviArgumentException e){
            Log.d("###非法导行参数","非法导行参数");
        }

    }
}
