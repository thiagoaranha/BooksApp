package com.thiagoaranha.booksapp;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class LibraryFragment extends Fragment {
    OnListItemClickListener onListItemClickListener;

    public LibraryFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_library, container, false);

        ArrayList<Book> bookList = getArguments().getParcelableArrayList("bookList");

        LibraryAdapter libraryAdapter = new LibraryAdapter(getContext(), bookList);

        ListView listView = (ListView) root.findViewById(R.id.listView);
        listView.setAdapter(libraryAdapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onListItemClickListener.onListItemClick((Book)adapterView.getItemAtPosition(i));
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onListItemClickListener = (OnListItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onListItemClick");
        }
    }

    public interface OnListItemClickListener {
        public void onListItemClick(Book book);
    }

}

