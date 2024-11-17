package com.example.cinemasapp.activities.movies_activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import com.example.cinemasapp.R;
import com.example.cinemasapp.adapters.MoviesAdapter;
import com.example.cinemasapp.database.DBHelper;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapter.MovieActionListener {
    DBHelper dbh = new DBHelper(this);
    MoviesAdapter myAdapter;
    ActivityResultLauncher<Intent> addMovieActivityResultLauncher;
    ActivityResultLauncher<Intent> editMovieActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ConnectAdapter();
        registerLaunchers();

        Button b = findViewById(R.id.button);
        b.setOnClickListener(evt -> AddMovie());
        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(evt -> finish());
    }

    private void ConnectAdapter() {
        Cursor myCursor = dbh.moviesCursor();
        myAdapter = new MoviesAdapter(this, myCursor, 0, this);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(myAdapter);
    }

    private void registerLaunchers() {
        addMovieActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            ConnectAdapter();
                        }
                    }
                });

        editMovieActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            ConnectAdapter();
                        }
                    }
                });
    }

    private void AddMovie() {
        Intent intent = new Intent(MoviesActivity.this, AddMovieActivity.class);
        addMovieActivityResultLauncher.launch(intent);
    }

    private void EditMovie(long id) {
        Intent intent = new Intent(MoviesActivity.this, EditMovieActivity.class);
        intent.putExtra("id", id);
        editMovieActivityResultLauncher.launch(intent);
    }

    private void DeleteMovie(long id) {
        dbh.deleteMovie(id);
        ConnectAdapter();
    }

    @Override
    public void onDeleteMovie(long id) {
        DeleteMovie(id);
    }

    @Override
    public void onEditMovie(long id) {
        EditMovie(id);
    }
}