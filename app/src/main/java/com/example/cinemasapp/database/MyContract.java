package com.example.cinemasapp.database;

public final class MyContract {

    public static class Movie {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PICTURE = "picture";
        public static final String COLUMN_DIRECTOR = "director";
        public static final String COLUMN_RELEASE_YEAR = "release_year";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_GENRE = "genre";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_LENGTH = "length";
        public static final String COLUMN_MIN_AGE = "min_age";
    }

    public static class Room {
        public static final String TABLE_NAME = "rooms";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_ROOM_NUMBER = "room_number";
        public static final String COLUMN_CAPACITY = "capacity";
    }

    public static class Schedule {
        public static final String TABLE_NAME = "schedules";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_ROOM_ID = "room_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_FORMAT = "format";
    }

}
