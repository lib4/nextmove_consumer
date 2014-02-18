package com.lib4.picmove;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;

import com.lib4.picmove.fragments.ChatFragment;
import com.lib4.picmove.fragments.ProfileFragment;

public class ProfileActivity extends BaseActivity{

	ProfileFragment mProfileFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String title 	=	getIntent().getStringExtra("Title");
		if(title==null)
			title	=	"Profile";
			
		getActionBar().setTitle(title);
		loadChatFragment();
		initializeDrawer();
	
	}
	
	

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadChatFragment() {

		mProfileFragment = new ProfileFragment();
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mProfileFragment,ChatFragment.class.getName());

		// Commit the transaction
		fragmentTransaction.commit();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		hideSearchActionItem();
		return true;
	}
	
	public void onDestroy(){
		showSearchActionItem();
		super.onDestroy();
	}

}
