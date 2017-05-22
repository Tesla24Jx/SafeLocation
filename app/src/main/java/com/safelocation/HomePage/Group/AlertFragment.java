package com.safelocation.HomePage.Group;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.trace.LBSTraceService;
import com.bumptech.glide.Glide;
import com.safelocation.Entity.HttpRequest;
import com.safelocation.Entity.StrJson;
import com.safelocation.Entity.UserInfo;
import com.safelocation.Entity.Userdata;
import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.R;
import com.safelocation.Register.RegisterPresenter;
import com.safelocation.Trace.TrackService;
import com.safelocation.Utils.ACache;
import com.safelocation.login.LoginActivity;
import com.safelocation.widge.BottomPopView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class AlertFragment extends Fragment implements View.OnClickListener {


    private TextView topbar_title;
    private ImageView topbar_right;
    private ImageView topbar_left;
    
    private ImageView fillup_head;
    private EditText fillup_username;
    private EditText fillup_age;
    private EditText fillup_phone;
    private RadioGroup fillup_radiogroup;
    private RadioButton radio_male;
    private RadioButton radio_female;
    private Button btn_submitInfo;

    private String sex="男";
    private String headUrl;
    private BottomPopView bottomPopView;
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    private GroupModel groupModel = new GroupModel();
    private SubscriberOnNextListener getOnNext;
    private SubscriberOnNextListener getOnNext_uploadhead;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alertinfo, container, false);

        topbar_title = (TextView) view.findViewById(R.id.topbar_title);
        topbar_left = (ImageView) view.findViewById(R.id.topbar_left);
        topbar_right = (ImageView) view.findViewById(R.id.topbar_right);
        fillup_head = (ImageView) view.findViewById(R.id.fillup_head);
        fillup_username = (EditText) view.findViewById(R.id.fillup_username);
        fillup_radiogroup = (RadioGroup) view.findViewById(R.id.fillup_radioGroup);
        radio_male = (RadioButton)view.findViewById(R.id.radioMale);
        radio_female = (RadioButton)view.findViewById(R.id.radioFemale);
        fillup_phone = (EditText) view.findViewById(R.id.fillup_phone);
        fillup_age = (EditText) view.findViewById(R.id.fillup_age);
        btn_submitInfo = (Button) view.findViewById(R.id.btn_submitInfo);


        fillup_head.setOnClickListener(this);
        topbar_left.setOnClickListener(this);
        btn_submitInfo.setOnClickListener(this);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(this).load(Userdata.img).placeholder(R.drawable.default_userhead).into(fillup_head);
        setHeadUrl(Userdata.img);
        topbar_title.setText("修改信息");
        topbar_left.setImageResource(R.drawable.ic_back);

        initInfo();
        btn_submitInfo.setText("保存");


        fillup_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radio_male.getId() == checkedId){
                    setSex("男");

                }else if(radio_female.getId() == checkedId){
                    setSex("女");
                }
            }
        });
        init_bottomPoP();
        //获取数据
        getOnNext = new SubscriberOnNextListener<StrJson>() {
            @Override
            public void onNext(StrJson json) {
                Log.d("###return data",""+json.getMsg());
                Userdata.uname = fillup_username.getText().toString().trim();
                Userdata.age = Integer.valueOf(fillup_age.getText().toString().trim());
                Userdata.sex = sex;
                Userdata.img = headUrl;
                //goto_homePage();
                getActivity().sendBroadcast(new Intent().setAction("updateInfo"));
            }
        };
        getOnNext_uploadhead = new SubscriberOnNextListener<StrJson>() {
            @Override
            public void onNext(StrJson json) {
                setHeadUrl(json.getData());
                Log.d("###图片地址123",""+json.getData());
            }
        };

    }

    private void initInfo() {
        fillup_username.setText(Userdata.uname);
        fillup_phone.setText(Userdata.uphone);
        fillup_age.setText(""+Userdata.age);

        if(Userdata.sex.equals("男")){
            radio_male.setChecked(true);
            radio_female.setChecked(false);
        }else{
            radio_male.setChecked(false);
            radio_female.setChecked(true);
        }
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
    public void setSex(String s){this.sex=s;}
    private void init_bottomPoP(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        bottomPopView = new BottomPopView(getActivity(),view) {
            @Override
            public void onTopButtonClick() {
                //拍照
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }

            @Override
            public void onBottomButtonClick() {
                //选择本地图片
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);

            }
        };
        bottomPopView.setTopText("拍照");
        bottomPopView.setBottomText("选择图片");
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fillup_head:
                // 显示底部菜单
                bottomPopView.show();
                break;
            case R.id.btn_submitInfo:
                UserInfo userInfo = new UserInfo();
                userInfo.setUname(fillup_username.getText().toString().trim());
                userInfo.setUphone(fillup_phone.getText().toString().trim());
                userInfo.setAge(Integer.valueOf(fillup_age.getText().toString().trim()));
                userInfo.setSex(sex);
                userInfo.setImg(headUrl);

                groupModel.submitInfo(getOnNext,userInfo);
                break;
            case R.id.topbar_left:
                getActivity().finish();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE)
        {
            if(data == null)
            {
                return;
            }
            else
            {
                Bundle extras = data.getExtras();
                if(extras != null)
                {
                    Bitmap bm = extras.getParcelable("data");
                    Uri uri = saveBitmap(bm);
                    startImageZoom(uri);
                }
            }
        }
        else if(requestCode == GALLERY_REQUEST_CODE)
        {
            if(data == null)
            {
                return;
            }
            Uri uri;
            uri = data.getData();
            Uri fileUri = convertUri(uri);
            startImageZoom(fileUri);
        }
        else if(requestCode == CROP_REQUEST_CODE)
        {
            if(data == null)
            {
                return;
            }
            Bundle extras = data.getExtras();
            if(extras == null){
                return;
            }
            Bitmap bm = extras.getParcelable("data");
            saveBitmap2(bm);
            fillup_head.setImageBitmap(bm);
            bottomPopView.dismiss();
            sendImage(bm);
        }
    }

    //转换URL
    private Uri convertUri(Uri uri)
    {
        InputStream is = null;
        try {
            is = getActivity().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //调用安卓自带的裁剪工具
    private void startImageZoom(Uri uri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    //保存原图片到本地SD卡
    private Uri saveBitmap(Bitmap bm)
    {
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/com.safelocation.headImg");
        if(!tmpDir.exists())
        {
            tmpDir.mkdir();
        }
        File img = new File(tmpDir.getAbsolutePath() + "/myhead.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            Log.d("###img","save");
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //保存裁剪后的图片到本地SD卡
    private void saveBitmap2(Bitmap bm)
    {
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/com.safelocation.headImg/");
        File img = new File(tmpDir.getAbsolutePath() + "/myhead.png");
        if(img.exists()) {      //覆盖原图像
            img.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            Log.d("###img","save2");
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //上传头像到服务器
    private void sendImage(Bitmap bm)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        groupModel.uploadhead(getOnNext_uploadhead,img);
}
}
