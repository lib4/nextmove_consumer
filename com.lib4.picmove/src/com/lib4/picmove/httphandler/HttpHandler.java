package com.lib4.picmove.httphandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;


import com.lib4.picmove.datahandler.SignInParser;
import com.lib4.picmove.network.Connection;
import com.lib4.picmove.utils.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class HttpHandler extends Thread {

	public static String BASE_URL = "http://10.76.72.16:8080/NextMove-war";
	public static String SIGN_IN_URL = BASE_URL + "/doSignIn";
	public static String SIGN_UP_URL = BASE_URL + "/doSignUp";
	public static String GET_MYMOVES_URL = BASE_URL + "/getMyMoves";
	
	public static String CREATE_MOVE_URL = "/doMove";
	public static String ACC_REJ_MOVE_URL = "/doAccRejMoveDeal";
	public static String UPDATE_PROFILE = "/updateProfile";
	public static String LOGUT_URL = BASE_URL + "/account/signOut";

	public static final String HTTP_POST = "POST";
	public static final String HTTP_GET = "GET";
	private int REQUEST_API_CODE = 0;
	final int SIGNIN_API_CODE = 1;
	final int SIGNUP_API_CODE = SIGNIN_API_CODE + 1;
	final int GET_MYMOVES_API_CODE = SIGNUP_API_CODE + 1;
	final int CREATE_MOVE_API_CODE = GET_MYMOVES_API_CODE + 1;
	final int ACC_REJ_MOVE_API_CODE = CREATE_MOVE_API_CODE + 1;
	final int UPDATE_PROFILE_API_CODE = ACC_REJ_MOVE_API_CODE + 1;
	final int LOGOUT_API_CODE = UPDATE_PROFILE_API_CODE + 1;
	public static final int NO_NETWORK_CODE = 999;
	public static final int DEFAULT_CODE = 1;
	String URL;
	String requestBody;
	Context context;
	String requestType;
	HTTPResponseListener mHttpResponseListener;

	/**
	 * 
	 * 
	 * Function calls the ServerConnection gateway once the response is received
	 * Response will sent to appropriate response handled method. which in turn
	 * stores the data in to RecordStore.
	 */
	public void getMyMoves(Context context,
			HTTPResponseListener mHttpResponseListener) {

		URL = GET_MYMOVES_URL;
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		
		try {

			JSONObject getMyMovesReqObject = new JSONObject();
			getMyMovesReqObject.put(HttpConstants.USERID_JKEY, "38bdceac-1289-4b2c-95b7-9e7572c4dc6c");
			getMyMovesReqObject.put(HttpConstants.MOVES_STATUS_KEY, "All");
			requestBody = new JSONObject().put(HttpConstants.GETMYMOVES_REQUEST_KEY, getMyMovesReqObject).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		REQUEST_API_CODE = GET_MYMOVES_API_CODE;
		requestType = HTTP_POST;
		start();
	}

	/**
	 * @param userName
	 *            // Username entered by the user
	 * @param password
	 *            //password entered by the user
	 * 
	 *            Function calls the ServerConnection gateway once the response
	 *            is received Response will sent to appropriate response handled
	 *            method. which in turn stores the data in to RecordStore.
	 */
	public void doSignIn(String emailAddress, String password, Context context,
			HTTPResponseListener mHttpResponseListener) {

		URL = SIGN_IN_URL;

		/**
		 * Forming the SignIn Json Request.
		 */
		try {

			JSONObject signInReqObject = new JSONObject();
			signInReqObject.put(HttpConstants.EMAILADDRESS_JKEY, emailAddress);
			signInReqObject.put(HttpConstants.PASSWORD_JKEY, password);
			Utils.USERNAME = emailAddress;
			Utils.PASSWORD = password;
			requestBody = new JSONObject().put(HttpConstants.SIGNIN_REQUEST_KEY, signInReqObject).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = SIGNIN_API_CODE;
		requestType = HTTP_POST;

		start();
	}



	/**
	 * 
	 * SIGNOUT Function calls the ServerConnection gateway once the response is
	 * received Response will sent to appropriate response handled method. which
	 * in turn stores the data in to RecordStore.
	 */
	public void signOut(Context context) {

		URL = LOGUT_URL;
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = LOGOUT_API_CODE;
		requestType = HTTP_GET;
		start();
	}

	/**
	 * Method Responsible for the following:- 1 Initiating Server Connection 2
	 * Initiating the Response Handling ie passing the response to appropriate
	 * parser class. 3.Trigger the UI events
	 */
	public void run() {
		if (!isNetworkOnline()) {
			mHttpResponseListener.onFailure(NO_NETWORK_CODE);
			return;
		}
		try {

			Connection mConnection = new Connection();
			try {
				mConnection.connect(URL, requestBody, requestType);
			} catch (Exception e) {
				e.printStackTrace();
				// Exception will be handled based on response code obtained.
				mHttpResponseListener.onFailure(DEFAULT_CODE);
				return;
			}
			switch (REQUEST_API_CODE) {

			case SIGNIN_API_CODE:
				switch (mConnection.responseCode) {
				case 200:
					SignInParser mSignInParser = new SignInParser(
							mConnection.responseStream, context);
					if(mHttpResponseListener!=null)
					mHttpResponseListener.onSuccess();

					break;

				default:

					// mHttpResponseListener.onSuccess();
					mHttpResponseListener.onFailure(DEFAULT_CODE);
					break;
				}
				break;

			case GET_MYMOVES_API_CODE:
				switch (mConnection.responseCode) {
				case 200:

					//LiveOrderParser mLiveOrderParser = new LiveOrderParser(
						//	mConnection.responseStream, context);
					//mHttpResponseListener.onSuccess();
					InputStreamReader is = new InputStreamReader(
							mConnection.responseStream);
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(is);
					String read = br.readLine();

					while (read != null) {
						// System.out.println(read);
						sb.append(read);
						read = br.readLine();

					}

					System.out.println(sb.toString());
					break;

				default:
					mHttpResponseListener.onFailure(DEFAULT_CODE);
					break;
				}
				break;

		
	/*		case GET_LIVE_ORDER_ACTION_API_CODE:
				switch (mConnection.responseCode) {
				// case 200:
				// String myString = IOUtils.toString(myInputStream, "UTF-8");
				// GetMenusParser mLiveOrderParser = new GetMenusParser(
				// mConnection.responseStream, context);
				// System.out.println("Success");
				// mHttpResponseListener.onSuccess();

				// break;

				default:
					InputStreamReader is = new InputStreamReader(
							mConnection.responseStream);
					StringBuilder sb = new StringBuilder();
					BufferedReader br = new BufferedReader(is);
					String read = br.readLine();

					while (read != null) {
						// System.out.println(read);
						sb.append(read);
						read = br.readLine();

					}

					System.out.println(sb.toString());
					break;
				}
				break;
				*/
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// responseResolver(mServerConnection);
	}

	public boolean isNetworkOnline() {
		boolean status = true;
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null
					&& netInfo.getState() == NetworkInfo.State.CONNECTED) {
				status = true;
			} else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo != null
						&& netInfo.getState() == NetworkInfo.State.CONNECTED)
					status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return status;

	}
}
