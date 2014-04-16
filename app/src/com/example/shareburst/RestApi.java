package com.example.shareburst;

import retrofit.http.GET;
import retrofit.http.Path;

public interface RestApi {

	// GET USER
    @GET("/user/{name}")
    User getUser(
    		@Path("name") String name
    );
    
    // GET GROUP
    @GET("/group/{name}")
    Group getGroup(
    		@Path("name") String name
    );
	
}
