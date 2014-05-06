package com.example.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import com.example.rest.Group;
import com.example.rest.User;

import android.content.Context;

/**
 * Data stored locally on the device
 */
public class UserName {
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    public static User user = new User();
    public static ArrayList<User> users = null;
    public static ArrayList<Group> groups = null;

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
    
    public static String setPassword(Context context, String password) {
    	File passwordFile = new File(context.getFilesDir(), PASSWORD);
    	try {
			user.setPassword(writePasswordFile(passwordFile, password));
			return user.getPassword();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	user.setPassword(null);
    	return null;
    }
    
    public static String getPassword(Context context) {
    	if(user.getPassword() == null) {
    		// need to load the username from the file
	    	File userPassword = new File(context.getFilesDir(), PASSWORD);
	    	try {
	    		user.setPassword(readPasswordFile(userPassword));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return user.getPassword();
    }

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
    
    public static void clearUserName(Context context) {
    	File userNameFile = new File(context.getFilesDir(), USERNAME);
    	try {
			deleteUserNameFile(userNameFile);
			deletePasswordFile(userNameFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	user = new User();
    	users = null;
    	groups = null;
    }

    private static String readPasswordFile(File passwordFile) throws IOException {
        RandomAccessFile f = new RandomAccessFile(passwordFile, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }
    
    private static void deletePasswordFile(File passwordFile) throws IOException {
    	passwordFile.delete();
    }

    private static String writePasswordFile(File passwordFile, String password) throws IOException {
        FileOutputStream out = new FileOutputStream(passwordFile);
        if(password == null) {
        	out.close();
        	return null;
        }
        out.write(password.getBytes());
        out.close();
        return password;
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
	
	public static ArrayList<User> getUsers() {
		return users;
	}

	public static void setUsers(ArrayList<User> users) {
		for(int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if(u.getUserName() == null || u.getUserName().equals(user.getUserName())) {
				users.remove(i);
				break;
			}
		}
		UserName.users = users;
	}
	
	public static ArrayList<Group> getGroups() {
		return groups;
	}

	public static void setGroups(ArrayList<Group> groups) {
		UserName.groups = groups;
	}
}
