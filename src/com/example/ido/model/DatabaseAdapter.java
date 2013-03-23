package com.example.ido.model;

import java.util.Calendar;
import java.util.UUID;

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
	
	// Some variables used for interacting with database
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase sqLiteDatabase;
	
	// Current context (activity)
	private final Context context;
	
	// Database name
	public static final String DATABASE_NAME = "ido_task_management.db";
	
	// Database version
	public static final int DATABASE_VERSION = 2;
	
	// Group table
	// Group table name
	public static final String GROUP_TABLE_NAME = "_group";
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
	
	// Task table
	// Task table name
	public static final String TASK_TABLE_NAME = "_task";
	// Task table - id column name
	public static final String TASK_TABLE_COLUMN_ID = "_id";
	// Task table - title column name
	public static final String TASK_TABLE_COLUMN_TITLE = "_title";
	// Task table - due date column name
	public static final String TASK_TABLE_COLUMN_DUE_DATE = "_due_date";
	// Task table - note column name
	public static final String TASK_TABLE_COLUMN_NOTE = "_note";
	// Task table - priority level column name
	public static final String TASK_TABLE_COLUMN_PRIORITY = "_priority";
	// Task table - group column name
	public static final String TASK_TABLE_COLUMN_GROUP = "_group";
	// Task table - completion status column name
	public static final String TASK_TABLE_COLUMN_COMPLETION_STATUS = "_completion_status";
	// Task table create statement
	public static final String TASK_TABLE_CREATE
			= "create table " + TASK_TABLE_NAME
			+ " ( "
			+ TASK_TABLE_COLUMN_ID + " text primary key, "
			+ TASK_TABLE_COLUMN_TITLE + " text not null, "
			+ TASK_TABLE_COLUMN_DUE_DATE + " integer not null, "
			+ TASK_TABLE_COLUMN_NOTE + " text,"
			+ TASK_TABLE_COLUMN_PRIORITY + " integer not null, "
			+ TASK_TABLE_COLUMN_GROUP + " text not null, "
			+ TASK_TABLE_COLUMN_COMPLETION_STATUS + " integer not null, "
			// create the foreign key from column group -> _group(id)
			+ "foreign key ( " + TASK_TABLE_COLUMN_GROUP + " ) references " + GROUP_TABLE_NAME + " ( " + GROUP_TABLE_COLUMN_ID + " ) "
			+ " );";
			
	
	// Collaborator table
	// Collaborator table name
	public static final String COLLABORATOR_TABLE_NAME = "_collaborator";
	// Collaborator table - id column name
	public static final String COLLABORATOR_TABLE_COLUMN_ID = "_id";
	// Collaborator table - email column name
	public static final String COLLABORATOR_TABLE_COLUMN_EMAIL = "_email";
	// Collaborator table - task id column name
	public static final String COLLABORATOR_TABLE_COLUMN_TASK_ID = "_task_id";
	// Collaborator table create statement
	public static final String COLLABORATOR_TABLE_CREATE
			= "create table " + COLLABORATOR_TABLE_NAME
			+ " ( "
			+ COLLABORATOR_TABLE_COLUMN_ID + " integer primary key autoincrement, "
			+ COLLABORATOR_TABLE_COLUMN_EMAIL + " text not null, "
			+ COLLABORATOR_TABLE_COLUMN_TASK_ID + " text not null, "
			// create the foreign key from column task id -> task(id)
			+ "foreign key ( " + COLLABORATOR_TABLE_COLUMN_TASK_ID + " ) references " + TASK_TABLE_NAME + " ( " + TASK_TABLE_COLUMN_ID + " ) "
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
			db.execSQL(DatabaseAdapter.TASK_TABLE_CREATE);
			// Create the collaborator table
			db.execSQL(DatabaseAdapter.COLLABORATOR_TABLE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Drop Collaborator table is exist
			db.execSQL("Drop table if exists " + DatabaseAdapter.COLLABORATOR_TABLE_NAME);
			// Drop Task table if exists
			db.execSQL("Drop table if exists " + DatabaseAdapter.TASK_TABLE_NAME);
			// Drop Group table if exists
			db.execSQL("Drop table if exists " + DatabaseAdapter.GROUP_TABLE_NAME);
			onCreate(db);
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
	
	// Find the group by its id
	public Cursor getGroupById(String groupId){
		return sqLiteDatabase.query(GROUP_TABLE_NAME,
				new String[] {GROUP_TABLE_COLUMN_ID, GROUP_TABLE_COULMN_TITLE},
				GROUP_TABLE_COLUMN_ID + " = '" + groupId + "'", null, null, null, null);
	}
	
	// Insert a new group into Group table
	public long insertGroup(String groupID, String groupTitle){
		ContentValues initialValues = new ContentValues();
		initialValues.put(GROUP_TABLE_COLUMN_ID, groupID);
		initialValues.put(GROUP_TABLE_COULMN_TITLE, groupTitle);
		return sqLiteDatabase.insert(GROUP_TABLE_NAME, null, initialValues);
	}
	
	// Insert a new task into Task table
	public void insertTask(Task task){
		ContentValues initialValues = new ContentValues();
		initialValues.put(TASK_TABLE_COLUMN_ID, task.getId());
		initialValues.put(TASK_TABLE_COLUMN_TITLE, task.getTitle());
		initialValues.put(TASK_TABLE_COLUMN_DUE_DATE, task.getDueDate().getTimeInMillis());
		initialValues.put(TASK_TABLE_COLUMN_NOTE, task.getNote());
		initialValues.put(TASK_TABLE_COLUMN_PRIORITY, task.getPriorityLevel());
		initialValues.put(TASK_TABLE_COLUMN_GROUP, task.getGroup().getId());
		initialValues.put(TASK_TABLE_COLUMN_COMPLETION_STATUS, task.getCompletionStatus());
		sqLiteDatabase.insert(TASK_TABLE_NAME, null, initialValues);
		
		// insert collaborators
		this.insertCollaborators(task);
	}
	
	// Return all task currently in database
	public Cursor getAllTasks(){
		return sqLiteDatabase.query(TASK_TABLE_NAME,
				new String[] {TASK_TABLE_COLUMN_ID, TASK_TABLE_COLUMN_TITLE, TASK_TABLE_COLUMN_DUE_DATE, TASK_TABLE_COLUMN_NOTE, TASK_TABLE_COLUMN_PRIORITY, TASK_TABLE_COLUMN_GROUP, TASK_TABLE_COLUMN_COMPLETION_STATUS},
				null, null, null, null, null);
	}
	
	// Find task by its id
	public Cursor getTaskById(String taskId){
		return sqLiteDatabase.query(TASK_TABLE_NAME,
				new String[] {TASK_TABLE_COLUMN_ID, TASK_TABLE_COLUMN_TITLE, TASK_TABLE_COLUMN_DUE_DATE, TASK_TABLE_COLUMN_NOTE, TASK_TABLE_COLUMN_PRIORITY, TASK_TABLE_COLUMN_GROUP, TASK_TABLE_COLUMN_COMPLETION_STATUS},
				TASK_TABLE_COLUMN_ID + " = '" + taskId + "'", null, null, null, null);
	}
	
	// Find all collaborators of a given task
	public Cursor getCollaboratorsByTaskId(String taskId){
		return sqLiteDatabase.query(COLLABORATOR_TABLE_NAME,
				new String[] {COLLABORATOR_TABLE_COLUMN_ID, COLLABORATOR_TABLE_COLUMN_EMAIL, COLLABORATOR_TABLE_COLUMN_TASK_ID},
				COLLABORATOR_TABLE_COLUMN_TASK_ID + " = '" + taskId + "'", null, null, null, null);
	}
	
	// Insert all collaborators of a given task
	public void insertCollaborators(Task task){
		for (String email : task.getCollaborators()){
			ContentValues initialValues = new ContentValues();
			initialValues.put(COLLABORATOR_TABLE_COLUMN_EMAIL, email);
			initialValues.put(COLLABORATOR_TABLE_COLUMN_TASK_ID, task.getId());
			sqLiteDatabase.insert(COLLABORATOR_TABLE_NAME, null, initialValues);
		}
	}
	
	// Edit an existing task
	public void editExistingTask(Task task){
		ContentValues updateValues;
		
		// Update Task table first
		updateValues = new ContentValues();
		updateValues.put(TASK_TABLE_COLUMN_TITLE, task.getTitle());
		updateValues.put(TASK_TABLE_COLUMN_NOTE, task.getNote());
		updateValues.put(TASK_TABLE_COLUMN_DUE_DATE, task.getDueDate().getTimeInMillis());
		updateValues.put(TASK_TABLE_COLUMN_PRIORITY, task.getPriorityLevel());
		updateValues.put(TASK_TABLE_COLUMN_GROUP, task.getGroup().getId());
		updateValues.put(TASK_TABLE_COLUMN_COMPLETION_STATUS, task.getCompletionStatus());
		sqLiteDatabase.update(TASK_TABLE_NAME, updateValues, TASK_TABLE_COLUMN_ID + " = '" + task.getId() + "'", null);
		
		// Update Collaborator table
		
	}
	
	// Return a new randomly generated task id
	public String getNewTaskId(){
		String uuid = null;
		Cursor cursor = null;
		
		// Create a random uuid and then check if it's exist
		do {
			uuid = UUID.randomUUID().toString();
			cursor = getTaskById(uuid);
		} while (cursor.getCount() > 0);
		
		return uuid;
	}
	
	// Return a new randomly generated group id
	public String getNewGroupId(){
		String uuid = null;
		Cursor cursor = null;
		
		// Create a random uuid and then check if it's exist
		// If yes, re-generate
		do {
			uuid = UUID.randomUUID().toString();
			cursor = getGroupById(uuid);
		} while (cursor.getCount() > 0);
		
		return uuid;
	}
}
