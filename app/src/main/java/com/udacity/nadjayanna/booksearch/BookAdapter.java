package com.udacity.nadjayanna.booksearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nadja on 29/08/2017.
 */

class BookAdapter extends ArrayAdapter<Book> {
    BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View itemView = convertView;

        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book, parent, false);
        }

        Book currentBook = getItem(position);

        if (currentBook != null) {
            TextView titleView = (TextView) itemView.findViewById(R.id.title);
            titleView.setText(currentBook.getTitle());

            TextView descriptionView = (TextView) itemView.findViewById(R.id.description);
            descriptionView.setText(currentBook.getDescription());
        }

        return itemView;

    }
}
