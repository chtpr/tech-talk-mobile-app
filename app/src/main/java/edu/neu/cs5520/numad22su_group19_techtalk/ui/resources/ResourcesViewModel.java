package edu.neu.cs5520.numad22su_group19_techtalk.ui.resources;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.ResourceSampleData.RESOURCE_SAMPLE_DATA;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.constants.ResourceSampleData;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;
import edu.neu.cs5520.numad22su_group19_techtalk.repositories.ResourceRepository;

public class ResourcesViewModel extends ViewModel {

    ResourceRepository resourceRepository;
    private static final String TAG = "ResourceViewModel";

    public ResourcesViewModel() {
        resourceRepository = ResourceRepository.getInstance();
//        resourceRepository.seedDatabase(RESOURCE_SAMPLE_DATA());
    }

    public LiveData<List<Resource>> getResources() {
        Log.v(TAG, "Getting resources");
        return resourceRepository.getAllResources();
    }

    public void addResource(Resource resource) {
        resourceRepository.addResource(resource);
    }

    public void updateResource(Resource resource) {
        resourceRepository.updateResource(resource);
    }

    public LiveData<Resource> getResourceById(String resourceID) {
        return resourceRepository.getResourceById(resourceID);
    }
}