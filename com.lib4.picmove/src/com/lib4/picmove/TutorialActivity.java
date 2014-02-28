package com.lib4.picmove;



import com.lib4.picmove.fragments.TutorialFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class TutorialActivity extends Activity{
	
	TutorialFragment mTutorialFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_layout);
		getActionBar().hide();
		loadTutorialFragment();
		
	}
	
	

	/**
	 * Load the Tutorial fragment
	 * 
	 */

	private void loadTutorialFragment() {

		mTutorialFragment = new TutorialFragment();
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mTutorialFragment,TutorialFragment.class.getName());

		// Commit the transaction
		fragmentTransaction.commit();

	}

}
