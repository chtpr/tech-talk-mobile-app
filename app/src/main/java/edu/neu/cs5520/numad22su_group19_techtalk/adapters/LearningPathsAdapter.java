package edu.neu.cs5520.numad22su_group19_techtalk.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.databinding.LearningPathItemBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnLearningPathClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;

public class LearningPathsAdapter extends RecyclerView.Adapter<LearningPathsAdapter.LPViewHolder> {

    private static final String TAG = " LearningPathsAdapter";
    private static final int DESCRIPTION_DISPLAY_MAX_LENGTH = 76;
    private static final int TITLE_DISPLAY_MAX_LENGTH = 28;
    List<LearningPath> learningPaths;
    private final OnLearningPathClickListener listener;

    public LearningPathsAdapter(List<LearningPath> learningPaths, OnLearningPathClickListener listener) {
        this.learningPaths = learningPaths;
        this.listener = listener;
    }

    public LearningPathsAdapter(OnLearningPathClickListener listener) {
        this.learningPaths = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public LPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LPViewHolder(LearningPathItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LPViewHolder holder, int position) {
        LearningPath learningPath = learningPaths.get(position);
        holder.learningPathItemBinding.txtLPTitle.setText(formatTitle(learningPath.title));
        holder.learningPathItemBinding.txtLPDescription.setText(formatDescription(learningPath.description));
        holder.learningPathItemBinding.txtAuthor.setText("By " + learningPath.author.username);
        holder.learningPathItemBinding.chpTopic.setText(learningPath.topic);
        holder.learningPathItemBinding.txtLPNumOfResources.setText(getNumOfResourcesString(learningPath.learningPathResources.size()));
        holder.learningPathItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnLearningPathClick(learningPath);
            }
        });
    }

    @Override
    public int getItemCount() {
        return learningPaths.size();
    }

    public void updateLearningPaths(List<LearningPath> newLearningPaths) {
        learningPaths.clear();
        learningPaths.addAll(newLearningPaths);
        this.notifyDataSetChanged();
    }

    private String getNumOfResourcesString(int itemSize) {
        if (itemSize == 0 ) {
            return "Empty Resources";
        } else if (itemSize == 1) {
            return "1 Resource";
        } else {
            return itemSize + " Resources";
        }
    }

    private String formatDescription(String description) {

        if (description.length() > DESCRIPTION_DISPLAY_MAX_LENGTH) {
            return description.substring(0, DESCRIPTION_DISPLAY_MAX_LENGTH) + "...";
        }
        return description;
    }

    private String formatTitle(String title) {
        if (title.length() > TITLE_DISPLAY_MAX_LENGTH) {
            return title.substring(0, TITLE_DISPLAY_MAX_LENGTH) + "...";
        }
        return title;
    }

    public static class LPViewHolder extends RecyclerView.ViewHolder {

        LearningPathItemBinding learningPathItemBinding;

        public LPViewHolder(@NonNull LearningPathItemBinding learningPathItemBinding) {
            super(learningPathItemBinding.getRoot());
            this.learningPathItemBinding = learningPathItemBinding;
        }
    }
}
