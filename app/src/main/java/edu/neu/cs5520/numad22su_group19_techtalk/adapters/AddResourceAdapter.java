package edu.neu.cs5520.numad22su_group19_techtalk.adapters;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.formatResourceTitle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.databinding.AddResourceItemBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.CheckboxClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;

public class AddResourceAdapter extends RecyclerView.Adapter<AddResourceAdapter.AddResourceViewHolder>{

    List<Resource> resources;
    private final CheckboxClickListener listener;

    public AddResourceAdapter(List<Resource> resources, CheckboxClickListener listener) {
        this.listener = listener;
        this.resources = resources;
    }

    public AddResourceAdapter(CheckboxClickListener listener) {
        this.listener = listener;
        resources = new ArrayList<>();
    }

    @NonNull
    @Override
    public AddResourceAdapter.AddResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddResourceAdapter.AddResourceViewHolder(AddResourceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), viewType);
    }

    public void onBindViewHolder(@NonNull AddResourceAdapter.AddResourceViewHolder holder, int position) {
        Resource resource = resources.get(position);
        holder.addResourceItemBinding.resourceTitleTv.setText(formatResourceTitle(resource.title));
        holder.addResourceItemBinding.createdByTv.setText(resource.user.username);
        CheckBox checkBox = holder.addResourceItemBinding.checkBox;
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnCheckboxClick(checkBox, resource);
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

    public static class AddResourceViewHolder extends RecyclerView.ViewHolder {
        AddResourceItemBinding addResourceItemBinding;

        public AddResourceViewHolder(@NonNull AddResourceItemBinding addResourceItemBinding, int ViewType) {
            super(addResourceItemBinding.getRoot());
            this.addResourceItemBinding = addResourceItemBinding;
        }
    }
}

