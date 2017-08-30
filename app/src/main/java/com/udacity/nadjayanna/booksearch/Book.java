package com.udacity.nadjayanna.booksearch;

import java.io.Serializable;

/**
 * Created by nadja on 29/08/2017.
 */

public class Book implements Serializable{

    private String mTitle;
    private String mDescription;

    public Book(String title, String description) {
        this.mTitle = title;
        this.mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
