package edu.neu.cs5520.numad22su_group19_techtalk.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Collection {

    @Exclude
    public String collectionID;

    public String title;
    public User author;
    public List<Resource> resources;
    public int numResources;
    public boolean isPrivate;

    public Collection(String title, User author, List<Resource> resources, boolean isPrivate) {
        this.title = title;
        this.author = author;
        this.resources = resources;
        this.numResources = resources.size();
        this.isPrivate = isPrivate;
    }

    // Constructor for Firebase
    public Collection() {}

    @Override
    public String toString() {
        return "Collection{" +
                "collectionID='" + collectionID + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
