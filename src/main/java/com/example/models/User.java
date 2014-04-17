package com.example.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;
import java.util.TimeZone;

@XmlRootElement
public class User {

	private String userName;

	public User(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
