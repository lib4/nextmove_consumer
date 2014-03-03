package com.lib4.picmove.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.lib4.picmove.R;
import com.lib4.picmove.adapters.GridViewAdapter;

public class FitInElevatorFragment extends BaseFragment{

	RelativeLayout requiresDiassembly;
	GridView mGridView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		requiresDiassembly = (RelativeLayout) inflater.inflate(
				R.layout.requires_diassembly, container, false);
		
		GridViewAdapter mGridViewAdapter	=	new GridViewAdapter(getActivity(),3);
		mGridView	=	(GridView) requiresDiassembly.findViewById(R.id.gridview);
		mGridView.setAdapter(mGridViewAdapter);
		
		return requiresDiassembly;
	}
}