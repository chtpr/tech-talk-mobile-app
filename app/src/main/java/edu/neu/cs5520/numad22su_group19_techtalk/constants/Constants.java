package edu.neu.cs5520.numad22su_group19_techtalk.constants;

public class Constants {
    public Constants() {}

    public static final String APP_VERSION = "v1.0.0";
    public static final String APP_AUTHOR = "NUMAD22Su Group 19";
    public static final String APP_SETTINGS = "app_settings";
    public static final String APP_NAME = "TechTalk";


    public static final String LEARNING_PATH_TITLE = "Learning Path Title";
    public static final String LEARNING_PATH_DESCRIPTION = "Learning Path Description";
    public static final String LEARNING_PATH_TOPIC = "Learning Path Topic";
    public static final String LEARNING_PATH_PRICE = "Learning Path Price";
    public static final String LEARNING_PATH_ADD_RESOURCE = "Add Resource";
    public static final String LEARNING_PATH_ADD_ANOTHER_RESOURCE = "Add Another Resource";
    public static final String LEARNING_PATH_LIST_UPDATED = "Learning Path List Updated!";
    public static final String LEARNING_PATH_LIST_UPDATE_ERROR = "Empty or unable to update list!";


    public static final String APP_PREF_THEME = "sw_theme_settings";
    public static final String APP_PREF_NOTIFICATION = "sw_notification_settings";
    public static final String APP_PREF_AUTHOR = "author";
    public static final String APP_PREF_VERSION = "version";
    public static final String[] APP_PREFERENCES_KEYS = {
            APP_PREF_THEME,
            APP_PREF_NOTIFICATION
    };


    public static final int LEARNER_POINTS_MIN = 0;
    public static final int PROFICIENT_POINTS_MIN = 25;
    public static final int PROFESSIONAL_POINTS_MIN = 50;
    public static final int EXPERT_POINTS_MIN = 100;
    public static final String LEARNER = "Learner";
    public static final String PROFICIENT = "Proficient";
    public static final String PROFESSIONAL = "Professional";
    public static final String EXPERT = "Expert";
    public static final int RESOURCE_ADD_POINTS = 50;

    public static final int[] USER_LEVELS = {
            LEARNER_POINTS_MIN,
            PROFICIENT_POINTS_MIN,
            PROFESSIONAL_POINTS_MIN,
            EXPERT_POINTS_MIN
    };

    public static final String GET_USER_LEVEL(int points) {
        if (points >= LEARNER_POINTS_MIN && points < PROFICIENT_POINTS_MIN) {
            return  LEARNER;
        } else if (points >= PROFICIENT_POINTS_MIN && points < PROFESSIONAL_POINTS_MIN) {
            return PROFICIENT;
        } else if (points >= PROFESSIONAL_POINTS_MIN  && points < EXPERT_POINTS_MIN) {
            return PROFESSIONAL;
        } else {
            return EXPERT;
        }
    }

    public static final int RESOURCE_TITLE_MAX_LENGTH = 34;
    public static final int COLLECTION_TITLE_MAX_LENGTH = 12;
    public static final int RESOURCE_DESCRIPTION_MAX_LENGTH = 50;

    public final static String formatResourceTitle(String title) {
        if (title.length() > RESOURCE_TITLE_MAX_LENGTH) {
            return title.substring(0, RESOURCE_TITLE_MAX_LENGTH) + "...";
        }
        return title;
    }

    public final static String formatCollectionTitle(String title) {
        if (title.length() > COLLECTION_TITLE_MAX_LENGTH) {
            return title.substring(0, COLLECTION_TITLE_MAX_LENGTH) + "...";
        }
        return title;
    }

    public final static String formatResourceDescription(String description) {
        if (description.length() > RESOURCE_DESCRIPTION_MAX_LENGTH) {
            return description.substring(0, RESOURCE_DESCRIPTION_MAX_LENGTH) + "...";
        }
        return description;
    }
}
