package com.example.ido;

import android.app.Activity;
import android.content.Intent;

// A class consists of static functions that help the application navigation between activity
// The idea of the class is that many events can call to the same activity,
// so that every time they need to start one particular activity, just call the static function here
// and it will do the rest of the work
// This is for reusable purpose
public class ApplicationNavigationHandler {
	
	// Go to ViewAllGroupsActivity
	public static void showAllGroups(Activity sourceActivity){
		Intent showAllGroupsIntent = new Intent(sourceActivity, ViewAllGroupsActivity.class);
		sourceActivity.startActivity(showAllGroupsIntent);
	}
	
	// Go to ViewAllTasksActivity
	public static void showAllTasks(Activity sourceActivity){
		Intent showAllTasksIntent = new Intent(sourceActivity, ViewAllTasksActivity.class);
		sourceActivity.startActivity(showAllTasksIntent);
	}
	
	// Go to ModifyGroupActivity
	public static void addNewGroup(Activity sourceActivity){
		Intent addNewGroupIntent = new Intent(sourceActivity, ModifyGroupActivity.class);
		sourceActivity.startActivity(addNewGroupIntent);
	}
}
