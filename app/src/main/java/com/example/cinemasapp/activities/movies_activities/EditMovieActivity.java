package com.example.cinemasapp.activities.movies_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.cinemasapp.R;
import com.example.cinemasapp.database.DBHelper;
import com.example.cinemasapp.models.Movie;

public class EditMovieActivity extends AppCompatActivity {
    DBHelper dbh = new DBHelper(this);
    EditText title, picture_name, director, release_year, description, length;
    Spinner genreSpinner, languageSpinner, min_ageSpinner;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        Intent myIntent = getIntent();
        long id = myIntent.getLongExtra("id", -1);
        movie = dbh.getMovie(id);

        setData();

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(evt-> edit());
        Button back_btn = findViewById(R.id.back_button);
        back_btn.setOnClickListener(evt-> finish());
    }

    private void setData() {
        title = findViewById(R.id.title); title.setText(movie.getTitle());
        picture_name = findViewById(R.id.picture_name); picture_name.setText(movie.getPicture());
        director = findViewById(R.id.director); director.setText(movie.getDirector());
        release_year = findViewById(R.id.release_year); release_year.setText(String.valueOf(movie.getReleaseYear()));
        description = findViewById(R.id.description); description.setText(movie.getDescription());
        length = findViewById(R.id.length); length.setText(String.valueOf(movie.getLength()));

        genreSpinner = findViewById(R.id.genre);
        String[] genres = {"Action", "Anime", "Comedy", "Crime", "Documentary", "Drama", "Fantasy",
                "Horror", "Kids and Family", "Musical", "Romance", "Sci-Fi", "Thriller"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_text, genres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);
        int position = adapter.getPosition(movie.getGenre());
        genreSpinner.setSelection(position);

        languageSpinner = findViewById(R.id.language);
        String[] languages = { "English", "Mandarin Chinese", "Hindi", "Spanish", "French",
                "Czech", "Russian", "Portuguese", "Slovak", "German", "Japanese", "Turkish",
                "Korean", "Italian", "Ukrainian", "Vietnamese" };
        adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_text, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        position = adapter.getPosition(String.valueOf(movie.getLanguage()));
        languageSpinner.setSelection(position);

        min_ageSpinner = findViewById(R.id.min_age);
        String[] ages = {"7", "12", "15", "18"};
        adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_text, ages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        min_ageSpinner.setAdapter(adapter);
        position = adapter.getPosition(String.valueOf(movie.getMinAge()));
        min_ageSpinner.setSelection(position);

    }

    private void edit() {
        String titleValue = title.getText().toString();
        String pictureNameValue = picture_name.getText().toString();
        String directorValue = director.getText().toString();
        String releaseYearValue = release_year.getText().toString();
        String descriptionValue = description.getText().toString();
        String lengthValue = length.getText().toString();
        String selectedGenre = genreSpinner.getSelectedItem().toString();
        String selectedLanguage = languageSpinner.getSelectedItem().toString();
        String selectedAge = min_ageSpinner.getSelectedItem().toString();

        if (titleValue.equals("") || pictureNameValue.equals("") || directorValue.equals("") || releaseYearValue.equals("")
                || descriptionValue.equals("") || lengthValue.equals("") || selectedGenre.equals("") || selectedLanguage.equals("")
                || selectedAge.equals("")) {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(movie.getTitle().equals(titleValue)) && dbh.titleExists(titleValue)) {
            Toast.makeText(this, "Movie with this title already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        Movie editedMovie = new Movie(movie.getID(), titleValue, pictureNameValue, directorValue, Integer.parseInt(releaseYearValue),
                descriptionValue, selectedGenre, selectedLanguage, Integer.parseInt(lengthValue), Integer.parseInt(selectedAge));
        dbh.updateMovie(editedMovie);

        Intent intentBack = new Intent();
        setResult(RESULT_OK, intentBack);
        finish();
    }
}