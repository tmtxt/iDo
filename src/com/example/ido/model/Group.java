package com.example.ido.model;

import java.io.Serializable;
import java.util.List;

// A Model Class to reflect a Group
public class Group implements Serializable {
	
	// USER DEFINED STUFFS
	
	// A static variable to use when put it into bundle
	public static final String GROUP_BUNDLE_KEY = "group_bundle_key";  
	
	// The ID of this group, which is a randomly generated combination of numbers and letters
	private String id;
	
	// The title of this group
	private String title;
	
	// The list of tasks in this group
	private List<Task> tasksList;
	
	
	// COMPUTER GENERATED STUFFS
	
	public Group(){
		
	}

	public Group(String id, String title, List<Task> tasksList) {
		super();
		this.id = id;
		this.title = title;
		this.tasksList = tasksList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Task> getTasksList() {
		return tasksList;
	}

	public void setTasksList(List<Task> tasksList) {
		this.tasksList = tasksList;
	}
	
}
