package com.example.jsonlabnew.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false, name = "books")
public class BooksList {

    @ElementList(name = "book", inline = true)
    private List<Book> bookList;

    public BooksList() {}

    public BooksList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
