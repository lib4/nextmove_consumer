package com.lib4.picmove.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lib4.picmove.R;

public class CreateNewMoveFragment extends BaseFragment{

	LinearLayout createNewMoveLinearLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		createNewMoveLinearLayout = (LinearLayout) inflater.inflate(
				R.layout.create_new_move_fragment, container, false);
		
		//ChatAdapter mChatAdapter	=	new ChatAdapter(getActivity());
		//ListView chatList	=	(ListView) createNewMoveLinearLayout.findViewById(R.id.listchat);
		//chatList.setAdapter(mChatAdapter);
		
		return createNewMoveLinearLayout;
	}
	
}
