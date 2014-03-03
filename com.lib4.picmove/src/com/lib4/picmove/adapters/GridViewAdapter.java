package com.lib4.picmove.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.appbase.androidquery.AQuery;
import com.lib4.picmove.R;
import com.lib4.picmove.entity.ItemProperty;
import com.lib4.picmove.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	private Context mContext;

	File folder;
	int count = 0;
	File[] mFiles;
	private HashMap<String, ItemProperty> mItemsHashMap	=	new HashMap<String, ItemProperty>();
	private ArrayList< ItemProperty> mItemProperties	=	new ArrayList<ItemProperty>();
	private final int BIG_ITEMS = 1, DIASSEMBLY = 2, FITINELEVATOR = 3;

	public GridViewAdapter(Context c, int FLAG) {
		mContext = c;
		filterHashMap(FLAG);
	}

	private void filterHashMap(int FLAG) {

		
		if (Utils.Items != null) {

			Iterator mIterator = Utils.Items.keySet().iterator();
			while (mIterator.hasNext()) {
				String key = (String) mIterator.next();
				ItemProperty mItemProperty = Utils.Items.get(key);

				if (FLAG == BIG_ITEMS) {
					mItemProperties.add(mItemProperty);
					mItemsHashMap.put(key, mItemProperty);

				} else if (FLAG == DIASSEMBLY
						&& mItemProperty.requiresDiassembly) {
					mItemProperties.add(mItemProperty);
					mItemsHashMap.put(key, mItemProperty);
				} else if (FLAG == FITINELEVATOR && mItemProperty.fitInElevator) {
					mItemProperties.add(mItemProperty);
					mItemsHashMap.put(key, mItemProperty);
				}
				
				

			}
		}
		

	}

	public int getCount() {

		return mItemsHashMap.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = new ViewHolder();
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			rowView = inflater.inflate(R.layout.grid_item, null);
			viewHolder.itemImage = (ImageView) rowView
					.findViewById(R.id.itempic);
			rowView.setTag(viewHolder);
		}

		File mFile	=	new File(mItemProperties.get(position).path);
		viewHolder = (ViewHolder) rowView.getTag();
		AQuery aq = new AQuery(viewHolder.itemImage);
		aq.id(viewHolder.itemImage).image(mFile, 200);

		return rowView;
	}

	static class ViewHolder {

		public ImageView itemImage;
		public RelativeLayout ItemHolder;
		// public TextView group_name;

	}

}