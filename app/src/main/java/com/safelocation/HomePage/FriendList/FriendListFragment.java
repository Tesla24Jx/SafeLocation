package com.safelocation.HomePage.FriendList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.safelocation.Activity;
import com.safelocation.Entity.Addfriend;
import com.safelocation.Entity.Flist;
import com.safelocation.Entity.HttpRequest;
import com.safelocation.Entity.Menu_item;
import com.safelocation.Entity.FriendInfo;
import com.safelocation.Entity.Userdata;
import com.safelocation.Map.MapActivity;
import com.safelocation.R;
import com.safelocation.Utils.ACache;
import com.safelocation.Utils.Common;
import com.safelocation.widge.FriendList.PinyinComparator;
import com.safelocation.widge.FriendList.PinyinUtils;
import com.safelocation.widge.FriendList.SideBar;
import com.safelocation.widge.TitlePopup;

import org.litepal.crud.DataSupport;
import org.litepal.exceptions.DataSupportException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Juliet on 2017/2/25.
 */

public class FriendListFragment extends Fragment implements TitlePopup.OnItemOnClickListener{

    private ACache aCache;
    private ListView listView;
    public ImageView topbar_left;
    private ImageView topbar_right;
    private TextView topbar_title;
    private TitlePopup titlePopup;
    private SortAdapter sortadapter;
    private List<Flist> data;
    private SideBar sidebar;
    private TextView dialog;
    private GotoActivity mCallback;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("###FlistFragment广播接收器","已收到广播");
            refulshList();
        }
    };
    BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("###FlistFragment广播接收器","已收到广播-请求添加成功");
             refulshList2();
        }
    };
    BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("###FlistFragment广播接收器","已收到广播-删除成功");
            int position = intent.getIntExtra("position",-1);
            Log.d("###position=====",""+position);
            refulshList3(position);
        }
    };


    public interface GotoActivity{
        void gotoAct(int type);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_2_flist,container,false);
        sidebar = (SideBar) view.findViewById(R.id.sidebar);
        listView = (ListView) view.findViewById(R.id.listview);
        dialog = (TextView) view.findViewById(R.id.dialog);
        topbar_left = (ImageView) view.findViewById(R.id.topbar_left);
        topbar_title = (TextView) view.findViewById(R.id.topbar_title);
        topbar_right = (ImageView) view.findViewById(R.id.topbar_right);
        topbar_title.setText("通讯录");
        topbar_right.setImageResource(R.drawable.ic_add);
        topbar_left.setImageResource(R.drawable.ic_msg);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        IntentFilter filter = new IntentFilter("rushList");
        getActivity().registerReceiver(broadcastReceiver,filter);
        IntentFilter filter2 = new IntentFilter("refulshList");
        getActivity().registerReceiver(broadcastReceiver2,filter2);
        IntentFilter filter3 = new IntentFilter("del_update_List");
        getActivity().registerReceiver(broadcastReceiver3,filter3);
    }

    private void init() {
        // TODO Auto-generated method stub
        aCache = ACache.get(getActivity());
        sidebar.setTextView(dialog);

        // 设置字母导航触摸监听
        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // TODO Auto-generated method stub
                // 该字母首次出现的位置
                int position = sortadapter.getPositionForSelection(s.charAt(0));

                if (position != -1) {
                    listView.setSelection(position);
                }
            }
        });
        data = preData("initdata");
        // 数据在放在adapter之前需要排序
        Collections.sort(data, new PinyinComparator());
        sortadapter = new SortAdapter(getActivity(), data);
        listView.setAdapter(sortadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("position",position);
                    bundle.putString("fhead",data.get(position).getImgurl());
                    bundle.putString("fphone",data.get(position).getPhone());
                    bundle.putString("fname",data.get(position).getName());
                    bundle.putInt("fgetPermission",data.get(position).getGetPermission());
                    bundle.putInt("fforPermission",data.get(position).getForPermission());
                    startActivity(new Intent(getActivity(),TransferActivity.class).putExtra("fdata",bundle));
//                }else{
//                    Snackbar.make(listView," 未获得好友权限！",Snackbar.LENGTH_SHORT).show();
//                }

            }
        });


        //测量view的宽高
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.menu_item,null);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        titlePopup = new TitlePopup(getActivity(),width, ViewGroup.LayoutParams.WRAP_CONTENT);
        initData();
        topbar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MsgListActivity.class));
            }
        });
        topbar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titlePopup.show(v);
            }
        });
        titlePopup.setItemOnClickListener(this);

    }

    private void initData() {
        titlePopup.addAction(new Menu_item("添加朋友", R.drawable.ic_add));
//        titlePopup.addAction(new Menu_item("请求权限", R.drawable.ic_add));
//        titlePopup.addAction(new Menu_item("授予权限", R.drawable.ic_add));
    }

    public List<Flist> preData(String cache){
        String str = aCache.getAsString(cache);
        Log.d("###shuju",str);
        Gson gson = new Gson();
        HttpRequest httpRequest = gson.fromJson(str, HttpRequest.class);
        List<FriendInfo> fl = httpRequest.getFriendlist();

        List<Flist> listdata = new ArrayList<Flist>();
        if(fl.get(0).getFid()==0){
            return listdata;
        }


        for(FriendInfo f:fl){
            listdata.add(deal(f.getFname(),f.getFimg(),f.getFphone(),f.getGetpermission(),f.getForpermission()));
        }

        return listdata;
    }

    public Flist deal(String name,String imgurl,String phone,int getPermission,int forPermission){
        String pinyin = PinyinUtils.getPingYin(name);
        String Fpinyin = pinyin.substring(0, 1).toUpperCase();

        Flist friend = new Flist();
        friend.setImgurl(imgurl);
        friend.setName(name);
        friend.setPhone(phone);
        friend.setGetPermission(getPermission);
        friend.setForPermission(forPermission);
        friend.setPinYin(pinyin);
        // 正则表达式，判断首字母是否是英文字母
        if (Fpinyin.matches("[A-Z]")) {
            friend.setFirstPinYin(Fpinyin);
        } else {
            friend.setFirstPinYin("#");
        }
        return friend;
    }


    @Override
    public void onItemClick(Menu_item item, int position) {
        switch (item.getItem_name()){
            case "添加朋友":
                startActivity(new Intent(getActivity(),AddFriendActivity.class));
                //mCallback.gotoAct(0);
                break;
            case "请求权限":

                break;
            case "授予权限":

                break;

        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (GotoActivity) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString());
        }
    }

    private void refulshList(){
//        data = preData("updatelist");
        Log.d("###data.size=",""+data.size());
        String str = aCache.getAsString("addinfo");
        Log.d("###shuju",str);
        HttpRequest httpRequest = Common.gson.fromJson(str, HttpRequest.class);
        List<FriendInfo> f = httpRequest.getFriendlist();
        data.add(deal(f.get(0).getFname(),f.get(0).getFimg(),f.get(0).getFphone(),f.get(0).getGetpermission(),f.get(0).getForpermission()));
        Log.d("###data.size=",""+data.size());
        sortadapter.notifyDataSetChanged();
    }
    private void refulshList2(){
//        data = preData("updatelist");
        Log.d("###data.size=",""+data.size());
        String str = aCache.getAsString("refulshList");
        Log.d("###shuju",str);
        HttpRequest httpRequest = Common.gson.fromJson(str, HttpRequest.class);
        List<FriendInfo> f = httpRequest.getFriendlist();
        data.add(deal(f.get(0).getFname(),f.get(0).getFimg(),f.get(0).getFphone(),f.get(0).getGetpermission(),f.get(0).getForpermission()));
        Log.d("###data.size=",""+data.size());
        sortadapter.notifyDataSetChanged();

    }
    private void refulshList3(int position){
//        data = preData("updatelist");
        Log.d("###data.size=",""+data.size());
        data.remove(position);
        Log.d("###data.size=",""+data.size());
        sortadapter.notifyDataSetChanged();

    }
}
