package com.lib4.picmove.utils;

import java.util.HashMap;

import com.lib4.picmove.R;
import com.lib4.picmove.entity.User;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebView.FindListener;

public class Utils {

	
	public static boolean IS_TABLET	=	false;
	public static String  	TOP_USERS_HEADER =	"Top Users";	
	public static String  	ONLINE_USERS_HEADER =	"Online Users";	
	public static boolean TILE_VIEW_PREFERENCE		=	true;
	public static String USERNAME = "admin";
	public static String PASSWORD = "password";
	
	
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
	
	public static int[] images = new int[]{
		
		R.drawable.user0,R.drawable.user1,R.drawable.user2,R.drawable.user3,R.drawable.user4,R.drawable.user5,R.drawable.user6,R.drawable.user7,R.drawable.user8,R.drawable.user9,
		R.drawable.user10,R.drawable.user11,R.drawable.user12,R.drawable.user13,R.drawable.user14,R.drawable.user15,R.drawable.user16,R.drawable.user17,R.drawable.user18,R.drawable.user19
	};
	
	
	public static HashMap<Integer, User> getUserList(){
		if(USERS_LIST.isEmpty()){
		for(int i =0;i<20;i++){
			
			User mUser	=	new User();
			mUser.name	=	Names[i];
			mUser.image	=	images[i];
			mUser.age	=	20+i;
			
			USERS_LIST.put(i, mUser);
		}
		
		
		}
		return USERS_LIST;
	}
}
