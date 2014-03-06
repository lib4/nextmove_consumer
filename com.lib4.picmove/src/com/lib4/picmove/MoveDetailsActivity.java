package com.lib4.picmove;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;


import com.lib4.picmove.fragments.MoveDetailsFragment;
import com.lib4.picmove.utils.Utils;

public class MoveDetailsActivity extends BaseActivity {

	MoveDetailsFragment moveDetailsFragment; 

	
	String moveId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title 	=	getIntent().getStringExtra("Title");
		if(title==null)
			title	=	Utils.FITIN_ELEVATOR;
		moveId	=	getIntent().getStringExtra("MoveId");
		getActionBar().setTitle(title);
		MoveDetailsFragment();
		initializeDrawer();

	}
	
	
	
	
	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void MoveDetailsFragment() {

		moveDetailsFragment = new MoveDetailsFragment();
		moveDetailsFragment.setMoveId(moveId);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, moveDetailsFragment,MoveDetailsFragment.class.getName());

		// Commit the transaction
		fragmentTransaction.commit();

	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {


		// Take appropriate action for each action item click


		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;


		}
		return super.onOptionsItemSelected(item);


	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// Sync the toggle state after onRestoreInstanceState has occurred.
		savedInstanceState = new Bundle();
		savedInstanceState.putBoolean("MoveDetailsActivity", true);
		super.onPostCreate(savedInstanceState);
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	

}

