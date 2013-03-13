package com.example.ido.view;

import com.example.ido.R;
import com.example.ido.R.layout;
import com.example.ido.R.menu;
import com.example.ido.controller.ApplicationNavigationHandler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class ViewTaskDetailActivity extends GeneralActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_task_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.activity_view_task_detail_Menu_actionbar_Item_add:
			ApplicationNavigationHandler.addNewTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
