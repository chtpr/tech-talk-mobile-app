package edu.neu.cs5520.numad22su_group19_techtalk.ui.collections;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.MainActivity;
import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.adapters.CollectionAdapter;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentCollectionsBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.CollectionOnClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Collection;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class CollectionsFragment extends Fragment {

    private static final String TAG = "CollectionsFragment";
    private static final String COLLECTION_ID = "collection_id";
    private FragmentCollectionsBinding binding;
    private CollectionAdapter collectionAdapter;
    private List<Collection> collectionList = new ArrayList<>();
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CollectionsViewModel collectionsViewModel =
                new ViewModelProvider(this).get(CollectionsViewModel.class);

        binding = FragmentCollectionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        user = ((MainActivity)getActivity()).getUser();
        if (user == null) {
            binding.addCollection.setVisibility(View.INVISIBLE);
        } else {
            binding.addCollection.setVisibility(View.VISIBLE);
        }

        collectionAdapter = new CollectionAdapter(new CollectionOnClickListener() {
            @Override
            public void OnCollectionClick(Collection collection) {
                Log.v(TAG, "Clicked Text");
                Bundle args = new Bundle();
                args.putString(COLLECTION_ID, collection.collectionID);
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.navigation_collection_contents, args);
            }
        });

        binding.rvCollectionList.setAdapter(collectionAdapter);
        collectionsViewModel.getCollections().observe(getViewLifecycleOwner(), new Observer<List<Collection>>() {
            @Override
            public void onChanged(List<Collection> collectionList) {
                if (collectionList != null) {
                    Log.v(TAG, "Called Data Changed!");
                    collectionAdapter.updateCollections(collectionList);
                } else {
                    Log.v(TAG, "Called Data Changed! Went to else");
                    Toast.makeText(getActivity(), "Empty learn path or unable to retrieve updated list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.addCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Add Collection Button clicked!");
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.navigation_add_collection);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}