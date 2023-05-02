package edu.neu.cs5520.numad22su_group19_techtalk.ui.resources;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.RESOURCE_ADD_POINTS;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.ResourceSampleData.RESOURCE_SAMPLE_DATA;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.MainActivity;
import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.adapters.ResourceRecyclerAdapter;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentLearningPathsBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentResourcesBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.ResourceItemBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceCommentClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceDownvoteClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnResourceUpvoteClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPathResource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.LearningPathsViewModel;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.userprofile.UserProfileViewModel;

public class ResourcesFragment extends Fragment {
    private static final String TAG = "ResourceFragment";
    private static final String resource_id = "resource_id";

    private FragmentResourcesBinding binding;
    RecyclerView resourceRecyclerView;
    ResourceRecyclerAdapter resourceRecyclerAdapter;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ResourcesViewModel resourcesViewModel =
                new ViewModelProvider(this).get(ResourcesViewModel.class);
        binding = FragmentResourcesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        user = ((MainActivity)getActivity()).getUser();
        if (user == null) {
            root.findViewById(R.id.resource_add_butn).setVisibility(View.INVISIBLE);
        } else {
            root.findViewById(R.id.resource_add_butn).setVisibility(View.VISIBLE);
        }
        resourceRecyclerAdapter = new ResourceRecyclerAdapter(new OnResourceClickListener() {
            @Override
            public void OnResourceClick(Resource resource) {
                Log.v("tag", "Julie was here");
                Bundle args = new Bundle();
                args.putString(resource_id, resource.resourceID);
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.navigation_resources_detail, args);
            }
        }, new OnResourceUpvoteClickListener() {
            @Override
            public void OnResourceUpvoteClickListener(Resource resource) {
                Log.v("tag", "Creating new resource upvote click listener");
                if (user == null) {
                    Toast.makeText(getActivity(), "Unauthenticated user. Please sign in.",
                            Toast.LENGTH_LONG).show();
                } else {
                    resource.upvotes = resource.upvotes + 1;
                    resourcesViewModel.updateResource(resource);
                }
            }
        }, new OnResourceDownvoteClickListener() {
            @Override
            public void OnResourceDownvoteClickListener(Resource resource) {
                Log.v("tag", "Creating new resource downvote click listener");
                user = ((MainActivity)getActivity()).getUser();
                if (user == null) {
                    Toast.makeText(getActivity(), "Unauthenticated user. Please sign in.",
                            Toast.LENGTH_LONG).show();
                } else {
                    resource.downvotes = resource.downvotes + 1;
                    resourcesViewModel.updateResource(resource);
                }
            }
        }, new OnResourceCommentClickListener() {
            @Override
            public void OnResourceCommentClickListener(Resource resource) {
                Bundle args = new Bundle();
                args.putString("comment1", resource.comments.get(0));
                args.putString("comment2", resource.comments.get(1));
                ResourceCommentDialogFragment resourceCommentDialogFragment = new ResourceCommentDialogFragment();
                resourceCommentDialogFragment.setArguments(args);
                resourceCommentDialogFragment.show(getChildFragmentManager(), "Resource comments");
//                new ResourceCommentDialogFragment().setArguments(args).show(getChildFragmentManager(), "Resource comments");
            }
        });
        binding.resourceRecyclerview.setAdapter(resourceRecyclerAdapter);
        resourcesViewModel.getResources().observe(getViewLifecycleOwner(), new Observer<List<Resource>>() {
            @Override
            public void onChanged(List<Resource> resourceList) {
                if (resourceList != null) {
                    Log.v(TAG, "ENTERING IF " + resourceRecyclerAdapter.getItemCount());
                    resourceRecyclerAdapter.updateResources(resourceList);
                    Log.v(TAG, "ADDED RESOURCE " + resourceRecyclerAdapter.getItemCount());
                    printList(resourceList);
                } else {
                    Log.v(TAG, "ENTERING ELSE");
                    Toast.makeText(getActivity(), "Empty resource or unable to retrieve updated list",
                            Toast.LENGTH_LONG).show();

                }
            }
        });
        binding.resourceAddButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ResourceAddDialogFragment().show(getChildFragmentManager(), "ResourceDialogFragment");
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //TODO: implement resource list observer

    public void printList(List<Resource> resourceList) {
        for (Resource resource: resourceList) {
            Log.v(TAG, resource.toString());
        }
    }

    public static class ResourceCommentDialogFragment extends DialogFragment {
        private FragmentResourcesBinding binding;
        private ResourcesViewModel resourcesViewModel;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            binding = FragmentResourcesBinding.inflate(LayoutInflater.from(getContext()));
            resourcesViewModel = new ViewModelProvider(this).get(ResourcesViewModel.class);
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            LayoutInflater inflater = getLayoutInflater();

            String comment1 = "1. " + getArguments().getString("comment1");
            String comment2 = "2. " + getArguments().getString("comment2");
            View view = inflater.inflate(R.layout.resource_comments, null);
            TextView tv1 = (TextView) view.findViewById(R.id.comment1_tv);
            tv1.setText(comment1);
            TextView tv2 = (TextView) view.findViewById(R.id.comment2_tv);
            tv2.setText(comment2);

            return builder.setView(view)
                    .setMessage("Comments")
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .create();
        }
    }
    public static class ResourceAddDialogFragment extends DialogFragment {
        private FragmentResourcesBinding binding;
        private ResourcesViewModel resourcesViewModel;
        private UserProfileViewModel userProfileViewModel;
        private User u1;
        private User user;
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            binding = FragmentResourcesBinding.inflate(LayoutInflater.from(getContext()));
            resourcesViewModel = new ViewModelProvider(this).get(ResourcesViewModel.class);
            userProfileViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.resource_dialog, null);
            return builder.setView(view)
                .setMessage("Add a New Resource")
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String username = ((EditText)view.findViewById(R.id.resource_dialog_username)).getText().toString();
                user = ((MainActivity)getActivity()).getUser();
                if (user == null) {
                    u1 = new User(username, 0, false);
                } else {
                    u1 = user;
                    u1.points = u1.points + RESOURCE_ADD_POINTS;
                    userProfileViewModel.updateUser(u1);
                }
                String title = ((EditText)view.findViewById(R.id.resource_dialog_title)).getText().toString();
                String description = ((EditText)view.findViewById(R.id.resource_dialog_description)).getText().toString();
                String weburl = ((EditText)view.findViewById(R.id.resource_dialog_weburl)).getText().toString();
                int initialvotes = 0;
                String topic = ((EditText)view.findViewById(R.id.resource_dialog_topics)).getText().toString();
                String topic2 = "Android Studio";
                List<String> topics = Arrays.asList(topic, topic2);
                List<String> comments = Arrays.asList("Wow this is such an insightful resource!", "Great read. Thanks for posting!");

                Resource r1 = new Resource("1", title, description, u1, "image.com", weburl, initialvotes, initialvotes, topics, comments, false);
                resourcesViewModel.addResource(r1);
                Toast.makeText(getActivity(), "Added new Resource " + title.toString(),
                        Toast.LENGTH_LONG).show();
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        })
                .create();
    }
}}