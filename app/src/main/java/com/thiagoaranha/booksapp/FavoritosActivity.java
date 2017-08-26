package com.thiagoaranha.booksapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.thiagoaranha.booksapp.dao.BookDao;
import com.thiagoaranha.booksapp.model.Book;

import java.util.List;

public class FavoritosActivity extends AppCompatActivity {

    OnListItemClickListener onListItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        BookDao bookDao = new BookDao(getApplicationContext());

        List<Book> favoritos = bookDao.getAll();

        LibraryAdapter libraryAdapter = new LibraryAdapter(getApplicationContext(), favoritos);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(libraryAdapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showBook((Book)adapterView.getItemAtPosition(i));
            }
        });


    }

    private void showBook(Book book) {
        BookFragment bookFragment = new BookFragment();
        Bundle arg = new Bundle();
        arg.putParcelable("book", book);
        bookFragment.setArguments(arg);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).add(R.id.main_container, bookFragment).commit();
    }

    public void delete(View view){
        BookDao bookDao = new BookDao(getApplicationContext());
        bookDao.delete();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public interface OnListItemClickListener {
        public void onListItemClick(Book book);
    }
}
