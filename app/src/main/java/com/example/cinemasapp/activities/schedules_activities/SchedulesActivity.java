package com.example.cinemasapp.activities.schedules_activities;

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
import com.example.cinemasapp.adapters.SchedulesAdapter;
import com.example.cinemasapp.database.DBHelper;

public class SchedulesActivity extends AppCompatActivity implements SchedulesAdapter.ScheduleActionListener{
    DBHelper dbh = new DBHelper(this);
    SchedulesAdapter myAdapter;
    ActivityResultLauncher<Intent> addScheduleActivityResultLauncher;
    ActivityResultLauncher<Intent> editScheduleActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedules);

        ConnectAdapter();
        registerLaunchers();

        Button b = findViewById(R.id.button);
        b.setOnClickListener(evt -> AddSchedule());
        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(evt -> finish());
    }

    private void ConnectAdapter() {
        Cursor myCursor = dbh.schedulesCursor();
        myAdapter = new SchedulesAdapter(this, myCursor, 0, this);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(myAdapter);
    }

    private void registerLaunchers() {
        addScheduleActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            ConnectAdapter();
                        }
                    }
                });

        editScheduleActivityResultLauncher = registerForActivityResult(
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

    private void AddSchedule() {
        Intent intent = new Intent(SchedulesActivity.this, AddScheduleActivity.class);
        addScheduleActivityResultLauncher.launch(intent);
    }

    private void EditSchedule(long id) {
        Intent intent = new Intent(SchedulesActivity.this, EditScheduleActivity.class);
        intent.putExtra("id", id);
        editScheduleActivityResultLauncher.launch(intent);
    }

    private void DeleteSchedule(long id) {
        dbh.deleteSchedule(id);
        ConnectAdapter();
    }

    @Override
    public void onDeleteSchedule(long id) {
        DeleteSchedule(id);
    }

    @Override
    public void onEditSchedule(long id) {
        EditSchedule(id);
    }
}