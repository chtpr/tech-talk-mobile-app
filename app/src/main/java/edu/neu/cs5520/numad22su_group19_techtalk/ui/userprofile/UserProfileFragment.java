package edu.neu.cs5520.numad22su_group19_techtalk.ui.userprofile;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.GET_USER_LEVEL;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.neu.cs5520.numad22su_group19_techtalk.MainActivity;
import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentUserProfileBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class UserProfileFragment extends Fragment {
    private FragmentUserProfileBinding binding;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        UserProfileViewModel userProfileViewModel =
                new ViewModelProvider(this).get(UserProfileViewModel.class);

        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        user = ((MainActivity)getActivity()).getUser();
        String city = ((MainActivity)getActivity()).getCity();
        String cityTextview = "Current city: " + city;
        Log.d("UserFragment", cityTextview);
        binding.userProfileCity.setText(cityTextview);

        // check if user is authenticated
        if (user == null) {
            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.navigation_login);
            user = ((MainActivity)getActivity()).getUser();
        }


        final TextView textView = binding.userProfileUsername;

        Log.v("tag", "msg");
        if (user != null) {
            userProfileViewModel.getUserById(user.userID).observe(getViewLifecycleOwner(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    Log.d("UserProfileFragment", "Making new instance of location finder");
                    binding.userProfileUsername.setText(user.username);
                    String points = "Level: " + GET_USER_LEVEL(user.points);
                    binding.userProfilePoints.setText(points);
                    String isadmin = "Is admin: " + user.isAdmin;
                    binding.userProfileIsadmin.setText(isadmin);
                    String createdon = "Created on: " + user.creationDate;
                    binding.userProfileCreationdate.setText(createdon);
                }
            });
        }

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setUser(null);
                user = null;
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.navigation_home);
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