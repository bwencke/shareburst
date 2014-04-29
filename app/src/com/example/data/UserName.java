package com.example.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.example.rest.User;

import android.content.Context;

/**
 * Generates and maintains the Company Code
 */
public class UserName {
    private static final String USERNAME = "USERNAME";
    private static String username = null;
    public static User user = new User();
    
    /**
     * Gets the Company Code
     * 
     * @param context where it was called from (getApplicationContext() or getActivity())
     * @return the Company Code as a String
     */
    public static String getUserName(Context context) {
    	if(user.getUserName() == null) {
    		// need to load the username from the file
	    	File userNameFile = new File(context.getFilesDir(), USERNAME);
	    	try {
	    		user.setUserName(readUserNameFile(userNameFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return user.getUserName();
    }
    
    /**
     * Sets the Company Code to the code provided or generates a new code if code == null
     * 
     * @param context where it was called from (getApplicationContext() or getActivity())
     * @param code the Company Code as a String or null to generate a new code
     * @return the Company Code as a String
     */
    public static String setUserName(Context context, String username) {
    	File userNameFile = new File(context.getFilesDir(), USERNAME);
    	try {
			user.setUserName(writeUserNameFile(userNameFile, username));
			return user.getUserName();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	user.setUserName(null);
    	return null;
    }
    
    /**
     * Erases the Company Code from the device
     * 
     * @param context where it was called from (getApplicationContext() or getActivity())
     */
    public static void clearUserName(Context context) {
    	File userNameFile = new File(context.getFilesDir(), USERNAME);
    	try {
			deleteUserNameFile(userNameFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	user.setUserName(null);
    }

    private static String readUserNameFile(File userNameFile) throws IOException {
        RandomAccessFile f = new RandomAccessFile(userNameFile, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }
    
    private static void deleteUserNameFile(File userNameFile) throws IOException {
    	userNameFile.delete();
    }

    private static String writeUserNameFile(File userNameFile, String username) throws IOException {
        FileOutputStream out = new FileOutputStream(userNameFile);
        if(username == null) {
        	out.close();
        	return null;
        }
        out.write(username.getBytes());
        out.close();
        return username;
    }

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		UserName.user = user;
	}
}
