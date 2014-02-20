package com.lib4.picmove.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DBManager {

	private AppSqliteHelper sqLiteOpenHelper; // SQLITE Helper instance

	private SQLiteDatabase appSqLiteDatabase; // Database instance

	/**
	 * Constructor initializes the Sqlitehelper
	 * 
	 * @param mContext
	 */
	public DBManager(Context mContext) {
		// TODO Auto-generated constructor stub
		sqLiteOpenHelper = new AppSqliteHelper(mContext);

	}

	/**
	 * Methods opens the database
	 * 
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		try {
			appSqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
			Uri mUril = Uri.parse(appSqLiteDatabase.getPath());
			Log.e("URI ", "" + mUril.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method closes the database.
	 */
	public void close() {
		sqLiteOpenHelper.close();
	}

	
	/**
	 * Inserting on to Profile Table. After succeful signup
	 */
	public void insertProfile(ContentValues values) {
		open();
		long insertId = appSqLiteDatabase.insert(
				sqLiteOpenHelper.TABLE_PROFILE, null, values);
		fetchProfile();
		close();

	}

	
	
	
	/**
	 * Flush the data in Menus  Table
	 */
	public void clearDB() {

		open();
		long insertId = appSqLiteDatabase.delete(
				AppSqliteHelper.TABLE_PROFILE, null, null);
		
		close();

	}

	
	
	 /**
	 * Fetching all the comments from Comment table.
	 */
	
	public Cursor fetchProfile() {

		String[] allColumns = { sqLiteOpenHelper.COLUMN_UID,
				sqLiteOpenHelper.COLUMN_NAME };
		Cursor cursor = appSqLiteDatabase.query(AppSqliteHelper.TABLE_PROFILE,
				null, null, null, null, null, null);
		Log.e("Curson Size ", "== " + cursor.getCount());
		return cursor;
	}

	
	


	public void startTransaction() {
		open();
		appSqLiteDatabase.beginTransaction();
	}

	public void endTransaction() {
		appSqLiteDatabase.setTransactionSuccessful();

		appSqLiteDatabase.endTransaction();
		close();

	}
}
