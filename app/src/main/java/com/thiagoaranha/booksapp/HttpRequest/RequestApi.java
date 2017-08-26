package com.thiagoaranha.booksapp.HttpRequest;

import com.thiagoaranha.booksapp.model.Book;
import com.thiagoaranha.booksapp.model.ResultBooksApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Thiago on 18/08/17.
 */

public interface RequestApi {

    @GET("volumes")
    Call<ResultBooksApi> getBooksApi(@Query("q") String q);

    @GET("volumes")
    Call<Book> getBookApi();

}
