package edu.neu.cs5520.numad22su_group19_techtalk.constants;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.R;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;


public class ResourceSampleData {
    private ResourceSampleData() {

    }

    public final static List<Resource> RESOURCE_SAMPLE_DATA() {
        //int resourceID, String title, User user, String imageURL, String webURL, int upvotes, int downvotes, List<String> topics, List<String> comments, boolean isLearningPath
        //String username, int points, boolean isAdmin
        User u1 = new User("julie", 1, false);
        User u2 = new User("ruben", 2, true);
        User u3 = new User("chris", 3, true);
        User u4 = new User("rando", 4, false);
        List<String> topics1 = Arrays.asList("Python", "Pandas");
        List<String> topics2 = Arrays.asList("Javascript", "Node");
        List<String> topics3 = Arrays.asList("Google", "AI");
        List<String> topics4 = Arrays.asList("Amazon", "AWS");
        List<String> comments1 = Arrays.asList("Java is better", "Pandas rocks!");
        List<String> comments2 = Arrays.asList("random comment for webdev", "random comment 2 for webdev");
        List<String> comments3 = Arrays.asList("Impossible", "Ai is taking over the world");
        List<String> comments4 = Arrays.asList("Lambda is the best code host.", "AWS is too expensive.");
        Resource r1 = new Resource("1", "Top 10 Python Packages", "Everyone knows that Python has an abundance of useful packages.", u1, "https://static01.nyt.com/images/2016/12/18/magazine/18ai-cover2/18ai-cover2-superJumbo-v4.jpg?quality=75&auto=webp", "geeksforgeeks.com", 10, 5, topics1, comments1, true);
        Resource r2 = new Resource("2", "What you need to know about webdev", "Do you have what it takes to be a web developer?",u2, "https://static01.nyt.com/images/2016/12/18/magazine/18ai-cover2/18ai-cover2-superJumbo-v4.jpg?quality=75&auto=webp", "javascript.com", 20, 5, topics2, comments2, false);
        Resource r3 = new Resource("3", "Is Google's AI sentient?", "Was society right about robots taking over the world?",u3, "https://static01.nyt.com/images/2016/12/18/magazine/18ai-cover2/18ai-cover2-superJumbo-v4.jpg?quality=75&auto=webp", "google.com", 30, 5, topics3, comments3, false);
        Resource r4 = new Resource("4", "Amazon vs Microsoft", "Is one place better than the other? Keep reading to find out!",u3, "https://static01.nyt.com/images/2016/12/18/magazine/18ai-cover2/18ai-cover2-superJumbo-v4.jpg?quality=75&auto=webp", "google.com", 10, 15, topics4, comments4, false);

        List<Resource> resourceList = Arrays.asList(r1, r2, r3, r4);
        return resourceList;
    }
}
