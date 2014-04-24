package com.example.shareburst;

import retrofit.RestAdapter;
import android.content.Context;
import android.os.AsyncTask;

public interface ModifyUser {

	enum ModifyUserMethods {
		LIST, GET, PUT, DELETE
	}
	
	public void modifyUserSuccess(ModifyUserMethods method, User user);
	public void modifyUserFailure(ModifyUserMethods method, User user);
	
	class GetUser extends AsyncTask<String, Void, User> {

		Context context;
		ModifyUser callingActivity;
		String userName;
		
		public GetUser(Context context, ModifyUser callingActivity, String userName) {
			this.context = context;
			this.callingActivity = callingActivity;
			this.userName = userName;
		}
		
		protected void onPreExecute(User user) {
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
		
		public PutUser(Context context, ModifyUser callingActivity, User user) {
			this.context = context;
			this.callingActivity = callingActivity;
			this.user = user;
		}
		
		protected void onPreExecute(User user) {
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
	        if(user != null) {
	        	callingActivity.modifyUserSuccess(ModifyUserMethods.PUT, user);
	        } else {
	        	callingActivity.modifyUserFailure(ModifyUserMethods.PUT, user);
	        }
	    }
	}
	
}
