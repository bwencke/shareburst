package com.example.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "userList")
public class UserList {

    private List<User> users;

    public List<User> getList() {
        return users;
    }

    public void setList(List<User> users) {
        this.users = users;
    }

}