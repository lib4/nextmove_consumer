package com.lib4.picmove.httphandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.lib4.picmove.datahandler.GetMyMovesParser;
import com.lib4.picmove.datahandler.SignInParser;
import com.lib4.picmove.datahandler.SignUpParser;
import com.lib4.picmove.network.Connection;
import com.lib4.picmove.utils.Utils;

public class HttpHandler extends Thread {

	public static String BASE_URL =  "http://10.76.72.16:8080/NextMove-war";//"http://192.168.1.100:8080/NextMove-war";
	public static String SIGN_IN_URL = BASE_URL + "/doSignIn";
	public static String SIGN_UP_URL = BASE_URL + "/doSignUp";
	public static String GET_MYMOVES_URL = BASE_URL + "/getMyMoves";

	public static String CREATE_MOVE_URL = BASE_URL + "/doMove";
	public static String ACC_REJ_MOVE_URL = "/doAccRejMoveDeal";
	public static String UPDATE_PROFILE_URL = "/updateProfile";
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
	public static final int UNABLE_TO_REACH_SERVER	=	1099;
	public static final int DEFAULT_CODE = 1;
	String URL;
	String requestBody;
	Context context;
	String requestType;
	HTTPResponseListener mHttpResponseListener;
	String UANBLETOREEACHSERVER	=	"Server is temperorily not available. Please try after some time.";
	String NONETWORK				=	"Your are not connected to internet. Please check your network settings and try again!";
	

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
			getMyMovesReqObject.put(HttpConstants.USERID_JKEY,
					Utils.userId);
			getMyMovesReqObject.put(HttpConstants.MOVES_STATUS_KEY, "All");
			requestBody = new JSONObject().put(
					HttpConstants.GETMYMOVES_REQUEST_KEY, getMyMovesReqObject)
					.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		REQUEST_API_CODE = GET_MYMOVES_API_CODE;
		requestType = HTTP_POST;
		start();
	}

	/**
	 * 
	 * 
	 */

	public void accrejMoveDealRequest() {

		URL = ACC_REJ_MOVE_URL;
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;

		try {

			JSONObject acceptRejectReqObject = new JSONObject();
			acceptRejectReqObject.put(HttpConstants.USERID_JKEY,
					"38bdceac-1289-4b2c-95b7-9e7572c4dc6c");
			acceptRejectReqObject.put(HttpConstants.MOVEID_JKEY,
					"9e84dac5-0338-4769-a1ce-0e2ad0e67774");
			acceptRejectReqObject.put(HttpConstants.MOVES_STATUS_KEY,
					"REJECTED");
			requestBody = new JSONObject()
					.put(HttpConstants.GETMYMOVES_REQUEST_KEY,
							acceptRejectReqObject).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		REQUEST_API_CODE = ACC_REJ_MOVE_API_CODE;
		requestType = HTTP_POST;
		start();

	}

	/**
	 * 
	 */

	public void updateProfile() {

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
			requestBody = new JSONObject().put(
					HttpConstants.SIGNIN_REQUEST_KEY, signInReqObject)
					.toString();
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
	 * Do signUp
	 */

	public void doSignUp(HashMap<String, String> requestParams,Context context,
			HTTPResponseListener mHttpResponseListener) {

		URL = SIGN_UP_URL;

		/**
		 * Forming the SignIn Json Request.
		 */
		try {

			JSONObject signUpReqObject = new JSONObject();
			signUpReqObject.put(HttpConstants.EMAILADDRESS_JKEY,
					requestParams.get(HttpConstants.EMAILADDRESS_JKEY));
			signUpReqObject.put(HttpConstants.PASSWORD_JKEY, requestParams.get(HttpConstants.PASSWORD_JKEY));
			signUpReqObject.put(HttpConstants.ADDRESS_JKEY,
					requestParams.get(HttpConstants.ADDRESS_JKEY));
			signUpReqObject.put(HttpConstants.PHONENUMBER_JKEY, requestParams.get(HttpConstants.PHONENUMBER_JKEY));

			signUpReqObject.put(HttpConstants.NAME_JKEY, requestParams.get(HttpConstants.NAME_JKEY));

			requestBody = new JSONObject().put(HttpConstants.SIGNUP_REQUEST,
					signUpReqObject).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = SIGNUP_API_CODE;
		requestType = HTTP_POST;

		start();

	}

	/**
	 * Do signUp
	 */

	public void doUpdateProfile(Context context,
			HTTPResponseListener mHttpResponseListener) {

		URL = UPDATE_PROFILE_URL;

		/**
		 * Forming the SignIn Json Request.
		 */
		try {

			JSONObject updateProfileReqObject = new JSONObject();
			updateProfileReqObject.put(HttpConstants.USERID_JKEY,
					"38bdceac-1289-4b2c-95b7-9e7572c4dc6c");
			updateProfileReqObject.put(HttpConstants.PHONENUMBER_JKEY,
					"7406667752");
			updateProfileReqObject.put(HttpConstants.NAME_JKEY,
					"John Carey Updated ");
			updateProfileReqObject
					.put(HttpConstants.ADDRESS_JKEY, "Bangalore ");

			requestBody = new JSONObject().put(
					HttpConstants.UPDATE_PROFILE_REQUEST,
					updateProfileReqObject).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = UPDATE_PROFILE_API_CODE;
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
	public void createMove(Context context,
			HTTPResponseListener mHttpResponseListener,JSONObject createMoveReqObject) {

		URL = CREATE_MOVE_URL;

		/**
		 * Forming the Create Move Json Request.
		 */
		try {

		
			requestBody = new JSONObject().put(HttpConstants.MOVE_REQUEST_JKEY,
					createMoveReqObject).toString();

		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.context = context;
		this.mHttpResponseListener = mHttpResponseListener;
		REQUEST_API_CODE = CREATE_MOVE_API_CODE;
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
			mHttpResponseListener.onFailure(NO_NETWORK_CODE, NONETWORK);
			return;
		}
		try {

			Connection mConnection = new Connection();
			try {
				mConnection.connect(URL, requestBody, requestType);
			} catch (Exception e) {
				e.printStackTrace();
				// Exception will be handled based on response code obtained.
				mHttpResponseListener.onFailure(UNABLE_TO_REACH_SERVER, UANBLETOREEACHSERVER);
				return;
			}
			switch (REQUEST_API_CODE) {

			case SIGNUP_API_CODE:
			case 200:

				Log.e("RESONSE CODE ","RESPONSE CODE "+mConnection.responseCode);
				switch (mConnection.responseCode) {
				case 200:
					SignUpParser mSignUpParser = new SignUpParser(
							mConnection.responseStream, context);

					if (mHttpResponseListener != null) {

						if (mSignUpParser.isSuccess)
							mHttpResponseListener.onSuccess();
						else
							mHttpResponseListener.onFailure(DEFAULT_CODE,
									mSignUpParser.message);

					}

					break;

				default:

					
					mHttpResponseListener.onFailure(UNABLE_TO_REACH_SERVER,UANBLETOREEACHSERVER);
					break;
				}
				break;

			case SIGNIN_API_CODE:
				
				Log.e("RESONSE CODE ","RESPONSE CODE "+mConnection.responseCode);
				switch (mConnection.responseCode) {
				case 200:
					SignInParser mSignInParser = new SignInParser(
							mConnection.responseStream, context);

					if (mHttpResponseListener != null) {

						if (mSignInParser.isSuccess)
							mHttpResponseListener.onSuccess();
						else
							mHttpResponseListener.onFailure(DEFAULT_CODE,
									mSignInParser.message);

					}

					break;

				default:

					
					mHttpResponseListener.onFailure(UNABLE_TO_REACH_SERVER,UANBLETOREEACHSERVER);
					break;
				}
				break;

			case GET_MYMOVES_API_CODE:
				switch (mConnection.responseCode) {
				case 200:
					
					GetMyMovesParser mGetMyMovesParser = new GetMyMovesParser(
							mConnection.responseStream, context);

					if (mHttpResponseListener != null) {

						if (mGetMyMovesParser.isSuccess)
							mHttpResponseListener.onSuccess();
						else
							mHttpResponseListener.onFailure(DEFAULT_CODE,
									mGetMyMovesParser.message);

					}

					break;

				default:
					mHttpResponseListener.onFailure(UNABLE_TO_REACH_SERVER,UANBLETOREEACHSERVER);
					break;
				}

			case CREATE_MOVE_API_CODE:
				switch (mConnection.responseCode) {
				case 200:

					serverResponeAsString(mConnection);
					break;

				default:
					mHttpResponseListener.onFailure(DEFAULT_CODE,"");
					break;
				}

				break;

			case UPDATE_PROFILE_API_CODE:
				switch (mConnection.responseCode) {
				case 200:

					serverResponeAsString(mConnection);
					break;

				default:
					mHttpResponseListener.onFailure(DEFAULT_CODE,"");
					break;
				}

				break;

			/*
			 * case GET_LIVE_ORDER_ACTION_API_CODE: switch
			 * (mConnection.responseCode) { // case 200: // String myString =
			 * IOUtils.toString(myInputStream, "UTF-8"); // GetMenusParser
			 * mLiveOrderParser = new GetMenusParser( //
			 * mConnection.responseStream, context); //
			 * System.out.println("Success"); //
			 * mHttpResponseListener.onSuccess();
			 * 
			 * // break;
			 * 
			 * default: InputStreamReader is = new InputStreamReader(
			 * mConnection.responseStream); StringBuilder sb = new
			 * StringBuilder(); BufferedReader br = new BufferedReader(is);
			 * String read = br.readLine();
			 * 
			 * while (read != null) { // System.out.println(read);
			 * sb.append(read); read = br.readLine();
			 * 
			 * }
			 * 
			 * System.out.println(sb.toString()); break; } break;
			 */
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// responseResolver(mServerConnection);
	}

	private void serverResponeAsString(Connection mConnection) {

		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}

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
