package com.example.rest;

import java.util.ArrayList;

import retrofit.RestAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public interface ModifyGroup {

	enum ModifyGroupMethods {
		LIST, PUT, DELETE
	}
	
	public void modifyGroupSuccess(ModifyGroupMethods method, Object group);
	public void modifyGroupFailure(ModifyGroupMethods method, Object group);
	
	class ListGroup extends AsyncTask<String, Void, ArrayList<Group>> {

		Context context;
		ModifyGroup callingActivity;
		String userName;
		ProgressDialog progDialog;
		
		public ListGroup(Context context, ModifyGroup callingActivity, String userName) {
			this.context = context;
			this.callingActivity = callingActivity;
			this.userName = userName;
		}
		
		protected void onPreExecute() {
			progDialog = new ProgressDialog(context);
        	progDialog.setMessage("Fetching");
        	progDialog.show();
	    }
		
	    protected ArrayList<Group> doInBackground(String... urls) {
	    	
	    	ArrayList<Group> groups = null;
	    	try {
	    		RestAdapter restAdapter = new RestAdapter.Builder()
	    			.setEndpoint(RestApi.API_URL)
	    			.build();

	    		// Create an instance of our API interface.
	    		RestApi restApi = restAdapter.create(RestApi.class);
		
	    		groups = restApi.getGroups(userName);
	    	} catch (Exception e) {
	    		
	    	}
			return groups;
	    }

	    protected void onPostExecute(ArrayList<Group> group) {
	    	progDialog.dismiss();
	        if(group != null) {
	        	callingActivity.modifyGroupSuccess(ModifyGroupMethods.LIST, group);
	        } else {
	        	callingActivity.modifyGroupFailure(ModifyGroupMethods.LIST, group);
	        }
	    }
	}
	
	class PutGroup extends AsyncTask<String, Void, Group> {

		Context context;
		ModifyGroup callingActivity;
		Group group;
		ProgressDialog progDialog;
		
		public PutGroup(Context context, ModifyGroup callingActivity, Group group) {
			this.context = context;
			this.callingActivity = callingActivity;
			this.group = group;
		}
		
		protected void onPreExecute() {
			progDialog = new ProgressDialog(context);
        	progDialog.setMessage("Loading");
        	progDialog.show();
	    }
		
	    protected Group doInBackground(String... urls) {
	    	
	    	Group newGroup = null;
	    	try {
		    	RestAdapter restAdapter = new RestAdapter.Builder()
		        .setEndpoint(RestApi.API_URL)
		        .build();
	
			    // Create an instance of our API interface.
			    RestApi restApi = restAdapter.create(RestApi.class);
			
			    newGroup = restApi.putGroup(group);
	    	} catch (Exception e) {
	    		
	    	}
			return newGroup;
	    }

	    protected void onPostExecute(Group group) {
	    	progDialog.dismiss();
	        if(group != null) {
	        	callingActivity.modifyGroupSuccess(ModifyGroupMethods.PUT, group);
	        } else {
	        	callingActivity.modifyGroupFailure(ModifyGroupMethods.PUT, group);
	        }
	    }
	}
	
	class DeleteGroup extends AsyncTask<String, Void, Boolean> {

		Context context;
		ModifyGroup callingActivity;
		int groupID;
		ProgressDialog progDialog;
		
		public DeleteGroup(Context context, ModifyGroup callingActivity, int groupID) {
			this.context = context;
			this.callingActivity = callingActivity;
			this.groupID = groupID;
		}
		
		protected void onPreExecute() {
			progDialog = new ProgressDialog(context);
        	progDialog.setMessage("Deleting");
        	progDialog.show();
	    }
		
	    protected Boolean doInBackground(String... urls) {
	    	
	    	Boolean b = false;
	    	try {
		    	RestAdapter restAdapter = new RestAdapter.Builder()
		        .setEndpoint(RestApi.API_URL)
		        .build();
	
			    // Create an instance of our API interface.
			    RestApi restApi = restAdapter.create(RestApi.class);
			
			    b = restApi.deleteGroup(groupID);
	    	} catch (Exception e) {
	    		
	    	}
	    	return b;
	    }

	    protected void onPostExecute(Boolean result) {
	    	progDialog.dismiss();
	        if(result) {
	        	callingActivity.modifyGroupSuccess(ModifyGroupMethods.DELETE, null);
	        } else {
	        	callingActivity.modifyGroupFailure(ModifyGroupMethods.DELETE, null);
	        }
	    }
	}
	
}
