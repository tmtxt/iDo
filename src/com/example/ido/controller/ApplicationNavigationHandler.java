package com.example.ido.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.ido.model.Group;
import com.example.ido.model.Task;
import com.example.ido.view.ModifyGroupActivity;
import com.example.ido.view.ModifyTaskActivity;
import com.example.ido.view.SettingActivity;
import com.example.ido.view.ViewAllGroupsActivity;
import com.example.ido.view.ViewAllTasksActivity;
import com.example.ido.view.ViewTaskDetailActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Note;

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
	
	// Go to SettingActivity
	public static void showSetting(Activity sourceActivity){
		Intent showSettingIntent = new Intent(sourceActivity, SettingActivity.class);
		sourceActivity.startActivity(showSettingIntent);
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
	
	// Go to ModifyTaskActivity to edit and existing Task
	public static void editExistingTask(Activity sourceActivity, Task existingTask){
		// create a skeleton
		Date date = new Date();
		List<String> collaborators = new ArrayList<String>();
		collaborators.add("truong.tx@facebook.com");
		collaborators.add("tommy.txtruong@gmail.com");
		collaborators.add("s3393320@rmit.edu.vn");
		Group group = new Group("groupID", "To do this week", null);
		Task task = new Task("taskID", "Go shopping", date, "This is something I need to do in this week", Task.HIGH_PRIORITY, collaborators, group, Task.TASK_COMPLETED);
		
		Intent editExistingTaskIntent = new Intent(sourceActivity, ModifyTaskActivity.class);
		// put the task to edit into the bundle
		Bundle editExistingTaskBundle = new Bundle();
		// editExistingTaskBundle.putSerializable(Task.TASK_BUNDLE_KEY, existingTask);
		editExistingTaskBundle.putSerializable(Task.TASK_BUNDLE_KEY, task);
		// put the bundle into intent
		editExistingTaskIntent.putExtras(editExistingTaskBundle);
		// start the activity
		sourceActivity.startActivity(editExistingTaskIntent);
	}
	
	// Go to ModifyTaskActivity
	public static void addNewTask(Activity sourceActivity){
		Intent addNewTaskIntent = new Intent(sourceActivity, ModifyTaskActivity.class);
		sourceActivity.startActivity(addNewTaskIntent);
	}
}
