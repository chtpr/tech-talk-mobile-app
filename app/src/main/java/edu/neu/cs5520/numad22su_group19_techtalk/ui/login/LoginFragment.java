package edu.neu.cs5520.numad22su_group19_techtalk.ui.login;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.Constants.APP_NAME;

import android.graphics.Typeface;
import android.os.Bundle;

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
import android.view.WindowManager;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import edu.neu.cs5520.numad22su_group19_techtalk.MainActivity;
import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentLearningPathsDetailBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.databinding.FragmentLoginBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;
import edu.neu.cs5520.numad22su_group19_techtalk.ui.learningpaths.LearningPathsViewModel;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(TAG, "onCreateView");

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = FragmentLoginBinding.inflate(inflater, container, false);


        // Style app name
        SpannableString spannableString = new SpannableString(APP_NAME);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        binding.txtAppName.setText(spannableString);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.getUserByUsername(binding.txtUsername.getText().toString()).observe(getViewLifecycleOwner(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        String usernameInput = binding.txtUsername.getText().toString();

                        if (user.username != null && user.username.equals(usernameInput)) {
                            Log.v(TAG, "Existing user " + user.username);
                            ((MainActivity)getActivity()).setUser(user);
                            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                            NavController navController = navHostFragment.getNavController();
                            navController.navigate(R.id.navigation_user_profile);

                        } else {
                            // create  new user
                            // Create new user triggers observe update
                            Log.v(TAG, "Create new user " + usernameInput );
                            User newUser = new User(usernameInput);
                            loginViewModel.addUser(newUser);
                        }
                    }
                });
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