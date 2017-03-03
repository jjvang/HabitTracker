package com.example.ojboba.habittracker.Data;

import android.provider.BaseColumns;

/**
 * Created by OjBoba on 2/6/2017.
 */

// Since this class will never be extended, could make class final
public final class HabitContract {

    private HabitContract() {
        throw new AssertionError("No HabitContract instances for you!");
    }

    public static final class HabitEntry implements BaseColumns {

        /**
         * Name of database table for habits
         */
        public final static String TABLE_NAME = "habits";

        /**
         * Unique ID number for the habit (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_HABIT_NAME = "habit";

        public final static String COLUMN_HABIT_TIME = "time";

        public final static String COLUMN_HABIT_DIFFICULTY = "difficulty";

        /**
         * public final static String COLUMN_HABIT_DIFFICULTY = "difficulty";
         * /**
         * Possible values for the difficulty of the Habit.
         */
        public static final int DIFFICULTY_EASY = 0;
        public static final int DIFFICULTY_MEDIUM = 1;
        public static final int DIFFICULTY_HARD = 2;
    }
}

