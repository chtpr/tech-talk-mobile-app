package edu.neu.cs5520.numad22su_group19_techtalk.ui.resources;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentResourcesDetailBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;

public class ResourcesDetailFragment extends Fragment {
    private static final String TAG = "ResourcesDetailFragment";
    private FragmentResourcesDetailBinding binding;
    private static final String resource_id = "resource_id";
    private String resourceID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ResourcesViewModel resourcesViewModel = new ViewModelProvider(this).get(ResourcesViewModel.class);
        binding = FragmentResourcesDetailBinding.inflate(inflater, container, false);
        Bundle args = getArguments();

        if (args != null) {
            resourceID = getArguments().getString(resource_id);
            resourcesViewModel.getResourceById(resourceID).observe(getViewLifecycleOwner(), new Observer<Resource>() {
                @Override
                public void onChanged(Resource resource) {
                    binding.detailTitleTv.setText(resource.title);
                    binding.resourceDescriptionTv.setText(resource.description);
                    String createdby = "By: " + resource.user.username;
                    binding.userDetailTv.setText(createdby);
                    String downvotes = "-" + String.valueOf(resource.downvotes);
                    binding.detailDownvotes.setText(downvotes);
                    String upvotes = "+" + String.valueOf(resource.upvotes);
                    binding.detailUpvotes.setText(upvotes);
                    String numcomments = "+" + String.valueOf(resource.comments.size());
                    binding.detailNumComments.setText(numcomments);
                    binding.detailTopic1.setText(resource.topics.get(0));
                    binding.detailTopic2.setText(resource.topics.get(1));
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
