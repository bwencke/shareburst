package com.example.rest;

public class Assignments {

	private String userName;
	private int orange;
	private int pink;
	private int red;
	private int yellow;
	
	public Assignments() {
		orange = 0;
		pink = 0;
		red = 0;
		yellow = 0;
	}
	
	public int getOrange() {
		return orange;
	}

	public void setOrange(int orange) {
		this.orange = orange;
	}

	public int getPink() {
		return pink;
	}

	public void setPink(int pink) {
		this.pink = pink;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getYellow() {
		return yellow;
	}

	public void setYellow(int yellow) {
		this.yellow = yellow;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
