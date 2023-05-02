package edu.neu.cs5520.numad22su_group19_techtalk.dao;

import androidx.annotation.NonNull;

import com.example.firbasedao.FirebaseDao;
import com.example.firbasedao.Listeners.RetrievalEventListener;
import com.example.firbasedao.Listeners.TaskListener;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class UserDao extends FirebaseDao<User> {

    private static final String TAG = "UserDao";
    private static UserDao singletonUserDao;
    private UserDao() {
        super("Users");
    }

    public static UserDao getInstance() {
        if (singletonUserDao == null)
            singletonUserDao = new UserDao();
        return singletonUserDao;
    }
    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<User> retrievalEventListener) {


        final User user  = new User();

        if (dataSnapshot.exists()) {
            user.userID = dataSnapshot.getKey();
            user.username = dataSnapshot.child("username").getValue().toString();
            user.password = dataSnapshot.child("password").getValue().toString();
            user.profileImageURL = dataSnapshot.child("profileImageURL").getValue().toString();
            user.creationDate = dataSnapshot.child("creationDate").getValue().toString();
            user.points = Integer.parseInt(dataSnapshot.child("points").getValue().toString());
            user.isAdmin = Boolean.parseBoolean(dataSnapshot.child("isAdmin").getValue().toString());
        }

        retrievalEventListener.OnDataRetrieved(user);
    }

    @Override
    public void getAll(RetrievalEventListener<List<User>> retrievalEventListener) {
        super.getAll(retrievalEventListener);

    }

    public void getUserByUsername(@NonNull String username, final RetrievalEventListener<User>  retrievalEventListener) {

        final User[] currUser = {new User()};

        getAll(new RetrievalEventListener<List<User>>() {
          @Override
          public void OnDataRetrieved(List<User> users) {
              for (User user : users) {
                  if (user.username.equals(username)) {
                      currUser[0] = user;
                  }
              }
              retrievalEventListener.OnDataRetrieved(currUser[0]);
          }
      });
    }

    public void delete(String id, TaskListener taskListener) {
        save(null, id, taskListener);}
}
