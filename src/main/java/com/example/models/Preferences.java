package com.example.models;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@XmlRootElement
public class Preferences {

	public int orange;
	public int pink;
	public int red;
	public int yellow;
	
	public Preferences() {
		orange = 0;
		pink = 0;
		red = 0;
		yellow = 0;
	}
	
}
