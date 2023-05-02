package edu.neu.cs5520.numad22su_group19_techtalk.ui.collections;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.Collection;
import edu.neu.cs5520.numad22su_group19_techtalk.repositories.CollectionRepository;

public class CollectionsViewModel extends AndroidViewModel {
    public static final String TAG = "CollectionsViewModel";
    CollectionRepository collectionRepository;

    public CollectionsViewModel(Application application) {
        super(application);
        collectionRepository = CollectionRepository.getInstance();
        // Only need to seed once
        // seedCollections(COLLECTION_DUMMY_DATA());
    }

    @NonNull
    public LiveData<List<Collection>> getCollections() {
        return collectionRepository.getAllCollections();
    }

    public void addCollection(Collection collection) {
        collectionRepository.addCollection(collection);
    }

    private void seedCollections(List<Collection> collectionList) {
        collectionRepository.seedDatabase(collectionList);
    }

    public LiveData<Collection> getCollectionById(String collectionId) {
        return collectionRepository.getCollectionById(collectionId);
    }
}