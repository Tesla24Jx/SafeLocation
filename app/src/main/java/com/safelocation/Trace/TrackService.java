package com.safelocation.Trace;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.OnStopTraceListener;
import com.baidu.trace.Trace;
import com.safelocation.Utils.ACache;

public class TrackService extends Service {

    /**
     * 轨迹服务
     */
    Trace trace = null;

    /**
     * 轨迹服务客户端
     */
    LBSTraceClient client = null;

    /**
     * 鹰眼服务ID，开发者创建的鹰眼服务对应的服务ID
     */
    int serviceId = 131714;

    /**
     * entity标识
     */
    String entitiesName ;

    /**
     * 轨迹服务类型（0 : 不建立socket长连接， 1 : 建立socket长连接但不上传位置数据，2 : 建立socket长连接并上传位置数据）
     */
    int traceType = 2;

    /**
     * 轨迹服务监听
     */
    OnStartTraceListener startTraceListener;

    // 采集周期
    int gatherInterval = 10;
    // 打包周期
    int packInterval = 60;
    // http协议类型
    int protocolType = 1;


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("###TrackService","onStartCommand");
        startTrace();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startTrace() {

        // 初始化轨迹服务客户端
        client = new LBSTraceClient(this);
        entitiesName = ACache.get(this).getAsString("userphone");
        Log.d("###entitiesName=", entitiesName);


        // 设置采集和打包周期
        client.setInterval(gatherInterval, packInterval);
        // 设置定位模式
        client.setLocationMode(LocationMode.High_Accuracy);
        // 设置http协议类型
        client.setProtocolType (protocolType);

        startTraceListener = new OnStartTraceListener() {
            //开启轨迹服务回调接口（arg0 : 消息编码，arg1 : 消息内容，详情查看类参考）
            @Override
            public void onTraceCallback(int arg0, String arg1) {
                Log.d("##轨迹服务回编码:", "" + arg0);
                Log.d("##轨迹服务回调信息:", arg1);
            }

            //轨迹服务推送接口（用于接收服务端推送消息，arg0 : 消息类型，arg1 : 消息内容，详情查看类参考）
            @Override
            public void onTracePushCallback(byte arg0, String arg1) {
                Log.d("##轨迹服务接收服务端推送消息:", arg1);

            }
        };
        // 通过轨迹服务客户端client开启轨迹服务
        trace = new Trace(this, serviceId, entitiesName, traceType);

        client.startTrace(trace, startTraceListener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OnStopTraceListener stopTraceListener = new OnStopTraceListener() {
            @Override
            public void onStopTraceSuccess() {

            }

            @Override
            public void onStopTraceFailed(int i, String s) {

            }
        };
        Log.d("###TrackService","OnDestroy()");
        client.stopTrace(trace,stopTraceListener);
    }
}
