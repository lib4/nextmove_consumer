package com.lib4.picmove.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DBManager {

	public static AppSqliteHelper sqLiteOpenHelper; // SQLITE Helper instance

	public static SQLiteDatabase appSqLiteDatabase; // Database instance

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
				if(appSqLiteDatabase==null)
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
	 * Inserting on to Moves Table. 
	 */
	public void insertMoves(ContentValues values) {
		open();
		long insertId = appSqLiteDatabase.insert(
				sqLiteOpenHelper.TABLE_MOVES, null, values);
		
		close();

	}
	
	/**
	 * Remove all Table rows 
	 */
	public void removeMoves() {
		open();
		appSqLiteDatabase.delete(sqLiteOpenHelper.TABLE_MOVES, null, null);
		appSqLiteDatabase.delete(sqLiteOpenHelper.TABLE_BIGITEMS, null, null);
		
		close();

	}
	
	
	/**
	 * Inserting on to Bigitems Table. 
	 */
	public void insertBigItems(ContentValues values) {
		open();
		long insertId = appSqLiteDatabase.insert(
				sqLiteOpenHelper.TABLE_BIGITEMS, null, values);
		
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
	 * Fetching  the Profile
	 */
	
	public Cursor fetchProfile() {

		String[] allColumns = { sqLiteOpenHelper.COLUMN_UID,
				sqLiteOpenHelper.COLUMN_NAME };
		Cursor cursor = appSqLiteDatabase.query(AppSqliteHelper.TABLE_PROFILE,
				null, null, null, null, null, null);
		Log.e("Curson Size ", "== " + cursor.getCount());
		return cursor;
	}

	
	
	 /**
	 * Fetching all the comments from Comment table.
	 */
	
	public Cursor fetchMoves() {
		open();
		String[] allColumns = { sqLiteOpenHelper.COLUMN_MOVEID,sqLiteOpenHelper.COLUMN_SMALLBOX_COUNT,sqLiteOpenHelper.COLUMN_MEDIUMBOX_COUNT,
				sqLiteOpenHelper.COLUMN_LARGEBOX_COUNT,sqLiteOpenHelper.COLUMN_SOURCE_ADDRESS,sqLiteOpenHelper.COLUMN_DESTINATION_ADDRESS,
				sqLiteOpenHelper.COLUMN_ROOT_ITEM_IMAGE_URL,sqLiteOpenHelper.COLUMN_NUMBER_OF_BIGITEMS
				 };
	
		Cursor cursor = appSqLiteDatabase.query(AppSqliteHelper.TABLE_MOVES,
				allColumns, null, null, null, null, null);
		Log.e("Curson Size ", "== " + cursor.getCount());
		
		return cursor;
	}

	

	
	 /**
	 * Fetching all the comments from Comment table.
	 */
	
	public Cursor fetchMove(String moveId) {
		//openDatabaseRead();
		open();
		String[] allColumns = { sqLiteOpenHelper.COLUMN_MOVEID,sqLiteOpenHelper.COLUMN_SMALLBOX_COUNT,sqLiteOpenHelper.COLUMN_MEDIUMBOX_COUNT,
				sqLiteOpenHelper.COLUMN_LARGEBOX_COUNT,sqLiteOpenHelper.COLUMN_SOURCE_ADDRESS,sqLiteOpenHelper.COLUMN_DESTINATION_ADDRESS,
				sqLiteOpenHelper.COLUMN_ROOT_ITEM_IMAGE_URL,sqLiteOpenHelper.COLUMN_NUMBER_OF_BIGITEMS
				 };
		
		Cursor cursor = appSqLiteDatabase.query(AppSqliteHelper.TABLE_MOVES,
				allColumns,sqLiteOpenHelper.COLUMN_MOVEID +"=?",  new String[] {moveId}, null, null, null);
		Log.e("Curson Size ", "== " + cursor.getCount());
		
		return cursor;
	}

	 /**
		 * Fetching all the comments from Comment table.
		 */
		
		public Cursor fetchBigItems(String moveId) {
			//openDatabaseRead();
			open();
			String[] allColumns = { sqLiteOpenHelper.COLUMN_MOVEID,sqLiteOpenHelper.COLUMN_SMALLBOX_COUNT,sqLiteOpenHelper.COLUMN_MEDIUMBOX_COUNT,
					sqLiteOpenHelper.COLUMN_LARGEBOX_COUNT,sqLiteOpenHelper.COLUMN_SOURCE_ADDRESS,sqLiteOpenHelper.COLUMN_DESTINATION_ADDRESS,
					sqLiteOpenHelper.COLUMN_ROOT_ITEM_IMAGE_URL,sqLiteOpenHelper.COLUMN_NUMBER_OF_BIGITEMS
					 };
			
			Cursor cursor = appSqLiteDatabase.query(AppSqliteHelper.TABLE_BIGITEMS,
					null,sqLiteOpenHelper.COLUMN_MOVEID +"=?",  new String[] {moveId}, null, null, null);
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
