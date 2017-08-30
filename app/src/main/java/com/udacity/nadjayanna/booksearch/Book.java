package com.udacity.nadjayanna.booksearch;

import java.io.Serializable;
import java.net.URI;

/**
 * Created by nadja on 29/08/2017.
 */

public class Book implements Serializable{

    private String mTitle;
    private String mDescription;
    private String mImage;


    public Book(String title, String description, String image) {
        this.mTitle = title;
        this.mDescription = description;
        this.mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImage() { return mImage; }
}
