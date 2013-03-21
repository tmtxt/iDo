package com.example.ido.view;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.ido.R;
import com.example.ido.R.layout;
import com.example.ido.R.menu;
import com.example.ido.controller.ConfirmCancelDialogHandler;
import com.example.ido.model.Group;
import com.example.ido.model.Task;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

// This activity is used for 2 purposes: Creating new Task and Editing existing Task
// Every time it's loaded, the program should check whether the Task object in the bundle is exist
// If it's exist, this means the activity is going to edit that task
// Otherwise, the activity is going to create a new task
public class ModifyTaskActivity extends GeneralActivity {

	// VARIABLES DEFINED HERE

	// a Task object to hold the data about the current task being processed
	// If the activity is creating a new task, this object will be initialized
	// Otherwise, it will be retrieved from the bundle
	private Task task = null;

	// store the current job of this activity, Edit or Add task
	// value will be set from those 2 static variables below
	private int currentJob;
	private final int CURRENT_JOB_EDIT = 1;
	private final int CURRENT_JOB_ADD = 2;

	// the result code for Add Collaborator Button to call to other activity
	public static final int SELECT_COLLABORATOR_ACTIVITY_RESULT_CODE = 1;

	// adapter for Collaborators ListView
	private ArrayAdapter<String> collaboratorsListViewAdapter;


	// OVERIDE FUNCTIONS

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){

		// When user select Cancel or Home button, show a confirmation dialog
		case R.id.activity_modify_task_Menu_actionbar_Item_cancel:
		case android.R.id.home:
			// Show the confirmation dialog
			// The rest (leave activity or not), the function ApplicationDialogHandler.showConfirmCancelDialog() will handle
			ConfirmCancelDialogHandler.showConfirmCancelDialog(this);
			return true;

			// default case, return the base class function
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		// Show the confirmation dialog
		// The rest (leave activity or not), the function ApplicationDialogHandler.showConfirmCancelDialog() will handle
		ConfirmCancelDialogHandler.showConfirmCancelDialog(this);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_task);

		// Check whether this activity is going to create a new Task or edit an existing one
		// First, retrieve the Task object from the bundle
		Bundle modifyTaskBundle = this.getIntent().getExtras();
		try {
			this.task = (Task) modifyTaskBundle.getSerializable(Task.TASK_BUNDLE_KEY);
		} catch (Exception ex){
			ex.printStackTrace();
		}
		// Next, check if it is null
		if (task != null){
			// Change the currentJob to edit task
			this.currentJob = this.CURRENT_JOB_EDIT;
			// If the Task object exist, load data from it and put to the form
			putDataToForm();
			// After that, change the title of the activity to "Edit task " + task name
		} else {
			// Init the new Task object
			this.task = new Task();
			// Change the currentJob to add new task
			this.currentJob = this.CURRENT_JOB_ADD;
			// If the Task object not exist, change the title of the activity to "Create new Task"
		}

		// init the collaborators
		this.initColaborators();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// check if request code is from Select Collaborator Button
		if(requestCode == ModifyTaskActivity.SELECT_COLLABORATOR_ACTIVITY_RESULT_CODE){
			// Now get the email address of the contact and then update the list view
			if(data != null){
				// get the email address
				String collaboratorEmailAddress = getCollaboratorEmailAddress(data);
				// update the listview
				this.task.getCollaborators().add(collaboratorEmailAddress);
				this.collaboratorsListViewAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modify_task, menu);
		return true;
	}


	// UTILITY FUNCTIONS

	// This functions is used to init collaborators list from the list adapter, list view...
	// to action listener for them
	// need to be called in the onCreate() method
	private void initColaborators(){
		// init the Collaborator ListView
		collaboratorsListViewAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, this.task.getCollaborators());
		ListView collaboratorsListView = (ListView) findViewById(R.id.activity_modify_task_ListView_collaborators);
		collaboratorsListView.setAdapter(collaboratorsListViewAdapter);

		// Add action listener for the Select Collaborator button, show the contact list to choose email
		Button selectCollaboratorButton = (Button) findViewById(R.id.activity_modify_task_Button_select_collaborator);
		selectCollaboratorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// show the Address book for user to choose colaborators
				selectCollaboratorEmail();
			}
		});
		
		// Add action listener for the Clear Collaborators button, empty the collaborator list
		Button clearCollaboratorsButton = (Button) findViewById(R.id.activity_modify_task_Button_clear_collaborator);
		clearCollaboratorsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clearCollaboratorsList();
			}
		});
	}
	
	// This function clears all collaborators
	private void clearCollaboratorsList(){
		this.task.getCollaborators().clear();
		this.collaboratorsListViewAdapter.notifyDataSetChanged();
	}

	// This function is to show the Address book for user to choose collaborator email
	private void selectCollaboratorEmail(){
		Intent selectCollaboratorIntent = new Intent(Intent.ACTION_PICK);
		selectCollaboratorIntent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);
		startActivityForResult(selectCollaboratorIntent,
				ModifyTaskActivity.SELECT_COLLABORATOR_ACTIVITY_RESULT_CODE);
	}

	// This function is used to get collaborator's email address
	// After pressing Add Collaborator button, a new activity appears for user to select collaborator
	// After finishing selecting collaborator, call this function and pass the Intent that activity return
	// Reference: http://stackoverflow.com/questions/4993063/how-to-call-android-contacts-list-and-select-one-phone-number-from-its-details-s
	private String getCollaboratorEmailAddress(Intent data){
		String collaboratorEmailAddress = null;		
		Uri uri = data.getData();

		if (uri != null) {
			Cursor c = null;
			try {
				c = getContentResolver().query(uri, new String[]{ 
						ContactsContract.CommonDataKinds.Email.ADDRESS,  
						ContactsContract.CommonDataKinds.Email.TYPE },
						null, null, null);

				if (c != null && c.moveToFirst()) {
					collaboratorEmailAddress = c.getString(0);
				}
			} finally {
				if (c != null) {
					c.close();
				}
			}
		}

		return collaboratorEmailAddress;
	}

	// This function is used to load data from this.task object and put it to form
	private void putDataToForm(){
		// First check if the current job is edit task
		if (this.currentJob == this.CURRENT_JOB_EDIT){
			// Now retrieve data from this Task oject and put it into form components
			// no need to set Collaborator list since it's a special component and there is another function that handles that task

			// set task title
			EditText taskTitleEditText = (EditText) findViewById(R.id.activity_modify_task_Edittext_task_title);
			taskTitleEditText.setText(this.task.getTitle());

			// set task date
			DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.activity_modify_task_Datepicker_due_date);
			taskDueDatePicker.updateDate(this.task.getDueDate().get(Calendar.YEAR),
					this.task.getDueDate().get(Calendar.MONTH),
					this.task.getDueDate().get(Calendar.DATE));
			
			// set task note
			EditText taskNoteEditText = (EditText) findViewById(R.id.activity_modify_task_EditText_note);
			taskNoteEditText.setText(this.task.getNote());
			
			// set priority level
			Spinner taskPriorityLevelSpinner = (Spinner) findViewById(R.id.activity_modify_task_Spinner_priority_level);
			taskPriorityLevelSpinner.setSelection(this.task.getPriorityLevel());
			
			// set group

			// set completion status
			Spinner completionStatusSpinner = (Spinner) findViewById(R.id.activity_modify_task_Spinner_completion_status);
			completionStatusSpinner.setSelection(this.task.getCompletionStatus());
		}
	}

}
