package edu.neu.cs5520.numad22su_group19_techtalk.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class LearningPath {

    @Exclude
    public String learningPathId;

    public String title;
    public  User author;
    public String description;
    public  List<User> subscribers;
    public String topic;
    public  int upvotes;
    public  int downvotes;
    public  double price;
    public  List<LearningPathResource> learningPathResources;

    public LearningPath() {
        // Firebase Default constructor required for calls to DataSnapshot.getValue(LearnPath.class)
    }

    public LearningPath(String learningPathId, String title, User author, String topic) {
        this.learningPathId = learningPathId;
        this.title = title;
        this.author = author;
        this.topic = topic;
        this.subscribers = new ArrayList<>();
        this.upvotes = 0;
        this.downvotes = 0;
        this.price = 0.0;
        learningPathResources = new ArrayList<>();
        this.description = "";
    }

    public LearningPath(String learningPathId, String title, User author, String description, String topic) {
        this.learningPathId = learningPathId;
        this.title = title;
        this.author = author;
        this.topic = topic;
        this.subscribers = new ArrayList<>();
        this.upvotes = 0;
        this.downvotes = 0;
        this.price = 0.0;
        learningPathResources = new ArrayList<>();
        this.description = description;
    }

    public LearningPath(String learningPathId, User user) {
        this.learningPathId = learningPathId;
        this.title = "";
        this.author = user;
        this.topic = "";
        this.subscribers = new ArrayList<>();
        this.upvotes = 0;
        this.downvotes = 0;
        this.price = 0.0;
        learningPathResources = new ArrayList<>();
        this.description = "";
    }


    @Override
    public String toString() {
        return "LearningPath{" +
                "learningPathId='" + learningPathId + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", description='" + description + '\'' +
                ", total resources='" + learningPathResources.size() + '\'' +
                '}';
    }
}

