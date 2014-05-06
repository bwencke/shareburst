package com.shareburst.main;

import com.example.rest.ModifyUser;
import com.example.rest.User;
import com.shareburst.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
	public static class PlaceholderFragment extends Fragment implements ModifyUser {

		EditText nameView;
		EditText usernameView;
		EditText passwordView;
		Button registerButton;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_register,
					container, false);
			
			String username = getActivity().getIntent().getStringExtra("username");
			String password = getActivity().getIntent().getStringExtra("password");
			
			nameView = (EditText) rootView.findViewById(R.id.nameField);
			nameView.requestFocus();
			usernameView = (EditText) rootView.findViewById(R.id.usernameField);
			usernameView.setText(username);
			passwordView = (EditText) rootView.findViewById(R.id.passwordField);
			passwordView.setText(password);
			registerButton = (Button) rootView.findViewById(R.id.registerButton);
			registerButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					register();
				}
				
			});
			
			return rootView;
		}
		
		public void register() {
			User newUser = new User();
			if(nameView.getText() != null) {
				String name = nameView.getText().toString();
				String firstName = name;
				String lastName = null;
				if(name.lastIndexOf(" ") > 0) {
					firstName = name.substring(0,name.lastIndexOf(" "));
					lastName = name.substring(name.lastIndexOf(" ")+1,name.length());
				}
				newUser.setFirstName(firstName);
				newUser.setLastName(lastName);
			}
			newUser.setUserName(usernameView.getText().toString());
			newUser.setPassword(passwordView.getText().toString());
			new PutUser(getActivity(), this, newUser).execute();
		}

		@Override
		public void modifyUserSuccess(ModifyUserMethods method, Object user) {
			// TODO Auto-generated method stub
			Intent returnIntent = new Intent();
			returnIntent.putExtra("username",usernameView.getText().toString());
			returnIntent.putExtra("password",passwordView.getText().toString());
			getActivity().setResult(RESULT_OK,returnIntent);     
			getActivity().finish();
		}

		@Override
		public void modifyUserFailure(ModifyUserMethods method, Object user) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "Failed to create user.", Toast.LENGTH_SHORT).show();
		}

	}

}
