package edu.neu.cs5520.numad22su_group19_techtalk.ui.collections;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.adapters.CollectionContentsAdapter;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentCollectionContentsBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Collection;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;
import edu.neu.cs5520.numad22su_group19_techtalk.repositories.ResourceRepository;

public class CollectionContentsFragment extends Fragment {

    private static final String TAG = "CollectionContentsFragment";
    private FragmentCollectionContentsBinding binding;
    private static final String COLLECTION_ID = "collection_id";
    private static final String RESOURCE_ID = "resource_id";
    private String argCollectionId;
    CollectionContentsAdapter collectionContentsAdapter;
    ResourceRepository resourceRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");

        CollectionsViewModel collectionsViewModel = new ViewModelProvider(this).get(CollectionsViewModel.class);
        binding = FragmentCollectionContentsBinding.inflate(inflater, container, false);
        resourceRepository = ResourceRepository.getInstance();
        Bundle arguments = getArguments();
        argCollectionId = getArguments().getString(COLLECTION_ID);

        collectionContentsAdapter = new CollectionContentsAdapter(new OnResourceClickListener() {
            @Override
            public void OnResourceClick(Resource resource) {
                Log.v(TAG, "Collection resource" + resource.resourceID);
                Bundle args = new Bundle();
                args.putString(COLLECTION_ID, argCollectionId);
                args.putString(RESOURCE_ID, resource.resourceID);
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.navigation_collection_resource_detail, args);
            }
        });
        binding.rvCollectionList.setAdapter(collectionContentsAdapter);

        collectionsViewModel.getCollectionById(argCollectionId).observe(getViewLifecycleOwner(), new Observer<Collection>() {
            @Override
            public void onChanged(Collection collection) {
                Log.v(TAG, "onChanged");
                binding.collectionTitle.setText(collection.title);
                collectionContentsAdapter.updateCollectionResources(collection.resources);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}