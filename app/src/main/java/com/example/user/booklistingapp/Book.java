package com.example.user.booklistingapp;

public class Book {

    private String mTitle, mAuthor, mDescription, mImageUrl, mURL, mCategories, mPublishedDate;
    private double mRating;
    private int mRatingCount, mPageCount;

    public Book(String mTitle, String mAuthor, String mDescription, String mImageUrl, String mURL, String mCategories, String mPublishedDate, double mRating, int mRatingCount, int mPageCount) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mDescription = mDescription;
        this.mImageUrl = mImageUrl;
        this.mURL = mURL;
        this.mCategories = mCategories;
        this.mPublishedDate = mPublishedDate;
        this.mRating = mRating;
        this.mRatingCount = mRatingCount;
        this.mPageCount = mPageCount;
    }

    public String getmTitle() { return mTitle; }
    public String getmAuthor() { return mAuthor; }
    public String getmDescription() { return mDescription; }
    public double getmRating() { return mRating; }
    public String getmImageUrl() { return mImageUrl; }
    public String getUrl() { return mURL; }
    public String getmCategories() { return mCategories; }
    public String getmPublishedDate() { return mPublishedDate; }
    public int getmRatingCount() { return mRatingCount; }
    public int getmPageCount() { return mPageCount; }

}
