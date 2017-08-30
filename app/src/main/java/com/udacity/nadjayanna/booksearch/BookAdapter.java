package com.udacity.nadjayanna.booksearch;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by nadja on 29/08/2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Book> mBookList;

    // Provide a reference to the view for each book in the list
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitle;
        public TextView mDescription;
        public SimpleDraweeView mImage;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.title);;
            mDescription = (TextView) v.findViewById(R.id.description);
            mImage = (SimpleDraweeView) v.findViewById(R.id.image);
        }
    }

    // Provide a suitable constructor

    public BookAdapter(List<Book> mBookList) {
        this.mBookList = mBookList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder holder, int position) {

        //get element from the list od books at this position
        //replace the contents of the view with that element
        holder.mTitle.setText(mBookList.get(position).getTitle());
        holder.mDescription.setText(mBookList.get(position).getDescription());
        holder.mImage.setImageURI(Uri.parse(mBookList.get(position).getImage()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}
