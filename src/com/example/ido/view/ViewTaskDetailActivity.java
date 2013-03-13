package com.example.ido.view;

import com.example.ido.R;
import com.example.ido.R.layout;
import com.example.ido.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

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

}
