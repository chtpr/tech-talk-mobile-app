package edu.neu.cs5520.numad22su_group19_techtalk.constants;

import java.util.Arrays;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class UserSampleData {

    private UserSampleData() {

    }

    public final static List<User> USER_SAMPLE_DATA() {
        User u1 = new User("1", "julie", "pw", "www.imagejh.com", "2022-08-12", 0, false);
        User u2 = new User("1", "ruben", "pw", "www.imagerr.com", "2022-08-11", 0, false);
        User u3 = new User("1", "chris", "pw", "www.imagecl.com", "2022-08-10", 0, false);

        List<User> userList = Arrays.asList(u1, u2, u3);
        return userList;
    }
}
