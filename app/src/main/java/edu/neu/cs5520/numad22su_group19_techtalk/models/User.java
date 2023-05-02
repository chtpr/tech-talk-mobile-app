package edu.neu.cs5520.numad22su_group19_techtalk.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

@IgnoreExtraProperties
public class User {
    @Exclude
    public String userID;


    public String username;
    public String password;
    public String profileImageURL;
    public String creationDate;
    public int points;
    public boolean isAdmin;


    public User() {
        // Firebase Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userID, String username, String password, String profileImageURL, String creationDate, int points, boolean isAdmin) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.profileImageURL = profileImageURL;
        this.creationDate = creationDate;
        this.points = points;
        this.isAdmin = isAdmin;
    }

    public User(String username, int points, boolean isAdmin) {
        this.userID = "";
        this.username = username;
        this.password = "";
        this.profileImageURL = "";
        this.creationDate = "";
        this.points = points;
        this.isAdmin = isAdmin;
    }

    public User(String username) {
        this.userID = "";
        this.username = username;
        this.password = "password";
        this.profileImageURL = "";
        this.creationDate = (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).toString();
        this.points = 0;
        this.isAdmin = false;
    }


}