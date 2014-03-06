package com.lib4.picmove.datahandler;

import java.io.IOException;
import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.lib4.picmove.httphandler.HttpConstants;

public class CreateNewMoveParser {

	
	String response;
	Context context;

	public boolean isSuccess		=	true;
	public String message			=	"";
	ContentValues movesValues;
	JsonParser jsonParser;

	public CreateNewMoveParser(String responseString, Context context) {
		this.context = context;
		parse(responseString);
	}

	/**
	 * Function used to parse and store the data on local db.
	 */
	private void parse(String responseString) {
		
		
		
		/*
		 * Parse the response here
		 */
		try {

			System.out.println("Parsing... : ");

			JsonFactory jsonfactory = new JsonFactory();
			jsonParser = jsonfactory.createJsonParser(responseString);
		
			
			while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			String token = jsonParser.getCurrentName();
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
				}else{
					isSuccess	=	true;	
				}
			}
			
			}
		
			
			
			} catch (JsonGenerationException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			}finally{
				try {
					jsonParser.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

}
