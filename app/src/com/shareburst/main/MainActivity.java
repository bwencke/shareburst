package com.shareburst.main;

import com.example.data.UserName;
import com.example.rest.ModifyUser;
import com.example.rest.User;
import com.shareburst.R;
import com.shareburst.group.GroupsActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
			startActivity(intent);
			finish();
		} else {
			new GetUser(MainActivity.this, this, UserName.getUserName(getApplicationContext())).execute();
		}
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
	public void modifyUserSuccess(ModifyUserMethods method, Object user) {
		// TODO Auto-generated method stub
		String pass = UserName.getPassword(getApplicationContext());
		UserName.setUser((User) user);
		UserName.setPassword(getApplicationContext(), pass);
		Intent intent = new Intent(this, GroupsActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void modifyUserFailure(ModifyUserMethods method, Object user) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
	}
    
}
