package com.lib4.picmove.fragments;



import com.lib4.picmove.OnlineUsersTileViewActivity;
import com.lib4.picmove.ProfileActivity;
import com.lib4.picmove.R;
import com.lib4.picmove.SignInActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;


public class DrawerFragment extends BaseFragment{

	
	ScrollView drawerLayout;
	LinearLayout my_profile,photos,friends,logout,topusers,newusers,onlineusers,memberlookup,messages;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		drawerLayout = (ScrollView) inflater.inflate(
				R.layout.drawer_fragment, container, false);
		init();
		return drawerLayout;
	}
	
	
	private void init(){
		
		my_profile	=	(LinearLayout) drawerLayout.findViewById(R.id.my_profile);
		photos	=	(LinearLayout) drawerLayout.findViewById(R.id.photos);
		friends	=	(LinearLayout) drawerLayout.findViewById(R.id.friends);
		logout	=	(LinearLayout) drawerLayout.findViewById(R.id.logout);
		topusers	=	(LinearLayout) drawerLayout.findViewById(R.id.topusers);
		newusers	=	(LinearLayout) drawerLayout.findViewById(R.id.newusers);
		onlineusers	=	(LinearLayout) drawerLayout.findViewById(R.id.onlineusers);
		memberlookup	=	(LinearLayout) drawerLayout.findViewById(R.id.memberlookup);
	
		resolveHighLight();
		
		
		topusers.setOnClickListener(drawerEntryClick);
		onlineusers.setOnClickListener(drawerEntryClick);
		newusers.setOnClickListener(drawerEntryClick);
		memberlookup.setOnClickListener(drawerEntryClick);
		
		my_profile.setOnClickListener(drawerEntryClick);
		
		friends.setOnClickListener(drawerEntryClick);
		photos.setOnClickListener(drawerEntryClick);
		logout.setOnClickListener(drawerEntryClick);
	}
	
	
	
	
	
	private void resolveHighLight(){
		disableHightLight();
		String callingActivityName = getActivity().getComponentName()
				.getClassName();
		String packageName = "com.lib4.themeonlinebuddies.";
		
		
		String title 	=	getActivity().getIntent().getStringExtra("Title");
		if(title==null){
			topusers.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
		}else if(title.compareToIgnoreCase("New Users")==0){	
			newusers.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
			
		}else if(title.compareToIgnoreCase("Online Users")==0){
			onlineusers.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
			
		}else if(title.compareToIgnoreCase("Member LookUp")==0){
			memberlookup.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
			
		}else if(title.compareToIgnoreCase("MyProfile")==0){
			my_profile.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
			
		}
			
		/*
		if (callingActivityName.equalsIgnoreCase(packageName
				+ "OnlineUsersTileViewActivity")) {
			topusers.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
		} */
	}
	
	public View.OnClickListener drawerEntryClick	=	new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			disableHightLight();
			if (v != null) {
				v.setBackgroundColor(getResources().getColor(R.color.grey_selector));

			}
			
			Intent intent = new Intent(getActivity(),
					OnlineUsersTileViewActivity.class);
			
			switch(v.getId()){
			
			case R.id.topusers:
				
				
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("Title", "Top Users");
				startActivity(intent);
				break;
				
			case R.id.newusers:
				
				
				
				intent.putExtra("Title", "New Users");
				startActivity(intent);
				break;
			case R.id.onlineusers:
				
				
				
				intent.putExtra("Title", "Online Users");
				startActivity(intent);
				break;
				
			case R.id.memberlookup:
				
				intent.putExtra("Title", "Member LookUp");
				startActivity(intent);
				break;
				
			case R.id.my_profile:
				
				Intent myProfileIntent = new Intent(getActivity(),
						ProfileActivity.class);
				myProfileIntent.putExtra("Title", "My Profile");
				startActivity(myProfileIntent);
				break;
				
			case R.id.photos:
			case R.id.friends:
				Toast.makeText(getActivity(), "Not implemented for POC!", 1000).show();
				break;
			case R.id.logout:
				signOutAlert();
				break;
			}
			
		}
	};
	
	private void disableHightLight(){
		
		
		
		
		my_profile.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		photos.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		friends.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		logout.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		topusers.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		onlineusers.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		newusers.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		memberlookup.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		
	}
	
	
	
	private void signOutAlert() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set dialog message
		alertDialogBuilder
				.setMessage(this.getString(R.string.logut_alert))
				.setCancelable(true)
				.setTitle(this.getString(R.string.app_name))
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity

							}
						})

				.setPositiveButton("Logout",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity

								Intent intent = new Intent(getActivity(),
										SignInActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								intent.putExtra("EXIT", true);
								startActivity(intent);
								getActivity().finish();

							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	
}
