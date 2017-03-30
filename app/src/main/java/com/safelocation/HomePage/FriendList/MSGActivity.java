package com.safelocation.HomePage.FriendList;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.safelocation.Entity.Addfriend;
import com.safelocation.Entity.FriendInfo;
import com.safelocation.Entity.HttpRequest;
import com.safelocation.Entity.StrJson;
import com.safelocation.Entity.Userdata;
import com.safelocation.HttpUtil.HttpUtil;
import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.R;
import com.safelocation.Utils.ACache;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MSGActivity extends AppCompatActivity {

    private ImageView topbar_left;
    private ImageView topbar_right;
    private TextView topbar_title;
    private ImageView fhead;
    private TextView fname;
    private Button btn_add;
    private TextView tv_mime;
    private int aSwitch_get = 1;
    private Switch aSwitch_give;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        topbar_left = (ImageView)findViewById(R.id.topbar_left);
        topbar_right = (ImageView)findViewById(R.id.topbar_right);
        topbar_title = (TextView) findViewById(R.id.topbar_title);
        fhead = (ImageView)findViewById(R.id.iv_f_img);
        fname = (TextView)findViewById(R.id.tv_f_name);
        tv_mime = (TextView) findViewById(R.id.tv_mime);
        aSwitch_give = (Switch)findViewById(R.id.switch_give);
        btn_add = (Button)findViewById(R.id.btn_add);
        topbar_left.setImageResource(R.drawable.ic_back);
        topbar_title.setText("好友添加请求");
        aSwitch_give.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    aSwitch_get=1;
                } else{
                    aSwitch_get=0;
                }
            }
        });

        ACache aCache=ACache.get(this);
        String str= aCache.getAsString("add_friend");
        if(str!=null) {
            final Addfriend friendInfo= new Gson().fromJson(str,Addfriend.class);
            fname.setText(friendInfo.getFname());
            Glide.with(this)
                    .load(friendInfo.getFimg())
                    .into(fhead);
            tv_mime.setText("备注："+friendInfo.getMime()+friendInfo.getFid());
            aSwitch_give.setChecked(true);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_add.setClickable(false);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("uid", Userdata.uid);
                        jsonObject.put("fid", friendInfo.getFid());
                        jsonObject.put("forpermission",aSwitch_get );
                        jsonObject.put("getpermission", friendInfo.getGetpermission());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String strJson = jsonObject.toString();
                    String type = "add_agree";
                    HttpUtil.getInstance().getJSON(new SubscriberOnNextListener<StrJson>() {
                        @Override
                        public void onNext(StrJson o) {

                            ACache.get(MSGActivity.this).put("refulshList",o.getData());  //把好友列表信息存储到缓存
                            Log.d("###return_data",o.getData());
                            sendBroadcast(new Intent().setAction("rushList"));
                            Snackbar.make(btn_add,"好友已添加",Snackbar.LENGTH_SHORT).show();
                            finish();
                        }
                    },strJson,type);

                }
            });
        }
    }
}
