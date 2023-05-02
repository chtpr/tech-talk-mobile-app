package edu.neu.cs5520.numad22su_group19_techtalk.constants;

import static edu.neu.cs5520.numad22su_group19_techtalk.constants.ResourceSampleData.RESOURCE_SAMPLE_DATA;

import java.util.Arrays;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.Collection;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class CollectionDummyData {

    private CollectionDummyData() {}

    public final static List<Collection> COLLECTION_DUMMY_DATA() {
        // title, user, resources, isPrivate
        User u1 = new User("clee", 1, false);
        User u2 = new User("ruben", 2, false);
        User u3 = new User("julie", 3, false);
        Collection c1 = new Collection("Java", u1, RESOURCE_SAMPLE_DATA(), false);
        Collection c2 = new Collection("Python", u2, RESOURCE_SAMPLE_DATA(), false);
        Collection c3 = new Collection("MySQL", u3, RESOURCE_SAMPLE_DATA(), false);
        List<Collection> collectionList = Arrays.asList(c1, c2, c3);
        return collectionList;
    }
}
