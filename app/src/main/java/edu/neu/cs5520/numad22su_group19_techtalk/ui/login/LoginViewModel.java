package edu.neu.cs5520.numad22su_group19_techtalk.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.User;
import edu.neu.cs5520.numad22su_group19_techtalk.repositories.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    public static final String TAG = "LoginViewModel";
    UserRepository userRepository;

    public LoginViewModel(Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
    }

    public Boolean isExistingUsername(String username) {

        List<User> userList = userRepository.getAllUserList();
        for (User usr: userList ) {
            if (usr.username.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String username) {
        return null;
    }

    public void addUser(User user) {
         userRepository.addUser(user);
    }

    @NonNull
    public LiveData<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
