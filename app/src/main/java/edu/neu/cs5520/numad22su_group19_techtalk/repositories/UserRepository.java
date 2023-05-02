package edu.neu.cs5520.numad22su_group19_techtalk.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.firbasedao.Listeners.RetrievalEventListener;
import com.example.firbasedao.Listeners.TaskListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.neu.cs5520.numad22su_group19_techtalk.dao.UserDao;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class UserRepository {

    private static final String TAG = "UserRepository";
    private final UserDao userDao;
    private static UserRepository userRepository;
    private MutableLiveData<List<User>> userListLiveData;
    private MutableLiveData<User> userIdLiveData;
    private MutableLiveData<User> usernameLiveData;
    private MutableLiveData<User> userLiveData;
    private List<User> userList;

    private UserRepository() {
        userDao = UserDao.getInstance();
        userListLiveData = new MutableLiveData<>();
        userIdLiveData = new MutableLiveData<>();
        usernameLiveData = new MutableLiveData<>();
        userList = new ArrayList<>();
        userLiveData = new MutableLiveData<>();
    }

    public synchronized static UserRepository getInstance() {
        if (userRepository == null) userRepository = new UserRepository();
        return userRepository;
    }

    public LiveData<List<User>> getAllUsers() {
        retrieveAllUsers();
        return userListLiveData;
    }

    public List<User> getAllUserList() {
        retrieveAllUsers();
        return userList;
    }

    public LiveData<User> getUserById(String userId) {
        retrieveUserById(userId);
        return userIdLiveData;
    }

    public LiveData<User> getUserByUsername(String username) {
        retrieveUserByUsername(username);
        return usernameLiveData;
    }

    private void retrieveUserByUsername(String username) {
        ExecutorService userService = Executors.newSingleThreadExecutor();
        userService.submit(new Runnable() {
            @Override
            public void run() {
                userDao.getUserByUsername(username, new RetrievalEventListener<User>() {
                    @Override
                    public void OnDataRetrieved(User user) {
                        usernameLiveData.postValue(user);
                    }
                });
            }
        });
    }

    private void retrieveUserById(String userId) {
        ExecutorService userService = Executors.newSingleThreadExecutor();
        userService.submit(new Runnable() {
            @Override
            public void run() {
                userDao.get(userId, new RetrievalEventListener<User>() {
                    @Override
                    public void OnDataRetrieved(User user) {
                        userIdLiveData.postValue(user);
                    }
                });
            }
        });
    }

    private void retrieveAllUsers() {
        ExecutorService userService = Executors.newSingleThreadExecutor();
        userService.submit(new Runnable() {
            @Override
            public void run() {
                userDao.getAll(new RetrievalEventListener<List<User>>() {
                    @Override
                    public void OnDataRetrieved(List<User> currentList) {
                        userListLiveData.postValue(currentList);
                        userList.clear();
                        userList.addAll(currentList);
                        Log.v(TAG, "retrieveAllUsers()");
                    }
                });
            }
        });

    }

    public void addUser(User user) {

        String newKey = userDao.GetNewKey();

        userDao.save(user, newKey, new TaskListener() {
            @Override
            public void OnSuccess() {
                Log.v(TAG, "Collection Object Saved!");
                retrieveAllUsers();
                retrieveUserByUsername(user.username);
            }

            @Override
            public void OnFail() {
                Log.e(TAG, "Unable to Save Collection Object!");
                userListLiveData.postValue(null);
            }
        });
    }

    public void seedDatabase(List<User> userList) {
        for (User ls : userList) {
            userDao.save(ls, userDao.GetNewKey(), new TaskListener() {
                @Override
                public void OnSuccess() {
                    Log.v(TAG, "Seed database completed!");
                }
                @Override
                public void OnFail() {
                    Log.v(TAG, "Seed database failed!");
                }
            });
        }
    }

    public void updateUser(User user) {
        userDao.save(user, user.userID, new TaskListener() {
            @Override
            public void OnSuccess() {
                Log.v(TAG, "Resource Object Updated!");
                retrieveAllUsers();
            }

            @Override
            public void OnFail() {
                Log.e(TAG, "Unable to update Resource Object!");
                userLiveData.postValue(null);
            }
        });
    }
}
