package edu.neu.cs5520.numad22su_group19_techtalk.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Resource {
    @Exclude
    public String resourceID;

    public String title;
    public String description;
    public User user;
    public String imageURL;
    public String webURL;
    public int upvotes;
    public int downvotes;
    public List<String> topics;
//    public String topics;
    public List<String> comments;
//    public String comments;
    public int numComments;
    public boolean isLearningPath;

    public Resource() {

    }

    public Resource(String resourceID, String title, String description, User user, String imageURL, String webURL, int upvotes, int downvotes, List<String> topics, List<String> comments, boolean isLearningPath) {
        this.resourceID = resourceID;
        this.title = title;
        this.description = description;
        this.user = user;
        this.imageURL = imageURL;
        this.webURL = webURL;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.topics = topics;
        this.comments = comments;
        this.isLearningPath = isLearningPath;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "resourceID='" + resourceID + '\'' +
                ", title='" + title + '\'' +
                ", user=" + user +
                '}';
    }
}
