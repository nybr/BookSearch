package com.udacity.nadjayanna.booksearch;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String BOOKS_REQUEST = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String MAX_RESULT = "&maxResults=10";
    private static final int BOOK_LOADER_ID = 1;
    private static final String STATE_ITEMS = "items";

    private BookAdapter mAdapter;
    private ArrayList<Book> mBooks;

    private String mSearch[];
    private EditText searchView;
    private String searchUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = getLoaderManager();

        if (savedInstanceState != null) {
            Log.i("onCreate", "NOT NULL");
            if (savedInstanceState.getSerializable(STATE_ITEMS) != null) {
                mBooks = (ArrayList<Book>) savedInstanceState.getSerializable(STATE_ITEMS);
                if(!mBooks.isEmpty() && mBooks!=null){
                    Log.i("onCreate", "set adapter with bundle " + mBooks.size());
                    mAdapter = new BookAdapter(this, mBooks);
                }else {
                    Log.i("onCreate", "mBooks is NULL");
                    mAdapter = new BookAdapter(this, new ArrayList<Book>());
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                }
            }
        } else {
            Log.i("onCreate", "NULL");
            mAdapter = new BookAdapter(this, new ArrayList<Book>());
            loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
        }


        ListView bookListView = (ListView) findViewById(R.id.list);
        bookListView.setAdapter(mAdapter);
        searchView = (EditText) findViewById(R.id.search_text);

        /* searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    mSearch = searchView.getText().toString().split(" ");
                    searchUrl = BOOKS_REQUEST;
                    searchUrl = searchUrl.concat(mSearch[0]);
                    for (int i=1; i<mSearch.length; i++){
                        searchUrl = searchUrl.concat("+");
                        searchUrl = searchUrl.concat(mSearch[i]);

                    }
                    searchUrl = searchUrl.concat(MAX_RESULT);

                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                    return true;
                }
                return false;
            }
        });*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.getLoaderManager().restartLoader(BOOK_LOADER_ID, null, this);
        Log.i("onRestart", "crestartLoader");
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Log.i("onCreateLoader", "create Loader " + searchUrl);
        return new BookLoader(MainActivity.this, searchUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {


        if(mAdapter !=null){
            Log.i("onLoadFinished", "clear adapter " + mAdapter.getCount());

            mAdapter.clear();
        }else Log.i("onLoadFinished", "null adapter");
        if (books != null && !books.isEmpty()) {
            Log.i("onLoadFinished", books.get(0).getTitle());
            mBooks = (ArrayList<Book>) books;
            mAdapter = new BookAdapter(MainActivity.this, mBooks);
            ListView bookList = (ListView) findViewById(R.id.list);
            bookList.setAdapter(mAdapter);
            Log.i("onLoadFinished", "set new adapter");
        }else Log.i("onLoadFinished", "books NULL");

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_ITEMS, mBooks);

        if (mBooks!=null){
            Log.i("OnSaveInstanceState", mBooks.get(0).getTitle());
        }else Log.i("OnSaveInstanceState", "mBooks NULL");

    }

    public void onClickMyFuckingButton(View view) {

        mSearch = searchView.getText().toString().split(" ");

        Log.i("onClickMyFuckingButton", searchView.getText().toString());
        searchUrl = BOOKS_REQUEST;
        searchUrl = searchUrl.concat(mSearch[0]);
        for (int i = 1; i < mSearch.length; i++) {
            searchUrl = searchUrl.concat("+");
            searchUrl = searchUrl.concat(mSearch[i]);

        }
        searchUrl = searchUrl.concat(MAX_RESULT);
        getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
    }
}
