package com.safelocation.Register;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.baidu.mapapi.http.AsyncHttpClient;
import com.google.gson.Gson;
import com.safelocation.Entity.HttpRequest;
import com.safelocation.Entity.StrJson;
import com.safelocation.HttpUtil.SubscriberOnNextListener;
import com.safelocation.MainActivity;
import com.safelocation.R;
import com.safelocation.login.LoginActivity;
import com.safelocation.widge.BottomPopView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.smssdk.SMSSDK;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Juliet on 2017/1/28.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View, View.OnClickListener {
    private RegisterContract.Presenter registerPresenter;
    private SubscriberOnNextListener getOnNext;
    private SubscriberOnNextListener getOnNext_uploadhead;
    private BottomPopView bottomPopView;
    private Gson gson;
    private CircleImageView fillup_head;
    private String headUrl="";
    private TextView topbar_title;
    private EditText fillup_username;
    private EditText fillup_password;
    private EditText fillup_cfnpwd;
    private EditText fillup_age;
    private EditText fillup_phone;
    private RadioGroup fillup_radiogroup;
    private RadioButton radio_male;
    private RadioButton radio_female;
    private Button btn_submitInfo;
    private String sex = "男";

    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("###onCreateView()", "loginFragment");
        View view = inflater.inflate(R.layout.register_fillupinfo, container, false);
        topbar_title = (TextView) view.findViewById(R.id.topbar_title);
        fillup_head = (CircleImageView) view.findViewById(R.id.fillup_head);
        fillup_username = (EditText) view.findViewById(R.id.fillup_username);
        fillup_password = (EditText) view.findViewById(R.id.fillup_pwd);
        fillup_cfnpwd = (EditText) view.findViewById(R.id.fillup_cfnpwd);
        fillup_phone = (EditText) view.findViewById(R.id.fillup_phone);
        fillup_age = (EditText) view.findViewById(R.id.fillup_age);
        fillup_radiogroup = (RadioGroup) view.findViewById(R.id.fillup_radioGroup);
        radio_male = (RadioButton)view.findViewById(R.id.radioMale);
        radio_female = (RadioButton)view.findViewById(R.id.radioFemale);
        btn_submitInfo = (Button) view.findViewById(R.id.btn_submitInfo);

        fillup_head.setOnClickListener(this);
        btn_submitInfo.setOnClickListener(this);
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

        topbar_title.setText("完善个人信息");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        Log.d("###phone=",bundle.getString("phone"));
        fillup_phone.setText(bundle.getString("phone"));
        fillup_phone.setFocusable(false);

        Log.d("###onActivityCreated()", "onActivityCreated()");

        init_bottomPoP();
        //获取数据
        getOnNext = new SubscriberOnNextListener<HttpRequest>() {
            @Override
            public void onNext(HttpRequest json) {
                goto_homePage();
            }
        };
        getOnNext_uploadhead = new SubscriberOnNextListener<StrJson>() {
            @Override
            public void onNext(StrJson json) {
                setHeadUrl(json.getData());
                Log.d("###图片地址",""+json.getData());
            }
        };

    }

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
    public void setPresent(RegisterContract.Presenter presenter) {
        Log.d("###setPresent()", "setPresent()");
        registerPresenter = presenter;
    }


    @Override
    public void goto_homePage() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public String getUserName() {
        return fillup_username.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return fillup_password.getText().toString().trim();
    }

    @Override
    public String getCfnpwd() {
        return fillup_cfnpwd.getText().toString().trim();
    }

    @Override
    public String getPhone() {
        return fillup_phone.getText().toString().trim();
    }

    @Override
    public String getAge() {
        return fillup_age.getText().toString().trim();
    }

    @Override
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String getHeadUrl() {
        return this.headUrl;
    }


    public void setRegisterPresenter(RegisterContract.Presenter registerPresenter) {
        this.registerPresenter = registerPresenter;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public void showTip(String tip) {
        Snackbar.make(btn_submitInfo, tip ,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fillup_head:
                // 显示底部菜单
                bottomPopView.show();
                break;
            case R.id.btn_submitInfo:
                registerPresenter.submitInfo(getOnNext);
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
        registerPresenter.uploadHead(getOnNext_uploadhead,img);
    }
}
