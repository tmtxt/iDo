package com.example.ido.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

// This is a class to handle all Message Dialogs in application
// This class is for reusable purpose
// Another reason is that the showDialog() method of Activity class is deprecated and I don't want to use it
public class MessageDialogHandler {

	// STATIC FUNCTION USED FOR ACTIVATING DIALOG WITHIN THE APPLICATION

	// Static function used for showing message dialog
	// Usage: pass the current activity that causes the dialog and the message to display
	public static void showMessageDialog(Activity sourceActivity, String message){
		Dialog messageDialog;
		messageDialog = new AlertDialog.Builder(sourceActivity)
			.setIcon(android.R.drawable.ic_menu_info_details)
			.setTitle(message)
			.setPositiveButton("OK", new MessageDialogPositiveButtonListener())
			.create();
		messageDialog.show();
	}

	// STATIC CLASS TO IMPLEMENT THE ACTION LISTENER FOR DIALOG BUTTONS

	// An inner class for handling OK button clicking
	private static class MessageDialogPositiveButtonListener implements OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			dialog.dismiss();
		}

	}
}
