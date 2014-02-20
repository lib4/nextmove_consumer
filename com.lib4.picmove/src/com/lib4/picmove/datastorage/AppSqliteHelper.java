package com.lib4.picmove.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppSqliteHelper extends SQLiteOpenHelper {

	public static final String TABLE_PROFILE = "profile";
	public static final String TABLE_MOVES	= "moves";
	public static final String TABLE_BIGITEMS	= "bigitems";
	private static final String DATABASE_NAME = "nextmove.db";
	private static final int DATABASE_VERSION = 1;

	/**
	 * Profile Table columns
	 */
	public static final String COLUMN_UID = "userId";
	public static final String COLUMN_EMAIL = "emailAddress";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
	public static final String COLUMN_EMAIL_VERIFIED = "isEmailVerified";
	public static final String COLUMN_PHONE_VERIFIED = "isPhoneVerified";
	

	/**
	 * Moves Table columns
	 */

	public static final String COLUMN_MOVEID = "moveId";
	public static final String COLUMN_LARGEBOX_COUNT = "largeBoxCount";
	public static final String COLUMN_MEDIUMBOX_COUNT = "mediumBoxCount";
	public static final String COLUMN_SMALLBOX_COUNT = "smallBoxCount";
	public static final String COLUMN_REJECTREASON = "rejectReason";
	public static final String COLUMN_DISPATCH_DATE = "dispatchDate";
	public static final String COLUMN_BIGITEM_PRESENT = "isBigItemsPresent";
	public static final String COLUMN_MOVE_STATUS = "moveStatus";
	public static final String COLUMN_CONDITIONS = "conditions";
	public static final String COLUMN_PRICE_QUOTE = "priceQuote";
	public static final String COLUMN_DESTINATION_ADDRESS = "destinationAddress";
	public static final String COLUMN_USERID = "userId";
	public static final String COLUMN_SOURCE_ADDRESS = "sourceAddress";
	public static final String COLUMN_EXPECTED_RECEIVEDATE = "expectedReceiveDate";

	/**
	 * BIG ITEM Table Columns
	 * 
	 * 
	 */
	public static final String COLUMN_ITEM_NAME = "itemName";
	public static final String COLUMN_ITEM_DESCRIPTION = "itemDescription";
	public static final String COLUMN_BIGITEM_ID = "bigItemId";
	public static final String COLUMN_REQUIRES_DISASSEMBLY = "requiresDisassembly";
	public static final String COLUMN_DOES_FIT_IN_ELEVATOR = "doesFitInElevator";
	public static final String COLUMN_ITEM_URL = "itemUrl";
	
	
	
	
	
	
	
	
	
	

	// Database creation sql statement
	private static final String CREATE_MOVES_TABLE = "create table "
			+ TABLE_MOVES + " "
			+ "(auto_id integer primary key autoincrement," + COLUMN_MOVEID
			+ " text not null," + 
			COLUMN_SMALLBOX_COUNT + " int not null default 0, " + 
			COLUMN_MEDIUMBOX_COUNT + " int not null default 0, " +
			COLUMN_LARGEBOX_COUNT + " int not null default 0, " + 
			COLUMN_REJECTREASON + " text ," +
			COLUMN_DISPATCH_DATE + " text ," +
			COLUMN_BIGITEM_PRESENT + " boolean not null default 0 ," +
			COLUMN_MOVE_STATUS + " text," +
			COLUMN_CONDITIONS + " text," +
			COLUMN_PRICE_QUOTE + " text," +
			COLUMN_DESTINATION_ADDRESS + " text," +
			COLUMN_USERID + " text," +
			COLUMN_SOURCE_ADDRESS + " text," +
			COLUMN_EXPECTED_RECEIVEDATE + " text );";
	
	
	
	// Database creation sql statement
	private static final String CREATE_BIGITEMS_TABLE = "create table "
			+ TABLE_BIGITEMS + " "
			+ "(auto_id integer primary key autoincrement," + COLUMN_BIGITEM_ID
			+ " text not null," + 
			COLUMN_ITEM_NAME + " text, " + 
			COLUMN_ITEM_DESCRIPTION + " text, " +
			COLUMN_ITEM_URL + " text ," +
			COLUMN_REQUIRES_DISASSEMBLY + " boolean not null default 0 ," +
			COLUMN_DOES_FIT_IN_ELEVATOR + " boolean not null default 0 ," +
			COLUMN_EXPECTED_RECEIVEDATE + " text );";
	

	// Database creation sql statement
	private static final String CREATE_PROFILE_TABLE = "create table "
			+ TABLE_PROFILE + " "
			+ "(auto_id integer primary key autoincrement," + COLUMN_UID
			+ " text not null, " + COLUMN_EMAIL + " text," + COLUMN_NAME
			+ " text," + "" + COLUMN_PASSWORD + " text," + COLUMN_ADDRESS
			+ " text, " +
			COLUMN_EMAIL_VERIFIED + " boolean not null default 0 ," +
			COLUMN_PHONE_VERIFIED + " boolean not null default 0 ," +
			COLUMN_PHONE_NUMBER + " text );";
	
	
	

	public AppSqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		Log.e("Creating Tables....","Creating Tables....");
		database.execSQL(CREATE_PROFILE_TABLE);
		database.execSQL(CREATE_MOVES_TABLE);
		database.execSQL(CREATE_BIGITEMS_TABLE);
		
		Log.e("Tables Creation Done....","Tables Creation Done....");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(AppSqliteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
		onCreate(db);
	}
}
