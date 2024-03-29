package com.shareburst.preference;

import com.example.data.UserName;
import com.example.rest.ModifyUser;
import com.example.rest.User;
import com.example.rest.Preferences;
import com.shareburst.R;
import com.shareburst.main.LoginActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PreferenceActivity extends Activity implements ModifyUser {
	
	int level;
	int used;
	int preferences[];
	int graysRem = 12;
	int colorPrefs[] = {0,0,0,0};
	RectangleView rvGray[];
	RectangleView rvRed[];
	RectangleView rvYellow[];
	RectangleView rvOrange[];
	RectangleView rvPink[];
	User currUser;
	Preferences savePrefs;
	
	int colorLightGray = Color.rgb(223, 224, 224);
	int colorRed = Color.rgb(234, 0, 9);
	int colorGray = Color.rgb(109, 109, 109);
	int colorYellow = Color.rgb(237, 204, 11);
	int colorOrange = Color.rgb(235, 124, 10);
	int colorPink = Color.rgb(252, 86, 190);
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		//setContentView(R.layout.activity_main);
		//level = 1;

		/*if (savedInstanceState == null) {
			PlaceholderFragment x = new PlaceholderFragment();
			getFragmentManager().beginTransaction()
					.add(R.id.container, x).commit();
		}*/
		
		rvGray = new RectangleView[13];
		rvRed = new RectangleView[13];
		rvYellow = new RectangleView[13];
		rvOrange = new RectangleView[13];
		rvPink = new RectangleView[13];
		LinearLayout cols = new LinearLayout(getApplicationContext());
		
		TextView instructions = new TextView(getApplicationContext());
		instructions.setText("Given 12 Starbursts, how many of each flavor would you prefer?");
		//Display display = getWindowManager().getDefaultDisplay();
		//Point size = new Point();
		//display.getSize(size);
		instructions.setTypeface(Typeface.create("sans-serif-light", Typeface.ITALIC));
		
		instructions.setTextSize(20);
		instructions.setPadding(40, 30, 40, 30);
		instructions.setTextColor(Color.BLACK);
		instructions.setGravity(Gravity.CENTER_VERTICAL);
		//instructions.setBackgroundColor(Color.MAGENTA);
		
		//cols.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				//LinearLayout.LayoutParams.WRAP_CONTENT));
		LinearLayout total = new LinearLayout(getApplicationContext());
		//RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		//relativeParams.addRule(RelativeLayout.BELOW, cols.getId());
		//relativeParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		total.setOrientation(LinearLayout.VERTICAL);
		//total.setScrollContainer(true);
		//cols.setBackgroundColor(Color.BLUE);
		
		LinearLayout colGray = new LinearLayout(getApplicationContext());
		colGray.setOrientation(LinearLayout.VERTICAL);
		colGray.setLayoutParams(new LinearLayout.LayoutParams(0,LayoutParams.FILL_PARENT, 1));
		//colGray.setPadding(20, 0, 20, 0);
		LinearLayout colRed = new LinearLayout(getApplicationContext());
		colRed.setOrientation(LinearLayout.VERTICAL);
		colRed.setLayoutParams(new LinearLayout.LayoutParams(0,LayoutParams.FILL_PARENT, 1));
		LinearLayout colYel = new LinearLayout(getApplicationContext());
		colYel.setOrientation(LinearLayout.VERTICAL);
		colYel.setLayoutParams(new LinearLayout.LayoutParams(0,LayoutParams.FILL_PARENT, 1));
		LinearLayout colOran = new LinearLayout(getApplicationContext());
		colOran.setOrientation(LinearLayout.VERTICAL);
		colOran.setLayoutParams(new LinearLayout.LayoutParams(0,LayoutParams.FILL_PARENT, 1));
		LinearLayout colPink = new LinearLayout(getApplicationContext());
		colPink.setOrientation(LinearLayout.VERTICAL);
		colPink.setLayoutParams(new LinearLayout.LayoutParams(0,LayoutParams.FILL_PARENT, 1));
		LinearLayout colDumbEmptySpace = new LinearLayout(getApplicationContext());
		colDumbEmptySpace.setLayoutParams(new LinearLayout.LayoutParams(0,LayoutParams.FILL_PARENT, 1));
		
		for (int i = 12; i > 0; i--){
			rvGray[i] = new RectangleView(getApplicationContext(), i, 0);
			rvGray[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1));
			//rvGray[i].setColor(colorGray);
			colGray.addView(rvGray[i]);
			//rvGray[i].setOnClickListener(rectListener);
			rvRed[i] = new RectangleView(getApplicationContext(), i, 1);
			rvRed[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1));
			//rvRed[i].setColor(colorLightGray);
			colRed.addView(rvRed[i]);
			rvRed[i].setOnClickListener(rectListener);
			rvYellow[i] = new RectangleView(getApplicationContext(), i, 2);
			rvYellow[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1));
			//rvYellow[i].setColor(colorLightGray);
			colYel.addView(rvYellow[i]);
			rvYellow[i].setOnClickListener(rectListener);
			rvOrange[i] = new RectangleView(getApplicationContext(), i, 3);
			rvOrange[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1));
			//rvOrange[i].setColor(colorLightGray);
			colOran.addView(rvOrange[i]);
			rvOrange[i].setOnClickListener(rectListener);
			rvPink[i] = new RectangleView(getApplicationContext(), i, 4);
			rvPink[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1));
			//rvPink[i].setColor(colorLightGray);
			colPink.addView(rvPink[i]);
			rvPink[i].setOnClickListener(rectListener);
		}
		
		//This section makes the little rectangles underneath
		rvRed[0] = new ThinRectangleView(getApplicationContext(), 0, 1);
		rvRed[0].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1));
		rvRed[0].setColor(colorRed);
		rvRed[0].setOnClickListener(rectListener);
		colRed.addView(rvRed[0]);
		
		rvYellow[0] = new ThinRectangleView(getApplicationContext(), 0, 2);
		rvYellow[0].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1));
		rvYellow[0].setColor(colorYellow);
		rvYellow[0].setOnClickListener(rectListener);
		colYel.addView(rvYellow[0]);
		
		rvOrange[0] = new ThinRectangleView(getApplicationContext(), 0, 3);
		rvOrange[0].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1));
		rvOrange[0].setColor(colorOrange);
		rvOrange[0].setOnClickListener(rectListener);
		colOran.addView(rvOrange[0]);
		
		rvPink[0] = new ThinRectangleView(getApplicationContext(), 0, 4);
		rvPink[0].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1));
		rvPink[0].setColor(colorPink);
		rvPink[0].setOnClickListener(rectListener);
		colPink.addView(rvPink[0]);
		
		rvGray[0] = new ThinRectangleView(getApplicationContext(), 0, 0);
		rvGray[0].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, 1));
		rvGray[0].setColor(colorGray);
		colGray.addView(rvGray[0]);

		//new GetUser(PreferenceActivity.this, this, UserName.getUserName(getApplicationContext())).execute();
		
		currUser = UserName.getUser();
		savePrefs = currUser.getPreferences();
		loadSaved();
		
		cols.addView(colGray);
		cols.addView(colDumbEmptySpace);
		cols.addView(colRed);
		cols.addView(colYel);
		cols.addView(colOran);
		cols.addView(colPink);
		cols.setPadding(30, 0, 40, 30);
		
		//total.setBackgroundColor(Color.GREEN);
		
		total.addView(instructions);
		total.addView(cols);
		setContentView(total);
		
	}
	
	public void clear(){
		for (int i = 12; i > 0; i--){
			rvGray[i].setColor(colorGray);
			rvGray[i].invalidate();
			rvRed[i].setColor(colorLightGray);
			rvRed[i].invalidate();
			rvYellow[i].setColor(colorLightGray);
			rvYellow[i].invalidate();
			rvOrange[i].setColor(colorLightGray);
			rvOrange[i].invalidate();
			rvPink[i].setColor(colorLightGray);
			rvPink[i].invalidate();
		}
		graysRem = 12;
		for (int j = 0; j < 4; j++){
			colorPrefs[j] = 0;
		}
		
	}
	
	final OnClickListener rectListener = new OnClickListener() {
        public void onClick(final View v) {
            //Inform the user the button has been clicked
        	//Toast.makeText(getApplicationContext(), "Clickin", Toast.LENGTH_SHORT).show();
        	RectangleView g = (RectangleView) v;
        	
        	int colorId = g.getColorId();
        	
        	switch (colorId){
        	case 0:
        		//Toast.makeText(getApplicationContext(), "Clickin", Toast.LENGTH_SHORT).show();
        		break;
        	case 1:
        		if (graysRem >= g.getLevel()-colorPrefs[colorId-1]){
        			colorSwitcher(rvRed, colorRed, g.getLevel());
        			graysRem = graysRem-g.getLevel()+colorPrefs[colorId-1];
        			colorSwitcher(rvGray, colorGray, graysRem);
        			colorPrefs[colorId-1] = g.getLevel();
        		}
        		else if (graysRem > 0) {
        			colorSwitcher(rvRed, colorRed, colorPrefs[colorId-1] + graysRem);
        			colorPrefs[colorId-1] = colorPrefs[colorId-1] + graysRem;
        			graysRem = 0;
        			colorSwitcher(rvGray, colorGray, graysRem);
        		}
        		break;
        	case 2:
        		if (graysRem >= g.getLevel()-colorPrefs[colorId-1]){
        			colorSwitcher(rvYellow, colorYellow, g.getLevel());
        			graysRem = graysRem-g.getLevel()+colorPrefs[colorId-1];
        			colorSwitcher(rvGray, colorGray, graysRem);
        			colorPrefs[colorId-1] = g.getLevel();
        		}
        		else if (graysRem > 0) {
        			colorSwitcher(rvYellow, colorYellow, colorPrefs[colorId-1] + graysRem);
        			colorPrefs[colorId-1] = colorPrefs[colorId-1] + graysRem;
        			graysRem = 0;
        			colorSwitcher(rvGray, colorGray, graysRem);
        		}
        		break;
        	case 3:
        		if (graysRem >= g.getLevel()-colorPrefs[colorId-1]){
        			colorSwitcher(rvOrange, colorOrange, g.getLevel());
        			graysRem = graysRem-g.getLevel()+colorPrefs[colorId-1];
        			colorSwitcher(rvGray, colorGray, graysRem);
        			colorPrefs[colorId-1] = g.getLevel();
        		}
        		else if (graysRem > 0) {
        			colorSwitcher(rvOrange, colorOrange, colorPrefs[colorId-1] + graysRem);
        			colorPrefs[colorId-1] = colorPrefs[colorId-1] + graysRem;
        			graysRem = 0;
        			colorSwitcher(rvGray, colorGray, graysRem);
        		}
        		break;
        	case 4:
        		if (graysRem >= g.getLevel()-colorPrefs[colorId-1]){
        			colorSwitcher(rvPink, colorPink, g.getLevel());
        			graysRem = graysRem-g.getLevel()+colorPrefs[colorId-1];
        			colorSwitcher(rvGray, colorGray, graysRem);
        			colorPrefs[colorId-1] = g.getLevel();
        		}
        		else if (graysRem > 0) {
        			colorSwitcher(rvPink, colorPink, colorPrefs[colorId-1] + graysRem);
        			colorPrefs[colorId-1] = colorPrefs[colorId-1] + graysRem;
        			graysRem = 0;
        			colorSwitcher(rvGray, colorGray, graysRem);
        		}
        		break;
        	}
        	//Log.i("Prefs: " + graysRem + " " + colorPrefs[0] + " " + colorPrefs[1] + " " + colorPrefs[2] + " " + colorPrefs[3], "msg");
            //g.setColor(0xff74AC23);
            g.invalidate();
        }
    };
    
    public void loadSaved(){
    	//Loads your current values on startup
    	if (savePrefs.getOrange() == 0 && savePrefs.getPink() == 0 &&
    		savePrefs.getRed() == 0 && savePrefs.getYellow() == 0){
    		clear();
    	}
    	else {
    		colorPrefs[0] = savePrefs.getRed();
    		colorPrefs[1] = savePrefs.getYellow();
    		colorPrefs[2] = savePrefs.getOrange();
    		colorPrefs[3] = savePrefs.getPink();
    		colorSwitcher(rvRed, colorRed, colorPrefs[0]);
    		colorSwitcher(rvYellow, colorYellow, colorPrefs[1]);
    		colorSwitcher(rvOrange, colorOrange, colorPrefs[2]);
    		colorSwitcher(rvPink, colorPink, colorPrefs[3]);
    		graysRem = 0; //GraysRem must be zero if you are to save preferences
    		colorSwitcher(rvGray, colorGray, graysRem);
    	}
    }
    
    public void colorSwitcher(RectangleView array[], int c, int lev){
    	for (int i = 1; i < 13; i++){
    		if (i <= lev){
    			array[i].setColor(c);
    			array[i].invalidate();
    		}
    		else {
    			array[i].setColor(colorLightGray);
    			array[i].invalidate();
    		}
    	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferences, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case android.R.id.home:
	        finish();
	        return true;
        case R.id.action_accept:
        	if (graysRem > 0){
				Toast.makeText(getApplicationContext(), "You have " + graysRem + " more Starburst(s) to distribute!", Toast.LENGTH_SHORT).show();
			} else {
				//Log.i("What", graysRem + " " + colorPrefs[2]);
				//savePrefs = new Preferences();
				savePrefs.setRed(colorPrefs[0]);
				savePrefs.setYellow(colorPrefs[1]);
				savePrefs.setOrange(colorPrefs[2]);
				savePrefs.setPink(colorPrefs[3]);
				//new GetUser(PreferenceActivity.this, this, UserName.getUserName(getApplicationContext())).execute();
				//currUser.setPreferences(savePrefs);
				//Log.i("Password", UserName.getUser().getPassword());
				//Log.i("Password6", UserName.getUser().getPassword());
				//currUser.setPassword(UserName.getUser().getPassword());
				new PutUser(PreferenceActivity.this, this, currUser).execute();
			}
            return true;
        case R.id.action_discard:
            clear();
            return true;
        case R.id.action_logout:
        	UserName.clearUserName(getApplicationContext());
    		Intent i = new Intent(getApplicationContext(), LoginActivity.class);
    		startActivity(i);
    		finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}
  	}
	
	public void changeColor(View v){
		ImageView iv = (ImageView) v;
		iv.setImageLevel(level);
		if (level == 1){ level = 0; }
		else { level = 1; }
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		ImageView tv;
		RectangleView rv[];

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main2, container,
					false);
			//Resources res = getResources();
			//Drawable shape = res.getDrawable(R.drawable.rectangle_level_list);
			//shape.setLevel(1);
			rv = new RectangleView[12];
			LinearLayout cols = new LinearLayout(getActivity().getApplicationContext());
			LinearLayout rowGray = new LinearLayout(getActivity().getApplicationContext());
			rowGray.setOrientation(LinearLayout.VERTICAL);
			rowGray.setLayoutParams(new LinearLayout.LayoutParams(50,50));

			for (int i = 0; i < 12; i++){
				rv[i] = new RectangleView(getActivity().getApplicationContext(), 8, 0);
						//, 10, 10*i + 50*i + 10);
				rowGray.addView(rv[i]);
			}
			cols.addView(rowGray);
			getActivity().setContentView(cols);
			//tv = (ImageView)rootView.findViewById(R.id.box);
			//tv.setImageLevel(0);
			return rootView;
		}
		
		public void changeColor(View v){
			tv.setImageLevel(1);
		}

	}

	@Override
	public void modifyUserSuccess(ModifyUserMethods method, Object user) {
		// TODO Auto-generated method stub
		switch (method){
		case GET:
			currUser = (User) user;
			savePrefs = currUser.getPreferences();
			loadSaved();
			break;
		case PUT:
			Intent returnIntent = new Intent();
			setResult(RESULT_OK,returnIntent);     
		default:
			finish();
		}
	}

	@Override
	public void modifyUserFailure(ModifyUserMethods method, Object user) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Processing, please press again", Toast.LENGTH_SHORT).show();
		Log.i("Yo", "Failed");
		
	}

}
