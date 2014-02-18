package com.lib4.picmove.fragments;

import java.util.Random;

import com.lib4.customviews.CustomParentView;
import com.lib4.customviews.IndicatorView;
import com.lib4.picmove.R;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TutorialFragment extends Fragment {

	RelativeLayout mRelativeLayout;
	CustomParentView mFrameLayout;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	IndicatorView	mIndicatorView;
	 private int CURRENT_SCREEN;
	 private int TOTAL_NUM_SCREENS	=	3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRelativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.tutorial_fragment, container, false);

		mFrameLayout	=	(CustomParentView) mRelativeLayout.findViewById(R.id.parentholder);
		gestureDetector = new GestureDetector(getActivity(),
				new CustomGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {

				return gestureDetector.onTouchEvent(event);
			}
		};

		mFrameLayout.setOnTouchListener(gestureListener);
		CURRENT_SCREEN	=	1;
		
		mIndicatorView = (IndicatorView) mRelativeLayout.findViewById(R.id.indicatorview);
		//mRelativeLayout.addView(mIndicatorView);
		mIndicatorView.setDrawables(R.drawable.tutorial_number_active,
			R.drawable.tutorial_bullets_bg, R.drawable.tutorial_number_inactive);
		mIndicatorView.setNumberofScreens(TOTAL_NUM_SCREENS);
		mIndicatorView.switchToScreen(CURRENT_SCREEN, CURRENT_SCREEN);
		return mRelativeLayout;
	}

	private void AddCustomView(FrameLayout mFrameLayout) {
		View mCustomView = new View(getActivity());
		Random rnd = new Random();
		int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),
				rnd.nextInt(256));
		mCustomView.setBackgroundColor(color);
		mFrameLayout.addView(mCustomView);
	}

	private void RemoveCustomView(FrameLayout mFrameLayout) {
		int childcount = mFrameLayout.getChildCount();
		Log.e("child Count", "Get child Count" + childcount);
		if (childcount > 0)
			mFrameLayout.removeView(mFrameLayout.getChildAt(childcount - 1));
		else {
			Toast.makeText(getActivity(), "No more views to remove!!",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 
	 * 
	 * @author Anas Abubacker Right Left Gesture Detection
	 * 
	 */
	class CustomGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {

				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					Toast.makeText(getActivity(), "Left Swipe",
							Toast.LENGTH_SHORT).show();
					AddCustomView(mFrameLayout);
					mIndicatorView.switchToScreen(CURRENT_SCREEN,
						    getScreenNumberIncrement());
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					Toast.makeText(getActivity(), "Right Swipe",
							Toast.LENGTH_SHORT).show();
					RemoveCustomView(mFrameLayout);
					 mIndicatorView.switchToScreen(CURRENT_SCREEN,
							    getScreenNumberDecrement());
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}

		@Override
		public boolean onDown(MotionEvent event) {

			return true;
		}

	}
	
	 private int getScreenNumberIncrement(){
			if(CURRENT_SCREEN==TOTAL_NUM_SCREENS){
			    CURRENT_SCREEN	=	TOTAL_NUM_SCREENS;
			    return CURRENT_SCREEN;
			}
			if(CURRENT_SCREEN<TOTAL_NUM_SCREENS)
			    CURRENT_SCREEN++;
			
			return CURRENT_SCREEN;
			
		    }
		    
		    private int getScreenNumberDecrement(){
			
			if(CURRENT_SCREEN==1){
			    CURRENT_SCREEN	=	1;
			    return CURRENT_SCREEN;
			}
			if(CURRENT_SCREEN>1)
			    CURRENT_SCREEN--;
			
			return CURRENT_SCREEN;
			
		    }


}
