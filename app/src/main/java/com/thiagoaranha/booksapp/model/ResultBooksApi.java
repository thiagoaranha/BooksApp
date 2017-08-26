package com.thiagoaranha.booksapp.model;

import java.util.List;

/**
 * Created by Thiago on 19/08/17.
 */

public class ResultBooksApi {

    private String kind;
    private int totalItems;
    private List<Book> items;

    public ResultBooksApi(String kind, int totalItems, List<Book> items) {
        this.kind = kind;
        this.totalItems = totalItems;
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }
}
