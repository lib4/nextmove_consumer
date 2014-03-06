package com.lib4.picmove.fragments;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lib4.customviews.CustomParentView;
import com.lib4.customviews.IndicatorView;
import com.lib4.picmove.R;
import com.lib4.picmove.SignInActivity;

public class TutorialFragment extends Fragment {

	RelativeLayout mRelativeLayout;
	CustomParentView mFrameLayout;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	IndicatorView mIndicatorView;
	private int CURRENT_SCREEN;
	private int TOTAL_NUM_SCREENS = 3;
	Handler mHandler = new Handler();
	private Button gotItButton;
	Timer mTimerTask;
	private int[] tutorialIds = new int[] { R.drawable.tutorial_1,
			R.drawable.tutorial_2, R.drawable.tutorial_3 };
	TextView mTutorialTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRelativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.tutorial_fragment, container, false);

		mFrameLayout = (CustomParentView) mRelativeLayout
				.findViewById(R.id.parentholder);
		gestureDetector = new GestureDetector(getActivity(),
				new CustomGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {

				return gestureDetector.onTouchEvent(event);
			}
		};

		mFrameLayout.setOnTouchListener(gestureListener);

		showTurialPicSlides();
		
		AddCustomView(mFrameLayout, 1, false);
		gotItButton = (Button) mRelativeLayout.findViewById(R.id.gotit_btn);
		gotItButton.setOnClickListener(gotiItBtnClick);
		mTutorialTextView	=	(TextView) mRelativeLayout.findViewById(R.id.tuttext);
		setTutorialText(1);
		 startAnimation(getActivity());
		return mRelativeLayout;
	}

	private void showTurialPicSlides() {

		CURRENT_SCREEN = 1;
		mIndicatorView = (IndicatorView) mRelativeLayout
				.findViewById(R.id.indicatorview);
		// mRelativeLayout.addView(mIndicatorView);
		mIndicatorView.setDrawables(R.drawable.tutorial_number_active,
				R.drawable.tutorial_bullets_bg,
				R.drawable.tutorial_number_inactive);
		mIndicatorView.setNumberofScreens(TOTAL_NUM_SCREENS);
		mIndicatorView.switchToScreen(CURRENT_SCREEN, CURRENT_SCREEN);

	}

	private void AddCustomView(CustomParentView mFrameLayout, int NextScreen,
			boolean Anim) {
		ImageView mCustomView = new ImageView(getActivity());
		mCustomView.setBackgroundResource(tutorialIds[NextScreen - 1]);
		if (!Anim)
			mFrameLayout.addViewNoAnim(mCustomView);
		else
			mFrameLayout.addView(mCustomView);
	}

	private void RemoveCustomView(CustomParentView mFrameLayout) {
		int childcount = mFrameLayout.getChildCount();
		Log.e("child Count", "Get child Count" + childcount);
		if (childcount > 0) {

			mFrameLayout.removeView(mFrameLayout.getChildAt(childcount - 1));
		} else {
			Toast.makeText(getActivity(), "No more views to remove!!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private OnClickListener gotiItBtnClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			// Calling the next Activity.
			Intent intent = new Intent(getActivity(), SignInActivity.class);
			startActivity(intent);
			getActivity().finish();
		}
	};

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
					// Toast.makeText(getActivity(), "Left Swipe",
					// Toast.LENGTH_SHORT).show();
					leftSwipe(false);

				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// Toast.makeText(getActivity(), "Right Swipe",
					// Toast.LENGTH_SHORT).show();
					rightSwipe();
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

	private void leftSwipe(boolean fromAutoSlider) {

		if(!fromAutoSlider)
			mTimerTask.cancel();
		
		Log.e("CURRENT SCREEN" + CURRENT_SCREEN, " NEXT SCREEN ");

		int currentScreen = CURRENT_SCREEN;
		int NextScreen = getScreenNumberIncrement();

		Log.e("CURRENT SCREEN" + CURRENT_SCREEN, " NEXT SCREEN " + NextScreen);
		mIndicatorView.switchToScreen(currentScreen, NextScreen);
		setTutorialText(NextScreen);
		if (currentScreen != NextScreen)
			AddCustomView(mFrameLayout, NextScreen, true);

	}

	private void rightSwipe() {
		mTimerTask.cancel();
		int currentScreen = CURRENT_SCREEN;
		int NextScreen = getScreenNumberDecrement();

		mIndicatorView.switchToScreen(currentScreen, NextScreen);
		setTutorialText(NextScreen);
		if (currentScreen != NextScreen)
			RemoveCustomView(mFrameLayout);
	}
	
	private void setTutorialText(int index){
		
		switch(index){
		case 1:
			mTutorialTextView.setText(getActivity().getResources().getString(R.string.tut1text));
			break;
		case 2:
			mTutorialTextView.setText(getActivity().getResources().getString(R.string.tut2text));
			break;
		case 3:
			mTutorialTextView.setText(getActivity().getResources().getString(R.string.tut3text));
			break;
		}
	}

	private int getScreenNumberIncrement() {
		if (CURRENT_SCREEN == TOTAL_NUM_SCREENS) {
			return CURRENT_SCREEN;
		}
		if (CURRENT_SCREEN < TOTAL_NUM_SCREENS)
			CURRENT_SCREEN++;

		return CURRENT_SCREEN;

	}

	private int getScreenNumberDecrement() {

		if (CURRENT_SCREEN == 1) {
			CURRENT_SCREEN = 1;
			return CURRENT_SCREEN;
		}
		if (CURRENT_SCREEN > 1)
			CURRENT_SCREEN--;

		return CURRENT_SCREEN;

	}

	private void startAnimation(Context context) {

		mTimerTask = new Timer();
		mTimerTask.schedule(new UpdateTimeTask(), 2000, 3000);
	}

	final class UpdateTimeTask extends TimerTask {
		public void run() {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					leftSwipe(true);

				}
			});
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask.purge();
		}
		Log.e("Paused", "Paused");

	}

	@Override
	public void onStop() {
		super.onStop();
		Log.e("Stop", "Stop");

	}

}
