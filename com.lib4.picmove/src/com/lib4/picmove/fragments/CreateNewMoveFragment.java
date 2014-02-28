package com.lib4.picmove.fragments;

import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lib4.picmove.CapturePicturesActivity;
import com.lib4.picmove.HomeActivity;
import com.lib4.picmove.R;
import com.lib4.picmove.RequiresDiassemblyActivity;
import com.lib4.picmove.utils.Utils;

public class CreateNewMoveFragment extends BaseFragment{

	ScrollView createNewMoveLinearLayout;
	EditText smallBoxCount,mediumBoxCount,largeBoxCount,moveFrom,moveTo,comments;
	LinearLayout bigItems,requiresDisassembly,fitInElevator;
	
	
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
	
	
	private void init(){
		
		smallBoxCount	=	(EditText) createNewMoveLinearLayout.findViewById(R.id.smallboxcountEdtTxt);
		mediumBoxCount	=	(EditText) createNewMoveLinearLayout.findViewById(R.id.mediumboxCountEdtTxt);
		largeBoxCount	=	(EditText) createNewMoveLinearLayout.findViewById(R.id.largeboxCountEdtTxt);
		moveFrom		=	(EditText) createNewMoveLinearLayout.findViewById(R.id.moveFromEdtTxt);
		moveTo			=	(EditText) createNewMoveLinearLayout.findViewById(R.id.moveToEdtTxt);
		comments		=	(EditText) createNewMoveLinearLayout.findViewById(R.id.commentsEdtTxt);
		
		bigItems		=	(LinearLayout) createNewMoveLinearLayout.findViewById(R.id.bigItems);
		requiresDisassembly		=	(LinearLayout) createNewMoveLinearLayout.findViewById(R.id.disassembly);
		fitInElevator		=	(LinearLayout) createNewMoveLinearLayout.findViewById(R.id.fitinElevator);
		
		
		
		
		bigItems.setOnClickListener(clickListener);
		requiresDisassembly.setOnClickListener(clickListener);
		fitInElevator.setOnClickListener(clickListener);
	}
	
	
	
	OnClickListener clickListener	=	new OnClickListener() {
		Intent intent;
		@Override
		public void onClick(View v) {
		
			
			switch(v.getId()){
			
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
				
				
				
				break;
				
			}
			
		}
	};
}
