package com.udacity.nadjayanna.booksearch;

import java.io.Serializable;

/**
 * Created by nadja on 29/08/2017.
 */

public class Book implements Serializable{

    private String mTitle;
    private String mDescription;
    private String mImage;
    private String mAuthor;
    private String mBuyLink;


    public Book(String title, String description, String image, String author, String buyLink) {
        this.mTitle = title;
        this.mDescription = description;
        this.mImage = image;
        this.mAuthor = author;
        this.mBuyLink = buyLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImage() { return mImage; }

    public String getAuthor() { return  mAuthor; }

    public String getBuyLink() { return  mBuyLink; }
}
