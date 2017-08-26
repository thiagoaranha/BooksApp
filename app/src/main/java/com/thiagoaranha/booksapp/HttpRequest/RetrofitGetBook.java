package com.thiagoaranha.booksapp.HttpRequest;

import android.os.AsyncTask;

import com.thiagoaranha.booksapp.model.Book;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thiago on 19/08/17.
 */

public class RetrofitGetBook  extends AsyncTask<ParamRequest, Void, Book> {

    public static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes/";
    RetrofitGetBook.GetNewApiListener getNewApiListener;

    public RetrofitGetBook(RetrofitGetBook.GetNewApiListener listener){
        getNewApiListener = listener;
    }

    @Override
    protected Book doInBackground(ParamRequest... param) {
        ParamRequest parameter = param[0];

        String url = BASE_URL + parameter.id + "/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestApi api = retrofit.create(RequestApi.class);

        Book book = null;

        try {
            Response<Book> response = api.getBookApi().execute();
            book = response.body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  book;
    }

    @Override
    protected void onPostExecute(Book book) {
        super.onPostExecute(book);

        if(getNewApiListener != null) {
            getNewApiListener.onApiResult(book);
        }
    }

    public interface GetNewApiListener{
        void  onApiResult(Book book);
    }
}
