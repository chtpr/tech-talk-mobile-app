package edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.LearningPathSampleData.SEED_LEARNING_PATHS;

import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;
import edu.neu.cs5520.numad22su_group19_techtalk.repositories.LearningPathRepository;

public class LearningPathsViewModel extends AndroidViewModel {

    public static final String TAG = "LearningPathsViewModel";
    LearningPathRepository learningPathRepository;

    public LearningPathsViewModel(Application application) {
        super(application);
        learningPathRepository = LearningPathRepository.getInstance();
        //seedLearningPaths(SEED_LEARNING_PATHS());
    }

    @NonNull
    public LiveData<List<LearningPath>> getLearningPaths() {
        return learningPathRepository.getAllLearningPath();
    }

    private void seedLearningPaths(List<LearningPath> learningPathList) {
        learningPathRepository.seedDatabase(learningPathList);
    }

    public void addLearningPath(LearningPath learningPath) {
        learningPathRepository.addLearningPath(learningPath);
    }

    public void updateLearningPath(LearningPath learningPath) {
        learningPathRepository.updateLearningPath(learningPath);
    }

    public LiveData<LearningPath> getLearningPathById(String learningPathId) {
        return learningPathRepository.getLearningPathById(learningPathId);
    }
}