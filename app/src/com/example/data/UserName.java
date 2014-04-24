package com.example.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import android.content.Context;

/**
 * Generates and maintains the Company Code
 */
public class UserName {
    private static final String USERNAME = "USERNAME";
    private static String username = null;

    /**
     * Gets the Company Code
     * 
     * @param context where it was called from (getApplicationContext() or getActivity())
     * @return the Company Code as a String
     */
    public static String getUserName(Context context) {
    	if(username != null) {
    		// already downloaded from the file...done!
    		return username;
    	} else {
    		// need to load the username from the file
	    	File userNameFile = new File(context.getFilesDir(), USERNAME);
	    	try {
	    		username = readUserNameFile(userNameFile);
				return username;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	username = null;
	    	return null;
    	}
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
			UserName.username = writeUserNameFile(userNameFile, username);
			return UserName.username;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	UserName.username = null;
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
    	username = null;
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
}
