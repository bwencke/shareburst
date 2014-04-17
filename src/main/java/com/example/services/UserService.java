package com.example.services;

import java.util.ArrayList;

import com.example.models.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	
	ArrayList<User> users = new ArrayList<User>();

    @GET
    public Object[] get() {
        return users.toArray();
    }
    
    @GET
    @Path("/{userName}")
    public User get(@PathParam("userName") String userName) {
        return new User(userName);
    }

}

