package edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.formsteps;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import ernestoyaquello.com.verticalstepperform.Step;

public class LearningPathDescriptionStep extends Step<String> {
    private EditText learningPathDescriptionView;
    private LearningPath newLearningPath;
    public LearningPathDescriptionStep(String stepTitle, LearningPath newLearningPath) {
        super(stepTitle, "");
        this.newLearningPath = newLearningPath;
    }

    @Override
    public String getStepData() {
        // get step's data from EditTex view
        Editable description = learningPathDescriptionView.getText();
        return description != null ? description.toString() : "";
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String description  = getStepData();
        return !description.isEmpty() ? description  : "(Empty)";
    }

    @Override
    protected void restoreStepData(String data) {
        learningPathDescriptionView.setText(data);
    }

    @Override
    protected Step.IsDataValid isStepDataValid(String stepData) {
        boolean isDescriptionValid = stepData.length() >= 6;
        String err= !isDescriptionValid ? "Minimum of 8 characters" : "";

        return new IsDataValid(isDescriptionValid, err);
    }

    @Override
    protected View createStepContentLayout() {
        learningPathDescriptionView = new EditText(getContext());
        learningPathDescriptionView.setSingleLine(true);
        learningPathDescriptionView.setHint("Learning path description");
        learningPathDescriptionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                markAsCompletedOrUncompleted(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return learningPathDescriptionView;
    }

    @Override
    protected void onStepOpened(boolean animated) {

    }

    @Override
    protected void onStepClosed(boolean animated) {


    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {
        newLearningPath.description = getStepData();
    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {

    }
}
