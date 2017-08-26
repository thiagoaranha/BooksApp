package com.thiagoaranha.booksapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thiagoaranha.booksapp.model.Book;

import java.util.List;

/**
 * Created by Thiago on 03/08/17.
 */

public class LibraryAdapter extends BaseAdapter {

    Context context;
    List<Book> booksList;

    public LibraryAdapter(Context context, List<Book> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int i) {
        return booksList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View contentView, ViewGroup viewGroup) {
        View view = viewGroup.inflate(context, R.layout.listitem_book, null);

        Book book = (Book) getItem(i);

        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        tvTitle.setText(book.getVolumeInfo().getTitle());

        TextView tvAuthor = (TextView) view.findViewById(R.id.author);
        tvAuthor.setText(book.getVolumeInfo().getAuthors()[0]);

        ImageView ivCover = (ImageView) view.findViewById(R.id.cover);
        Picasso.with(context).load(book.getVolumeInfo().getImageLinks().getSmallThumbnail()).into(ivCover);

        return view;
    }
}
