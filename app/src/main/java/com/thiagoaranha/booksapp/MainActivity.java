package com.thiagoaranha.booksapp;

import android.content.*;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.thiagoaranha.booksapp.HttpRequest.ParamRequest;
import com.thiagoaranha.booksapp.HttpRequest.RetrofitGetBooks;
import com.thiagoaranha.booksapp.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnSubmitSearchListener, LibraryFragment.OnListItemClickListener, RetrofitGetBooks.GetNewApiListener {

    SearchFragment searchFragment;
    FragmentManager fm;

    ProgressBar pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        pb = (ProgressBar) findViewById(R.id.progress);

        //Pegando os dados da notificação para exibir o livro
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.containsKey("bookId")){
                String bookId = (String)bundle.get("bookId");

                findBookById(bookId);
            }
        }

        if(savedInstanceState == null){

            searchFragment = new SearchFragment();
            searchFragment.setRetainInstance(true);

            fm.beginTransaction()
                    .add(R.id.container, searchFragment)
                    .commit();

        }else{

            searchFragment = (SearchFragment) fm.findFragmentById(R.id.container);

            fm.beginTransaction().show(searchFragment);

        }

    }


    @Override
    public void onSubmitSearch(String text) {

        if(text.length()>0)
        {
            text = text.replace(" ", "+");

            RetrofitGetBooks task = new RetrofitGetBooks(this);
            ParamRequest params = new ParamRequest();
            params.q = text;
            task.execute(params);

            /*AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    List<Book> bookList = new ArrayList<Book>();

                    String json = new String(responseBody);

                    try {
                        JSONObject object = new JSONObject(json);
                        JSONArray array = object.getJSONArray("items");

                        for (int i = 0; i < array.length(); i++) {

                            Book book = new Book(Parcel.obtain());
                            JSONObject item = array.getJSONObject(i);

                            String id = item.getString("id");
                            book.setId(id);

                            JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                            String title = volumeInfo.getString("title");
                            book.setTitle(title);

                            JSONArray authors = volumeInfo.getJSONArray("authors");
                            String author = authors.getString(0);
                            book.setAuthor(author);

                            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                            String imageLink = imageLinks.getString("smallThumbnail");
                            book.setImage_url(imageLink);

                            String description = volumeInfo.getString("description");
                            book.setDescription(description);

                            bookList.add(book);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Bundle args = new Bundle();
                    args.putParcelableArrayList("bookList", (ArrayList<? extends Parcelable>) bookList);
                    LibraryFragment libraryFragment = new LibraryFragment();

                    libraryFragment.setArguments(args);

                    android.support.v4.app.Fragment fragmentLoaded = (android.support.v4.app.Fragment) fm.findFragmentById(R.id.main_container);
                    fm = getSupportFragmentManager();

                    if(fragmentLoaded != null) {
                        fm.beginTransaction().addToBackStack(null).replace(R.id.main_container, libraryFragment).commit();
                    }else{
                        fm.beginTransaction().addToBackStack(null).add(R.id.main_container, libraryFragment).commit();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onStart() {
                    super.onStart();
                    pb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {
                    pb.setVisibility(View.GONE);
                    super.onFinish();
                }

                @Override
                public void onCancel() {
                    pb.setVisibility(View.GONE);
                    super.onCancel();
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                }

            });*/

        }

    }

    public void showFavorite(View view){
        Intent intent = new Intent(getApplicationContext(), FavoritosActivity.class);
        startActivity(intent);
    }

    public void findBookById(String bookId) {

        if(bookId.length()>0)
        {
            RetrofitGetBooks task = new RetrofitGetBooks(this);
            ParamRequest params = new ParamRequest();
            params.id = bookId;
            task.execute(params);

            bookId = bookId.replace(" ", "+");
            String url = "https://www.googleapis.com/books/v1/volumes/";
            url = url + bookId;

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    Book book = new Book(Parcel.obtain());

                    String json = new String(responseBody);

                    try {
                        JSONObject object = new JSONObject(json);

                        String id = object.getString("id");
                        book.setId(id);

                        JSONObject volumeInfo = object.getJSONObject("volumeInfo");

                        String title = volumeInfo.getString("title");
                        //book.setTitle(title);

                        JSONArray authors = volumeInfo.getJSONArray("authors");
                        String author = authors.getString(0);
                        //book.setAuthor(author);

                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                        String imageLink = imageLinks.getString("smallThumbnail");
                        //book.setImage_url(imageLink);

                        String description = volumeInfo.getString("description");
                        //book.setDescription(description);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    BookFragment bookFragment = new BookFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("book", book);
                    bookFragment.setArguments(bundle);

                    android.support.v4.app.Fragment fragmentLoaded = (android.support.v4.app.Fragment) fm.findFragmentById(R.id.main_container);
                    fm = getSupportFragmentManager();

                    if(fragmentLoaded != null) {
                        fm.beginTransaction().addToBackStack(null).replace(R.id.main_container, bookFragment).commit();
                    }else{
                        fm.beginTransaction().addToBackStack(null).add(R.id.main_container, bookFragment).commit();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onStart() {
                    super.onStart();
                    pb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {
                    pb.setVisibility(View.GONE);
                    super.onFinish();
                }

                @Override
                public void onCancel() {
                    pb.setVisibility(View.GONE);
                    super.onCancel();
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                }

            });
        }

    }

    public void onListItemClick(Book book){

        BookFragment bookFragment = new BookFragment();
        Bundle arg = new Bundle();
        arg.putParcelable("book", book);
        bookFragment.setArguments(arg);

        fm = getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).replace(R.id.main_container, bookFragment).commit();
    }


    @Override
    public void onApiResult(List<Book> books) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("bookList", (ArrayList<? extends Parcelable>) books);

        LibraryFragment libraryFragment = new LibraryFragment();

        libraryFragment.setArguments(args);

        android.support.v4.app.Fragment fragmentLoaded = fm.findFragmentById(R.id.main_container);
        fm = getSupportFragmentManager();

        if(fragmentLoaded != null) {
            fm.beginTransaction().addToBackStack(null).replace(R.id.main_container, libraryFragment).commit();
        }else{
            fm.beginTransaction().addToBackStack(null).add(R.id.main_container, libraryFragment).commit();
        }

    }

}
