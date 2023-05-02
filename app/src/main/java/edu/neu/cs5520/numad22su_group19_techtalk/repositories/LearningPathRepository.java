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
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;

public class LearningPathRepository {
    private static final String TAG = "LearningPathRepository";
    private final LearningPathDao learningPathDao;
    private static LearningPathRepository learningPathRepository;
    private MutableLiveData<List<LearningPath>> learningPathListLiveData;
    private MutableLiveData<LearningPath> learningPathLiveData;

    private LearningPathRepository() {
        learningPathDao = LearningPathDao.getInstance();
        learningPathListLiveData = new MutableLiveData<>();
        learningPathLiveData = new MutableLiveData<>();
    }

    public synchronized static LearningPathRepository getInstance() {
        if (learningPathRepository == null) learningPathRepository = new LearningPathRepository();
        return learningPathRepository;
    }

    public LiveData<List<LearningPath>> getAllLearningPath() {
        retrieveAllLearningPaths();
        return learningPathListLiveData;
    }

    public void addLearningPath(LearningPath learningPath) {

        String newKey = learningPathDao.GetNewKey();

        learningPathDao.save(learningPath, newKey, new TaskListener() {
            @Override
            public void OnSuccess() {
                Log.v(TAG, "LearningPath Object Saved!");
                retrieveAllLearningPaths();
            }

            @Override
            public void OnFail() {
                Log.e(TAG, "Unable to Save LearningPath Object!");
                learningPathListLiveData.postValue(null);
            }
        });
    }

    public void updateLearningPath(LearningPath learningPath) {
        learningPathDao.save(learningPath, learningPath.learningPathId, new TaskListener() {
            @Override
            public void OnSuccess() {
                Log.v(TAG, "LearningPath Object Updated!");
                retrieveAllLearningPaths();
                getLearningPathById(learningPath.learningPathId);
            }

            @Override
            public void OnFail() {
                Log.e(TAG, "Unable to update LearningPath Object!");
                learningPathLiveData.postValue(null);
            }
        });
    }

    public void deleteLearningPath(LearningPath learningPath) {
        learningPathDao.delete(learningPath.learningPathId, new TaskListener() {
            @Override
            public void OnSuccess() {
                Log.v(TAG, "LearningPath saved!");
            }

            @Override
            public void OnFail() {
                Log.e(TAG, "Unable to save learningPath object!");
            }
        });
    }

    public LiveData<LearningPath> getLearningPathById(String learningPathId) {
       retrieveLearningPathById(learningPathId);
       return learningPathLiveData;
    }

    private void retrieveAllLearningPaths() {
        ExecutorService lpService = Executors.newSingleThreadExecutor();
        lpService.submit(new Runnable() {
            @Override
            public void run() {
                learningPathDao.getAll(new RetrievalEventListener<List<LearningPath>>() {
                    @Override
                    public void OnDataRetrieved(List<LearningPath> learningPathList) {
                        learningPathListLiveData.postValue(learningPathList);
                        Log.v(TAG, "retrieveAllLearningPaths()");
                    }
                });
            }
        });
    }

    private void retrieveLearningPathById(String learningPathId) {
        ExecutorService lpService = Executors.newSingleThreadExecutor();
        lpService.submit(new Runnable() {
            @Override
            public void run() {
                learningPathDao.get(learningPathId, new RetrievalEventListener<LearningPath>() {
                    @Override
                    public void OnDataRetrieved(LearningPath learningPath) {
                       learningPathLiveData.postValue(learningPath);
                        Log.v(TAG, "Retrieved learning path id" + learningPathId);
                    }
                });
            }
        });
    }

   public void seedDatabase(List<LearningPath> learningPathList) {
        for (LearningPath ls : learningPathList) {
            learningPathDao.save(ls, learningPathDao.GetNewKey(), new TaskListener() {
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


