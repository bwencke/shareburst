package com.example.services;

import java.util.ArrayList;
import java.util.List;

import com.example.models.User;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	
	static ArrayList<User> users = new ArrayList<User>();

    @GET
    public List<User> get() {
    	return users;
    }
    
    @GET
    @Path("/{userName}")
    public User get(@PathParam("userName") String userName) {
    	for(User u : users) {
    		if(u.getUserName().equals(userName)) {
    			return u;
    		}
    	}
    	return null;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public User putUser(User user) {
    	for(User u : users) {
    		if(u.getUserName().equals(user.getUserName())) {
    			if(user.getFirstName() != null) {
    				u.setFirstName(user.getFirstName());
    			}
    			if(user.getLastName() != null) {
    				u.setLastName(user.getLastName());
    			}
    			if(user.getPassword() != null) {
    				u.setPassword(user.getPassword());
    			}
    			if(user.getPreferences() != null) {
    				u.setPreferences(user.getPreferences());
    			}
    			return u;
    		}
    	}
    	users.add(user);
    	return user;
    }
    
    @DELETE
    @Path("/{userName}")
    public String delete(@PathParam("userName") String userName) {
    	for(User u : users) {
    		if(u.getUserName().equals(userName)) {
    			users.remove(u);
    			return "Success";
    		}
    	}
    	return null;
    }

}

