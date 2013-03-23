package com.example.ido.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.ido.R;
import com.example.ido.model.DatabaseAdapter;
import com.example.ido.model.Task;

// This is a class to handle all Delete Confirmation Dialogs in application
// This class is for reusable purpose
// Another reason is that the showDialog() method of Activity class is deprecated and I don't want to use it
public class ConfirmDeletionDialog {

	// STATIC FUNCTIONS USED FOR ACTIVATING DIALOG WITHIN THE APPLICATION

	// Static function used for show a Confirmation dialog when user select Delete Task
	public static void showConfirmDeleteDialogForTask(Activity sourceActivity, Task task, DatabaseAdapter databaseAdapter){
		Dialog confirmCancelDialog;
		confirmCancelDialog = new AlertDialog.Builder(sourceActivity)
		.setIcon(android.R.drawable.ic_menu_help)
		.setTitle("Are you sure to want to delete?")
		.setPositiveButton("Yes",
				new ConfirmDeleteDialogForTaskPositiveButtonListener(sourceActivity, task, databaseAdapter))
		.setNegativeButton("No",
				new ConfirmDeleteDialogNegativeButtonListener())
		.create();
		confirmCancelDialog.show();
	}


	// STATIC CLASSES TO IMPLEMENT THE ACTION LISTENER FOR DIALOG BUTTONS  

	// An inner class to handle event when user select Yes button on Confirm Cancel dialog
	private static class ConfirmDeleteDialogForTaskPositiveButtonListener implements OnClickListener{

		// sourceActivity to indicate the activity that call the dialog
		private Activity sourceActivity;
		
		// the task to delete
		private Task task;
		
		// the database adapter to handle the database
		private DatabaseAdapter databaseAdapter;

		// constructor
		public ConfirmDeleteDialogForTaskPositiveButtonListener(Activity sourceActivity, Task task, DatabaseAdapter databaseAdapter){
			this.sourceActivity = sourceActivity;
			this.task = task;
			this.databaseAdapter = databaseAdapter;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// Delete the current task
			databaseAdapter.deleteTask(task);
			
			// If user selects yes, finish the current activity and return to the last activity in the back stack
			sourceActivity.finish();
		}

	}

	// An inner class to handle event when user select No button on Confirm Cancel dialog
	private static class ConfirmDeleteDialogNegativeButtonListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// If user selects no, simply just close this dialog
			dialog.dismiss();
		}

	}
}
