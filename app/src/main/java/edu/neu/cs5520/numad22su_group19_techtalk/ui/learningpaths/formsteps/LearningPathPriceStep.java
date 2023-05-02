package edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.formsteps;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import ernestoyaquello.com.verticalstepperform.Step;

public class LearningPathPriceStep extends Step<Double> {

    private EditText learningPathPriceView;
    private LearningPath newLearningPath;

    public LearningPathPriceStep(String title, LearningPath newLearningPath) {
        super(title);
        this.newLearningPath = newLearningPath;
    }

    @Override
    public Double getStepData() {
        // get step's data from EditTex view
        Editable price = learningPathPriceView.getText();
        if (price == null) {
            return 0.0;
        }
        return (price.toString().matches("\\d+(?:\\.\\d+)?")) ? Double.parseDouble(price.toString()) : 0.0;
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String price = String.valueOf(getStepData());
        return !price.isEmpty() ? price : "(Free)";
    }

    @Override
    protected void restoreStepData(Double data) {
        learningPathPriceView.setText(data.toString());
    }

    @Override
    protected IsDataValid isStepDataValid(Double stepData) {
        boolean isPriceValid = stepData >= 0;
        String err= !isPriceValid ? "Enter 0 for free resources" : "";

        return new IsDataValid(isPriceValid, err);

    }

    @Override
    protected View createStepContentLayout() {
        learningPathPriceView = new EditText(getContext());
        learningPathPriceView.setSingleLine(true);
        learningPathPriceView.setHint("Learning path price");
        learningPathPriceView.addTextChangedListener(new TextWatcher() {
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

        return learningPathPriceView;
    }

    @Override
    protected void onStepOpened(boolean animated) {

    }

    @Override
    protected void onStepClosed(boolean animated) {
        newLearningPath.topic = String.valueOf(getStepData());
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {

    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {

    }
}
