package com.lib4.picmove.adapters;



import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lib4.picmove.R;

public class ChatAdapter extends BaseAdapter{

	
	Activity mContext;
	public ChatAdapter(Activity mContext) {
		this.mContext	=	mContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			rowView = inflater.inflate(R.layout.chat_items, null);
			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.chatmessage = (TextView) rowView
					.findViewById(R.id.chat);
			
			viewHolder.leftImage = (ImageView) rowView
					.findViewById(R.id.leftImage);
			viewHolder.rightImage = (ImageView) rowView
					.findViewById(R.id.rightImage);
			
			rowView.setTag(viewHolder);
			
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		if(position%2==0){
			holder.leftImage.setVisibility(View.GONE);
			holder.rightImage.setVisibility(View.VISIBLE);
			holder.chatmessage.setGravity(Gravity.RIGHT);
		}else{
			holder.leftImage.setVisibility(View.VISIBLE);
			holder.rightImage.setVisibility(View.GONE);
			holder.chatmessage.setGravity(Gravity.LEFT);
		}
		
		
		
		return rowView;
	}
	
	
	static class ViewHolder {
		public TextView chatmessage;
		public ImageView leftImage;
		public ImageView rightImage;

	}
	
}
