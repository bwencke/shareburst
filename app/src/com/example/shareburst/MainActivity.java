package com.example.shareburst;

import com.example.data.UserName;
import com.example.rest.ModifyUser;
import com.example.rest.User;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends Activity implements ModifyUser {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		User u = new User();
//		u.setUserName("mouse");
//		u.setFirstName("bob");
		
		//new PutUser(MainActivity.this, this, u).execute();
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		Intent intent;
		if(UserName.getUserName(getApplicationContext()) == null) {
			intent = new Intent(this, LoginActivity.class);
		} else {
			intent = new Intent(this, HomeActivity.class);
		}
		startActivity(intent);
		finish();
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

	@Override
	public void modifyUserSuccess(ModifyUserMethods method, User user) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), user.getUserName(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void modifyUserFailure(ModifyUserMethods method, User user) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
	}
    
}
