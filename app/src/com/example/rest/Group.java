package com.example.rest;

import java.util.ArrayList;

public class Group {
	
	private int groupID;
	private String groupName;
	private int nPackets;
	private ArrayList<Assignments> assignments;
	
	public Group()
	{
		assignments = new ArrayList<Assignments>();
	}

	public int getnPackets() {
		return nPackets;
	}

	public void setnPackets(int nPackets) {
		this.nPackets = nPackets;
	}

	public ArrayList<Assignments> getAssignments() {
		return assignments;
	}

	public void setAssignments(ArrayList<Assignments> assignments) {
		this.assignments = assignments;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
}
