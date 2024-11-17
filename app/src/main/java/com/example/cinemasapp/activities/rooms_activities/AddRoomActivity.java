package com.example.cinemasapp.activities.rooms_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.example.cinemasapp.R;
import com.example.cinemasapp.database.DBHelper;
import com.example.cinemasapp.models.Room;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AddRoomActivity extends AppCompatActivity {
    DBHelper dbh = new DBHelper(this);
    EditText room_number;
    Spinner capacitySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        loadElements();

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(evt-> add());

        Button back_btn = findViewById(R.id.back_button);
        back_btn.setOnClickListener(evt-> finish());
    }

    private void loadElements() {
        room_number = findViewById(R.id.room_number);

        capacitySpinner = findViewById(R.id.capacity_spinner);
        String[] capacities = {"50", "75", "100"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_text, capacities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capacitySpinner.setAdapter(adapter);
    }

    private void add() {
        String roomNumberValue = room_number.getText().toString();
        String selectedCapacity = capacitySpinner.getSelectedItem().toString();

        if (roomNumberValue.equals("") || selectedCapacity.equals("")) {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dbh.roomNumberExists(Integer.parseInt(roomNumberValue))) {
            Toast.makeText(this, "Room number already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(roomNumberValue) > 10) {
            Toast.makeText(this, "Room number cannot be greater than 10", Toast.LENGTH_SHORT).show();
            return;
        }

        Room room = new Room(1, Integer.parseInt(roomNumberValue), Integer.parseInt(selectedCapacity));
        dbh.addRoom(room);

        Intent intentBack = new Intent();
        setResult(RESULT_OK, intentBack);
        finish();
    }
}