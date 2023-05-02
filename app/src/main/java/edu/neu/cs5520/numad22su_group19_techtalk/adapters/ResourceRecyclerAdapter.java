package edu.neu.cs5520.numad22su_group19_techtalk.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.ResourceItemBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceCommentClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceDownvoteClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceUpvoteClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;

public class ResourceRecyclerAdapter extends RecyclerView.Adapter<ResourceRecyclerAdapter.ResourceViewHolder> {
    List<Resource> resourceList;
    private final OnResourceClickListener listener;
    private static final String TAG = "ResourceAdapter";
    private final OnResourceUpvoteClickListener upvoteClickListener;
    private final OnResourceDownvoteClickListener downvoteClickListener;
    private final OnResourceCommentClickListener commentClickListener;
    private static final int TITLE_DISPLAY_MAX_LENGTH = 40;

    public ResourceRecyclerAdapter(List<Resource> resourceList, OnResourceClickListener listener, OnResourceUpvoteClickListener upvoteClickListener, OnResourceDownvoteClickListener downvoteClickListener, OnResourceCommentClickListener commentClickListener) {
        this.resourceList = resourceList;
        this.listener = listener;
        this.upvoteClickListener = upvoteClickListener;
        this.downvoteClickListener = downvoteClickListener;
        this.commentClickListener = commentClickListener;
    }

    public ResourceRecyclerAdapter(OnResourceClickListener listener, OnResourceUpvoteClickListener upvoteClickListener, OnResourceDownvoteClickListener downvoteClickListener, OnResourceCommentClickListener commentClickListener) {
        this.resourceList = new ArrayList<>();
        this.listener = listener;
        this.upvoteClickListener = upvoteClickListener;
        this.downvoteClickListener = downvoteClickListener;
        this.commentClickListener = commentClickListener;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResourceViewHolder(ResourceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        Resource resource = resourceList.get(position);
        holder.resourceItemBinding.resourceTitleTv.setText(formatTitle(resource.title));
        holder.resourceItemBinding.topicTv1.setText(resource.topics.get(0));
        holder.resourceItemBinding.topicTv2.setText(resource.topics.get(1));
        String numComments = "+" + resource.numComments;
        String votes = (resource.upvotes - resource.downvotes) >= 0 ? "+" + (resource.upvotes - resource.downvotes) : "-" + (resource.upvotes - resource.downvotes);
        holder.resourceItemBinding.votesTv.setText(votes);
        String createdBy = "By: " + resource.user.username;
        holder.resourceItemBinding.createdByTv.setText(createdBy);

        holder.resourceItemBinding.resourceTitleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnResourceClick(resource);
            }
        });

        holder.resourceItemBinding.upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upvoteClickListener.OnResourceUpvoteClickListener(resource);
            }
        });

        holder.resourceItemBinding.downvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downvoteClickListener.OnResourceDownvoteClickListener(resource);
            }
        });

        holder.resourceItemBinding.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentClickListener.OnResourceCommentClickListener(resource);
            }
        });
    }

    private String formatTitle(String title) {
        if (title.length() > TITLE_DISPLAY_MAX_LENGTH) {
            return title.substring(0, TITLE_DISPLAY_MAX_LENGTH) + "...";
        }
        return title;
    }

    public void updateResources(List<Resource> resourceList) {
        this.resourceList.clear();
        this.resourceList.addAll(resourceList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    class ResourceViewHolder extends RecyclerView.ViewHolder {
        ResourceItemBinding resourceItemBinding;

        public ResourceViewHolder(@NonNull ResourceItemBinding resourceItemBinding) {
            super(resourceItemBinding.getRoot());
            this.resourceItemBinding = resourceItemBinding;
            resourceItemBinding.resourceTitleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "clicked the title - open to resource page");
                }
            });
            resourceItemBinding.commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "clicked the comments button");
                }
            });

        }

    }
}
