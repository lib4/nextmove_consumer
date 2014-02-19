package com.lib4.picmove.datastorage;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppSqliteHelper extends SQLiteOpenHelper {

	public static final String TABLE_PROFILE = "profile";
	private static final String DATABASE_NAME = "nextmove.db";
	private static final int DATABASE_VERSION = 1;

	public static final String COLUMN_UID = "userId";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PASSWORD = "password";

	
	

	// Database creation sql statement
	private static final String CREATE_PROFILE_TABLE = "create table "
			+ TABLE_PROFILE + " "
			+ "(auto_id integer primary key autoincrement," + COLUMN_UID
			+ " text not null," + COLUMN_EMAIL + " integer," + ""
			+ COLUMN_NAME + " text not null," + "" + COLUMN_PASSWORD
			+ " text not null," + COLUMN_EMAIL + " text not null);";



	
	public AppSqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_PROFILE_TABLE);
	
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
