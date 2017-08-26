package com.thiagoaranha.booksapp.HttpRequest;

import android.os.AsyncTask;

import com.thiagoaranha.booksapp.model.Book;
import com.thiagoaranha.booksapp.model.ResultBooksApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thiago on 18/08/17.
 */

public class RetrofitGetBooks extends AsyncTask<ParamRequest, Void, List<Book>> {

    public static final String BASE_URL = "https://www.googleapis.com/books/v1/";
    GetNewApiListener getNewApiListener;

    public RetrofitGetBooks(GetNewApiListener listener){
        getNewApiListener = listener;
    }

    @Override
    protected List<Book> doInBackground(ParamRequest... param) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestApi api = retrofit.create(RequestApi.class);
        ParamRequest parameter = param[0];

        List<Book> books = new ArrayList<Book>();

        try {
            Response<ResultBooksApi> response = api.getBooksApi(parameter.q).execute();
            books = response.body().getItems();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  books;
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);

        if(getNewApiListener != null) {
            getNewApiListener.onApiResult(books);
        }
    }

    public interface GetNewApiListener{
        void  onApiResult(List<Book> books);
    }

}
