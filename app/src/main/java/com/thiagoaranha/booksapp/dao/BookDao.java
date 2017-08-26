package com.thiagoaranha.booksapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.util.Log;

import com.thiagoaranha.booksapp.model.Book;
import com.thiagoaranha.booksapp.model.ImageLinks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thiago on 20/08/17.
 */

public class BookDao {

    OpenHelper openHelper;

    public BookDao (Context context) {
        openHelper = new OpenHelper(context);
    }

    public void insert(Book book) {

        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", book.getId());
        contentValues.put("title", book.getVolumeInfo().getTitle());
        contentValues.put("author", book.getVolumeInfo().getAuthors()[0]);
        contentValues.put("imageUrl", book.getVolumeInfo().getImageLinks().getSmallThumbnail());
        contentValues.put("description", book.getVolumeInfo().getDescription());

        long bookId = sqLiteDatabase.insert("book", null, contentValues);

        sqLiteDatabase.close();
        Log.d("Book", "Insert id: " + bookId);
    }

    public List<Book> getAll(){
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM book", null);

        List<Book> books = new ArrayList();

        c.moveToFirst();

        while (c.moveToNext()){
            Book book = new Book(Parcel.obtain());
            String id = c.getString(c.getColumnIndex("id"));
            String title = c.getString(c.getColumnIndex("title"));
            String author = c.getString(c.getColumnIndex("author"));
            String imageUrl = c.getString(c.getColumnIndex("imageUrl"));
            String description = c.getString(c.getColumnIndex("description"));
            String[] authors = new String[1];
            authors[0] = author;
            ImageLinks imageLinks = new ImageLinks(imageUrl);

            book.setId(id);
            book.getVolumeInfo().setTitle(title);
            book.getVolumeInfo().setAuthors(authors);
            book.getVolumeInfo().setImageLinks(imageLinks);
            book.getVolumeInfo().setDescription(description);

            books.add(book);

        }

        return books;
    }

    public void delete(){
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        sqLiteDatabase.delete("book", null, null);
    }
}
