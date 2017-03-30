package com.safelocation.Trace;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.safelocation.Utils.ACache;

public class TrackReceiver extends BroadcastReceiver {

    boolean flag =false;
    @SuppressLint("Wakelock")
    @Override
    public void onReceive(final Context context, final Intent intent) {
        String from = context.getPackageName();
        Log.d("###广播已执行from:","启动TestService");



//        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(50)) {
//            if ("com.safelocation.Trace.TrackService".equals(service.service.getClassName())) {
//               flag = true;
//            }
//        }
//
//        if(flag){
//            Log.d("###服务在运行","不需要重启服务");
//        }else{
            context.startService(new Intent(context, TrackService.class));
//            Log.d("###服务没在运行","现在重启服务");
//
//        }
    }

}
