package com.example.shareburst;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface RestApi {

	public final static String API_URL = "http://shareburst.herokuapp.com/";
	
	// USERS
    @GET("/users/{name}")
    User getUser(
    		@Path("name") String name
    );
    
    @PUT("/users")
    User putUser(
    		@Body User user
    );
    
    @PUT("/users/login")
    boolean loginUser(
    		@Body User user
    );
    
    // GROUPS
    @GET("/group/{name}")
    Group getGroup(
    		@Path("name") String name
    );
	
}
