package com.example.ido.model;

import java.util.List;

// A Model Class to reflect a Group
public class Group {
	
	// USER DEFINED STUFFS
	
	// The ID of this group, which is a randomly generated combination of numbers and letters
	private String id;
	
	// The title of this group
	private String title;
	
	// The list of tasks in this group
	private List<Task> tasksList;
	
	
	// COMPUTER GENERATED STUFFS

	public Group(String id, String title, List<Task> tasksList) {
		super();
		this.id = id;
		this.title = title;
		this.tasksList = tasksList;
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
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
