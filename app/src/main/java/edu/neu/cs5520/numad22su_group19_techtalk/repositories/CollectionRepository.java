package edu.neu.cs5520.numad22su_group19_techtalk.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.firbasedao.Listeners.RetrievalEventListener;
import com.example.firbasedao.Listeners.TaskListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.neu.cs5520.numad22su_group19_techtalk.dao.CollectionDao;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Collection;

public class CollectionRepository {
    private static final String TAG = "CollectionRepository";
    private final CollectionDao collectionDao;
    private static CollectionRepository collectionRepository;
    private MutableLiveData<List<Collection>> collectionListLiveData;
    private MutableLiveData<Collection> collectionLiveData;

    private CollectionRepository() {
        collectionDao = CollectionDao.getInstance();
        collectionListLiveData = new MutableLiveData<>();
        collectionLiveData = new MutableLiveData<>();
    }

    public synchronized static CollectionRepository getInstance() {
        if (collectionRepository == null) collectionRepository = new CollectionRepository();
        return collectionRepository;
    }

    public LiveData<List<Collection>> getAllCollections() {
        retrieveAllCollections();
        return collectionListLiveData;
    }

    public void addCollection(Collection collection) {

        String newKey = collectionDao.GetNewKey();

        collectionDao.save(collection, newKey, new TaskListener() {
            @Override
            public void OnSuccess() {
                Log.v(TAG, "Collection Object Saved!");
                retrieveAllCollections();
            }

            @Override
            public void OnFail() {
                Log.e(TAG, "Unable to Save Collection Object!");
                collectionListLiveData.postValue(null);
            }
        });
    }

    public void deleteCollection(Collection collection) {
        collectionDao.delete(collection.collectionID, new TaskListener() {
            @Override
            public void OnSuccess() {
                Log.v(TAG, "Collection saved!");
            }

            @Override
            public void OnFail() {
                Log.e(TAG, "Unable to save Collection object!");
            }
        });
    }

    public LiveData<Collection> getCollectionById(String collectionId) {
        retrieveCollectionById(collectionId);
        return collectionLiveData;
    }

    private void retrieveCollectionById(String collectionId) {
        ExecutorService collectionService = Executors.newSingleThreadExecutor();
        collectionService.submit(new Runnable() {
            @Override
            public void run() {
                collectionDao.get(collectionId, new RetrievalEventListener<Collection>() {
                    @Override
                    public void OnDataRetrieved(Collection collection) {
                        collectionLiveData.postValue(collection);
                        Log.v(TAG, "Retrieved collection id" + collectionId);
                    }
                });
            }
        });
    }

    private void retrieveAllCollections() {
        ExecutorService lpService = Executors.newSingleThreadExecutor();
        lpService.submit(new Runnable() {
            @Override
            public void run() {
                collectionDao.getAll(new RetrievalEventListener<List<Collection>>() {
                    @Override
                    public void OnDataRetrieved(List<Collection> collectionList) {
                        collectionListLiveData.postValue(collectionList);
                        Log.v(TAG, "retrieveAllCollections()");
                    }
                });
            }
        });
    }

    public void seedDatabase(List<Collection> collectionList) {
        for (Collection collection : collectionList) {
            collectionDao.save(collection, collectionDao.GetNewKey(), new TaskListener() {
                @Override
                public void OnSuccess() {
                    Log.v(TAG, "Seed database completed!");
                }
                @Override
                public void OnFail() {
                    Log.v(TAG, "Seed database failed!");
                }
            });
        }
    }
}