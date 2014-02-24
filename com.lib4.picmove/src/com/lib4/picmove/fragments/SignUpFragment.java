package com.lib4.picmove.fragments;

import java.util.HashMap;

import org.json.JSONObject;

import com.lib4.picmove.HomeActivity;
import com.lib4.picmove.R;
import com.lib4.picmove.SignUpActivity;
import com.lib4.picmove.httphandler.HTTPResponseListener;
import com.lib4.picmove.httphandler.HttpConstants;
import com.lib4.picmove.httphandler.HttpHandler;
import com.lib4.picmove.utils.Utils;

import android.app.AlertDialog;
import android.app.Fragment;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class SignUpFragment extends BaseFragment implements
		HTTPResponseListener {

	ScrollView mSignOutLayout;
	EditText usernameEdtTxt, passwordEdtTxt,retypePassword,nameEdtText,addessEdtText,phoneNumberEdtText;
	Button signInButton, signupButton;
	static ProgressDialog mDialog;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		mSignOutLayout = (ScrollView) inflater.inflate(
				R.layout.signup_fragment, container, false);
		init();

		return mSignOutLayout;
	}

	private void init() {

		usernameEdtTxt = (EditText) mSignOutLayout
				.findViewById(R.id.email_edtTxt);
		passwordEdtTxt = (EditText) mSignOutLayout
				.findViewById(R.id.password_edtTxt);
		retypePassword = (EditText) mSignOutLayout
				.findViewById(R.id.retype_password_edtTxt);
		nameEdtText = (EditText) mSignOutLayout
				.findViewById(R.id.name);
		phoneNumberEdtText = (EditText) mSignOutLayout
				.findViewById(R.id.phoneNumber);
		
		addessEdtText = (EditText) mSignOutLayout
				.findViewById(R.id.address);
		

	
		

		
		signupButton = (Button) mSignOutLayout.findViewById(R.id.signup_btn);
		signupButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				trgrSignOutWebService();
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
		Intent intent = new Intent(getActivity(), HomeActivity.class);
		startActivity(intent);
		getActivity().finish();
	}

	@Override
	public void onFailure(final int failureCode, final String message) {
		Log.e("On Failure", "On failure" + message);
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

	private void dismissDialoge() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();

	}

	/**
	 * TRIGGER SIGN UP WEB SERVICE CALL
	 */

	private void trgrSignOutWebService() {
		hideKeyboard();
		mDialog = new ProgressDialog(getActivity());
		mDialog.setMessage(getActivity().getString(R.string.signup));
		mDialog.setCancelable(false);
		mDialog.show();
		
		HashMap<String, String> requestMap	=	new HashMap<String, String>();
		requestMap.put(HttpConstants.EMAILADDRESS_JKEY,
				usernameEdtTxt.getText().toString());
		requestMap.put(HttpConstants.PASSWORD_JKEY,
				passwordEdtTxt.getText().toString());
		requestMap.put(HttpConstants.ADDRESS_JKEY,
				addessEdtText.getText().toString());
		requestMap.put(HttpConstants.PHONENUMBER_JKEY,
				phoneNumberEdtText.getText().toString());
		
		requestMap.put(HttpConstants.NAME_JKEY,
				nameEdtText.getText().toString());
		

		new HttpHandler().doSignUp(requestMap,getActivity(),
				SignUpFragment.this);

	}

	/**
	 * Method to hide the keyboard
	 */

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(usernameEdtTxt.getWindowToken(), 0);
	}

}
