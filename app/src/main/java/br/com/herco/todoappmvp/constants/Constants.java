package br.com.herco.todoappmvp.constants;

public class Constants {
    public static class Keys {
        public static final int ACTIVITY_FROM_RESULT_CODE_TASK = 1000;
        public static final String TASK_DTO = "TASK_DTO";
    }

    public static class Database {
        public static final String DATABASE_PREFERENCES = "database_task";
        public static final String TASK = "task";
        public static final String ALL_TASKS = "tasks";
        public static final int MAX_ID = 10;

        public static final String GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        // public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    }

    public static class Animations {
        public static long FIRST_ANIMATION_DURATION = 1500;
        public static int DELAY_TO_ANIMATE_CIRCLE_AROUND_PROFILE = 200;
    }

    public static class TAGS {
        public static final String TASK = "TASK";

    }

    public static class TIME {
        public static final int ANIMATION_FOR_DELETE = 1000;

    }

    public static class Events {
        public static final String CREATED_TASK_ACTION = "CREATED A NEW TASK";
    }
}
