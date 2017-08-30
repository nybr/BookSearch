package com.udacity.nadjayanna.booksearch;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by nadja on 29/08/2017.
 */

class BookLoader extends AsyncTaskLoader<List<Book>> {


    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchBookData(mUrl);
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

}
