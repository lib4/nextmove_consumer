package com.lib4.picmove.datahandler;

import java.io.IOException;
import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.lib4.picmove.datastorage.AppSqliteHelper;
import com.lib4.picmove.datastorage.DBManager;
import com.lib4.picmove.httphandler.HttpConstants;

public class SignUpParser {

	String response;
	Context context;
	ContentValues values = new ContentValues();
	public boolean isSuccess		=	true;
	public String message			=	"";
	

	public SignUpParser(InputStream inputStream, Context context) {
		this.context = context;
		parse(inputStream);
	}

	/**
	 * Function used to parse and store the data on local db.
	 */
	private void parse(InputStream response) {
		/*
		 * Parse the response here
		 */
		try {

			System.out.println("Parsing... : ");

			JsonFactory jsonfactory = new JsonFactory();
			JsonParser jsonParser = jsonfactory.createJsonParser(response);

			// Begin the parsing procedure
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

				String token = jsonParser.getCurrentName();
				//Log.e("token  "," "+token);
				if (HttpConstants.MESSAGE_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					message = jsonParser.getText();
				}
				if (HttpConstants.STATUS_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					String status 	=	jsonParser.getText();
					if(status.compareToIgnoreCase(HttpConstants.SUCCESS_JKEY)!=0){
						isSuccess	=	false;
					}
				}
				if (HttpConstants.PROFILE_JKEY.equals(token)) {
					Log.e("SignIn Response Profile "," "+token);
					profileParser(jsonParser);
				}

			}
			
			
			Log.e("SignIn Response After Parsing "," "+values);

			jsonParser.close();
			if(isSuccess)
			new DBManager(context).insertProfile(values);
		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
	
	/**
	 * Profile Parsing
	 * @param jsonParser
	 */


	private void profileParser(JsonParser jsonParser) {

		try {
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
				String token = jsonParser.getCurrentName();
				Log.e("Token ","Token"+token);
				if (HttpConstants.USERID_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_USERID,
							jsonParser.getText());
				}
				if (HttpConstants.EMAILADDRESS_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_EMAIL,
							jsonParser.getText());
				}

				if (HttpConstants.PASSWORD_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_PASSWORD,
							jsonParser.getText());
				}

				if (HttpConstants.ADDRESS_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_ADDRESS,
							jsonParser.getText());
				}

				if (HttpConstants.EMAILVERIFIED_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_EMAIL_VERIFIED,
							jsonParser.getBooleanValue());
				}

				if (HttpConstants.PHONENUMBER_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_PHONE_VERIFIED,
							jsonParser.getText());
				}

			}

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
