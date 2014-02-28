package com.lib4.picmove.utils;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;

import com.lib4.picmove.R;
import com.lib4.picmove.entity.User;

public class Utils {

	
	public static boolean IS_TABLET	=	false;
	public static String  	MOVES_HEADER =	"Moves";	
	public static String  	ONLINE_USERS_HEADER =	"Online Users";	
	public static String  	REQUIRES_DIASSEBLY =	"Diassembly";	
	public static boolean TILE_VIEW_PREFERENCE		=	true;
	public static String USERNAME = "anaschaky@gmail.com";
	public static String PASSWORD = "12345678";
	public static String CURRENT_ACTIVE_FOLDER	=	"";
	
	
	public static boolean isTabletDevice(Context activityContext) {
        boolean device_large = ((activityContext.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE);

        if (device_large) {
            DisplayMetrics metrics = new DisplayMetrics();
            Activity activity = (Activity) activityContext;
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
                    || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
                    || metrics.densityDpi == DisplayMetrics.DENSITY_TV
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
                Log.e("DeviceHelper","IsTabletDevice-True");
                return true;
            }
        }
        Log.e("DeviceHelper","IsTabletDevice-False");
        return false;
    }
	
	
	
	public static HashMap<Integer, User> USERS_LIST	=	new HashMap<Integer, User>();
	
	public static String[] Names = new String[]{
		"Jame Miller","Pual collin","Freddy Albert","Dsouza Jani","Keane Fabre","Robert Wilinkin","Dave Mat", "Prio taba","Oool Mitha","Sean Kore",
		"Fame Jack","Camlin dcrouz","albert wellin","Tom san","Phili luois","Doly Methey","Sara Jon", "Elina Taog","Meetu Kaniya","Mexza love"
		
	};
	
	
	
	public static void showNoNetworkAlertDialog(final Context mContext) {


		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				mContext);


		// set title
		// alertDialogBuilder.setTitle("Your Title");


		// set dialog message
		alertDialogBuilder
				.setMessage(
						mContext.getString(R.string.uname_not_matching))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// current activity


					}
				})


				.setNegativeButton("Settings",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								mContext.startActivity(new Intent(
										android.provider.Settings.ACTION_SETTINGS));
							}
						});


		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();


		// show it
		alertDialog.show();
	}

	
}
