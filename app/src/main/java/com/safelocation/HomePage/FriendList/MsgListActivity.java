package com.safelocation.HomePage.FriendList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.safelocation.Entity.Addfriend;
import com.safelocation.Entity.Userdata;
import com.safelocation.R;
import com.safelocation.Utils.ToastUtils;

import org.litepal.crud.DataSupport;
import org.litepal.exceptions.DataSupportException;

import java.util.List;

public class MsgListActivity extends AppCompatActivity {

    private ImageView topbar_left;
    private TextView topbar_title;
    private ListView listView;

    private MsgAdapter msgAdapter;
    private List<Addfriend> listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_list);

        topbar_left = (ImageView) findViewById(R.id.topbar_left);
        topbar_title = (TextView) findViewById(R.id.topbar_title);
        listView = (ListView) findViewById(R.id.listview);
        listView.setDivider(null);
        topbar_left.setImageResource(R.drawable.ic_back);
        topbar_title.setText("消息列表");
//        listdata = DataSupport.findAll(Addfriend.class);
        listdata = DataSupport.where("uid = ?", Userdata.uid).find(Addfriend.class);
//        try{
//            List<Addfriend> listdata2 = DataSupport.findAll(Addfriend.class);
//            for (Addfriend ld:listdata2){
//                ld.get
//            }
//            listdata2.get()
//        }catch (DataSupportException e){
//            Log.d("###DataSupportException",e.getMessage());
//        }
        ToastUtils.snackbar_long(listView,listdata.size()+"");

        msgAdapter = new MsgAdapter(this,listdata);
        listView.setAdapter(msgAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Addfriend addfriend = listdata.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("fname",addfriend.getFname());
                bundle.putString("fid",addfriend.getFid());
                bundle.putString("fimg",addfriend.getFimg());
                bundle.putString("fmime",addfriend.getMime());
                bundle.putString("type",addfriend.getType());
                bundle.putString("already_add",addfriend.getAlready_add());
                startActivity(new Intent(MsgListActivity.this,MSGActivity.class).putExtra("fdata",bundle));
            }
        });


        topbar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
