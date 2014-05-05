package com.example.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.data.UserName;
import com.example.rest.ModifyGroup.ModifyGroupMethods;

import retrofit.RestAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public interface ModifyUser {

	enum ModifyUserMethods {
		LIST, GET, PUT, DELETE, LOGIN
	}
	
	public void modifyUserSuccess(ModifyUserMethods method, Object user);
	public void modifyUserFailure(ModifyUserMethods method, Object user);
	
	class ListUser extends AsyncTask<String, Void, ArrayList<User>> {

		Context context;
		ModifyUser callingActivity;
		ProgressDialog progDialog;
		
		public ListUser(Context context, ModifyUser callingActivity) {
			this.context = context;
			this.callingActivity = callingActivity;
		}
		
		protected void onPreExecute() {
			progDialog = new ProgressDialog(context);
        	progDialog.setMessage("Fetching");
        	progDialog.show();
	    }
		
	    protected ArrayList<User> doInBackground(String... urls) {
	    	
	    	ArrayList<User> users = null;
	    	try {
	    		RestAdapter restAdapter = new RestAdapter.Builder()
	    			.setEndpoint(RestApi.API_URL)
	    			.build();

	    		// Create an instance of our API interface.
	    		RestApi restApi = restAdapter.create(RestApi.class);
		
	    		users = restApi.getUsers();
	    	} catch (Exception e) {
	    		
	    	}
			return users;
	    }

	    protected void onPostExecute(ArrayList<User> users) {
	    	progDialog.dismiss();
	        if(users != null) {
	        	Collections.sort(users, new Comparator<User>() {
					@Override
					public int compare(User u1, User u2) {
						String lastName1 = ((u1.getLastName() == null) ? "" : u1.getLastName());
						String lastName2 = ((u2.getLastName() == null) ? "" : u2.getLastName());
						return lastName1.compareToIgnoreCase(lastName2);
					}
	            });
	        	callingActivity.modifyUserSuccess(ModifyUserMethods.LIST, users);
	        } else {
	        	callingActivity.modifyUserFailure(ModifyUserMethods.LIST, users);
	        }
	    }
	}
	
	class GetUser extends AsyncTask<String, Void, User> {

		Context context;
		ModifyUser callingActivity;
		String userName;
		ProgressDialog progDialog;
		
		public GetUser(Context context, ModifyUser callingActivity, String userName) {
			this.context = context;
			this.callingActivity = callingActivity;
			this.userName = userName;
		}
		
		protected void onPreExecute() {
			progDialog = new ProgressDialog(context);
        	progDialog.setMessage("Fetching");
        	progDialog.show();
	    }
		
	    protected User doInBackground(String... urls) {
	    	
	    	User user = null;
	    	try {
	    		RestAdapter restAdapter = new RestAdapter.Builder()
	    			.setEndpoint(RestApi.API_URL)
	    			.build();

	    		// Create an instance of our API interface.
	    		RestApi restApi = restAdapter.create(RestApi.class);
		
	    		user = restApi.getUser(userName);
	    	} catch (Exception e) {
	    		
	    	}
			return user;
	    }

	    protected void onPostExecute(User user) {
	    	progDialog.dismiss();
	        if(user != null) {
	        	callingActivity.modifyUserSuccess(ModifyUserMethods.GET, user);
	        } else {
	        	callingActivity.modifyUserFailure(ModifyUserMethods.GET, user);
	        }
	    }
	}
	
	class PutUser extends AsyncTask<String, Void, User> {

		Context context;
		ModifyUser callingActivity;
		User user;
		ProgressDialog progDialog;
		
		public PutUser(Context context, ModifyUser callingActivity, User user) {
			this.context = context;
			this.callingActivity = callingActivity;
			this.user = user;
		}
		
		protected void onPreExecute() {
			progDialog = new ProgressDialog(context);
        	progDialog.setMessage("Loading");
        	progDialog.show();
	    }
		
	    protected User doInBackground(String... urls) {
	    	
	    	User newUser = null;
	    	try {
		    	RestAdapter restAdapter = new RestAdapter.Builder()
		        .setEndpoint(RestApi.API_URL)
		        .build();
	
			    // Create an instance of our API interface.
			    RestApi restApi = restAdapter.create(RestApi.class);
			
			    newUser = restApi.putUser(user);
	    	} catch (Exception e) {
	    		
	    	}
			return newUser;
	    }

	    protected void onPostExecute(User user) {
	    	progDialog.dismiss();
	        if(user != null) {
	        	callingActivity.modifyUserSuccess(ModifyUserMethods.PUT, user);
	        } else {
	        	callingActivity.modifyUserFailure(ModifyUserMethods.PUT, user);
	        }
	    }
	}
	
	class LoginUser extends AsyncTask<String, Void, User> {

		Context context;
		ModifyUser callingActivity;
		User user;
		ProgressDialog progDialog;
		
		public LoginUser(Context context, ModifyUser callingActivity, User user) {
			this.context = context;
			this.callingActivity = callingActivity;
			this.user = user;
		}
		
		protected void onPreExecute() {
			progDialog = new ProgressDialog(context);
        	progDialog.setMessage("Checking Credentials");
        	progDialog.show();
	    }
		
	    protected User doInBackground(String... urls) {
	    	
	    	User u = null;
	    	try {
		    	RestAdapter restAdapter = new RestAdapter.Builder()
		        .setEndpoint(RestApi.API_URL)
		        .build();
	
			    // Create an instance of our API interface.
			    RestApi restApi = restAdapter.create(RestApi.class);
			    u = restApi.loginUser(user);
			    //Log.i("bool", ""+b);
	    	} catch(Exception e) {
	    		
	    	}
		    return u;
	    }

	    protected void onPostExecute(User u) {
	    	progDialog.dismiss();
	        if(u != null) {
	        	u.setPassword(user.getPassword());
	        	UserName.setUser(u);
	        	UserName.setUserName(context, u.getUserName());
	        	UserName.setPassword(context, u.getPassword());
	        	callingActivity.modifyUserSuccess(ModifyUserMethods.LOGIN, u);
	        } else {
	        	callingActivity.modifyUserFailure(ModifyUserMethods.LOGIN, u);
	        }
	    }
	}
	
}
