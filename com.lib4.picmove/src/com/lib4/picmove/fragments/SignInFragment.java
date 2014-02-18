package com.lib4.picmove.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lib4.picmove.OnlineUsersTileViewActivity;
import com.lib4.picmove.R;
import com.lib4.picmove.utils.Utils;

public class SignInFragment extends BaseFragment {

	LinearLayout mSignInLayout;
	EditText usernameEdtTxt, passwordEdtTxt;
	Button signInButton;

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
		signInButton = (Button) mSignInLayout.findViewById(R.id.signin_btn);

		signInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (usernameEdtTxt.getText().toString()
						.compareTo(Utils.USERNAME) == 0
						&& passwordEdtTxt.getText().toString()
								.compareTo(Utils.PASSWORD) == 0) {

					// Calling the next Activity.
					Intent intent = new Intent(getActivity(),
							OnlineUsersTileViewActivity.class);
					startActivity(intent);
					getActivity().finish();

				} else {

					showAlertDialog(getActivity().getString(
							R.string.uname_not_matching));
				}

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

}