package com.example.blog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    private String login;
    private String firstname;
    private String lastname;
    private String description;
    @Id @GeneratedValue
    private Long id;

    public User(String login, String firstname, String lastname, String description, Long id) {
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
        this.id = id;
    }

    public User(String login, String firstname, String lastname, String description) {
        this(login, firstname, lastname, description, null);
    }

    public User(String login, String firstname, String lastname) {
        this(login, firstname, lastname, null);
    }
    
}
