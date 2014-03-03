package com.lib4.picmove.fragments;

import java.util.Calendar;
import java.util.Iterator;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
				"38bdceac-1289-4b2c-95b7-9e7572c4dc6c");
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
				itemObject
						.put(HttpConstants.ITEMIMAGE_JKEY,
								"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");
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

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(int failureCode, String message) {
		// TODO Auto-generated method stub
		
	}
}
