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
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.MainActivity;
import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.adapters.AddResourceAdapter;
import edu.neu.cs5520.numad22su_group19_techtalk.adapters.CollectionAdapter;
import edu.neu.cs5520.numad22su_group19_techtalk.adapters.CollectionContentsAdapter;
import edu.neu.cs5520.numad22su_group19_techtalk.adapters.LearningPathTimelineAdapter;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentAddNewCollectionBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentLearningPathsDetailBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.CheckboxClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnLearningPathTimelineClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Collection;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPathResource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;
import edu.neu.cs5520.numad22su_group19_techtalk.repositories.CollectionRepository;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.LearningPathsFragment;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.LearningPathsViewModel;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.resources.ResourcesViewModel;

public class AddNewCollectionFragment extends Fragment {

    private static final String TAG = "AddNewCollectionFragment";
    private static final Integer MINIMUM_NUM_RESOURCES = 0;
    private FragmentAddNewCollectionBinding binding;
    AddResourceAdapter addResourceAdapter;
    CollectionRepository collectionRepository;
    List<Resource> resourceList;
    String title;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");

        ResourcesViewModel resourcesViewModel = new ViewModelProvider(this).get(ResourcesViewModel.class);

        binding = FragmentAddNewCollectionBinding.inflate(inflater, container, false);

        collectionRepository = CollectionRepository.getInstance();

        resourceList = new ArrayList<>();

        addResourceAdapter = new AddResourceAdapter(new CheckboxClickListener() {
            @Override
            public void OnCheckboxClick(CheckBox checkBox, Resource resource) {
                Log.v(TAG, "Clicked checkbox");
                if (!checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    resourceList.remove(resource);
                } else {
                    checkBox.setChecked(true);
                    resourceList.add(resource);
                }
                Log.v(TAG, String.valueOf(resourceList.size()));
                Log.v(TAG, resource.resourceID);
            }
        });
        binding.rvResourceList.setAdapter(addResourceAdapter);

        resourcesViewModel.getResources().observe(getViewLifecycleOwner(), new Observer<List<Resource>>() {
            @Override
            public void onChanged(List<Resource> resourceList) {
                addResourceAdapter.updateCollectionResources(resourceList);
            }
        });

        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Button Add On Click Listener!");
                title = binding.collectionDialogTitle.getText().toString();
                if (title != null && resourceList.size() > MINIMUM_NUM_RESOURCES) {
                    user = ((MainActivity)getActivity()).getUser();
                    Collection newCollection = new Collection(title, user, resourceList, false);
                    collectionRepository.addCollection(newCollection);
                    NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.navigation_collections);
                }
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