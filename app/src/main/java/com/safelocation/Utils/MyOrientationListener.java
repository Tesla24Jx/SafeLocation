package com.safelocation.Utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Juliet on 2016/8/3.
 * 方向监听类
 */
public class MyOrientationListener implements SensorEventListener {
    private SensorManager sensorManager;
    private Context context;
    private Sensor sensor; //传感器

    private float lastX;


    public MyOrientationListener(Context context){
        this.context=context;
    }
    //开启监听
    public void start(){
        //获得系统的传感器服务
        sensorManager= (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager!=null){
            //获得方向传感器
            sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if(sensor!=null){
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_UI);
        }
    }
    //结束监听
    public void stop(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
            float x =event.values[sensorManager.DATA_X];
            if (Math.abs(x-lastX)>1.0){
                if (onOrientationListener!=null){
                    onOrientationListener.OnOrientationChanged(x);
                }
            }
            lastX=x;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private OnOrientationListener onOrientationListener;

    public void setOnOrientationListener(OnOrientationListener onOrientationListener) {
        this.onOrientationListener = onOrientationListener;
    }

    public interface OnOrientationListener{
        void OnOrientationChanged(float x);
    }
}
