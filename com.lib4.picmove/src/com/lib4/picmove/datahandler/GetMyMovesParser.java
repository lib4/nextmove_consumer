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

public class GetMyMovesParser {

	String response;
	Context context;

	public boolean isSuccess		=	true;
	public String message			=	"";
	

	public GetMyMovesParser(InputStream inputStream, Context context) {
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

			
		while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
			ContentValues values = new ContentValues();
			Log.e("endObject","Pbject");
			// Begin the parsing procedure
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
				
				

				String token = jsonParser.getCurrentName();
				
				Log.e("endObject","Pbject "+token);
				//Log.e("token  "," "+token);
				if (HttpConstants.LARGEBOX_COUNT_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_LARGEBOX_COUNT, jsonParser.getIntValue());
				}
				

				if (HttpConstants.MOVEID_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_MOVEID, jsonParser.getText());
				}

				if (HttpConstants.DESTINATION_ADDESS_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_DESTINATION_ADDRESS, jsonParser.getText());
				}

				if (HttpConstants.USERID_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_USERID, jsonParser.getText());
				}

				if (HttpConstants.DISPATCH_DATE_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_DISPATCH_DATE, jsonParser.getText());
				}

				
				if (HttpConstants.BIGITEMPRESENT_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_BIGITEM_PRESENT, jsonParser.getBooleanValue());
				}
				
				if (HttpConstants.MOVESTATUS_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_MOVE_STATUS, jsonParser.getText());
				}
				
				if (HttpConstants.SOURCEADDRESS_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_ADDRESS, jsonParser.getText());
				}
				
				if (HttpConstants.SMALLBOX_COUNT_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_SMALLBOX_COUNT, jsonParser.getIntValue());
				}
				
				if (HttpConstants.EXPECTED_RECEIVEDDATE_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_EXPECTED_RECEIVEDATE, jsonParser.getText());
				}
				
				if (HttpConstants.MEDIUMBOX_COUNT_JKEY.equals(token)) {

					// get the next token which will be the value...
					jsonParser.nextToken();
					values.put(AppSqliteHelper.COLUMN_MEDIUMBOX_COUNT, jsonParser.getIntValue());
				}
				
				

				if (HttpConstants.BIGITEMS_JKEY.equals(token)) {

					// get the next token which will be the value...
					bigItemParser(jsonParser);
					
				}
				
				
			}
			if(jsonParser.getCurrentToken()==JsonToken.END_OBJECT){
				Log.e("MOVES ","MOVES "+values);
				new DBManager(context).insertMoves(values);
			}
			
			
			Log.e("SignIn Response After Parsing "," "+values);

			
			
		}
		
		jsonParser.close();

			
			
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


	private void bigItemParser(JsonParser jsonParser) {

			
			try {
				while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
					
					ContentValues values = new ContentValues();
						while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
							String token = jsonParser.getCurrentName();
							Log.e("Token ","Token"+token);
							if (HttpConstants.ITEMNAME_JKEY.equals(token)) {
			
								// get the next token which will be the value...
								jsonParser.nextToken();
								values.put(AppSqliteHelper.COLUMN_ITEM_NAME,
										jsonParser.getText());
							}
							
							if (HttpConstants.ITEM_DESCRIPTION_JKEY.equals(token)) {
			
								// get the next token which will be the value...
								jsonParser.nextToken();
								values.put(AppSqliteHelper.COLUMN_ITEM_DESCRIPTION,
										jsonParser.getText());
							}
			
							if (HttpConstants.MOVEID_JKEY.equals(token)) {
			
								// get the next token which will be the value...
								jsonParser.nextToken();
								//values.put(AppSqliteHelper.COLUMN_MOVEID,
										//jsonParser.getText());
							}
			
							if (HttpConstants.REQUIRES_DISASSEMBLY_JKEY.equals(token)) {
			
								// get the next token which will be the value...
								jsonParser.nextToken();
								values.put(AppSqliteHelper.COLUMN_REQUIRES_DISASSEMBLY,
										jsonParser.getBooleanValue());
							}

							if (HttpConstants.BIGITEM_ID_JKEY.equals(token)) {
			
								// get the next token which will be the value...
								jsonParser.nextToken();
								values.put(AppSqliteHelper.COLUMN_BIGITEM_ID,
										jsonParser.getText());
							}
							
							
			
							if (HttpConstants.DOES_FITIN_ELEVATOR_JKEY.equals(token)) {
			
								// get the next token which will be the value...
								jsonParser.nextToken();
								values.put(AppSqliteHelper.COLUMN_DOES_FIT_IN_ELEVATOR,
										jsonParser.getBooleanValue());
							}
							
							
							if (HttpConstants.ITEM_URL_JKEY.equals(token)) {
								
								// get the next token which will be the value...
								jsonParser.nextToken();
								values.put(AppSqliteHelper.COLUMN_ITEM_URL,
										jsonParser.getText());
							}
			
						}
						
						
						if(jsonParser.getCurrentToken()==JsonToken.END_OBJECT){
							
							Log.e("BIG ITEM ","BIG ITEMS "+values);
							new DBManager(context).insertBigItems(values);
						}
			
		}

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
