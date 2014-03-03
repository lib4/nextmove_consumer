package com.lib4.picmove.fragments;




import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lib4.picmove.CreateNewMoveActivity;
import com.lib4.picmove.HomeActivity;
import com.lib4.picmove.R;
import com.lib4.picmove.SignInActivity;


public class DrawerFragment extends BaseFragment{

	
	ScrollView drawerLayout;
	LinearLayout my_profile,logout,moves,createmove;
	
	

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
		

		logout	=	(LinearLayout) drawerLayout.findViewById(R.id.logout);
		moves	=	(LinearLayout) drawerLayout.findViewById(R.id.moves);
		createmove	=	(LinearLayout) drawerLayout.findViewById(R.id.createmove);
		my_profile	=	(LinearLayout) drawerLayout.findViewById(R.id.profile);
		
	
		resolveHighLight();
		
		createmove.setOnClickListener(drawerEntryClick);
		moves.setOnClickListener(drawerEntryClick);
		my_profile.setOnClickListener(drawerEntryClick);
		logout.setOnClickListener(drawerEntryClick);
	}
	
	
	public void onResume(){
		super.onResume();
	}
	
	
	
	private void resolveHighLight(){
		disableHightLight();
		String callingActivityName = getActivity().getComponentName()
				.getClassName();
		String packageName = "com.lib4.picmove.";
		
		
		String title 	=	getActivity().getIntent().getStringExtra("Title");
		
		
		Log.e("TITLE "," "+title);
		if(title==null){
			logout.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
		}else if(title.compareToIgnoreCase("Moves")==0){	
			moves.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
			
		}else if(title.compareToIgnoreCase("New Move")==0){
			createmove.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
			
		}else if(title.compareToIgnoreCase("Profile")==0){
			my_profile.setBackgroundColor(getResources().getColor(
					R.color.grey_selector));
			
		}
			

	}
	
	public View.OnClickListener drawerEntryClick	=	new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			disableHightLight();
			if (v != null) {
				v.setBackgroundColor(getResources().getColor(R.color.grey_selector));

			}
			
			Intent intent = new Intent(getActivity(),
					HomeActivity.class);
			
			switch(v.getId()){
			
			case R.id.moves:
				
				
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("Title", "Moves");
				startActivity(intent);
				break;
				
			case R.id.createmove:
				
				
				intent = new Intent(getActivity(),
						CreateNewMoveActivity.class);
				intent.putExtra("Title", "New Move");
				startActivity(intent);
				break;
			case R.id.profile:
				
				
				
				intent.putExtra("Title", "Profile");
				startActivity(intent);
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
		moves.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		createmove.setBackgroundColor(getResources().getColor(
				R.color.white_tile_background_color));
		logout.setBackgroundColor(getResources().getColor(
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
