package edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.cs5520.numad22su_group19_techtalk.MainActivity;
import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.adapters.LearningPathTimelineAdapter;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentLearningPathsDetailBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnLearningPathTimelineClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPathResource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;


public class LearningPathsDetailFragment extends Fragment {

    private static final String TAG = "LearningPathsDetailFragment";
    private FragmentLearningPathsDetailBinding binding;
    private static final String LEARNING_PATH_ID = "learning_path_id";
    private String argLearningPathId;
    LearningPathTimelineAdapter learningPathTimelineAdapter;
    User currUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");

        LearningPathsViewModel learningPathsViewModel = new ViewModelProvider(this).get(LearningPathsViewModel.class);
        binding = FragmentLearningPathsDetailBinding.inflate(inflater, container, false);
        Bundle arguments = getArguments();
        currUser = ((MainActivity)getActivity()).getUser();

        learningPathTimelineAdapter = new LearningPathTimelineAdapter(new OnLearningPathTimelineClickListener() {
            @Override
            public void OnLearningPathTimelineClick(LearningPathResource learningPathResource) {
                Log.v(TAG, "OnLearningPathTimelineClick");
            }
        });
        binding.rvLearningPathTimeline.setAdapter(learningPathTimelineAdapter);

        if (arguments != null) {
            argLearningPathId = getArguments().getString(LEARNING_PATH_ID);
            learningPathsViewModel.getLearningPathById(argLearningPathId).observe(getViewLifecycleOwner(), new Observer<LearningPath>() {
                @Override
                public void onChanged(LearningPath learningPath) {
                    Log.v(TAG, "onChanged");
                    binding.txtLPDetailTitle.setText(learningPath.title);
                    binding.txtLPDetailAuthor.setText("By " + learningPath.author.username);
                    binding.txtLPDetailDescription.setText(learningPath.description);
                    binding.txtLPNumOfResourcesDetail.setText(learningPath.learningPathResources.size() + " Resources");
                    binding.txtLPNumOfSubscribersDetail.setText(learningPath.subscribers.size() + " Subscribers");
                    binding.txtLPDetailPrice.setText("$" + String.format("%.2f", learningPath.price));
                    learningPathTimelineAdapter.updateLearningPathResources(learningPath.learningPathResources);

                    // Check if user is logged in
                    if (currUser != null) {
                        Boolean isUserSubscribed = false;
                        for (User usr: learningPath.subscribers) {
                            if (currUser.username.equals(usr.username)) {
                                isUserSubscribed = true;
                            }
                        }

                        // Check if user is subscribed
                        if (isUserSubscribed) {
                            binding.btnLPDetailSubscribe.setText("Subscribed");
                            binding.btnLPDetailSubscribe.setBackgroundColor(getActivity().getResources().getColor(R.color.rv_background));
                            binding.btnLPDetailSubscribe.setEnabled(false);
                            binding.txtViewMore.setVisibility(View.GONE);
                            binding.rvLearningPathTimeline.setVisibility(View.VISIBLE);
                        } else {

                            binding.txtViewMore.setVisibility(View.VISIBLE);
                            binding.rvLearningPathTimeline.setVisibility(View.GONE);
                            binding.btnLPDetailSubscribe.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Subscribe user
                                    Log.v(TAG, "Triggered Subscribe!!!");
                                    learningPath.subscribers.add(currUser);
                                    learningPathsViewModel.updateLearningPath(learningPath);;
                                }
                            });
                        }
                    }
                }
            });
        }

        if (currUser == null) {
            Log.v(TAG, "Anonymous user!");
            new LearningPathsDetailFragment.LearningPathDetailLoginDialogFragment().show(getChildFragmentManager(), "LearningPathDetailLoginDialogFragment");
        }



        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class LearningPathDetailLoginDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            String message = "You must LOGON to see a Learning Path content";
            SpannableString ss = styleMessage(message);
            return new AlertDialog.Builder(requireContext())
                    .setTitle("Click that logon button, Mate!")
                    .setMessage(ss)
                    .setPositiveButton("Logon", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                            NavController navController = navHostFragment.getNavController();
                            navController.popBackStack();
                            navController.navigate(R.id.navigation_login);

                        }
                    }).setNegativeButton("Return", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                            NavController navController = navHostFragment.getNavController();
                            navController.popBackStack();
                            navController.navigate(R.id.navigation_learning_paths);
                        }
                    })
                    .create();
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
        }

        private SpannableString styleMessage(String message) {
            SpannableString spannableString = new SpannableString(message);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 9, 15, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }
}