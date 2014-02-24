package com.lib4.picmove;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.lib4.picmove.fragments.SignUpFragment;

public class SignUpActivity extends Activity{

	
	SignUpFragment mSignUpFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_layout);
		loadSignUpFragment();
		
	}
	
	

	/**
	 * Load the SignUp fragment
	 * 
	 */

	private void loadSignUpFragment() {

		mSignUpFragment = new SignUpFragment();
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mSignUpFragment,SignUpFragment.class.getName());

		// Commit the transaction
		fragmentTransaction.commit();

	}
	
}
