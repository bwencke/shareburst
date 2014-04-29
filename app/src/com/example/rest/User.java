package com.example.rest;

import java.util.ArrayList;
public class User {

	public static ArrayList<User> users = new ArrayList<User>();
	
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private Preferences preferences;

	public User() {
		preferences = new Preferences();
	}
	
	public User(String userName) {
		this();
		this.userName = userName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Preferences getPreferences() {
		return preferences;
	}

	public int[] getPreferencesArray() {
		return new int[]{preferences.getOrange(), preferences.getPink(), preferences.getRed(), preferences.getYellow()};
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o == null) {
			return false;
		}
		if(((User) o).getUserName().equals(this.getUserName())) {
			return true;
		}
		return false;
	}

}
