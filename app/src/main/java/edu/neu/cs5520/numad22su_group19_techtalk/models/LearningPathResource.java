package edu.neu.cs5520.numad22su_group19_techtalk.models;

import java.io.Serializable;

public class LearningPathResource implements Serializable {
    public String resourceID;
    public  String title;
    public  String author;
    public  String url;
    public  String topic;
    public  String source;
    public  String type;
    public  Boolean isCompleted;
    public  Boolean isPaid;

    public LearningPathResource() {
        // Firebase Default constructor required for calls to DataSnapshot.getValue(LearnPathResource.class)
    }

    public LearningPathResource(String resourceID, String title, String author, String url, String topic, String source, String type, Boolean isCompleted, Boolean isPaid) {
        this.resourceID = resourceID;
        this.title = title;
        this.author = author;
        this.url = url;
        this.topic = topic;
        this.source = source;
        this.type = type;
        this.isCompleted = isCompleted;
        this.isPaid = isPaid;
    }

    public LearningPathResource(String title) {
        this.resourceID = "";
        this.title = title;
        this.author = "";
        this.url = "";
        this.topic = "";
        this.source = "";
        this.type = "";
        this.isCompleted = false;
        this.isPaid = false;
    }


    @Override
    public String toString() {
        return "LearningPathResource{" +
                "resourceID=" + resourceID +
                ", title='" + title + '\'' +
                '}';
    }
}

