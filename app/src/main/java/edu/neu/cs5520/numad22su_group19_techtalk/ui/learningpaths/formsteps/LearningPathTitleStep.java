package edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.formsteps;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import ernestoyaquello.com.verticalstepperform.Step;

public class LearningPathTitleStep extends Step<String> {

    private EditText learningPathTitleView;
    private LearningPath newLearningPath;

    public LearningPathTitleStep(String stepTitle, LearningPath newLearningPath) {
        super(stepTitle);
        this.newLearningPath = newLearningPath;
    }

    @Override
    public String getStepData() {
        // get step's data from EditTex view
        Editable title = learningPathTitleView.getText();
        return title != null ? title.toString() : "";
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String title = getStepData();
        return !title.isEmpty() ? title : "(Empty)";
    }

    @Override
    protected void restoreStepData(String data) {
        learningPathTitleView.setText(data);
    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        boolean isTitleValid = stepData.length() >= 6;
        String err= !isTitleValid ? "Minimum of 5 characters" : "";

        return new IsDataValid(isTitleValid, err);
    }

    @Override
    protected View createStepContentLayout() {
        learningPathTitleView = new EditText(getContext());
        learningPathTitleView.setSingleLine(true);
        learningPathTitleView.setHint("Learning path title");
        learningPathTitleView.addTextChangedListener(new TextWatcher() {
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

        return learningPathTitleView;
    }

    @Override
    protected void onStepOpened(boolean animated) {

    }

    @Override
    protected void onStepClosed(boolean animated) {
        newLearningPath.title = getStepData();
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {

    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {

    }
}
