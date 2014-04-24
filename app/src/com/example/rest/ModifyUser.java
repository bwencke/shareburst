package com.example.rest;

import com.example.data.UserName;

import retrofit.RestAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public interface ModifyUser {

	enum ModifyUserMethods {
		LIST, GET, PUT, DELETE, LOGIN
	}
	
	public void modifyUserSuccess(ModifyUserMethods method, User user);
	public void modifyUserFailure(ModifyUserMethods method, User user);
	
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
	    	
	    	RestAdapter restAdapter = new RestAdapter.Builder()
	        .setEndpoint(RestApi.API_URL)
	        .build();

		    // Create an instance of our API interface.
		    RestApi restApi = restAdapter.create(RestApi.class);
		
		    User user = restApi.getUser(userName);
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
	    	
	    	RestAdapter restAdapter = new RestAdapter.Builder()
	        .setEndpoint(RestApi.API_URL)
	        .build();

		    // Create an instance of our API interface.
		    RestApi restApi = restAdapter.create(RestApi.class);
		
		    user = restApi.putUser(user);
			return user;
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
	
	class LoginUser extends AsyncTask<String, Void, Boolean> {

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
		
	    protected Boolean doInBackground(String... urls) {
	    	
	    	RestAdapter restAdapter = new RestAdapter.Builder()
	        .setEndpoint(RestApi.API_URL)
	        .build();

		    // Create an instance of our API interface.
		    RestApi restApi = restAdapter.create(RestApi.class);
		
		    return restApi.loginUser(user);
	    }

	    protected void onPostExecute(Boolean success) {
	    	progDialog.dismiss();
	        if(success) {
	        	UserName.setUserName(context, user.getUserName());
	        	callingActivity.modifyUserSuccess(ModifyUserMethods.LOGIN, user);
	        } else {
	        	callingActivity.modifyUserFailure(ModifyUserMethods.LOGIN, user);
	        }
	    }
	}
	
}
