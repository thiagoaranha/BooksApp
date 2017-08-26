package com.thiagoaranha.booksapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thiago on 03/08/17.
 */

public class Book implements Parcelable {

    private String id;
    private VolumeInfo volumeInfo;

    public Book(Parcel in) {
        id = in.readString();
        volumeInfo = new VolumeInfo();
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

    public VolumeInfo getVolumeInfo() { return  volumeInfo; }

    public void setVolumeInfo(VolumeInfo volumeInfo) { this.volumeInfo = volumeInfo; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
    }

}
