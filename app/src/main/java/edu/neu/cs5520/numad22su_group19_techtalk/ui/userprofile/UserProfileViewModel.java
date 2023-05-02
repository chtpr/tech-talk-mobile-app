package edu.neu.cs5520.numad22su_group19_techtalk.ui.userprofile;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.UserSampleData.USER_SAMPLE_DATA;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.User;
import edu.neu.cs5520.numad22su_group19_techtalk.repositories.UserRepository;

public class UserProfileViewModel extends ViewModel {

    public static final String TAG = "UserProfileViewModel";
    UserRepository userRepository;

    public UserProfileViewModel() {
        userRepository = UserRepository.getInstance();
        //seedUsers(USER_SAMPLE_DATA());
    }

    @NonNull
    public LiveData<List<User>> getUsers() {
        return userRepository.getAllUsers();
    }

    @NonNull
    public LiveData<User> getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    public String getUserByUsername(String username) {
        List<User> userList = userRepository.getAllUserList();
        for (User usr: userList ) {
            if (usr.username.equals(username)) {
                return usr.userID;
            }
        }
        return "";
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    private void seedUsers(List<User> userList) {
        userRepository.seedDatabase(userList);
    }
}