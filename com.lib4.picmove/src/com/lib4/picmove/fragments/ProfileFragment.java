package com.lib4.picmove.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.lib4.picmove.R;

public class ProfileFragment extends BaseFragment{

	LinearLayout profileLayout;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		profileLayout = (LinearLayout) inflater.inflate(
				R.layout.profile_fragment, container, false);
		
		return profileLayout;
	}
}
