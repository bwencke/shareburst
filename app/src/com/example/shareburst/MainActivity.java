package com.example.shareburst;

import java.util.List;

import retrofit.RestAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public final static String API_URL = "http://shareburst.herokuapp.com/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new RestTask().execute();
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void alert(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
	}

	  class RestTask extends AsyncTask<String, Void, User> {

		    protected User doInBackground(String... urls) {
		    	
			    	RestAdapter restAdapter = new RestAdapter.Builder()
			        .setEndpoint(API_URL)
			        .build();
	
				    // Create an instance of our GitHub API interface.
				    RestApi restApi = restAdapter.create(RestApi.class);
				
				    // Fetch and print a list of the contributors to this library.
				    User user = restApi.getUser("trohman");
				    //Toast.makeText(getApplicationContext(), res.toString(), Toast.LENGTH_LONG).show();
				    System.out.println(user.getUserName());
//				    for (User user : res) {
//				    	System.out.println(user.name);
//				    }
				return user;
		    }

		    protected void onPostExecute(User user) {
		        // TODO: check this.exception 
		        // TODO: do something with the feed
		    	alert(user.getFirstName() + "\n" + user.getPassword());
		    }
		}
    
}
