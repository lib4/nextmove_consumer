package com.lib4.picmove.fragments;

import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lib4.picmove.CapturePicturesActivity;
import com.lib4.picmove.FitInElevatorActivity;
import com.lib4.picmove.R;
import com.lib4.picmove.RequiresDiassemblyActivity;
import com.lib4.picmove.entity.ItemProperty;
import com.lib4.picmove.httphandler.HTTPResponseListener;
import com.lib4.picmove.httphandler.HttpConstants;
import com.lib4.picmove.httphandler.HttpHandler;
import com.lib4.picmove.utils.Utils;

public class CreateNewMoveFragment extends BaseFragment implements HTTPResponseListener{

	ScrollView createNewMoveLinearLayout;
	EditText smallBoxCount, mediumBoxCount, largeBoxCount, moveFrom, moveTo,
			comments;
	LinearLayout bigItems, requiresDisassembly, fitInElevator;
	
	private int bigitemCount, requiresDiassemblyCount, fitInelevatorCount;
	
	Button getQuotesBtn;
	
	static ProgressDialog mDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		createNewMoveLinearLayout = (ScrollView) inflater.inflate(
				R.layout.create_new_move_fragment, container, false);

		init();
		Utils.CURRENT_ACTIVE_FOLDER = UUID.randomUUID().toString();
		return createNewMoveLinearLayout;
	}

	private void init() {

		smallBoxCount = (EditText) createNewMoveLinearLayout
				.findViewById(R.id.smallboxcountEdtTxt);
		mediumBoxCount = (EditText) createNewMoveLinearLayout
				.findViewById(R.id.mediumboxCountEdtTxt);
		largeBoxCount = (EditText) createNewMoveLinearLayout
				.findViewById(R.id.largeboxCountEdtTxt);
		moveFrom = (EditText) createNewMoveLinearLayout
				.findViewById(R.id.moveFromEdtTxt);
		moveTo = (EditText) createNewMoveLinearLayout
				.findViewById(R.id.moveToEdtTxt);
		comments = (EditText) createNewMoveLinearLayout
				.findViewById(R.id.commentsEdtTxt);
		getQuotesBtn	=	(Button) createNewMoveLinearLayout.findViewById(R.id.getquote_btn);
		

		bigItems = (LinearLayout) createNewMoveLinearLayout
				.findViewById(R.id.bigItems);
		requiresDisassembly = (LinearLayout) createNewMoveLinearLayout
				.findViewById(R.id.disassembly);
		fitInElevator = (LinearLayout) createNewMoveLinearLayout
				.findViewById(R.id.fitinElevator);

		bigItems.setOnClickListener(clickListener);
		requiresDisassembly.setOnClickListener(clickListener);
		fitInElevator.setOnClickListener(clickListener);

		getQuotesBtn.setOnClickListener(clickListener);
		
		
		
		
	}
	
	


	@Override
	public void onResume(){
		super.onResume();
		updateCounts();
		
	}
	
	private void updateCounts(){
		
		bigitemCount	=	0;requiresDiassemblyCount=0; fitInelevatorCount=0;
		if (Utils.Items != null) {

			Iterator mIterator = Utils.Items.keySet().iterator();
			while (mIterator.hasNext()) {
				String key = (String) mIterator.next();
				ItemProperty mItemProperty	=	Utils.Items.get(key);
				
				if (mItemProperty.fitInElevator) {
					fitInelevatorCount++;

				}
				if (mItemProperty.requiresDiassembly) {
					requiresDiassemblyCount++;
				}
				
				bigitemCount++;
			}

		}
		
		TextView bigItemCount_txtView	=	(TextView) createNewMoveLinearLayout.findViewById(R.id.bigItemsCount);
		TextView requiresDiassembly_txtView	=	(TextView) createNewMoveLinearLayout.findViewById(R.id.requiresDiassemblyCount);
		TextView fitInElevator_txt_View	=	(TextView) createNewMoveLinearLayout.findViewById(R.id.fitInElevatorCount);
		
		
		
		bigItemCount_txtView.setText(""+bigitemCount);
		requiresDiassembly_txtView.setText(""+requiresDiassemblyCount);
		fitInElevator_txt_View.setText(""+fitInelevatorCount);
	}
	

	OnClickListener clickListener = new OnClickListener() {
		Intent intent;

		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.bigItems:
				intent = new Intent(getActivity(),
						CapturePicturesActivity.class);
				startActivity(intent);
				break;
			case R.id.disassembly:
				intent = new Intent(getActivity(),
						RequiresDiassemblyActivity.class);
				startActivity(intent);

				break;
			case R.id.fitinElevator:
				intent = new Intent(getActivity(),
						FitInElevatorActivity.class);
				startActivity(intent);

				break;
				
			case R.id.getquote_btn:
				
				trgrGeQuotes();
				break;

			}

		}
	};
	
	
	private void trgrGeQuotes(){
		mDialog = new ProgressDialog(getActivity());
		mDialog.setMessage(getActivity().getString(R.string.sendingmoveinfo));
		mDialog.setCancelable(false);
		mDialog.show();
		
		
		JSONObject createMoveReqObject 	=	new JSONObject();
		
		try{
		createMoveReqObject.put(HttpConstants.LARGEBOX_COUNT_JKEY, Integer.parseInt(largeBoxCount.getText().toString()));
		createMoveReqObject.put(HttpConstants.EXPECTED_RECEIVEDDATE_JKEY,
				""+Calendar.getInstance().getTime());
		createMoveReqObject.put(HttpConstants.DESTINATION_ADDESS_JKEY,moveTo.getText().toString()
				);
		createMoveReqObject.put(HttpConstants.DISPATCH_DATE_JKEY,
				""+Calendar.getInstance().getTime());
		createMoveReqObject.put(HttpConstants.USERID_JKEY,
				Utils.userId);
		createMoveReqObject.put(HttpConstants.SOURCEADDRESS_JKEY, moveFrom.getText().toString());
		createMoveReqObject.put(HttpConstants.SMALLBOX_COUNT_JKEY, Integer.parseInt(smallBoxCount.getText().toString()));
		createMoveReqObject.put(HttpConstants.MEDIUMBOX_COUNT_JKEY, Integer.parseInt(mediumBoxCount.getText().toString()));

		JSONArray itemArray = new JSONArray();
		
		if (Utils.Items != null) {

			Iterator mIterator = Utils.Items.keySet().iterator();
			while (mIterator.hasNext()) {
				String key = (String) mIterator.next();
				ItemProperty mItemProperty	=	Utils.Items.get(key);
				
				JSONObject itemObject = new JSONObject();
				itemObject.put(HttpConstants.ITEM_DESCRIPTION_JKEY,
						"Item is too old. Need extra care");
				itemObject.put(HttpConstants.ITEMNAME_JKEY, "Dining Table");
				
		
				StringWriter writer = new StringWriter();
				IOUtils.copy(new FileInputStream(mItemProperty.path), writer);
				String theString = writer.toString();
				theString	=	Base64.encodeToString(theString.getBytes(), Base64.DEFAULT);
				
				
				
				
				//itemObject
				//.put(HttpConstants.ITEMIMAGE_JKEY,theString);
		
				itemObject.put(HttpConstants.DISASSEMBLY_JKEY, mItemProperty.requiresDiassembly);
				itemObject.put(HttpConstants.FITIN_ELEVATOR_JKEY, mItemProperty.fitInElevator);
				itemArray.put(itemObject);
				
				
			}

		}

		createMoveReqObject.put(HttpConstants.BIGITEMS_JKEY, itemArray);
		}catch(Exception e){
			e.printStackTrace();
		}

		new HttpHandler().createMove(getActivity(), this, createMoveReqObject);
	}
	
	/**
	 * Disimiss Dialog
	 */

	private void dismissDialoge() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();

	}


	@Override
	public void onSuccess(final String message) {
	
		dismissDialoge();

		final Handler mHandler = new Handler(Looper.getMainLooper()) {

			public void handleMessage(Message msg) {
				Toast.makeText(getActivity(), message, 1000).show();
				getActivity().finish();
			}
		};
		mHandler.sendEmptyMessage(1);
		
		
	}

	@Override
	public void onFailure(int failureCode, String message) {
		// TODO Auto-generated method stub
		
	}
}
