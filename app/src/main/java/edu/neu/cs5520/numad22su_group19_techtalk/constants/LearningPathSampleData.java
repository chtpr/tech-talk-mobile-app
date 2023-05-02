package edu.neu.cs5520.numad22su_group19_techtalk.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPath;
import edu.neu.cs5520.numad22su_group19_techtalk.models.LearningPathResource;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class LearningPathSampleData {

    private LearningPathSampleData() {

    }

    public final static List<LearningPath> SEED_LEARNING_PATHS() {
        /* Learning Path 1 */
        LearningPathResource lp01r01 = new LearningPathResource("1",  "Java Tutorial for Beginners", "Mosh", "https://www.youtube.com/watch?v=eIrMbAQSU34", "Java", "YouTube", "Video", true, false);
        LearningPathResource lp01r02 = new LearningPathResource("2",  "Java Cookbook", "Ian F. Darwin", "https://www.amazon.com/Java-Cookbook-Problems-Solutions-Developers/dp/1492072583/", "Java", "Amazon", "Book", true, true);
        LearningPathResource lp01r03 = new LearningPathResource("3",  "Java Programming Master Class", "Tim Buchalka", "https://www.udemy.com/course/java-the-complete-java-developer-course/", "Java", "Udemy", "Video", true, true);
        LearningPathResource lp01r04 = new LearningPathResource("4",  "Java Object Oriented Programming", "Tom Briggs", "https://www.udemy.com/course/java-the-complete-java-developer-course/", "Java", "Udemy", "Video", true, true);
        LearningPathResource lp01r05 = new LearningPathResource("5",  "Advanced Java Programming", "", "https://www.udemy.com/course/java-the-complete-java-developer-course/", "Python", "Udemy", "Video", true, true);
        User lp01Author = new User("johndoe", 100, false);
        List<LearningPathResource> lp01ResourceList = new ArrayList<LearningPathResource>(
                Arrays.asList( new LearningPathResource[]{lp01r01, lp01r02, lp01r03, lp01r04, lp01r05}));
        LearningPath lp01 = new LearningPath("1", "Mastering Java", lp01Author, "For one, Java is arguably the most acclaimed skill and is in demand nearly everywhere.", "Java" );
        lp01.learningPathResources = lp01ResourceList;

        /*Learning Path 2 */
        LearningPathResource lp02r01 = new LearningPathResource("4",  "Python Tutorial for Beginners", "Mosh", "https://www.youtube.com/watch?v=eIrMbAQSU34", "Java", "YouTube", "Video", true, false);
        LearningPathResource lp02r02 = new LearningPathResource("5",  "Python Cookbook", "Ian F. Darwin", "https://www.amazon.com/Java-Cookbook-Problems-Solutions-Developers/dp/1492072583/", "Python", "Amazon", "Book", true, true);
        User lp02Author = new User("jbriggs", 100, false);
        List<LearningPathResource> lp02ResourceList = new ArrayList<LearningPathResource>(
                Arrays.asList( new LearningPathResource[]{lp02r01, lp02r02}));
        LearningPath lp02 = new LearningPath("2", "Learn Python the Hard way", lp01Author, "I think that the best way to learn Python (or any other skills) is by doing it. This book includes visual charts", "Python" );
        lp02.learningPathResources = lp02ResourceList;

        /*Learning Path 3 */
        LearningPathResource lp03r01 = new LearningPathResource("7",  "Android Tutorial for Beginners", "Mosh", "https://www.youtube.com/watch?v=eIrMbAQSU34", "Java", "YouTube", "Video", true, false);
        LearningPathResource lp03r02 = new LearningPathResource("8",  "Android Cookbook", "Ian F. Darwin", "https://www.amazon.com/Java-Cookbook-Problems-Solutions-Developers/dp/1492072583/", "Android", "Amazon", "Book", true, true);
        LearningPathResource lp03r03 = new LearningPathResource("9",  "Android Programming Master Class", "Tim Buchalka", "https://www.udemy.com/course/java-the-complete-java-developer-course/", "Android", "Udemy", "Video", true, true);
        User lp03Author = new User("jsmith", 100, false);
        List<LearningPathResource> lp03ResourceList = new ArrayList<LearningPathResource>(
                Arrays.asList( new LearningPathResource[]{lp03r01, lp03r02, lp03r03}));
        LearningPath lp03 = new LearningPath("3", "Get started with Android", lp01Author, "This is yet another android resource. Lorem ipsum dolor sit amet, consectetur adipiscing elit,", "Android" );
        lp03.learningPathResources = lp03ResourceList;

        return new ArrayList<>(
                Arrays.asList(new LearningPath[] { lp01, lp02, lp03})
        );
    }
}
