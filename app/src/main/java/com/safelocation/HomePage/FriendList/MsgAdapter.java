package com.safelocation.HomePage.FriendList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.safelocation.Entity.Addfriend;
import com.safelocation.Entity.Flist;
import com.safelocation.Entity.FriendInfo;
import com.safelocation.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Juliet on 2017/5/2.
 */

public class MsgAdapter extends BaseAdapter{

    private List<Addfriend> flists;
    private Context context;
    private LayoutInflater inflater;

    public MsgAdapter(Context context, List<Addfriend> flists) {
        this.context = context;
        this.flists = flists;
        this.inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return flists.size();
    }

    @Override
    public Object getItem(int position) {
        return flists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;

        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = inflater.inflate(R.layout.msg_item, null);
            viewholder.iv_head = (CircleImageView) convertView
                    .findViewById(R.id.img_head);
            viewholder.tv_msg = (TextView) convertView
                    .findViewById(R.id.msg);
            viewholder.tv_name = (TextView) convertView
                    .findViewById(R.id.username);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(flists.get(position).getFimg())
                .placeholder(R.drawable.default_userhead)
                .into(viewholder.iv_head);
        viewholder.tv_name.setText(flists.get(position).getFname());
        viewholder.tv_msg.setText(flists.get(position).getMime());


        return convertView;
    }

    class ViewHolder {
        TextView tv_msg;
        CircleImageView iv_head;
        TextView tv_name;
    }
}
