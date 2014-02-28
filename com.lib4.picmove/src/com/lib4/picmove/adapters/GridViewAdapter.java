package com.lib4.picmove.adapters;

import java.io.File;

import com.appbase.androidquery.AQuery;
import com.lib4.picmove.utils.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    
    File folder;
    int count 	=0;
    File[] mFiles;
    
    public GridViewAdapter(Context c) {
        mContext = c;
        folder	=	new File(mContext.getFilesDir()+"/"+Utils.CURRENT_ACTIVE_FOLDER);
        
        if(folder.exists()&&folder.isDirectory()){
        	count 	=	folder.list().length;	
        	mFiles	=	folder.listFiles();
        }
    }

    public int getCount() {
    	
    	
    	
        return count;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        AQuery aq = new AQuery(imageView);
		
		aq.id(imageView).image(mFiles[position],100);

        
        return imageView;
    }


}