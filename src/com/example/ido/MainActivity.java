package com.example.ido;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView mainListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mainListView = (ListView) findViewById(R.id.activity_main_options_listview);
		mainListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View listView, int selectedItemId,
					long arg4) {
				// TODO Auto-generated method stub
				showAllGroups(adapterView, selectedItemId);
			}
		});
	}
	
	private void showAllGroups(AdapterView<?> adapterView, int selectedItemId){
		String selectedItem = (String) mainListView.getItemAtPosition(selectedItemId);
		if(selectedItem.equals(getString(R.string.activity_main_options_list_show_all_groups))){
			Intent intent = new Intent(this, ViewAllGroupsActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
