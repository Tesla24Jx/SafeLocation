package com.safelocation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.OnEntityListener;
import com.baidu.trace.OnTrackListener;
import com.baidu.trace.TraceLocation;
import com.google.gson.Gson;
import com.safelocation.Entity.Userdata;
import com.safelocation.Entity.locEntities;

import org.json.JSONException;
import org.json.JSONObject;

/*
** 1.下拉列表控件
** 2.日期选择器
** 3.时间选择器
** 4.单项选择
** 5.多项选择
 */
public class Activity extends AppCompatActivity {
    private EditText ed_serviceID;
    private TextView tv_result;
    LBSTraceClient client;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);


        ed_serviceID = (EditText)findViewById(R.id.serviceId);
        tv_result = (TextView)findViewById(R.id.result);
        // 初始化轨迹服务客户端
        client = new LBSTraceClient(getApplicationContext());
        //
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //client.queryRealtimeLoc(Long.valueOf(Userdata.uid), onEntityListener);
                //queryHistoryTrack();

//查询entity
                client.queryEntityList(131714, Userdata.uphone, null, 0, 10000, 100, 1, onEntityListener);
            }
        });

    }

    //实体监听
    OnEntityListener onEntityListener = new OnEntityListener() {
        @Override
        public void onQueryEntityListCallback(String s) {
            super.onQueryEntityListCallback(s);
            Log.d("###json",s);

            locEntities locEntities = new Gson().fromJson(s,locEntities.class);
            double loc = locEntities.getEntities().get(0).getRealtime_point().getLocation().get(0);
            double loc1 = locEntities.getEntities().get(0).getRealtime_point().getLocation().get(1);
            String time = locEntities.getEntities().get(0).getModify_time();
            tv_result.setText(loc+"  "+loc1+"  "+time);
        }

        @Override
        public void onRequestFailedCallback(String s) {
            tv_result.setText("失败");
        }

        @Override
        public void onReceiveLocation(TraceLocation traceLocation) {
            super.onReceiveLocation(traceLocation);
            double latitude = traceLocation.getLatitude();//纬度
            double longitude = traceLocation.getLongitude();//经度
            Log.d("###经度和纬度：",longitude+", "+latitude);
            String str="经度："+longitude+" \n纬度:"+latitude;
            tv_result.setText(str);
        }
    };

//    // 轨迹服务ID
    long serviceId = Long.valueOf(Userdata.uid);
    // entity标识
    String entityName = Userdata.uname;
    // 是否返回精简结果
    int simpleReturn = 0;
    // 是否纠偏
    int isProcessed = 0;
    // 纠偏选项
    String processOption = null;
    // 开始时间
    int startTime = (int)(System.currentTimeMillis()/1000);
    // 分页大小
    int pageSize = 5000;
    // 分页索引
    int pageIndex = 1;

    // 间隔打包周期，轮询调用queryHistoryTrack()
    private void queryHistoryTrack() {
        // 结束时间
        int endTime = (int)(System.currentTimeMillis()/1000);
        // 查询新增的轨迹
        client.queryHistoryTrack(serviceId , entityName, simpleReturn, isProcessed,
                processOption, startTime, endTime, pageSize, pageIndex, trackListener);
    }

    OnTrackListener trackListener = new OnTrackListener() {
        public void onQueryHistoryTrackCallback(String message) {
            JSONObject data = null;
            try {
                Log.d("###message==",message);
                data = new JSONObject(message);
                JSONObject endPoint = data. getJSONObject ("end_point");
                //更新startTime，在当前查询的最后一个点的时间戳上加1，作为下一次查询的开始时间
                startTime = endPoint.getInt("loc_time") + 1;
                tv_result.setText(message);
                // 解析并保存轨迹信息
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onRequestFailedCallback(String s) {

        }
    };
}
