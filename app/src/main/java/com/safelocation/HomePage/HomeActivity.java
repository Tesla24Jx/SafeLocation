package com.safelocation.HomePage;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.igexin.sdk.PushManager;
import com.safelocation.HomePage.FriendList.AddFriendActivity;
import com.safelocation.HomePage.FriendList.FriendListFragment;
import com.safelocation.R;
import com.safelocation.Service.GeTuiIntentService;
import com.safelocation.Service.GeTuiPushService;
import com.safelocation.Trace.MyService;
import com.safelocation.Trace.TrackReceiver;
import com.safelocation.Trace.TrackService;
import com.safelocation.Utils.ToastUtils;


public class HomeActivity extends AppCompatActivity implements FriendListFragment.GotoActivity{

    private HomeFragment homeFragment;


    @Override
    protected void onResume() {
        super.onResume();
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiPushService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(),GeTuiIntentService.class);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.common_layout);

        Intent intent = new Intent(this, TrackReceiver.class);
        intent.setAction("com.safelocation.HomePage.HomeActivity");
//        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//        sendBroadcast(intent);
        startService(new Intent(this,TrackService.class));

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, homeFragment).commit();


        //new FriendListPresenter(new FriendListModel(),loginFragment);
    }

    @Override
    public void gotoAct(int type) {
        switch (type){
            case 0:
                startActivity(new Intent(HomeActivity.this, AddFriendActivity.class));
                break;
        }
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}