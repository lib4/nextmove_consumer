package com.lib4.picmove;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.lib4.picmove.fragments.ChatFragment;
import com.lib4.picmove.fragments.CreateNewMoveFragment;
import com.lib4.picmove.utils.Utils;

public class CreateNewMoveActivity extends BaseActivity{

	CreateNewMoveFragment mCreateNewMoveFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title 	=	getIntent().getStringExtra("Title");
		if(title==null)
			title	=	Utils.MOVES_HEADER;
		getActionBar().setTitle(title);
		loadCreateNewMoveFragment();
		initializeDrawer();
	}
	
	

	/**
	 * Load the SignIn fragment
	 * 
	 */

	private void loadCreateNewMoveFragment() {

		mCreateNewMoveFragment = new CreateNewMoveFragment();
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit,
		// R.anim.pop_enter, R.anim.pop_exit);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		fragmentTransaction.replace(R.id.fragment_holder, mCreateNewMoveFragment,ChatFragment.class.getName());

		// Commit the transaction
		fragmentTransaction.commit();

	}

}
