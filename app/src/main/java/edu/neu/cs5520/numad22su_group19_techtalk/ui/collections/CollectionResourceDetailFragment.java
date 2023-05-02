package edu.neu.cs5520.numad22su_group19_techtalk.ui.collections;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentResourcesDetailBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Collection;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.resources.ResourcesViewModel;

public class CollectionResourceDetailFragment extends Fragment {
    private static final String TAG = "CollectionResourceDetailFragment";
    private FragmentResourcesDetailBinding binding;
    private static final String RESOURCE_ID = "resource_id";
    private static final String COLLECTION_ID = "collection_id";
    private String argsResourceID;
    private String argsCollectionId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CollectionsViewModel collectionsViewModel = new ViewModelProvider(this).get(CollectionsViewModel.class);
        binding = FragmentResourcesDetailBinding.inflate(inflater, container, false);
        Bundle args = getArguments();

        if (args != null) {
            argsResourceID = getArguments().getString(RESOURCE_ID);
            argsCollectionId = getArguments().getString(COLLECTION_ID);
            collectionsViewModel.getCollectionById(argsCollectionId).observe(getViewLifecycleOwner(), new Observer<Collection>() {
                @Override
                public void onChanged(Collection collection) {
                    Log.v(TAG, "Collection ID " + collection.collectionID);

                    for (Resource resource : collection.resources) {
                        if (resource.resourceID.equals(argsResourceID)) {
                            binding.detailTitleTv.setText(resource.title);
                            binding.resourceDescriptionTv.setText(resource.description);
                            //binding.userDetailTv.setText(resource.user.username);
                            binding.userDetailTv.setText(collection.author.username);
                        }
                    }
                }
            });
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}