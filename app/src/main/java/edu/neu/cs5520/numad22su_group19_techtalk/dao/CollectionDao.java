package edu.neu.cs5520.numad22su_group19_techtalk.dao;

import android.util.Log;

import com.example.firbasedao.FirebaseDao;
import com.example.firbasedao.Listeners.RetrievalEventListener;
import com.example.firbasedao.Listeners.TaskListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.Collection;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class CollectionDao extends FirebaseDao<Collection> {
    private static final String TAG = "CollectionDao";
    private static CollectionDao singletonCollectionDao;
    private CollectionDao() {
        super("Collection");
    }

    public static CollectionDao getInstance() {
        if (singletonCollectionDao == null)
            singletonCollectionDao = new CollectionDao();
        return singletonCollectionDao;
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Collection> retrievalEventListener) {
        final Collection collection = new Collection();
        collection.collectionID = dataSnapshot.getKey();
        collection.title = dataSnapshot.child("title").getValue().toString();
        collection.author = dataSnapshot.child("author").getValue(User.class);
        collection.resources = new ArrayList<>();
        collection.isPrivate = Boolean.parseBoolean(dataSnapshot.child("isPrivate").getValue().toString());

        for (DataSnapshot ds : dataSnapshot.child("resources").getChildren()) {
            collection.resources.add(parseResourceDataSnapshot(ds));
        }

        retrievalEventListener.OnDataRetrieved(collection);
    }

    @Override
    public void getAll(RetrievalEventListener<List<Collection>> retrievalEventListener) {
        super.getAll(retrievalEventListener);
        Log.v(TAG, "Triggered Get All!");
    }

    private Resource parseResourceDataSnapshot(DataSnapshot dataSnapshot) {
        final Resource resource = new Resource();
        resource.resourceID = dataSnapshot.getKey();
        resource.title = dataSnapshot.child("title").getValue().toString();
        resource.description = dataSnapshot.child("description").getValue().toString();
        resource.imageURL = dataSnapshot.child("imageURL").getValue().toString();
        resource.webURL = dataSnapshot.child("webURL").getValue().toString();
        resource.user = dataSnapshot.child("author").getValue(User.class);
//        resource.topics = dataSnapshot.child("topics").getValue().toString();
        resource.upvotes = Integer.parseInt(dataSnapshot.child("upvotes").getValue().toString());
        resource.downvotes = Integer.parseInt(dataSnapshot.child("downvotes").getValue().toString());
        resource.numComments = Integer.parseInt(dataSnapshot.child("numComments").getValue().toString());
        resource.isLearningPath = Boolean.parseBoolean(dataSnapshot.child("isLearningPath").getValue().toString());
//        resource.comments = dataSnapshot.child("comments").getValue().toString();;
        return resource;
    }

    public void delete(String id, TaskListener taskListener) {
        save(null, id, taskListener);
    }
}
