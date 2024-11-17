package com.example.cinemasapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinemasapp.R;
import com.example.cinemasapp.database.MyContract;

public class SchedulesAdapter extends CursorAdapter {
    private ScheduleActionListener mListener;

    public SchedulesAdapter(Context context, Cursor cursor, int flags, ScheduleActionListener listener) {
        super(context, cursor, flags);
        this.mListener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from (context).inflate(
                R.layout.schedule_list_layout,
                parent,
                false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView idTextView = view.findViewById(R.id.id);
        ImageView pictureImage = view.findViewById(R.id.movie_poster);
        TextView titleTextView = view.findViewById(R.id.title);
        TextView genreTextView = view.findViewById(R.id.genre);
        TextView lengthTextView = view.findViewById(R.id.length);
        TextView formatTextView = view.findViewById(R.id.format);
        TextView minAgeTextView = view.findViewById(R.id.min_age);
        TextView dateTextView = view.findViewById(R.id.date);
        TextView timeTextView = view.findViewById(R.id.time);
        TextView roomNumberTextView = view.findViewById(R.id.room_number);
        ImageView deleteButton = view.findViewById(R.id.delete_button);
        ImageView editButton = view.findViewById(R.id.edit_button);

        String id = cursor.getString(cursor.getColumnIndexOrThrow("s." + MyContract.Schedule.COLUMN_ID));
        String picture = cursor.getString(cursor.getColumnIndexOrThrow("m." + MyContract.Movie.COLUMN_PICTURE));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("m." + MyContract.Movie.COLUMN_TITLE));
        String genre = cursor.getString(cursor.getColumnIndexOrThrow("m." + MyContract.Movie.COLUMN_GENRE));
        long length = cursor.getLong(cursor.getColumnIndexOrThrow("m." + MyContract.Movie.COLUMN_LENGTH));
        String format = cursor.getString(cursor.getColumnIndexOrThrow("s." + MyContract.Schedule.COLUMN_FORMAT));
        long min_age = cursor.getLong(cursor.getColumnIndexOrThrow("m." + MyContract.Movie.COLUMN_MIN_AGE));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("s." + MyContract.Schedule.COLUMN_DATE));
        String time = cursor.getString(cursor.getColumnIndexOrThrow("s." + MyContract.Schedule.COLUMN_TIME));
        long room_number = cursor.getLong(cursor.getColumnIndexOrThrow("r." + MyContract.Room.COLUMN_ROOM_NUMBER));

        idTextView.setText(id);
        titleTextView.setText(title);
        genreTextView.setText(genre);
        lengthTextView.setText(String.valueOf(length) + " min");
        formatTextView.setText(format);
        minAgeTextView.setText(String.valueOf(min_age));
        dateTextView.setText(date);
        timeTextView.setText(time);
        roomNumberTextView.setText("Room number: " + String.valueOf(room_number));

        int drawableResourceId = context.getResources().getIdentifier(picture, "drawable", context.getPackageName());
        if (drawableResourceId != 0) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableResourceId);
            pictureImage.setImageBitmap(bitmap);
        } else {
            pictureImage.setImageResource(R.drawable.picture_unavaible);
        }


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteSchedule(Long.parseLong(id));
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditSchedule(Long.parseLong(id));
            }
        });
    }

    public interface ScheduleActionListener {
        void onDeleteSchedule(long id);
        void onEditSchedule(long id);
    }
}
