package com.safelocation.widge;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.safelocation.Entity.Menu_item;
import com.safelocation.R;

import java.util.ArrayList;

/**
 * @author yangyu
 *
 */
public class TitlePopup extends PopupWindow {
	private Context mContext;

	protected final int LIST_PADDING = 10;

	private Rect mRect = new Rect();

	private final int[] mLocation = new int[2];

	private int mScreenWidth,mScreenHeight;

	private boolean mIsDirty;

	private int popupGravity = Gravity.NO_GRAVITY;

	private OnItemOnClickListener mItemOnClickListener;
	private ListView mListView;
	

	private ArrayList<Menu_item> menulist = new ArrayList<Menu_item>();
	
	public TitlePopup(Context context){

		this(context, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}
	
	public TitlePopup(Context context, int width, int height){
		this.mContext = context;
		

		setFocusable(true);

		setTouchable(true);

		setOutsideTouchable(true);

		mScreenWidth = getScreenWidth(mContext);
		mScreenHeight = getScreenHeight(mContext);

		setWidth(width);
		setHeight(height);

		setBackgroundDrawable(new BitmapDrawable());
		
		setContentView(LayoutInflater.from(mContext).inflate(R.layout.title_popup, null));
		
		initUI();
	}
		
	/**
	 *
	 */
	private void initUI(){
		mListView = (ListView) getContentView().findViewById(R.id.title_list);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,long arg3) {
				//Toast.makeText(mContext,"you click "+index,Toast.LENGTH_SHORT).show();
				dismiss();
				
				if(mItemOnClickListener != null)
					mItemOnClickListener.onItemClick(menulist.get(index), index);
			}
		}); 
	}
	
	/**
	 *
	 */
	public void show(View view){
		view.getLocationOnScreen(mLocation);
		
		mRect.set(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(),mLocation[1] + view.getHeight());
		
		if(mIsDirty){
			populateActions();
		}
		
		showAtLocation(view, popupGravity, mScreenWidth - LIST_PADDING - (getWidth()/2), mRect.bottom);
	}
	
	/**
	 *
	 */
	private void populateActions(){
		mIsDirty = false;
		
		mListView.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder viewholder = null;
				Menu_item m = menulist.get(position);
				if (convertView == null) {
					viewholder = new ViewHolder();
					convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_item, null);
					viewholder.imageView = (ImageView) convertView
							.findViewById(R.id.img_memu_item);
					viewholder.textView = (TextView) convertView
							.findViewById(R.id.tv_menu_item);
					convertView.setTag(viewholder);
				} else {
					viewholder = (ViewHolder) convertView.getTag();
				}

				viewholder.textView.setText(m.getItem_name());
				viewholder.imageView.setImageResource(m.getItem_icon());
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				return menulist.get(position);
			}
			
			@Override
			public int getCount() {
				return menulist.size();
			}
		}) ;
	}
	class ViewHolder {
		ImageView imageView;
		TextView textView;
	}
	/**
	 *
	 */
	public void addAction(Menu_item action){
		if(action != null){
			menulist.add(action);
			mIsDirty = true;
		}
	}
	
	/**
	 *
	 */
	public void cleanAction(){
		if(menulist.isEmpty()){
			menulist.clear();
			mIsDirty = true;
		}
	}
	
	/**
	 *
	 */
	public Menu_item getAction(int position){
		if(position < 0 || position > menulist.size())
			return null;
		return menulist.get(position);
	}			
	
	/**
	 *
	 */
	public void setItemOnClickListener(OnItemOnClickListener onItemOnClickListener){
		this.mItemOnClickListener = onItemOnClickListener;
	}
	
	/**
	 * @author yangyu
	 *
	 */
	public static interface OnItemOnClickListener{
		public void onItemClick(Menu_item item, int position);
	}


	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * �õ��豸��Ļ�ĸ߶�
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
}
