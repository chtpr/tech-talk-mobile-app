package edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.formsteps;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import ernestoyaquello.com.verticalstepperform.Step;

public class LearningPathTopicStep extends Step<String> {

    private EditText learningPathTopicView;
    private LearningPath newLearningPath;

    public LearningPathTopicStep(String stepTitle, LearningPath newLearningPath) {
        super(stepTitle);
        this.newLearningPath = newLearningPath;
    }

    @Override
    public String getStepData() {
        // get step's data from EditTex view
        Editable title = learningPathTopicView.getText();
        return title != null ? title.toString() : "";
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String topic = getStepData();
        return !topic.isEmpty() ? topic : "(Empty)";
    }

    @Override
    protected void restoreStepData(String data) {
        learningPathTopicView.setText(data);
    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        boolean isTopicValid = stepData.length() >= 3;
        String err= !isTopicValid ? "Minimum of 3 characters" : "";

        return new IsDataValid(isTopicValid, err);
    }

    @Override
    protected View createStepContentLayout() {
        learningPathTopicView = new EditText(getContext());
        learningPathTopicView.setSingleLine(true);
        learningPathTopicView.setHint("Learning path topic");
        learningPathTopicView.addTextChangedListener(new TextWatcher() {
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

        return learningPathTopicView;
    }

    @Override
    protected void onStepOpened(boolean animated) {

    }

    @Override
    protected void onStepClosed(boolean animated) {
        newLearningPath.topic = getStepData();
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {

    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {

    }
}
