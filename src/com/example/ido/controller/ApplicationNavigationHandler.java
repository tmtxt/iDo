package com.example.ido.controller;

import com.example.ido.model.Group;
import com.example.ido.view.ModifyGroupActivity;
import com.example.ido.view.ModifyTaskActivity;
import com.example.ido.view.ViewAllGroupsActivity;
import com.example.ido.view.ViewAllTasksActivity;
import com.example.ido.view.ViewTaskDetailActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
	
	// Go to ViewTaskDetailActivity
	public static void viewTaskDetail(Activity sourceActivity){
		Intent viewTaskDetailIntent = new Intent(sourceActivity, ViewTaskDetailActivity.class);
		sourceActivity.startActivity(viewTaskDetailIntent);
	}
	
	// Go to ViewAllTasksActivity
	public static void showAllTasks(Activity sourceActivity){
		Intent showAllTasksIntent = new Intent(sourceActivity, ViewAllTasksActivity.class);
		sourceActivity.startActivity(showAllTasksIntent);
	}
	
	// Go to ModifyGroupActivity to add a new group
	public static void addNewGroup(Activity sourceActivity){
		Intent addNewGroupIntent = new Intent(sourceActivity, ModifyGroupActivity.class);
		sourceActivity.startActivity(addNewGroupIntent);
	}
	
	// Go to ModifyGroupActivity to edit an existing group
	public static void editExistingGroup(Activity sourceActivity, Group existingGroup){
		Intent editExistingGroupIntent = new Intent(sourceActivity, ModifyGroupActivity.class);
		// put the group to edit into bundle
		Bundle editExistingGroupBundle = new Bundle();
		editExistingGroupBundle.putSerializable(Group.GROUP_BUNDLE_KEY, existingGroup);
		// put the bundle into intent
		editExistingGroupIntent.putExtras(editExistingGroupBundle);
		// start the activity
		sourceActivity.startActivity(editExistingGroupIntent);
	}
	
	// Go to ModifyTaskActivity
	public static void addNewTask(Activity sourceActivity){
		Intent addNewTaskIntent = new Intent(sourceActivity, ModifyTaskActivity.class);
		sourceActivity.startActivity(addNewTaskIntent);
	}
}
