package com.example.cinemasapp.activities.rooms_activities;

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
import com.example.cinemasapp.models.Room;

public class EditRoomActivity extends AppCompatActivity {
    DBHelper dbh = new DBHelper(this);
    EditText room_number;
    Spinner capacitySpinner;
    Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);

        Intent myIntent = getIntent();
        long id = myIntent.getLongExtra("id", -1);
        room = dbh.getRoom(id);

        setData();

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(evt-> edit());
        Button back_btn = findViewById(R.id.back_button);
        back_btn.setOnClickListener(evt-> finish());
    }

    private void setData() {
        room_number = findViewById(R.id.room_number);
        room_number.setText(String.valueOf(room.getRoomNumber()));

        capacitySpinner = findViewById(R.id.capacity_spinner);
        String[] capacities = {"50", "75", "100"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_selected_text, capacities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capacitySpinner.setAdapter(adapter);
        int position = adapter.getPosition(String.valueOf(room.getCapacity()));
        capacitySpinner.setSelection(position);
    }

    private void edit() {
        String roomNumberValue = room_number.getText().toString();
        String selectedCapacity = capacitySpinner.getSelectedItem().toString();

        if (roomNumberValue.equals("") || selectedCapacity.equals("")) {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (room.getRoomNumber() != Integer.parseInt(roomNumberValue) && dbh.roomNumberExists(Integer.parseInt(roomNumberValue))) {
            Toast.makeText(this, "Room number already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(roomNumberValue) > 10) {
            Toast.makeText(this, "Room number cannot be greater than 10", Toast.LENGTH_SHORT).show();
            return;
        }

        dbh.updateRoom(new Room(room.getID(), Integer.parseInt(roomNumberValue), Integer.parseInt(selectedCapacity)));

        Intent intentBack = new Intent();
        setResult(RESULT_OK, intentBack);
        finish();
    }
}