package com.safelocation.HomePage.FriendList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.safelocation.Entity.Flist;
import com.safelocation.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SortAdapter extends BaseAdapter {
	private Context context;
	private List<Flist> flists;
	private LayoutInflater inflater;

	public SortAdapter(Context context, List<Flist> flists) {
		this.context = context;
		this.flists = flists;
		this.inflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return flists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return flists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder = null;
		Flist friend = flists.get(position);
		if (convertView == null) {
			viewholder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item, null);
			viewholder.iv_head = (CircleImageView) convertView
					.findViewById(R.id.iv_lv_item_head);
			viewholder.tv_tag = (TextView) convertView
					.findViewById(R.id.tv_lv_item_tag);
			viewholder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_lv_item_name);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		// 获取首字母的assii值
		int selection = friend.getFirstPinYin().charAt(0);
		// 通过首字母的assii值来判断是否显示字母
		int positionForSelection = getPositionForSelection(selection);
		if (position == positionForSelection) {// 相等说明需要显示字母
			viewholder.tv_tag.setVisibility(View.VISIBLE);
			viewholder.tv_tag.setText(friend.getFirstPinYin());
		} else {
			viewholder.tv_tag.setVisibility(View.GONE);

		}
		viewholder.tv_name.setText(friend.getName());
		Log.d("###imgUrl=",friend.getImgurl());
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setFillAfter(true);
		scaleAnimation.setDuration(2500);
		viewholder.iv_head.startAnimation(scaleAnimation);

		Glide.with(context)
				.load(friend.getImgurl())
				.animate(scaleAnimation)
				.placeholder(R.drawable.default_userhead)
				.into(viewholder.iv_head);
		return convertView;
	}

	public int getPositionForSelection(int selection) {
		for (int i = 0; i < flists.size(); i++) {
			String Fpinyin = flists.get(i).getFirstPinYin();
			char first = Fpinyin.toUpperCase().charAt(0);
			if (first == selection) {
				return i;
			}
		}
		return -1;

	}

	class ViewHolder {
		TextView tv_tag;
		CircleImageView iv_head;
		TextView tv_name;
	}

}
