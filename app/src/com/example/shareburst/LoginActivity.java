package com.example.shareburst;

import com.example.data.UserName;
import com.example.rest.ModifyUser;
import com.example.rest.User;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity implements ModifyUser {

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private LoginUser mAuthTask = null;
	User user;

	// Values for email and password at the time of the login attempt.
	private String mUserName;
	private String mPassword;

	// UI references.
	private EditText mUserNameView;
	private EditText mPasswordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		user = new User();
		
		// Set up the login form.
		mUserName = getIntent().getStringExtra(EXTRA_EMAIL);
		mUserNameView = (EditText) findViewById(R.id.username);
		mUserNameView.setText(mUserName);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		
		findViewById(R.id.register_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
						intent.putExtra("username", mUserNameView.getText().toString());
						intent.putExtra("password", mPasswordView.getText().toString());
						startActivityForResult(intent, 1);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK) {
			try {
				String username = (String) data.getCharSequenceExtra("username");
				String password = (String) data.getCharSequenceExtra("password");
				
				mUserNameView.setText(username);
				mPasswordView.setText(password);
				
				mPasswordView.setSelection(mPasswordView.getText().length());
				
			} catch(Exception e) {
				Toast.makeText(getApplicationContext(), "Oops. Something went wrong.", Toast.LENGTH_LONG).show();
				return;
			}
		
			attemptLogin();
		}
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUserNameView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUserName = mUserNameView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mUserName)) {
			mUserNameView.setError(getString(R.string.error_field_required));
			focusView = mUserNameView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			login();
		}
	}

	private void login() {
		user.setUserName(mUserName);
		user.setPassword(mPassword);
		mAuthTask = new LoginUser(LoginActivity.this, this, user);
		mAuthTask.execute();
	}

	@Override
	public void modifyUserSuccess(ModifyUserMethods method, Object user) {
		// TODO Auto-generated method stub
		mAuthTask = null;

		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void modifyUserFailure(ModifyUserMethods method, Object user) {
		// TODO Auto-generated method stub
		mAuthTask = null;
		
		mPasswordView
		.setError(getString(R.string.error_incorrect_password));
		mPasswordView.requestFocus();
	}
}
