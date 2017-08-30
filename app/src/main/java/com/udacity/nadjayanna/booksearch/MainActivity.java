package com.udacity.nadjayanna.booksearch;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String BOOKS_REQUEST = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String MAX_RESULT = "&maxResults=10";
    private static final int BOOK_LOADER_ID = 1;
    private static final String ITEMS = "items";
    private static final String EMPTY_STATE = "empty";

    private ArrayList<Book> mBooks;

    private String mSearch[];
    private EditText searchView;
    private String searchUrl;

    private LoaderManager loaderManager;

    private TextView mEmptyState;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaderManager = getLoaderManager();

        if (savedInstanceState != null) {
            if (savedInstanceState.getSerializable(ITEMS) != null) {
                mBooks = (ArrayList<Book>) savedInstanceState.getSerializable(ITEMS);
                Log.i("onCreate", "set adapter with bundle");
                mAdapter = new BookAdapter(mBooks);
            }else{
                mAdapter = new BookAdapter(new ArrayList<Book>());
            }
        } else {
            mAdapter = new BookAdapter(new ArrayList<Book>());
        }

        //ListView bookListView = (ListView) findViewById(R.id.list);
        //bookListView.setAdapter(mAdapter);

        mRecyclerView = (RecyclerView) findViewById(R.id.books_recycler_view);

        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        searchView = (EditText) findViewById(R.id.search_text);
        searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    if (searchView.getText().toString().isEmpty()){

                        // Update empty state with empty search error message
                        mBooks = new ArrayList<Book>();
                        mAdapter=new BookAdapter(mBooks);
                        mRecyclerView.setAdapter(mAdapter);
                        mEmptyState.setText(R.string.empty_search);

                    }else {
                        mSearch = searchView.getText().toString().split(" ");
                        searchUrl = BOOKS_REQUEST;
                        searchUrl = searchUrl.concat(mSearch[0]);
                        for (int i=1; i<mSearch.length; i++){
                            searchUrl = searchUrl.concat("+");
                            searchUrl = searchUrl.concat(mSearch[i]);

                        }
                        searchUrl = searchUrl.concat(MAX_RESULT);

                        // If there is a network connection, fetch data
                        if (isConnected()) {

                            loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                        }
                        else {

                            // Update empty state with no connection error message
                            mBooks = new ArrayList<Book>();
                            mAdapter=new BookAdapter(mBooks);
                            mRecyclerView.setAdapter(mAdapter);
                            mEmptyState.setText(R.string.no_internet_connection);
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.GONE);

        mEmptyState = (TextView) findViewById(R.id.empty_view);

        // If there is a network connection, fetch data
        if (isConnected()) {
            mEmptyState.setText("");
        }

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {

        mAdapter=new BookAdapter(new ArrayList<Book>());


        mRecyclerView.setAdapter(mAdapter);
        ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        mProgressBar.setVisibility(View.VISIBLE);
        mEmptyState.setText("");

        return new BookLoader(MainActivity.this, searchUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {


        mBooks = new ArrayList<Book>();
        mAdapter=new BookAdapter(mBooks);


        if (books != null && !books.isEmpty()) {

            mBooks = (ArrayList<Book>) books;
            mAdapter = new BookAdapter(mBooks);

            mRecyclerView.setAdapter(mAdapter);
            ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);
            mProgressBar.setVisibility(View.GONE);
            mEmptyState.setText("");

        } else{

            mRecyclerView.setAdapter(mAdapter);
            ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);
            mProgressBar.setVisibility(View.GONE);
            mEmptyState.setText(R.string.no_books);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter = new BookAdapter(new ArrayList<Book>());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ITEMS, mBooks);
        outState.putString(EMPTY_STATE,mEmptyState.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mBooks = (ArrayList<Book>) savedInstanceState.getSerializable(ITEMS);
        mEmptyState.setText(savedInstanceState.getString(EMPTY_STATE));
    }

    public void onClickMyFuckingButton(View view) {

        if (searchView.getText().toString().isEmpty()){

            // Update empty state with empty search error message
            mBooks = new ArrayList<Book>();
            mAdapter=new BookAdapter(mBooks);
            mRecyclerView.setAdapter(mAdapter);
            mEmptyState.setText(R.string.empty_search);

        }else{

            mSearch = searchView.getText().toString().split(" ");

            searchUrl = BOOKS_REQUEST;
            searchUrl = searchUrl.concat(mSearch[0]);
            for (int i = 1; i < mSearch.length; i++) {
                searchUrl = searchUrl.concat("+");
                searchUrl = searchUrl.concat(mSearch[i]);

            }
            searchUrl = searchUrl.concat(MAX_RESULT);

            // If there is a network connection, fetch data
            if (isConnected()) {
                loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
            }
            else {
                // Update empty state with no connection error message
                mBooks = new ArrayList<Book>();
                mAdapter = new BookAdapter(mBooks);
                mRecyclerView.setAdapter(mAdapter);
                mEmptyState.setText(R.string.no_internet_connection);
            }
        }
    }

    public boolean isConnected (){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
