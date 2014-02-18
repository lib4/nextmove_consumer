package com.lib4.picmove.customui;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.lib4.picmove.ChatActivity;
import com.lib4.picmove.ProfileActivity;
import com.lib4.picmove.R;
import com.lib4.picmove.entity.User;
import com.lib4.picmove.utils.Utils;

public class PinterestUI extends LinearLayout {
	private int NUM_COLUMN = 2;
	Context context;
	int SCREEN_WIDTH = 0;
	int SCREEN_HEIGHT = 0;

	LinearLayout NextLayout;
	int TOTAL_NUM_ITEMS = 10;
	int ITEM_DRAWN_INDEX = 0;
	public HashMap<Integer, User> data = null;
	PopupMenu mPopupMenu;

	public PinterestUI(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public PinterestUI(Context context, HashMap<Integer, User> data) {
		super(context);
		this.data = data;
		this.context = context;
		if (Utils.TILE_VIEW_PREFERENCE) {// 2 column

			NUM_COLUMN = 2;
		} else {// 1 column
			NUM_COLUMN = 1;
		}

		TOTAL_NUM_ITEMS = data.size();
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);

		SCREEN_WIDTH = metrics.widthPixels;

		ViewTreeObserver vto = getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout() {
				ITEM_DRAWN_INDEX++;
				NextLayout = (LinearLayout) getChildAt(getLayoutIndexToAdd());

				if (ITEM_DRAWN_INDEX >= TOTAL_NUM_ITEMS) {

					ViewTreeObserver obs = getViewTreeObserver();

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						obs.removeOnGlobalLayoutListener(this);
					} else {
						obs.removeGlobalOnLayoutListener(this);
					}
				} else {
					draw();
				}
			}

		});
	}

	public void createLayout() {

		if (this.data.size() == 0) {

			LinearLayout mLinearLayout = new LinearLayout(context);
			mLinearLayout.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			TextView mTextView = new TextView(context);
			mTextView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			mTextView.setTextColor(getResources().getColor(
					R.color.blue_text_color));
			mTextView.setTextSize(25);
			mTextView.setText("No users found!");
			mLinearLayout.addView(mTextView);
			setGravity(Gravity.CENTER);

			addView(mLinearLayout);
			return;

		}

		System.out.println("IM HERE>>>>>>>>>>>" + SCREEN_WIDTH);
		for (int i = 0; i < NUM_COLUMN; i++) {

			LinearLayout mLinearLayout = new LinearLayout(context);
			mLinearLayout.setLayoutParams(new LayoutParams(SCREEN_WIDTH
					/ NUM_COLUMN, LayoutParams.WRAP_CONTENT));

			mLinearLayout.setOrientation(LinearLayout.VERTICAL);
			setOrientation(LinearLayout.HORIZONTAL);
			addView(mLinearLayout);

			System.out.println("IM HERE>>>>>>>>>>> Layout Creation" + i);
		}

		for (int i = 0; i < 1; i++) {

			draw();
		}

	}

	private void draw() {
		if (NextLayout == null) {
			NextLayout = (LinearLayout) getChildAt(0);
		}

		LayoutInflater mLayoutInflater = LayoutInflater.from(context);
		FrameLayout mFrameLayout;

		if (Utils.TILE_VIEW_PREFERENCE) {
			mFrameLayout = (FrameLayout) mLayoutInflater.inflate(
					R.layout.tiles, null);
		} else {

			mFrameLayout = (FrameLayout) mLayoutInflater.inflate(R.layout.item,
					null);
		}

		TextView nameTextView = (TextView) mFrameLayout.findViewById(R.id.name);
		ImageView profileImage = (ImageView) mFrameLayout
				.findViewById(R.id.profile_pic);
		TextView statusTextView = (TextView) mFrameLayout
				.findViewById(R.id.status_text);
		ImageView actionItems = (ImageView) mFrameLayout
				.findViewById(R.id.action_items);

		User mUser = data.get(ITEM_DRAWN_INDEX);
		profileImage.setImageResource(mUser.image);
		nameTextView.setText(mUser.name);

		actionItems.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showViewAsMenu(v);
			}
		});

		mFrameLayout.startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.scale_alpha));

		NextLayout.addView(mFrameLayout);
	}

	private void showViewAsMenu(View mView) {

		mPopupMenu = new PopupMenu(context, mView);
		mPopupMenu.getMenuInflater().inflate(R.menu.actions_menu, mPopupMenu.getMenu());
		mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {

			
				switch (item.getItemId()) {

				case R.id.Profile:
					callProfileActivity();
					break;
				case R.id.Message:
					
					break;
				case R.id.Chat:
					Log.e("CHAT ","CHAT");
					callChatActivity();
					break;
				case R.id.AddAsFriend:
					AddASFriend();
					break;
				case R.id.Block:
					BlockUser();
					break;
				}
				return false;
			}
		});

		mPopupMenu.show();

	}

	private int getLayoutIndexToAdd() {
		int ChildCount = getChildCount();
		int i = 0;
		int fewer = 0;
		int layoutIndex = 0;
		while (i < ChildCount) {
			System.out.println("IM HERE>>>>>>>>>>> Layout getChildAt(i) " + i
					+ "        " + getChildAt(i).getHeight());
			if (i == 0) {
				fewer = getChildAt(i).getHeight();
				layoutIndex = i;
			} else {

				if (getChildAt(i).getHeight() < fewer) {
					fewer = getChildAt(i).getHeight();
					layoutIndex = i;
				}
			}
			i++;
		}

		System.out.println("IM HERE>>>>>>>>>>> Layout layoutIndex"
				+ layoutIndex);
		return layoutIndex;
	}

	private void callChatActivity(){
		
		Intent intent = new Intent(context, ChatActivity.class);
		context.startActivity(intent);
		
	}
	
	private void AddASFriend(){
		
			Toast.makeText(context, "Friend request has been sent.", 1000).show();
		
		
	}
	
	
	private void BlockUser(){
		
		
		showNoNetworkAlertDialog();
	}
	
	private void callProfileActivity(){
		
		Intent intent = new Intent(context, ProfileActivity.class);
		context.startActivity(intent);
		
	}
	
	private void showNoNetworkAlertDialog() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set title
		// alertDialogBuilder.setTitle("Your Title");

		// set dialog message
		alertDialogBuilder
				.setMessage("Are you sure you want to block the user?")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// current activity
						Toast.makeText(context, "User has been blocked.", 1000).show();
					}
				})

				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
}
