package com.lib4.picmove;

import com.lib4.picmove.datastorage.DBManager;
import com.lib4.picmove.httphandler.HttpHandler;
import com.lib4.picmove.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();

		/**
		 * DB Creation
		 */
		new DBManager(this).open();
		new DBManager(this).close();

		/**
		 * 
		 * Do All Api
		 */

		//HttpHandler mSignUpHandler = new HttpHandler();
		//mSignUpHandler.doSignUp(this, null);
		//HttpHandler mSignInHandler = new HttpHandler();
		//mSignInHandler.doSignIn("anaschaky@gmail.com", "12345678", this, null);
		//HttpHandler getMyMoves = new HttpHandler();
		//getMyMoves.getMyMoves(this, null);
		//HttpHandler createMoveHandler = new HttpHandler();
		//createMoveHandler.createMove(this, null);
		//HttpHandler mUpdateProfileHandler = new HttpHandler();
		//mUpdateProfileHandler.updateProfile();

		Utils.IS_TABLET = false;

		if (!Utils.IS_TABLET) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else {

			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

		setContentView(R.layout.splash);
		swapTutorialActivity();

	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void swapToSignInActivity() {

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Calling the next Activity.
				Intent intent = new Intent(SplashActivity.this,
						SignInActivity.class);
				startActivity(intent);
				finish();

			}

		}, 1500);

	}

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void swapTutorialActivity() {

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Calling the next Activity.
				Intent intent = new Intent(SplashActivity.this,
						TutorialActivity.class);
				startActivity(intent);
				finish();

			}

		}, 1500);

	}

}
