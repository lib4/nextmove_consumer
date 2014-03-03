package com.lib4.picmove.fragments;

import java.util.HashMap;
import java.util.Iterator;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import com.lib4.picmove.R;
import com.lib4.picmove.customui.PinterestUI;
import com.lib4.picmove.datastorage.DBManager;
import com.lib4.picmove.entity.ItemProperty;
import com.lib4.picmove.httphandler.HTTPResponseListener;
import com.lib4.picmove.httphandler.HttpHandler;
import com.lib4.picmove.utils.Utils;

public class HomeFragment extends BaseFragment implements HTTPResponseListener{

	LinearLayout onlineUsersLayout;
	PinterestUI mPinterestUI;
	ScrollView mScrollView;
	static ProgressDialog mDialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		onlineUsersLayout = (LinearLayout) inflater.inflate(
				R.layout.onlineusers_fragment, container, false);
		//trgrGetmoves();
		Cursor mCursor	=	new DBManager(getActivity()).fetchMoves();
		
		if(mCursor!=null&&mCursor.getCount()>0){
				mPinterestUI	=	new PinterestUI(getActivity(),mCursor);
				mPinterestUI.createLayout();
				mScrollView	=new ScrollView(getActivity());
				mScrollView.addView(mPinterestUI);
				onlineUsersLayout.addView(mScrollView);
		}else{
			
			
			
		}
		return onlineUsersLayout;
	}
	
	
	
	
	
	public void resetView(){
		
		if(mPinterestUI!=null){
			mScrollView.removeAllViews();
		}
		Cursor mCursor	=	new DBManager(getActivity()).fetchMoves();
		if(mCursor!=null&&mCursor.getCount()>0){
		
		mPinterestUI	=	new PinterestUI(getActivity(),mCursor);
		mPinterestUI.createLayout();
		mScrollView.addView(mPinterestUI);
		
		}
	}
	
	public void trgrGetmoves(){
		mDialog = new ProgressDialog(getActivity());
		mDialog.setMessage(getActivity().getString(R.string.signin));
		mDialog.setCancelable(false);
		mDialog.show();
		new HttpHandler().getMyMoves(getActivity(), this);
		
		
		
	}




	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		dismissDialoge();
		
		final Handler mHandler = new Handler(Looper.getMainLooper()) {


			public void handleMessage(Message msg) {
				resetView();
			}
		};
		mHandler.sendEmptyMessage(1);
		
		
	}





	@Override
	public void onFailure(final int failureCode,final String message) {
		
		dismissDialoge();
		final Handler mHandler = new Handler(Looper.getMainLooper()) {


			public void handleMessage(Message msg) {
				if (failureCode == HttpHandler.NO_NETWORK_CODE) {


					Utils.showNoNetworkAlertDialog(getActivity());
				} else {
					showAlertDialog(message);


				}
			}
		};
		
		
		
		mHandler.sendEmptyMessage(1);
	}
	
	/**
	 * Disimiss Dialog
	 */
	
	private void dismissDialoge(){
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();

	}
	

	private void showAlertDialog(String messgae) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set title
		// alertDialogBuilder.setTitle("Your Title");

		// set dialog message
		alertDialogBuilder.setMessage(messgae).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// current activity

					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}


}
