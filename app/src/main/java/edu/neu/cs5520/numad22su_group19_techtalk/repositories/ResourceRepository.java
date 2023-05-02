package edu.neu.cs5520.numad22su_group19_techtalk.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.firbasedao.Listeners.RetrievalEventListener;
import com.example.firbasedao.Listeners.TaskListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.neu.cs5520.numad22su_group19_techtalk.dao.LearningPathDao;
import edu.neu.cs5520.numad22su_group19_techtalk.dao.ResourceDao;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;

public class ResourceRepository {
    private static final String TAG = "ResourceRepository";
    private final ResourceDao resourceDao;
    private static ResourceRepository resourceRepository;
    private MutableLiveData<List<Resource>> resourceListLiveData;
    private MutableLiveData<Resource> resourceLiveData;

    private ResourceRepository() {
        resourceDao = ResourceDao.getInstance();
        resourceLiveData = new MutableLiveData<>();
        resourceListLiveData = new MutableLiveData<>();
    }

    public synchronized static ResourceRepository getInstance() {
        if (resourceRepository == null) resourceRepository = new ResourceRepository();
        return resourceRepository;
    }

    public LiveData<List<Resource>> getAllResources() {
        Log.v(TAG, "calling retrieve all resources");
        retrieveAllResources();
        Log.v(TAG, "after calling retrieve all resources");
        return resourceListLiveData;
    }

    public void updateResource(Resource resource) {
        resourceDao.save(resource, resource.resourceID, new TaskListener() {
            @Override
            public void OnSuccess() {
                Log.v(TAG, "Resource Object Updated!");
                retrieveAllResources();
            }

            @Override
            public void OnFail() {
                Log.e(TAG, "Unable to update Resource Object!");
                resourceLiveData.postValue(null);
            }
        });
    }

    public void addResource(Resource resource) {
        String newKey = resourceDao.GetNewKey();
        resourceDao.save(resource, newKey, new TaskListener() {
            @Override
            public void OnSuccess() {
                Log.v(TAG, "Resource Object Saved!");
                retrieveAllResources();
            }

            @Override
            public void OnFail() {
                Log.e(TAG, "Unable to Save Resource Object!");
                resourceListLiveData.postValue(null);
            }
        });
    }
    private void retrieveAllResources() {
        ExecutorService resourceService = Executors.newSingleThreadExecutor();
        resourceService.submit(new Runnable() {
            @Override
            public void run() {
                resourceDao.getAll(new RetrievalEventListener<List<Resource>>() {
                    @Override
                    public void OnDataRetrieved(List<Resource> resourceList) {
                        for (Resource resource: resourceList) {
                            Log.v(TAG, resource.toString());
                        }
                        resourceListLiveData.postValue(resourceList);
                        Log.v(TAG, "retrieved all resource data");
                    }
                });
            }
        });
    }

    public void seedDatabase(List<Resource> resourceList) {
        for (Resource resource : resourceList) {
            resourceDao.save(resource, resourceDao.GetNewKey(), new TaskListener() {
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

    public LiveData<Resource> getResourceById(String resourceID) {
        retrieveResourceById(resourceID);
        return resourceLiveData;
    }

    private void retrieveResourceById(String resourceID) {
        ExecutorService resourceService = Executors.newSingleThreadExecutor();
        resourceService.submit(new Runnable() {
            @Override
            public void run() {
                resourceDao.get(resourceID, new RetrievalEventListener<Resource>() {
                    @Override
                    public void OnDataRetrieved(Resource resource) {
                        resourceLiveData.postValue(resource);
                    }
                });
            }
        });
    }
}
