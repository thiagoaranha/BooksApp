package com.thiagoaranha.booksapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thiago on 03/08/17.
 */

class Book implements Parcelable {

    private String id;
    private String title;
    private String author;
    private String image_url;
    private String description;

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        author = in.readString();
        image_url = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage_url() { return image_url; }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(image_url);
        parcel.writeString(description);
    }

}
