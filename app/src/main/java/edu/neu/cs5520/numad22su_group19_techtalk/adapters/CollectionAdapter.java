package edu.neu.cs5520.numad22su_group19_techtalk.adapters;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.formatCollectionTitle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.databinding.CollectionItemBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Collection;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.CollectionOnClickListener;


public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionItemViewHolder> {
    List<Collection> collectionList;
    private final CollectionOnClickListener listener;
    private static final String RESOURCES = " Resources";


    public CollectionAdapter(List<Collection> collectionList, CollectionOnClickListener listener) {
        this.collectionList = collectionList;
        this.listener = listener;
    }

    public CollectionAdapter(CollectionOnClickListener listener) {
        this.collectionList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public CollectionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CollectionItemViewHolder(CollectionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionItemViewHolder holder, int position) {
        Collection collection = collectionList.get(position);
        holder.collectionItemBinding.collectionTitle.setText(formatCollectionTitle(collection.title));
        holder.collectionItemBinding.collectionUser.setText(collection.author.username);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnCollectionClick(collection);
            }
        });
    }

    public void updateCollections(List<Collection> collectionList) {
        this.collectionList.clear();
        this.collectionList.addAll(collectionList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public static class CollectionItemViewHolder extends RecyclerView.ViewHolder {
        CollectionItemBinding collectionItemBinding;

        public CollectionItemViewHolder(@NonNull CollectionItemBinding collectionItemBinding) {
            super(collectionItemBinding.getRoot());
            this.collectionItemBinding = collectionItemBinding;
        }

    }
}
