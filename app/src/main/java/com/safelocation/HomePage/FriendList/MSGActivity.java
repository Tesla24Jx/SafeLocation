package com.safelocation.HomePage.FriendList;

import android.content.ContentValues;
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
import com.safelocation.Utils.Common;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import de.hdodenhof.circleimageview.CircleImageView;

public class MSGActivity extends AppCompatActivity {

    private ImageView topbar_left;
    private ImageView topbar_right;
    private TextView topbar_title;
    private ImageView fhead;
    private TextView tv_fname;
    private Button btn_add;
    private TextView tv_mime;
    private int aSwitch_get = 1;
    private Switch aSwitch_give;
    private SubscriberOnNextListener getOnNext;

    String fid;
    String fimg;
    String fname;
    String fmime;
    String type;
    String already_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        topbar_left = (ImageView)findViewById(R.id.topbar_left);
        topbar_right = (ImageView)findViewById(R.id.topbar_right);
        topbar_title = (TextView) findViewById(R.id.topbar_title);
        fhead = (ImageView)findViewById(R.id.iv_f_img);
        tv_fname = (TextView)findViewById(R.id.tv_f_name);
        tv_mime = (TextView) findViewById(R.id.tv_mime);
        aSwitch_give = (Switch)findViewById(R.id.switch_give);
        btn_add = (Button)findViewById(R.id.btn_add);
        topbar_left.setImageResource(R.drawable.ic_back);
        topbar_title.setText("好友添加请求");
        topbar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        getOnNext = new SubscriberOnNextListener<StrJson>() {
            @Override
            public void onNext(StrJson json) {
                HttpRequest httpRequest=Common.gson.fromJson(json.getData(),HttpRequest.class);
                String str = Common.gson.toJson(httpRequest);
                ACache.get(MSGActivity.this).put("addinfo",str);  //把好友列表信息存储到缓存
                ACache.get(MSGActivity.this).put(fid,fid);
                Log.d("###return_data",str);
                sendBroadcast(new Intent().setAction("rushList"));
                Snackbar.make(btn_add,"好友已添加",Snackbar.LENGTH_SHORT).show();
                btn_add.setEnabled(false);
                btn_add.setText("已添加为好友");
                ContentValues values = new ContentValues();
                values.put("already_add", "1");
                DataSupport.updateAll(Addfriend.class, values, "fid = ?", fid);
//                tv_fname.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        finish();
//                    }
//                },1500);
            }
        };

        Bundle bundle = getIntent().getBundleExtra("fdata");
        if(bundle!=null) {
            fid = bundle.getString("fid");
            fimg = bundle.getString("fimg");
            fname = bundle.getString("fname");
            fmime = bundle.getString("fmime");
            type = bundle.getString("type");
            already_add = bundle.getString("already_add");
            tv_fname.setText(fname);
            Glide.with(this)
                    .load(fimg)
                    .placeholder(R.drawable.default_userhead)
                    .into(fhead);

            if(type.equals("af")){
                tv_mime.setText("备注："+fmime);
                aSwitch_give.setChecked(true);

                ;
                if(already_add.equals("1")){
                    btn_add.setEnabled(false);
                    btn_add.setText("已添加为好友");
                    return;
                }

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_add.setClickable(false);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("uid", Userdata.uid);
                            jsonObject.put("fid", fid);
                            jsonObject.put("forpermission",aSwitch_get );
                            jsonObject.put("getpermission", 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String strJson = jsonObject.toString();
                        String type = "add_agree";
                        HttpUtil.getInstance().getJSON(getOnNext,strJson,type);
                    }
                });

            }else{
                tv_mime.setText(fname+"请求查看你的位置权限");
                topbar_title.setText("好友权限请求");
                btn_add.setText("同意授予权限");
            }
        }
//        ACache aCache=ACache.get(this);
//        String str= aCache.getAsString("add_friend");
//        if(str!=null) {
//            final Addfriend friendInfo= new Gson().fromJson(str,Addfriend.class);
//            fname.setText(friendInfo.getFname());
//            Glide.with(this)
//                    .load(friendInfo.getFimg())
//                    .into(fhead);
//            tv_mime.setText("备注："+friendInfo.getMime()+friendInfo.getFid());
//
//            aSwitch_give.setChecked(true);
//
//            btn_add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    btn_add.setClickable(false);
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("uid", Userdata.uid);
//                        jsonObject.put("fid", friendInfo.getFid());
//                        jsonObject.put("forpermission",aSwitch_get );
//                        jsonObject.put("getpermission", friendInfo.getGetpermission());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String strJson = jsonObject.toString();
//                    String type = "add_agree";
//                    HttpUtil.getInstance().getJSON(new SubscriberOnNextListener<StrJson>() {
//                        @Override
//                        public void onNext(StrJson o) {
//
//                            ACache.get(MSGActivity.this).put("refulshList",o.getData());  //把好友列表信息存储到缓存
//                            Log.d("###return_data",o.getData());
//                            sendBroadcast(new Intent().setAction("rushList"));
//                            Snackbar.make(btn_add,"好友已添加",Snackbar.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    },strJson,type);
//
//                }
//            });
//
//        }
    }

}
