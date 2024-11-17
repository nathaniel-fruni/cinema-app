package com.example.cinemasapp.activities.schedules_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.cinemasapp.R;
import com.example.cinemasapp.database.DBHelper;
import com.example.cinemasapp.models.Schedule;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class EditScheduleActivity extends AppCompatActivity {
    DBHelper dbh = new DBHelper(this);
    Schedule schedule;
    TextView displayedDate, displayedTime;
    Spinner movieSpinner, roomSpinner, formatSpinner;
    String selectedDate = "", selectedTime = "";
    long selectedMovieId = -1 , selectedRoomId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        Intent myIntent = getIntent();
        long id = myIntent.getLongExtra("id", -1);
        schedule = dbh.getSchedule(id);

        setData();
        Button dateBtn = findViewById(R.id.date_button);
        dateBtn.setOnClickListener(evt->showDatePickerDialog());
        Button timeBtn = findViewById(R.id.time_button);
        timeBtn.setOnClickListener(evt->showTimePickerDialog());

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(evt-> edit());
        Button back_btn = findViewById(R.id.back_button);
        back_btn.setOnClickListener(evt-> finish());
    }

    private void setData() {
        displayedDate = findViewById(R.id.displayedDate); displayedDate.setText(schedule.getDate());
        displayedTime = findViewById(R.id.displayedTime); displayedTime.setText(schedule.getTime());
        int position;

        movieSpinner = findViewById(R.id.movie);
        Map<String, String> moviesMap = dbh.getMovieTitlesAndIds();
        String[] movies = moviesMap.keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_text, movies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        movieSpinner.setAdapter(adapter);
        movieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMovieTitle = (String) parent.getItemAtPosition(position);
                selectedMovieId = Long.parseLong(moviesMap.get(selectedMovieTitle));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMovieId = -1;
            }
        });
        String storedMovieTitle = "";
        for (Map.Entry<String, String> entry : moviesMap.entrySet()) {
            if (entry.getValue().equals(String.valueOf(schedule.getMOVIE_ID()))) {
                storedMovieTitle = entry.getKey();
                break;
            }
        }
        if (!storedMovieTitle.equals("")) {
            position = adapter.getPosition(storedMovieTitle);
            movieSpinner.setSelection(position);
        }

        roomSpinner = findViewById(R.id.room);
        Map<String, String> roomsMap = dbh.getRoomNumbersAndIds();
        String[] rooms = roomsMap.keySet().toArray(new String[0]);
        adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_text, rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter);
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRoomNumber = (String) parent.getItemAtPosition(position);
                selectedRoomId = Long.parseLong(roomsMap.get(selectedRoomNumber));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedRoomId = -1;
            }
        });
        String storedRoomNumber = "";
        for (Map.Entry<String, String> entry : roomsMap.entrySet()) {
            if (entry.getValue().equals(String.valueOf(schedule.getROOM_ID()))) {
                storedRoomNumber = entry.getKey();
                break;
            }
        }
        if (!storedRoomNumber.equals("")) {
            position = adapter.getPosition(storedRoomNumber);
            roomSpinner.setSelection(position);
        }

        formatSpinner = findViewById(R.id.format);
        String[] formats = {"2D", "3D"};
        adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_text, formats);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formatSpinner.setAdapter(adapter);
        position = adapter.getPosition(schedule.getFormat());
        formatSpinner.setSelection(position);
    }

    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedDate = String.format(Locale.getDefault(), "%02d.%02d.%d", dayOfMonth, (monthOfYear + 1), year);
                        displayedDate.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void showTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        displayedTime.setText(selectedTime);
                    }
                }, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    private void edit() {
        String selectedFormat = formatSpinner.getSelectedItem().toString();

        if (selectedMovieId == -1 || selectedRoomId == -1 || selectedDate.equals("") ||
                selectedTime.equals("") || selectedFormat.equals("")) {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(schedule.getROOM_ID() == selectedRoomId && schedule.getMOVIE_ID() == selectedMovieId) && dbh.scheduleExists(selectedDate, selectedTime, selectedRoomId)) {
            Toast.makeText(this, "Room at this date and time is already taken", Toast.LENGTH_SHORT).show();
            return;
        }

        Schedule editedSchedule = new Schedule(schedule.getID(), selectedMovieId, selectedRoomId, selectedDate, selectedTime, selectedFormat);
        dbh.updateSchedule(editedSchedule);

        Intent intentBack = new Intent();
        setResult(RESULT_OK, intentBack);
        finish();
    }
}