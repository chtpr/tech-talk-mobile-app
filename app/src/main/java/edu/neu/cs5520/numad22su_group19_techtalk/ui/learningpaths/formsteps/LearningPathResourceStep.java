package edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.formsteps;


import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.LEARNING_PATH_ADD_ANOTHER_RESOURCE;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.time.Instant;

import edu.neu.cs5520.numad22su_group19_techtalk.databinding.LearningPathAddResourceDialogBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPathResource;
import ernestoyaquello.com.verticalstepperform.Step;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;

public class LearningPathResourceStep extends Step<LearningPathResource>  {

    private static final String TAG = "LearningPathResourceStep";
    Button btnAddResource;
    String resourceTitle;
    private LearningPathAddResourceDialogBinding binding;
    private VerticalStepperFormView verticalStepperFormView;
    private LearningPathResource learningPathResource;
    private LearningPath newLearningPath;


    public LearningPathResourceStep(String title, VerticalStepperFormView verticalStepperFormView, LearningPath newLearningPath) {
        super(title);
        this.resourceTitle = title;
        this.verticalStepperFormView = verticalStepperFormView;
        this.learningPathResource = new LearningPathResource();
        this.newLearningPath = newLearningPath;
    }

    @Override
    public LearningPathResource getStepData() {
        return new LearningPathResource(resourceTitle);
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String title  = getStepData().title;
        return !title.isEmpty() ? title  : "(Empty)";
    }

    @Override
    protected void restoreStepData(LearningPathResource data) {
        resourceTitle = data.title;
    }

    @Override
    protected IsDataValid isStepDataValid(LearningPathResource stepData) {
        boolean isTitleValid = stepData.title.length() >= 6;
        String err= !isTitleValid ? "Minimum of 4 characters" : "";

        return new IsDataValid(isTitleValid, err);
    }

    @Override
    protected View createStepContentLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        binding = LearningPathAddResourceDialogBinding.inflate(inflater, (ViewGroup)getFormView() , false);
        btnAddResource = binding.resourceAddBtn;

        btnAddResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "OnClick createStepContentLayout");
                int nextStep = verticalStepperFormView.getOpenStepPosition() + 1;
                LearningPathResourceStep lpResourceStep = new LearningPathResourceStep(LEARNING_PATH_ADD_ANOTHER_RESOURCE, verticalStepperFormView, newLearningPath);

                // Generate on-demand steps
                verticalStepperFormView.addStep(nextStep, lpResourceStep);
                verticalStepperFormView.goToNextStep(true);
            }
        });
        return binding.getRoot();
    }

    @Override
    protected void onStepOpened(boolean animated) {

    }

    @Override
    protected void onStepClosed(boolean animated) {

        // Add resource to Learning Path
        saveLearningPathResource();
        if(!learningPathResource.author.isEmpty())
            newLearningPath.learningPathResources.add(learningPathResource);
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {

    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {

    }

    public LearningPathResource getLearningPathResource() {
        return learningPathResource;
    }

    private void saveLearningPathResource() {
        // Save Data
        learningPathResource.resourceID = String.valueOf(Instant.now().toEpochMilli());
        learningPathResource.title = binding.lpResourceDialogTitle.getText().toString();
        learningPathResource.author = binding.lpResourceDialogAuthor.getText().toString();
        learningPathResource.url = binding.lpResourceDialogUrl.getText().toString();
        learningPathResource.topic = binding.lpResourceDialogTopic.getText().toString();
        learningPathResource.source = "online";
        learningPathResource.type = binding.lpResourceTypeSpinner.getSelectedItem().toString();
        learningPathResource.isCompleted = false;
        learningPathResource.isPaid = (binding.lpResourceContentCheckBox.isChecked())? true : false;
    }
}

