package com.example.ido.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// This class is used for interacting with database
public class DatabaseAdapter {
	
	// STATIC VARIABLES FOR DEFINING DATABASE
	
	// Used for logging
	public static final String TAG = "ido";
	
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase sqLiteDatabase;
	private final Context context;
	
	// Database name
	public static final String DATABASE_NAME = "ido_task_management.db";
	
	// Database version
	public static final int DATABASE_VERSION = 2;
	
	// Group table
	// Group table name
	public static final String GROUP_TABLE_NAME = "task_group";
	// Group table - id column name
	public static final String GROUP_TABLE_COLUMN_ID = "_id";
	// Group table - title coumn name
	public static final String GROUP_TABLE_COULMN_TITLE = "_title";
	// Group table create statement
	private static final String GROUP_TABLE_CREATE
			= "create table " + GROUP_TABLE_NAME
			+ " ( "
			+ GROUP_TABLE_COLUMN_ID + " text primary key, "
			+ GROUP_TABLE_COULMN_TITLE + " text not null "
			+ " );";
	
	
	// STATIC CLASS DATABASE HELPER
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		
		// Must override implicit constructor
		public DatabaseHelper(Context context, String name, CursorFactory factory, int version){
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// Create the Group table 
			db.execSQL(DatabaseAdapter.GROUP_TABLE_CREATE);
			// Create the Task table
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Drop Task table if exists
			
			// Drop Group table if exists
			db.execSQL("Drop table if exists " + GROUP_TABLE_NAME);
		}
		
	}
	
	
	// UTILITY FUNCTIONS FOR CREATING DATABASE AND MANIPULATING DATA
	
	// Constructor, pass the current activity to the context
	public DatabaseAdapter(Context context){
		this.context = context;
	}
	
	// Open connection to database, should be called right after constructor
	public DatabaseAdapter open(){
		databaseHelper = new DatabaseHelper(context, this.DATABASE_NAME, null, this.DATABASE_VERSION);
		sqLiteDatabase = databaseHelper.getWritableDatabase();
		return this;
	}
	
	// Close connection to database, should be called at the end when everything is finished
	public void close(){
		databaseHelper.close();
	}
	
	// Return all groups currently in database
	public Cursor getAllGroups(){
		return sqLiteDatabase.query(GROUP_TABLE_NAME,
				new String[] {GROUP_TABLE_COLUMN_ID, GROUP_TABLE_COULMN_TITLE}, null, null, null, null, null);
	}
	
	// Insert a new group into Group table
	public long insertGroup(String groupID, String groupTitle){
		ContentValues initialValues = new ContentValues();
		initialValues.put(GROUP_TABLE_COLUMN_ID, groupID);
		initialValues.put(GROUP_TABLE_COULMN_TITLE, groupTitle);
		return sqLiteDatabase.insert(GROUP_TABLE_NAME, null, initialValues);
	}
	
}
