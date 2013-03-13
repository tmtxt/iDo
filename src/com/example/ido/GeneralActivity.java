package com.example.ido;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

// This class is for reusable purpose
// This class set some default configurations for all applications
// All activities in this Application except MainActivity extend this class
// so that the application can keep the same layout display
public class GeneralActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// enable icon for up navigation
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
}
