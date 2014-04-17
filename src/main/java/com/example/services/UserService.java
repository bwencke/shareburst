package com.example.services;

import java.util.ArrayList;

import com.example.models.User;
import com.example.models.ResponseList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	
	ArrayList<Object> users = new ArrayList<Object>();

    @GET
    public ResponseList get() {
        ResponseList responseList = new ResponseList();
        responseList.setList(users);
    	return responseList;
    }
    
    @GET
    @Path("/{userName}")
    public User get(@PathParam("userName") String userName) {
        User newUser = new User(userName);
    	users.add(newUser);
    	return newUser;
    }

}

