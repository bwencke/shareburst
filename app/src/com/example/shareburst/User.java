package com.example.shareburst;

public class User {
	
	enum prefs {
		ORANGE, PINK, RED, YELLOW
	}
	
	public String name;
	int preferences[];
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getPreferences() {
		return preferences;
	}
	public void setPreferences(int[] preferences) {
		this.preferences = preferences;
	}
}
