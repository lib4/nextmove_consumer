package com.lib4.picmove;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.lib4.picmove.fragments.CapturePictureFragment;

public class CapturePicturesActivity extends BaseActivity{
	
	
	CapturePictureFragment mCapturePictureFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loadCapturePictureFragment();
		initializeDrawer();
	}
	
	

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadCapturePictureFragment() {

		mCapturePictureFragment = new CapturePictureFragment();
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mCapturePictureFragment,CapturePictureFragment.class.getName());

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
		savedInstanceState.putBoolean("CapturePicturesActivity", true);
		super.onPostCreate(savedInstanceState);
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


}
