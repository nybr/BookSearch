package com.udacity.nadjayanna.booksearch;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by nadja on 29/08/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {


    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i("onStartLoading", "forceLoad");
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if(mUrl == null){
            Log.i("loadInBackgtound", "null URL");
            return null;
        }
        Log.i("loadInBackgtound", mUrl.toString());
        List<Book> books = QueryUtils.fetchBookData(mUrl);

        return books;
    }

    @Override
    protected void onStopLoading() {
        Log.i("onStopLoading", "cancelLoad");
        cancelLoad();
    }

}
