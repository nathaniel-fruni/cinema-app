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


public class MoviesAdapter extends CursorAdapter {
    private MovieActionListener mListener;

    public MoviesAdapter(Context context, Cursor cursor, int flags, MovieActionListener listener) {
        super(context, cursor, flags);
        this.mListener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from (context).inflate(
                R.layout.movie_list_layout,
                parent,
                false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView idTextView = view.findViewById(R.id.id);
        TextView titleTextView = view.findViewById(R.id.title);
        TextView yearTextView = view.findViewById(R.id.year);
        TextView genreTextView = view.findViewById(R.id.genre);
        TextView descriptionTextView = view.findViewById(R.id.description);
        ImageView pictureImage = view.findViewById(R.id.movie_poster);
        ImageView deleteButton = view.findViewById(R.id.delete_button);
        ImageView editButton = view.findViewById(R.id.edit_button);

        String id = cursor.getString(cursor.getColumnIndexOrThrow(MyContract.Movie.COLUMN_ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(MyContract.Movie.COLUMN_TITLE));
        String picture = cursor.getString(cursor.getColumnIndexOrThrow(MyContract.Movie.COLUMN_PICTURE));
        long year = cursor.getLong(cursor.getColumnIndexOrThrow(MyContract.Movie.COLUMN_RELEASE_YEAR));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(MyContract.Movie.COLUMN_DESCRIPTION));
        String genre = cursor.getString(cursor.getColumnIndexOrThrow(MyContract.Movie.COLUMN_GENRE));

        idTextView.setText(id);
        titleTextView.setText(title);
        yearTextView.setText(String.valueOf(year));
        genreTextView.setText(genre);
        descriptionTextView.setText(description);

        int drawableResourceId = context.getResources().getIdentifier(picture, "drawable", context.getPackageName());
        if (drawableResourceId != 0) {
            pictureImage.setImageResource(drawableResourceId);
        } else {
            pictureImage.setImageResource(R.drawable.picture_unavaible);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteMovie(Long.parseLong(id));
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditMovie(Long.parseLong(id));
            }
        });
    }

    public interface MovieActionListener {
        void onDeleteMovie(long id);
        void onEditMovie(long id);
    }
}
