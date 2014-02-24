package com.lib4.picmove.fragments;

import java.util.HashMap;
import java.util.Iterator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import com.lib4.picmove.R;
import com.lib4.picmove.customui.PinterestUI;
import com.lib4.picmove.entity.User;
import com.lib4.picmove.utils.Utils;

public class OnlineUsersFragment extends BaseFragment{

	LinearLayout onlineUsersLayout;
	PinterestUI mPinterestUI;
	HashMap<Integer, User> userLists;
	ScrollView mScrollView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		onlineUsersLayout = (LinearLayout) inflater.inflate(
				R.layout.onlineusers_fragment, container, false);
		
		userLists	=	Utils.getUserList();
		mPinterestUI	=	new PinterestUI(getActivity(),Utils.getUserList());
		
		mPinterestUI.createLayout();
		mScrollView	=new ScrollView(getActivity());
		mScrollView.addView(mPinterestUI);
		onlineUsersLayout.addView(mScrollView);
		
		return onlineUsersLayout;
	}
	
	
	
	public void trgrSearch(String searchString){
		
			HashMap<Integer, User> searchResult	=	new HashMap<Integer, User>();
			Iterator mIterator	=	(Iterator) userLists.keySet().iterator();
			
			int i =0;
			while(mIterator.hasNext()){
				int key 	=	(Integer) mIterator.next();
				User mUser	=	userLists.get(key);
				
				String name =	mUser.name.toLowerCase();
				
				if(name.contains(searchString.toLowerCase())){
					searchResult.put(i, mUser);
					i++;
				}
			}
			
			if(mPinterestUI!=null){
				mScrollView.removeAllViews();
			}
			mPinterestUI	=	new PinterestUI(getActivity(),searchResult);
			mPinterestUI.createLayout();
			mScrollView.addView(mPinterestUI);
		
	}
	
	public void resetView(){
		HashMap<Integer, User> storedHashMap	=	mPinterestUI.data;
		if(mPinterestUI!=null){
			mScrollView.removeAllViews();
		}
		mPinterestUI	=	new PinterestUI(getActivity(),storedHashMap);
		mPinterestUI.createLayout();
		mScrollView.addView(mPinterestUI);
	}
	
}
