package com.example.cinemasapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cinemasapp.R;
import com.example.cinemasapp.database.MyContract;


public class RoomsAdapter extends CursorAdapter {
    private RoomActionListener mListener;

    public RoomsAdapter(Context context, Cursor cursor, int flags, RoomActionListener listener) {
        super(context, cursor, flags);
        this.mListener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from (context).inflate(
                R.layout.room_list_layout,
                parent,
                false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView idTextView = view.findViewById(R.id.id);
        TextView roomNumberTextView = view.findViewById(R.id.room_number);
        TextView roomCapacityTextView = view.findViewById(R.id.room_capacity);
        ImageView deleteButton = view.findViewById(R.id.delete_button);
        ImageView editButton = view.findViewById(R.id.edit_button);

        String id = cursor.getString(cursor.getColumnIndexOrThrow(MyContract.Room.COLUMN_ID));
        String roomNumber = cursor.getString(cursor.getColumnIndexOrThrow(MyContract.Room.COLUMN_ROOM_NUMBER));
        String roomCapacity = cursor.getString(cursor.getColumnIndexOrThrow(MyContract.Room.COLUMN_CAPACITY));

        idTextView.setText(id);
        roomNumberTextView.setText("Room number " + roomNumber);
        roomCapacityTextView.setText("Capacity: " + roomCapacity);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteRoom(Long.parseLong(id));
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditRoom(Long.parseLong(id));
            }
        });
    }

    public interface RoomActionListener {
        void onDeleteRoom(long id);
        void onEditRoom(long id);
    }

}