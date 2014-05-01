package com.example.rest;

import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface RestApi {

	public final static String API_URL = "http://shareburst.herokuapp.com/";
	
	// USERS
	@GET("/users")
    ArrayList<User> getUsers();
	
    @GET("/users/{name}")
    User getUser(
    		@Path("name") String name
    );
    
    @PUT("/users")
    User putUser(
    		@Body User user
    );
    
    @PUT("/users/login")
    User loginUser(
    		@Body User user
    );
    
    // GROUPS
    @GET("/groups/{name}")
    ArrayList<Group> getGroups(
    		@Path("name") String name
    );
    
    @PUT("/groups")
    Group putGroup(
    		@Body Group group
    );
	
}
