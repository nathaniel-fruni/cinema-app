package com.example.cinemasapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.cinemasapp.R;
import com.example.cinemasapp.activities.movies_activities.MoviesActivity;
import com.example.cinemasapp.activities.rooms_activities.RoomsActivity;
import com.example.cinemasapp.activities.schedules_activities.SchedulesActivity;
import com.example.cinemasapp.database.DBHelper;
import com.example.cinemasapp.models.Movie;
import com.example.cinemasapp.models.Room;
import com.example.cinemasapp.models.Schedule;

public class MainActivity extends AppCompatActivity {
    DBHelper dbh = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtons();

        // vložiť údaje iba ak je databáza prázdna
        if (dbh.getMoviesCount() == 0) {
            InsertData();
        }
    }

    private void setButtons() {
        Button btn1 = findViewById(R.id.enter_movies);
        btn1.setOnClickListener(evt->{
            Intent intent = new Intent(MainActivity.this, MoviesActivity.class);
            startActivity(intent);
        });

        Button btn2 = findViewById(R.id.enter_rooms);
        btn2.setOnClickListener(evt->{
            Intent intent = new Intent(MainActivity.this, RoomsActivity.class);
            startActivity(intent);
        });

        Button btn3 = findViewById(R.id.enter_schedules);
        btn3.setOnClickListener(evt->{
            Intent intent = new Intent(MainActivity.this, SchedulesActivity.class);
            startActivity(intent);
        });
    }

    private void InsertData() {
        dbh.addMovie(new Movie(1, "Civil War", "movie_civil_war", "Alex Garland", 2024, "A journey across a dystopian future America, following a team of military-embedded journalists as they race against time to reach DC before rebel factions descend upon the White House.", "Action", "English", 109, 15));
        dbh.addMovie(new Movie(2, "Immaculate", "movie_immaculate", "Michael Mohan", 2024, "Cecilia, a woman of devout faith, is warmly welcomed to the picture-perfect Italian countryside where she is offered a new role at an illustrious convent.", "Horror", "English", 89, 18));
        dbh.addMovie(new Movie(3, "Arthur The King", "movie_arthur_the_king", "Simon Cellan Jones", 2024, "An adventure racer adopts a stray dog named Arthur to join him in an epic endurance race.", "Adventure", "English", 107, 12));
        dbh.addMovie(new Movie(4, "Baghead", "movie_baghead", "Alberto Corredor", 2023, "A young woman inherits a run-down pub and discovers a dark secret within its basement - Baghead - a shape-shifting creature that will let you speak to lost loved ones, but not without consequence.", "Horror", "English", 94, 15));
        dbh.addMovie(new Movie(5, "Dune: Part Two", "movie_dune_part_two", "Denis Villeneuve", 2024, "Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators who destroyed his family.", "Adventure", "English", 166, 12));
        dbh.addMovie(new Movie(6, "Immaculate", "movie_immaculate", "Michael Mohan", 2024, "Cecilia, a woman of devout faith, is warmly welcomed to the picture-perfect Italian countryside where she is offered a new role at an illustrious convent.", "Horror", "English", 89, 18));

        dbh.addRoom(new Room(1, 1, 50));
        dbh.addRoom(new Room(2, 2, 75));
        dbh.addRoom(new Room(3, 3, 100));
        dbh.addRoom(new Room(4, 4, 50));
        dbh.addRoom(new Room(5, 5, 100));

        dbh.addSchedule(new Schedule(1, 1,1,"20.04.2024","20:30", "2D"));
        dbh.addSchedule(new Schedule(2, 2, 2, "20.04.2024", "18:00", "2D"));
        dbh.addSchedule(new Schedule(3, 1, 3, "20.04.2024", "22:00", "3D"));
        dbh.addSchedule(new Schedule(4, 3, 4, "21.04.2024", "20:00", "2D"));
        dbh.addSchedule(new Schedule(5, 2, 5, "21.04.2024", "17:30", "2D"));
        dbh.addSchedule(new Schedule(6, 5, 1, "22.04.2024", "19:45", "2D"));
        dbh.addSchedule(new Schedule(7, 3, 2, "22.04.2024", "21:15", "2D"));
        dbh.addSchedule(new Schedule(8, 4, 3, "23.04.2024", "20:30", "3D"));
        dbh.addSchedule(new Schedule(9, 6, 4, "23.04.2024", "19:00", "2D"));
        dbh.addSchedule(new Schedule(10, 4, 5, "24.04.2024", "18:45", "2D"));
    }
}