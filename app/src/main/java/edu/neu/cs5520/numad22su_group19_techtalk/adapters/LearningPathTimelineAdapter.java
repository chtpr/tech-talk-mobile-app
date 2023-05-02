package edu.neu.cs5520.numad22su_group19_techtalk.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.LearningPathDetailTimemelineBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnLearningPathTimelineClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPathResource;


public class LearningPathTimelineAdapter extends RecyclerView.Adapter<LearningPathTimelineAdapter.LPTimeLineViewHolder>{

    List<LearningPathResource> learningPathResources;
    private final OnLearningPathTimelineClickListener listener;

    public LearningPathTimelineAdapter(List<LearningPathResource> learningPathResources, OnLearningPathTimelineClickListener listener) {
        this.listener = listener;
        this.learningPathResources = learningPathResources;
    }

    public LearningPathTimelineAdapter(OnLearningPathTimelineClickListener listener) {
        this.listener = listener;
        learningPathResources = new ArrayList<>();
    }

    @NonNull
    @Override
    public LPTimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LearningPathTimelineAdapter.LPTimeLineViewHolder(LearningPathDetailTimemelineBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull LPTimeLineViewHolder holder, int position) {
        LearningPathResource learningPathResource = learningPathResources.get(position);

        if (learningPathResource.isCompleted) {
            holder.learningPathDetailTimemelineBinding.timelineView.setMarker(holder.learningPathDetailTimemelineBinding.getRoot().getContext().getDrawable(R.drawable.ic_marker), R.color.purple_200);
        } else {
            holder.learningPathDetailTimemelineBinding.timelineView.setMarker(holder.learningPathDetailTimemelineBinding.getRoot().getContext().getDrawable(R.drawable.ic_marker_inactive));
        }

        holder.learningPathDetailTimemelineBinding.txtName.setText(learningPathResource.title);

    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return learningPathResources.size();
    }

    public void updateLearningPathResources(List<LearningPathResource> newLearningPathResources) {
        learningPathResources.clear();
        learningPathResources.addAll(newLearningPathResources);
        this.notifyDataSetChanged();
    }


    public static class LPTimeLineViewHolder extends RecyclerView.ViewHolder {
        LearningPathDetailTimemelineBinding learningPathDetailTimemelineBinding;

        public LPTimeLineViewHolder(@NonNull LearningPathDetailTimemelineBinding learningPathDetailTimemelineBinding, int ViewType) {
            super(learningPathDetailTimemelineBinding.getRoot());
            this.learningPathDetailTimemelineBinding = learningPathDetailTimemelineBinding;
            this.learningPathDetailTimemelineBinding.timelineView.initLine(ViewType);
        }
    }
}

