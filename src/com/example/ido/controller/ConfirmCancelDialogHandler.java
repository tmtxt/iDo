package com.example.ido.controller;

import com.example.ido.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

// This is a class to handle all Cancel Confirmation Dialogs in application
// This class is for reusable purpose
// Another reason is that the showDialog() method of Activity class is deprecated and I don't want to use it
public class ConfirmCancelDialogHandler {
	
	// STATIC FUNCTION USED FOR ACTIVATING DIALOG WITHIN THE APPLICATION
	
	// Static function used for show a Confirmation dialog when user select Back key, Cancel button,...
	// Can be used for those activities that take the input from user for confirmation when user has not saved the data yet.
	public static void showConfirmCancelDialog(Activity sourceActivity){
		Dialog confirmCancelDialog;
		confirmCancelDialog = new AlertDialog.Builder(sourceActivity)
			.setIcon(android.R.drawable.ic_menu_help)
			.setTitle(R.string.activity_modify_task_Dialog_confirm_cancel_TextView_label_String_title)
			.setPositiveButton(R.string.activity_modify_task_Dialog_confirm_cancel_Button_positive_String_title,
					new ConfirmCancelDialogPositiveButtonListener(sourceActivity))
			.setNegativeButton(R.string.activity_modify_task_Dialog_confirm_cancel_Button_negative_String_title,
					new ConfirmCancelDialogNegativeButtonListener())
			.create();
		confirmCancelDialog.show();
	}
	
	
	// STATIC CLASSES TO IMPLEMENT THE ACTION LISTENER FOR DIALOG BUTTONS  
	
	// An inner class to handle event when user select Yes button on Confirm Cancel dialog
	private static class ConfirmCancelDialogPositiveButtonListener implements OnClickListener{

		// sourceActivity to indicate the activity that call the dialog
		private Activity sourceActivity;
		
		// constructor
		public ConfirmCancelDialogPositiveButtonListener(Activity sourceActivity){
			this.sourceActivity = sourceActivity;
		}
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// If user selects yes, finish the current activity and return to the last activity in the back stack
			sourceActivity.finish();
		}
		
	}
	
	// An inner class to handle event when user select No button on Confirm Cancel dialog
	private static class ConfirmCancelDialogNegativeButtonListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// If user selects no, simply just close this dialog
			dialog.dismiss();
		}
		
	}
}
