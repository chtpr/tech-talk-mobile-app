package edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.EXPERT;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.LEARNING_PATH_LIST_UPDATE_ERROR;
import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.GET_USER_LEVEL;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.MainActivity;
import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.adapters.LearningPathsAdapter;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentLearningPathsBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.listeners.OnLearningPathClickListener;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class LearningPathsFragment extends Fragment {

    private static final String TAG = "LearningPathsFragment";
    private static final String LEARNING_PATH_ID = "learning_path_id";
    private FragmentLearningPathsBinding binding;
    LearningPathsAdapter learningPathsAdapter;
    User currUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        LearningPathsViewModel learningPathsViewModel = new ViewModelProvider(this).get(LearningPathsViewModel.class);

        learningPathsAdapter = new LearningPathsAdapter(new OnLearningPathClickListener() {
            @Override
            public void OnLearningPathClick(LearningPath learningPath) {
                Bundle args = new Bundle();
                args.putString(LEARNING_PATH_ID, learningPath.learningPathId);
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.navigation_learning_paths_detail, args);
            }
        });

        binding = FragmentLearningPathsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.rvLearningPathList.setAdapter(learningPathsAdapter);
        currUser = ((MainActivity)getActivity()).getUser();

        learningPathsViewModel.getLearningPaths().observe(getViewLifecycleOwner(), new Observer<List<LearningPath>>() {
            @Override
            public void onChanged(List<LearningPath> learningPathList) {
                if (learningPathList != null) {
                    Log.v(TAG, "OnChanged updated list!");
                    learningPathsAdapter.updateLearningPaths(learningPathList);

                } else {
                    Log.v(TAG, "OnChanged failed to update list!");
                    Toast.makeText(getActivity(),LEARNING_PATH_LIST_UPDATE_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currUser = currUser = ((MainActivity)getActivity()).getUser();

                // check if no logged user
                if (currUser == null) {
                    Log.v(TAG, "Anonymous user!");
                    new LearningPathLoginDialogFragment().show(getChildFragmentManager(), "LearningPathLoginDialogFragment");
                } else {
                    String userStatus = GET_USER_LEVEL(currUser.points);
                    if (!userStatus.equals(EXPERT)) {
                        Log.v(TAG, "Non Expert: Button Add On Click Listener!");
                        new LearningPathAddDialogFragment().show(getChildFragmentManager(), "LearningPathAddDialogFragment");

                    } else {
                        Log.v(TAG, "Button Add onClick Listener!");
                        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                        NavController navController = navHostFragment.getNavController();
                        navController.navigate(R.id.navigation_learning_paths_add);
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class LearningPathAddDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            User user = ((MainActivity)getActivity()).getUser();
            String userStatus = GET_USER_LEVEL(user.points);
            String message = "Your current status is a " + userStatus.toUpperCase() + " and only " + EXPERT.toUpperCase() + "S" + " can create a LEARNING PATH!" + "\n\n"
                    + "Posting a couple more RESOURCES or COLLECTIONS will get you to the " + EXPERT.toUpperCase() + " Level!!!!";
            SpannableString ss = styleMessage(message);
            return new AlertDialog.Builder(requireContext())
                    .setTitle("Not quite there yet, Mate!")
                    .setMessage(ss)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
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
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 62, 78, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }

    public static class LearningPathLoginDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            String message = "You must LOGON to be able to post a LEARNING PATH!";
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
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
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