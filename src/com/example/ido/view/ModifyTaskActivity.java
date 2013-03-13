package com.example.ido.view;

import com.example.ido.R;
import com.example.ido.R.layout;
import com.example.ido.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;

public class ModifyTaskActivity extends GeneralActivity {
	
	private static final int DIALOG_CANCEL_ID = 1;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		// When user select Cancel or Home button, show a confirmation dialog
		case R.id.activity_modify_task_Menu_actionbar_Item_cancel:
		case android.R.id.home:
			showDialog(DIALOG_CANCEL_ID);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		// When user press Back key, show a confirmation dialog
		showDialog(DIALOG_CANCEL_ID);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id){
		case DIALOG_CANCEL_ID:
			return getConfirmCancelDialog();
		default:
			return null;
		}
	}

	// Create and return the confirm dialog when user click Cancel button
	private Dialog getConfirmCancelDialog(){
		Dialog confirmCancelDialog;
		confirmCancelDialog = new AlertDialog.Builder(ModifyTaskActivity.this)
			.setIcon(android.R.drawable.ic_menu_help)
			.setTitle(R.string.activity_modify_task_Dialog_confirm_cancel_TextView_label_String_title)
			.setPositiveButton(R.string.activity_modify_task_Dialog_confirm_cancel_Button_positive_String_title,
					new ConfirmCancelDialogPositiveButtonListener())
			.setNegativeButton(R.string.activity_modify_task_Dialog_confirm_cancel_Button_negative_String_title,
					new ConfirmCancelDialogNegativeButtonListener())
			.create();
		return confirmCancelDialog;
	}
	
	// An inner class to handle event when user select Yes button on Confirm Cancel dialog
	private class ConfirmCancelDialogPositiveButtonListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();
		}
		
	}
	
	// An inner class to handle event when user select No button on Confirm Cancel dialog
	private class ConfirmCancelDialogNegativeButtonListener implements OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_task);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modify_task, menu);
		return true;
	}

}
