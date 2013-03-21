package com.example.ido.view;

import com.example.ido.R;
import com.example.ido.R.layout;
import com.example.ido.R.menu;
import com.example.ido.controller.ConfirmCancelDialogHandler;
import com.example.ido.model.Group;
import com.example.ido.model.Task;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

// This activity is used for 2 purposes: Creating new Task and Editing existing Task
// Every time it's loaded, the program should check whether the Task object in the bundle is exist
// If it's exist, this means the activity is going to edit that task
// Otherwise, the activity is going to create a new task
public class ModifyTaskActivity extends GeneralActivity {

	// a Task object to hold the data about the current task being processed
	// If the activity is creating a new task, this object will be null
	// Otherwise, it will be retrieved from the bundle
	private Task task = null;
	
	// the result code for Add Collaborator Button to call to other activity
	public static int SELECT_COLLABORATOR_ACTIVITY_RESULT_CODE = 1;

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
			// If the Task object exist, load data from it and put to the form
			putDataToForm();
			// After that, change the title of the activity to "Edit task " + task name
		} else {
			// If the Task object not exist, change the title of the activity to "Create new Task"
		}
		
		// Add action listener for the Select Collaborator button, show the contact list to choose email
		Button selectCollaboratorButton = (Button) findViewById(R.id.activity_modify_task_Button_select_collaborator);
		selectCollaboratorButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent selectCollaboratorIntent = new Intent(Intent.ACTION_PICK);
				selectCollaboratorIntent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);
				startActivityForResult(selectCollaboratorIntent,
						ModifyTaskActivity.SELECT_COLLABORATOR_ACTIVITY_RESULT_CODE);
			}
		});
	}

	// This function is used to load data from this.task object and put it to form
	private void putDataToForm(){
		// First check if the task object is exist
		if (this.task != null){
			// Now retrieve data from this Task oject and put it into form components
			// set task title
			EditText taskTitleEditText = (EditText) findViewById(R.id.activity_modify_task_Edittext_task_title);
			taskTitleEditText.setText(this.task.getTitle());
			// set task date
			DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.activity_modify_task_Datepicker_due_date);
			taskDueDatePicker.updateDate(this.task.getDueDate().getYear(), this.task.getDueDate().getMonth(), this.task.getDueDate().getDate());
			// set task note
			EditText taskNoteEditText = (EditText) findViewById(R.id.activity_modify_task_EditText_note);
			taskNoteEditText.setText(this.task.getNote());
			// set priority level
			Spinner taskPriorityLevelSpinner = (Spinner) findViewById(R.id.activity_modify_task_Spinner_priority_level);
			taskPriorityLevelSpinner.setSelection(this.task.getPriorityLevel());
			// set group
			
			// set completion status
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modify_task, menu);
		return true;
	}

}
