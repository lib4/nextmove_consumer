package com.lib4.picmove.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.lib4.picmove.R;

public class MoveDetailsFragment extends BaseFragment{

	ScrollView movesDetails;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		movesDetails = (ScrollView) inflater.inflate(
				R.layout.move_details_fragment, container, false);
		
		
		return movesDetails;
	}
	
}