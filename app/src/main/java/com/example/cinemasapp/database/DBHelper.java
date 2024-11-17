package com.example.cinemasapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.cinemasapp.models.Movie;
import com.example.cinemasapp.models.Room;
import com.example.cinemasapp.models.Schedule;
import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NAME = "cinemasApp";
    SQLiteDatabase db;

    public DBHelper(Context context) { // konštruktor databázy
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE " + MyContract.Movie.TABLE_NAME  + "("
                + MyContract.Movie.COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MyContract.Movie.COLUMN_TITLE + " TEXT, "
                + MyContract.Movie.COLUMN_PICTURE + " TEXT, "
                + MyContract.Movie.COLUMN_DIRECTOR + " TEXT, "
                + MyContract.Movie.COLUMN_RELEASE_YEAR + " INTEGER, "
                + MyContract.Movie.COLUMN_DESCRIPTION + " TEXT, "
                + MyContract.Movie.COLUMN_GENRE + " TEXT, "
                + MyContract.Movie.COLUMN_LANGUAGE + " TEXT, "
                + MyContract.Movie.COLUMN_LENGTH + " INTEGER, "
                + MyContract.Movie.COLUMN_MIN_AGE + " INTEGER)";

        String SQL_CREATE_TABLE_ROOM = "CREATE TABLE " + MyContract.Room.TABLE_NAME  + "("
                + MyContract.Room.COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MyContract.Room.COLUMN_ROOM_NUMBER + " INTEGER, "
                + MyContract.Room.COLUMN_CAPACITY + " INTEGER)";

        String SQL_CREATE_TABLE_SCHEDULE = "CREATE TABLE " + MyContract.Schedule.TABLE_NAME  + "("
                + MyContract.Schedule.COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MyContract.Schedule.COLUMN_MOVIE_ID + " INTEGER, "
                + MyContract.Schedule.COLUMN_ROOM_ID + " INTEGER, "
                + MyContract.Schedule.COLUMN_DATE + " TEXT, "
                + MyContract.Schedule.COLUMN_TIME + " TEXT, "
                + MyContract.Schedule.COLUMN_FORMAT + " TEXT, "
                + "FOREIGN KEY(" + MyContract.Schedule.COLUMN_ROOM_ID + ") REFERENCES " + MyContract.Room.TABLE_NAME + "(" + MyContract.Room.COLUMN_ID + "), "
                + "FOREIGN KEY(" + MyContract.Schedule.COLUMN_MOVIE_ID + ") REFERENCES " + MyContract.Movie.TABLE_NAME + "(" + MyContract.Movie.COLUMN_ID + "))";

        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_ROOM);
        db.execSQL(SQL_CREATE_TABLE_SCHEDULE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVers, int newVers){
        db.execSQL("DROP TABLE IF EXISTS " + MyContract.Movie.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MyContract.Room.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MyContract.Schedule.TABLE_NAME);
        onCreate(db);
    }

    /* MOVIES TABLE */
    public int getMoviesCount() {
        db = getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, MyContract.Movie.TABLE_NAME);
        db.close();
        return (int) count;
    }

    public Cursor moviesCursor() {
        db = getWritableDatabase();

        String[] projection = {
                MyContract.Movie.COLUMN_ID,
                MyContract.Movie.COLUMN_TITLE,
                MyContract.Movie.COLUMN_PICTURE,
                MyContract.Movie.COLUMN_DIRECTOR,
                MyContract.Movie.COLUMN_RELEASE_YEAR,
                MyContract.Movie.COLUMN_DESCRIPTION,
                MyContract.Movie.COLUMN_GENRE,
                MyContract.Movie.COLUMN_LANGUAGE,
                MyContract.Movie.COLUMN_LENGTH,
                MyContract.Movie.COLUMN_MIN_AGE
        };

        Cursor cursor = db.query(
                MyContract.Movie.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        db.close();
        return cursor;
    }

    public Movie getMovie(long ID) {
        db = getWritableDatabase();

        String[] projection = {
                MyContract.Movie.COLUMN_TITLE,
                MyContract.Movie.COLUMN_PICTURE,
                MyContract.Movie.COLUMN_DIRECTOR,
                MyContract.Movie.COLUMN_RELEASE_YEAR,
                MyContract.Movie.COLUMN_DESCRIPTION,
                MyContract.Movie.COLUMN_GENRE,
                MyContract.Movie.COLUMN_LANGUAGE,
                MyContract.Movie.COLUMN_LENGTH,
                MyContract.Movie.COLUMN_MIN_AGE
        };
        String selection = MyContract.Movie.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(ID) };

        Cursor c = db.query(
                MyContract.Movie.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Movie movie = null;
        if (c != null && c.moveToFirst()) {
            movie = new Movie(ID,
                    c.getString(c.getColumnIndexOrThrow(MyContract.Movie.COLUMN_TITLE)),
                    c.getString(c.getColumnIndexOrThrow(MyContract.Movie.COLUMN_PICTURE)),
                    c.getString(c.getColumnIndexOrThrow(MyContract.Movie.COLUMN_DIRECTOR)),
                    c.getLong(c.getColumnIndexOrThrow(MyContract.Movie.COLUMN_RELEASE_YEAR)),
                    c.getString(c.getColumnIndexOrThrow(MyContract.Movie.COLUMN_DESCRIPTION)),
                    c.getString(c.getColumnIndexOrThrow(MyContract.Movie.COLUMN_GENRE)),
                    c.getString(c.getColumnIndexOrThrow(MyContract.Movie.COLUMN_LANGUAGE)),
                    c.getLong(c.getColumnIndexOrThrow(MyContract.Movie.COLUMN_LENGTH)),
                    c.getLong(c.getColumnIndexOrThrow(MyContract.Movie.COLUMN_MIN_AGE))
                    );
        }

        if (c != null) {
            c.close();
        }
        db.close();
        return movie;
    }

    public Map<String, String> getMovieTitlesAndIds() {
        db = this.getReadableDatabase();
        Map<String, String> movieMap = new HashMap<>();

        String[] projection = {
                MyContract.Movie.COLUMN_ID,
                MyContract.Movie.COLUMN_TITLE
        };

        Cursor cursor = db.query(
                MyContract.Movie.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String id = String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(MyContract.Movie.COLUMN_ID)));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MyContract.Movie.COLUMN_TITLE));
            movieMap.put(title, id);
        }

        cursor.close();
        db.close();

        return movieMap;
    }


    public boolean titleExists(String title) {
        db = this.getReadableDatabase();

        String[] projection = { MyContract.Movie.COLUMN_TITLE };
        String selection = MyContract.Movie.COLUMN_TITLE + " = ?";
        String[] selectionArgs = { title };

        Cursor cursor = db.query(
                MyContract.Movie.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void addMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Movie.COLUMN_TITLE, movie.getTitle());
        values.put(MyContract.Movie.COLUMN_PICTURE, movie.getPicture());
        values.put(MyContract.Movie.COLUMN_DIRECTOR, movie.getDirector());
        values.put(MyContract.Movie.COLUMN_RELEASE_YEAR, movie.getReleaseYear());
        values.put(MyContract.Movie.COLUMN_DESCRIPTION, movie.getDescription());
        values.put(MyContract.Movie.COLUMN_GENRE, movie.getGenre());
        values.put(MyContract.Movie.COLUMN_LANGUAGE, movie.getLanguage());
        values.put(MyContract.Movie.COLUMN_LENGTH, movie.getLength());
        values.put(MyContract.Movie.COLUMN_MIN_AGE, movie.getMinAge());

        db = getWritableDatabase();
        long newRowId = db.insert(
                MyContract.Movie.TABLE_NAME, null, values);
        db.close();
    }

    public void updateMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Movie.COLUMN_TITLE, movie.getTitle());
        values.put(MyContract.Movie.COLUMN_PICTURE, movie.getPicture());
        values.put(MyContract.Movie.COLUMN_DIRECTOR, movie.getDirector());
        values.put(MyContract.Movie.COLUMN_RELEASE_YEAR, movie.getReleaseYear());
        values.put(MyContract.Movie.COLUMN_DESCRIPTION, movie.getDescription());
        values.put(MyContract.Movie.COLUMN_GENRE, movie.getGenre());
        values.put(MyContract.Movie.COLUMN_LANGUAGE, movie.getLanguage());
        values.put(MyContract.Movie.COLUMN_LENGTH, movie.getLength());
        values.put(MyContract.Movie.COLUMN_MIN_AGE, movie.getMinAge());

        db = getWritableDatabase();
        db.update(
                MyContract.Movie.TABLE_NAME,
                values,
                MyContract.Movie.COLUMN_ID + "= ?",
                new String[] { ""+movie.getID() });
        db.close();
    }

    public void deleteMovie(long ID) {
        db = getWritableDatabase();

        db.delete(
                MyContract.Movie.TABLE_NAME,
                MyContract.Movie.COLUMN_ID + "= ?",
                new String[] { ""+ID });

        db.delete(
                MyContract.Schedule.TABLE_NAME,
                MyContract.Schedule.COLUMN_MOVIE_ID + "= ?",
                new String[] { ""+ID });
    }

    /* ROOMS TABLE */
    public Cursor roomsCursor() {
        db = getWritableDatabase();

        String[] projection = {
                MyContract.Room.COLUMN_ID,
                MyContract.Room.COLUMN_ROOM_NUMBER,
                MyContract.Room.COLUMN_CAPACITY
        };

        Cursor cursor = db.query(
                MyContract.Room.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        db.close();
        return cursor;
    }

    public Room getRoom(long ID) {
        db = getWritableDatabase();

        String[] projection = { MyContract.Room.COLUMN_ROOM_NUMBER,
                MyContract.Room.COLUMN_CAPACITY};
        String selection = MyContract.Room.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(ID) };

        Cursor c = db.query(
                MyContract.Room.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Room room = null;
        if (c != null && c.moveToFirst()) {
            room = new Room(ID,
                    c.getLong(c.getColumnIndexOrThrow(MyContract.Room.COLUMN_ROOM_NUMBER)),
                    c.getLong(c.getColumnIndexOrThrow(MyContract.Room.COLUMN_CAPACITY)));
        }

        if (c != null) {
            c.close();
        }
        db.close();
        return room;
    }

    public Map<String, String> getRoomNumbersAndIds() {
        db = this.getReadableDatabase();
        Map<String, String> roomMap = new HashMap<>();

        String[] projection = {
                MyContract.Room.COLUMN_ID,
                MyContract.Room.COLUMN_ROOM_NUMBER
        };

        Cursor cursor = db.query(
                MyContract.Room.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String id = String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(MyContract.Room.COLUMN_ID)));
            String room_number = cursor.getString(cursor.getColumnIndexOrThrow(MyContract.Room.COLUMN_ROOM_NUMBER));
            roomMap.put(room_number, id);
        }

        cursor.close();
        db.close();

        return roomMap;
    }

    public boolean roomNumberExists(long roomNumber) {
        db = this.getReadableDatabase();

        String[] projection = { MyContract.Room.COLUMN_ROOM_NUMBER };
        String selection = MyContract.Room.COLUMN_ROOM_NUMBER + " = ?";
        String[] selectionArgs = { String.valueOf(roomNumber) };

        Cursor cursor = db.query(
                MyContract.Room.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void addRoom(Room room) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Room.COLUMN_ROOM_NUMBER, room.getRoomNumber());
        values.put(MyContract.Room.COLUMN_CAPACITY, room.getCapacity());

        db = getWritableDatabase();
        long newRowId = db.insert(
                MyContract.Room.TABLE_NAME, null, values);
        db.close();
    }

    public void updateRoom(Room room) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Room.COLUMN_ROOM_NUMBER, room.getRoomNumber());
        values.put(MyContract.Room.COLUMN_CAPACITY, room.getCapacity());

        db = getWritableDatabase();
        db.update(
                MyContract.Room.TABLE_NAME,
                values,
                MyContract.Room.COLUMN_ID + "= ?",
                new String[] { ""+room.getID() });
        db.close();
    }

    public void deleteRoom(long ID) {
        db = getWritableDatabase();

        db.delete(
                MyContract.Room.TABLE_NAME,
                MyContract.Room.COLUMN_ID + "= ?",
                new String[] { ""+ID });

        db.delete(
                MyContract.Schedule.TABLE_NAME,
                MyContract.Schedule.COLUMN_ROOM_ID + "= ?",
                new String[] { ""+ID });
    }

    /* SCHEDULE TABLE */
    public Cursor schedulesCursor() {
        db = getWritableDatabase();

        String[] projection = {
                "s." + MyContract.Schedule.COLUMN_ID,
                "s." + MyContract.Schedule.COLUMN_MOVIE_ID,
                "s." + MyContract.Schedule.COLUMN_ROOM_ID,
                "s." + MyContract.Schedule.COLUMN_DATE,
                "s." + MyContract.Schedule.COLUMN_TIME,
                "s." + MyContract.Schedule.COLUMN_FORMAT,
                "m." + MyContract.Movie.COLUMN_TITLE,
                "m." + MyContract.Movie.COLUMN_PICTURE,
                "m." + MyContract.Movie.COLUMN_GENRE,
                "m." + MyContract.Movie.COLUMN_LENGTH,
                "m." + MyContract.Movie.COLUMN_MIN_AGE,
                "r." + MyContract.Room.COLUMN_ROOM_NUMBER
        };

        String tables = MyContract.Schedule.TABLE_NAME + " AS s"
                + " INNER JOIN " + MyContract.Movie.TABLE_NAME + " AS m ON s."
                + MyContract.Schedule.COLUMN_MOVIE_ID + " = m." + MyContract.Movie.COLUMN_ID
                + " INNER JOIN " + MyContract.Room.TABLE_NAME + " AS r ON s."
                + MyContract.Schedule.COLUMN_ROOM_ID + " = r." + MyContract.Room.COLUMN_ID;

        Cursor cursor = db.query(
                tables,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        db.close();
        return cursor;
    }

    public boolean scheduleExists(String date, String time, long roomId) {
        db = this.getReadableDatabase();

        String[] projection = {
                MyContract.Schedule.COLUMN_ID
        };

        String selection = MyContract.Schedule.COLUMN_DATE + " = ? AND " +
                MyContract.Schedule.COLUMN_TIME + " = ? AND " +
                MyContract.Schedule.COLUMN_ROOM_ID + " = ?";

        String[] selectionArgs = {date, time, String.valueOf(roomId)};

        Cursor cursor = db.query(
                MyContract.Schedule.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return exists;
    }


    public Schedule getSchedule(long ID) {
        db = getWritableDatabase();

        String[] projection = {
                MyContract.Schedule.COLUMN_MOVIE_ID,
                MyContract.Schedule.COLUMN_ROOM_ID,
                MyContract.Schedule.COLUMN_DATE,
                MyContract.Schedule.COLUMN_TIME,
                MyContract.Schedule.COLUMN_FORMAT
        };
        String selection = MyContract.Schedule.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(ID) };

        Cursor c = db.query(
                MyContract.Schedule.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Schedule schedule = null;
        if (c != null && c.moveToFirst()) {
            schedule = new Schedule(ID,
                    c.getLong(c.getColumnIndexOrThrow(MyContract.Schedule.COLUMN_MOVIE_ID)),
                    c.getLong(c.getColumnIndexOrThrow(MyContract.Schedule.COLUMN_ROOM_ID)),
                    c.getString(c.getColumnIndexOrThrow(MyContract.Schedule.COLUMN_DATE)),
                    c.getString(c.getColumnIndexOrThrow(MyContract.Schedule.COLUMN_TIME)),
                    c.getString(c.getColumnIndexOrThrow(MyContract.Schedule.COLUMN_FORMAT))
            );
        }

        if (c != null) {
            c.close();
        }
        db.close();
        return schedule;
    }
    public void addSchedule(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Schedule.COLUMN_MOVIE_ID, schedule.getMOVIE_ID());
        values.put(MyContract.Schedule.COLUMN_ROOM_ID, schedule.getROOM_ID());
        values.put(MyContract.Schedule.COLUMN_DATE, schedule.getDate());
        values.put(MyContract.Schedule.COLUMN_TIME, schedule.getTime());
        values.put(MyContract.Schedule.COLUMN_FORMAT, schedule.getFormat());

        db = getWritableDatabase();
        long newRowId = db.insert(
                MyContract.Schedule.TABLE_NAME, null, values);
        db.close();
    }

    public void updateSchedule(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put(MyContract.Schedule.COLUMN_MOVIE_ID, schedule.getMOVIE_ID());
        values.put(MyContract.Schedule.COLUMN_ROOM_ID, schedule.getROOM_ID());
        values.put(MyContract.Schedule.COLUMN_DATE, schedule.getDate());
        values.put(MyContract.Schedule.COLUMN_TIME, schedule.getTime());
        values.put(MyContract.Schedule.COLUMN_FORMAT, schedule.getFormat());

        db = getWritableDatabase();
        db.update(
                MyContract.Schedule.TABLE_NAME,
                values,
                MyContract.Schedule.COLUMN_ID + "= ?",
                new String[] { ""+schedule.getID() });
        db.close();
    }

    public void deleteSchedule(long ID) {
        db = getWritableDatabase();

        db.delete(
                MyContract.Schedule.TABLE_NAME,
                MyContract.Schedule.COLUMN_ID + "= ?",
                new String[] { ""+ID });
    }
}

