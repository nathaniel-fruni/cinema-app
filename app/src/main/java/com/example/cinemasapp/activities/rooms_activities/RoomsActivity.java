package com.example.cinemasapp.activities.rooms_activities;

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
import com.example.cinemasapp.adapters.RoomsAdapter;
import com.example.cinemasapp.database.DBHelper;

public class RoomsActivity extends AppCompatActivity implements RoomsAdapter.RoomActionListener {
    DBHelper dbh = new DBHelper(this);
    RoomsAdapter myAdapter;
    ActivityResultLauncher<Intent> addRoomActivityResultLauncher;
    ActivityResultLauncher<Intent> editRoomActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        ConnectAdapter();
        registerLaunchers();

        Button b = findViewById(R.id.button);
        b.setOnClickListener(evt -> AddRoom());
        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(evt -> finish());
    }

    private void ConnectAdapter() {
        Cursor myCursor = dbh.roomsCursor();
        myAdapter = new RoomsAdapter(this, myCursor, 0, this);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(myAdapter);
    }

    private void registerLaunchers() {
        addRoomActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            ConnectAdapter();
                        }
                    }
                });

        editRoomActivityResultLauncher = registerForActivityResult(
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

    private void AddRoom() {
        Intent intent = new Intent(RoomsActivity.this, AddRoomActivity.class);
        addRoomActivityResultLauncher.launch(intent);
    }

    private void EditRoom(long id) {
        Intent intent = new Intent(RoomsActivity.this, EditRoomActivity.class);
        intent.putExtra("id", id);
        editRoomActivityResultLauncher.launch(intent);
    }

    private void DeleteRoom(long id) {
        dbh.deleteRoom(id);
        ConnectAdapter();
    }

    @Override
    public void onDeleteRoom(long id) {
        DeleteRoom(id);
    }

    @Override
    public void onEditRoom(long id) {
        EditRoom(id);
    }
}