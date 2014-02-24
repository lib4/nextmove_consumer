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
					"38bdceac-1289-4b2c-95b7-9e7572c4dc6c");
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
			HTTPResponseListener mHttpResponseListener) {

		URL = CREATE_MOVE_URL;

		/**
		 * Forming the Create Move Json Request.
		 */
		try {

			JSONObject createMoveReqObject = new JSONObject();

			createMoveReqObject.put(HttpConstants.LARGEBOX_COUNT_JKEY, 20);
			createMoveReqObject.put(HttpConstants.EXPECTED_RECEIVEDDATE_JKEY,
					"21/02/2014");
			createMoveReqObject.put(HttpConstants.DESTINATION_ADDESS_JKEY,
					"Bangalore pincode 560100");
			createMoveReqObject.put(HttpConstants.DISPATCH_DATE_JKEY,
					"19/02/2014");
			createMoveReqObject.put(HttpConstants.USERID_JKEY,
					"38bdceac-1289-4b2c-95b7-9e7572c4dc6c");
			createMoveReqObject.put(HttpConstants.SOURCEADDRESS_JKEY, "Kerala");
			createMoveReqObject.put(HttpConstants.SMALLBOX_COUNT_JKEY, 20);
			createMoveReqObject.put(HttpConstants.MEDIUMBOX_COUNT_JKEY, 20);

			JSONArray itemArray = new JSONArray();
			for (int i = 0; i < 10; i++) {
				JSONObject itemObject = new JSONObject();
				itemObject.put(HttpConstants.ITEM_DESCRIPTION_JKEY,
						"Item is too old. Need extra care");
				itemObject.put(HttpConstants.ITEMNAME_JKEY, "Dining Table");
				itemObject
						.put(HttpConstants.ITEMIMAGE_JKEY,
								"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");
				itemObject.put(HttpConstants.DISASSEMBLY_JKEY, true);
				itemObject.put(HttpConstants.FITIN_ELEVATOR_JKEY, true);
				itemArray.put(itemObject);

			}
			createMoveReqObject.put(HttpConstants.BIGITEMS_JKEY, itemArray);

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
