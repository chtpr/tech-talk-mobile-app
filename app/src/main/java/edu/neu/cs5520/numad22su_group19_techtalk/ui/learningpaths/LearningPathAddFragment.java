package edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.LEARNING_PATH_ADD_RESOURCE;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.LEARNING_PATH_DESCRIPTION;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.LEARNING_PATH_PRICE;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.LEARNING_PATH_TITLE;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.LEARNING_PATH_TOPIC;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.Instant;

import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentLearningPathAddBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.formsteps.LearningPathDescriptionStep;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.formsteps.LearningPathPriceStep;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.formsteps.LearningPathResourceStep;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.formsteps.LearningPathTitleStep;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.formsteps.LearningPathTopicStep;
import ernestoyaquello.com.verticalstepperform.Step;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;

public class LearningPathAddFragment extends Fragment implements StepperFormListener {

    private static final String TAG = "LearningPathAddFragment";
    private LearningPathTitleStep learningPathTitleStep;
    private LearningPathDescriptionStep learningPathDescriptionStep;
    private LearningPathTopicStep learningPathTopicStep;
    private LearningPathResourceStep learningPathResource;
    private LearningPathPriceStep learningPathPriceStep;
    private LearningPathsViewModel mViewModel;
    private FragmentLearningPathAddBinding binding;
    private LearningPath newLearningPath;

    private VerticalStepperFormView verticalStepperFormView;

    public static LearningPathAddFragment newInstance() {
        return new LearningPathAddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LearningPathsViewModel learningPathViewModel = new ViewModelProvider(this).get(LearningPathsViewModel.class);
        binding = FragmentLearningPathAddBinding.inflate(inflater, container, false);

        // if user exists continue
        User u4 = new User("rando", 4, false);
        String learningPathId = String.valueOf(Instant.now().toEpochMilli());
        newLearningPath = new LearningPath(learningPathId, u4);


        // Initialize form
        verticalStepperFormView = binding.stepperForm;

        // Create steps
        learningPathTitleStep = new LearningPathTitleStep(LEARNING_PATH_TITLE, newLearningPath);
        learningPathDescriptionStep = new LearningPathDescriptionStep(LEARNING_PATH_DESCRIPTION, newLearningPath);
        learningPathTopicStep = new LearningPathTopicStep(LEARNING_PATH_TOPIC, newLearningPath);
        learningPathPriceStep = new LearningPathPriceStep(LEARNING_PATH_PRICE, newLearningPath);
        learningPathResource = new LearningPathResourceStep(LEARNING_PATH_ADD_RESOURCE, verticalStepperFormView, newLearningPath);


        verticalStepperFormView.setup(this, learningPathTitleStep, learningPathDescriptionStep, learningPathTopicStep, learningPathPriceStep, learningPathResource)
                .lastStepCancelButtonText("Cancel")
                .displayCancelButtonInLastStep(true)
                .stepNextButtonText("Save & Continue")
                .stepNumberColors(getContext().getColor(R.color.purple_500), getContext().getColor(R.color.white))
                .lastStepNextButtonText("Save Learning Path")
                .init();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCompletedForm() {
        LearningPathsViewModel learningPathsViewModel = new ViewModelProvider(this).get(LearningPathsViewModel.class);
        newLearningPath.title = learningPathTitleStep.getStepData();
        newLearningPath.description = learningPathDescriptionStep.getStepData();
        newLearningPath.topic = learningPathTopicStep.getStepData();
        newLearningPath.price = learningPathPriceStep.getStepData();
        learningPathsViewModel.addLearningPath(newLearningPath);
        Log.v(TAG, "onCompleteForm Saved New Learning Path: " +  newLearningPath.toString());
        navigateToLearningPath();

    }

    @Override
    public void onCancelledForm() {
        Log.v(TAG, "onCancelledForm: " +  newLearningPath.toString());
        navigateToLearningPath();
    }

    @Override
    public void onStepAdded(int index, Step<?> addedStep) {

    }

    @Override
    public void onStepRemoved(int index) {

    }

    private void navigateToLearningPath() {
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.navigation_learning_paths);
    }
}