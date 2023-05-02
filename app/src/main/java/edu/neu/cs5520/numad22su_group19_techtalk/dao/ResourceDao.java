package edu.neu.cs5520.numad22su_group19_techtalk.dao;

import android.util.Log;

import com.example.firbasedao.FirebaseDao;
import com.example.firbasedao.Listeners.RetrievalEventListener;
import com.example.firbasedao.Listeners.TaskListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class ResourceDao extends FirebaseDao<Resource> {
    private static final String TAG = "ResourceDao";
    private static ResourceDao singletonResourceDao;
    private ResourceDao() {
        super("Resources");
    }

    public static ResourceDao getInstance() {
        if (singletonResourceDao == null)
            singletonResourceDao = new ResourceDao();
        return singletonResourceDao;
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Resource> retrievalEventListener) {
        final Resource resource = new Resource();
        resource.resourceID = dataSnapshot.getKey();
        resource.title = dataSnapshot.child("title").getValue().toString();
        resource.description = dataSnapshot.child("description").getValue().toString();
        resource.imageURL = dataSnapshot.child("imageURL").getValue().toString();
        resource.webURL = dataSnapshot.child("webURL").getValue().toString();
        resource.user = dataSnapshot.child("user").getValue(User.class);
//        resource.topics = dataSnapshot.child("topics").getValue().toString();
        resource.upvotes = Integer.parseInt(dataSnapshot.child("upvotes").getValue().toString());
        resource.downvotes = Integer.parseInt(dataSnapshot.child("downvotes").getValue().toString());
        resource.numComments = Integer.parseInt(dataSnapshot.child("numComments").getValue().toString());
        resource.isLearningPath = Boolean.parseBoolean(dataSnapshot.child("isLearningPath").getValue().toString());
//        resource.comments = dataSnapshot.child("comments").getValue().toString();

        List<String> topics = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.child("topics").getChildren()) {
            topics.add(ds.getValue().toString());
        }
        resource.topics = topics;

        List<String> comments = new ArrayList<>();
        for (DataSnapshot ds : dataSnapshot.child("comments").getChildren()) {
            comments.add(ds.getValue().toString());
        }
        resource.comments = comments;

        retrievalEventListener.OnDataRetrieved(resource);
    }

    @Override
    public void getAll(RetrievalEventListener<List<Resource>> retrievalEventListener) {
        super.getAll(retrievalEventListener);
        Log.v(TAG, "Triggered Resource Get All!");
    }

    public void delete(String id, TaskListener taskListener) {
        save(null, id, taskListener);}

}
