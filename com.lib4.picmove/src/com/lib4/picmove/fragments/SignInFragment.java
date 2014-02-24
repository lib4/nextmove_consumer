package com.lib4.picmove.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lib4.picmove.HomeActivity;
import com.lib4.picmove.R;
import com.lib4.picmove.SignUpActivity;
import com.lib4.picmove.httphandler.HTTPResponseListener;
import com.lib4.picmove.httphandler.HttpHandler;
import com.lib4.picmove.utils.Utils;

public class SignInFragment extends BaseFragment implements
		HTTPResponseListener {

	LinearLayout mSignInLayout;
	EditText usernameEdtTxt, passwordEdtTxt;
	Button signInButton,signupButton;
	static ProgressDialog mDialog;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		mSignInLayout = (LinearLayout) inflater.inflate(
				R.layout.signin_fragment, container, false);
		init();

		return mSignInLayout;
	}

	private void init() {

		usernameEdtTxt = (EditText) mSignInLayout
				.findViewById(R.id.email_edtTxt);
		passwordEdtTxt = (EditText) mSignInLayout
				.findViewById(R.id.password_edtTxt);

		usernameEdtTxt.setText("anaschaky@yahoo.co.in");
		passwordEdtTxt.setText("12345678");

		signInButton = (Button) mSignInLayout.findViewById(R.id.signin_btn);
		signupButton =	(Button) mSignInLayout.findViewById(R.id.signup_btn);
		signInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				trgrSignInWebService();
			}
		});
		
		signupButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						SignUpActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});

	}

	private void showAlertDialog(String messgae) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set title
		// alertDialogBuilder.setTitle("Your Title");

		// set dialog message
		alertDialogBuilder.setMessage(messgae).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// current activity

					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	@Override
	public void onSuccess() {
		dismissDialoge();
		// Calling the next Activity.
		Intent intent = new Intent(getActivity(),
				HomeActivity.class);
		startActivity(intent);
		getActivity().finish();
	}

	@Override
	public void onFailure(final int failureCode, final String message) {
		Log.e("On Failure","On failure"+message);
		dismissDialoge();
		final Handler mHandler = new Handler(Looper.getMainLooper()) {


			public void handleMessage(Message msg) {
				if (failureCode == HttpHandler.NO_NETWORK_CODE) {


					Utils.showNoNetworkAlertDialog(getActivity());
				} else {
					showAlertDialog(message);


				}
			}
		};
		
		
		
		mHandler.sendEmptyMessage(1);

	}
	
	
	/**
	 * Disimiss Dialog
	 */
	
	private void dismissDialoge(){
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();

	}

	/**
	 * TRIGGER SIGN IN WEB SERVICE CALL
	 */


	private void trgrSignInWebService() {
		hideKeyboard();
		mDialog = new ProgressDialog(getActivity());
		mDialog.setMessage(getActivity().getString(R.string.signin));
		mDialog.setCancelable(false);
		mDialog.show();
		new HttpHandler().doSignIn(usernameEdtTxt.getText().toString(),
				passwordEdtTxt.getText().toString(), getActivity(),
				SignInFragment.this);
		
	}
	/**
	 * Method to hide the keyboard
	 */


	private void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(usernameEdtTxt.getWindowToken(), 0);
	}

	

}