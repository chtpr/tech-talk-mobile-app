package edu.neu.cs5520.numad22su_group19_techtalk.adapters;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.formatResourceDescription;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.formatResourceTitle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.databinding.CollectionListItemBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;

public class CollectionContentsAdapter extends RecyclerView.Adapter<CollectionContentsAdapter.CollectionResourceViewHolder>{

    List<Resource> resources;
    private final OnResourceClickListener listener;

    public CollectionContentsAdapter(List<Resource> resources, OnResourceClickListener listener) {
        this.listener = listener;
        this.resources = resources;
    }

    public CollectionContentsAdapter(OnResourceClickListener listener) {
        this.listener = listener;
        resources = new ArrayList<>();
    }

    @NonNull
    @Override
    public CollectionContentsAdapter.CollectionResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CollectionContentsAdapter.CollectionResourceViewHolder(CollectionListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionContentsAdapter.CollectionResourceViewHolder holder, int position) {
        Resource resource = resources.get(position);
        holder.collectionListItemBinding.resourceTitle.setText(formatResourceTitle(resource.title));
        holder.collectionListItemBinding.resourceDescription.setText(formatResourceDescription(resource.description));
        //NOTE: Doesn't work when clicking on title
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnResourceClick(resource);
            }
        });


    }

    @Override
    public int getItemCount() {
        return resources.size();
    }

    public void updateCollectionResources(List<Resource> newResources) {
        resources.clear();
        resources.addAll(newResources);
        this.notifyDataSetChanged();
    }


    public static class CollectionResourceViewHolder extends RecyclerView.ViewHolder {
        CollectionListItemBinding collectionListItemBinding;

        public CollectionResourceViewHolder(@NonNull CollectionListItemBinding collectionListItemBinding, int ViewType) {
            super(collectionListItemBinding.getRoot());
            this.collectionListItemBinding = collectionListItemBinding;
        }
    }
}
