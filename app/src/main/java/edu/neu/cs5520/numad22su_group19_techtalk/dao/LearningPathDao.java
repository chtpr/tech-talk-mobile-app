package edu.neu.cs5520.numad22su_group19_techtalk.dao;

import android.util.Log;

import com.example.firbasedao.FirebaseDao;
import com.example.firbasedao.Listeners.RetrievalEventListener;
import com.example.firbasedao.Listeners.TaskListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPathResource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class LearningPathDao extends FirebaseDao<LearningPath> {
    private static final String TAG = "LearningPathDao";
    private static LearningPathDao singletonLearningPathDao;
    private LearningPathDao() {
        super("LearningPaths");
    }

    public static LearningPathDao getInstance() {
        if (singletonLearningPathDao == null)
            singletonLearningPathDao = new LearningPathDao();
        return singletonLearningPathDao;
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<LearningPath> retrievalEventListener) {
        final LearningPath learningPath = new LearningPath();
        learningPath.learningPathId = dataSnapshot.getKey();
        learningPath.title = dataSnapshot.child("title").getValue().toString();
        learningPath.author = dataSnapshot.child("author").getValue(User.class);
        learningPath.description = dataSnapshot.child("description").getValue().toString();
        learningPath.topic = dataSnapshot.child("topic").getValue().toString();
        learningPath.subscribers = new ArrayList<>();
        learningPath.upvotes = Integer.parseInt(dataSnapshot.child("upvotes").getValue().toString());
        learningPath.downvotes = Integer.parseInt(dataSnapshot.child("downvotes").getValue().toString());
        learningPath.price = Double.parseDouble(dataSnapshot.child("price").getValue().toString());
        learningPath.learningPathResources = new ArrayList<>();

        for (DataSnapshot ds : dataSnapshot.child("subscribers").getChildren()) {
            learningPath.subscribers.add(parseUserDataSnapshot(ds));
        }

        for (DataSnapshot ds : dataSnapshot.child("learningPathResources").getChildren()) {
            learningPath.learningPathResources.add(parseLearningPathResourceDataSnapshot(ds));
        }
        retrievalEventListener.OnDataRetrieved(learningPath);
    }

    @Override
    public void getAll(RetrievalEventListener<List<LearningPath>> retrievalEventListener) {
        super.getAll(retrievalEventListener);
        Log.v(TAG, "Triggered Get All!");
    }

    private User parseUserDataSnapshot(DataSnapshot dataSnapshot) {
        final User user = new User();
        user.username = dataSnapshot.child("username").getValue().toString();
        user.points = Integer.parseInt(dataSnapshot.child("points").getValue().toString());
        user.isAdmin = Boolean.parseBoolean(dataSnapshot.child("isAdmin").getValue().toString());
        return user;
    }

    private LearningPathResource parseLearningPathResourceDataSnapshot(DataSnapshot dataSnapshot) {
        final LearningPathResource learningPathResource = new LearningPathResource();
        learningPathResource.title = dataSnapshot.child("title").getValue().toString();
        learningPathResource.author = dataSnapshot.child("author").getValue().toString();
        learningPathResource.url = dataSnapshot.child("url").getValue().toString();
        learningPathResource.topic = dataSnapshot.child("topic").getValue().toString();
        learningPathResource.source = dataSnapshot.child("source").getValue().toString();
        learningPathResource.type = dataSnapshot.child("type").getValue().toString();
        learningPathResource.isCompleted = Boolean.parseBoolean(dataSnapshot.child("isCompleted").getValue().toString());
        learningPathResource.isPaid = Boolean.parseBoolean(dataSnapshot.child("isPaid").getValue().toString());

        return learningPathResource;
    }

    public void delete(String id, TaskListener taskListener) {
        save(null, id, taskListener);
    }

}


