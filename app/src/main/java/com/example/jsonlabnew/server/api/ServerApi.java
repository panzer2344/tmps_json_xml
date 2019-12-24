package com.example.jsonlabnew.server.api;

import com.example.jsonlabnew.model.Book;

import java.math.BigInteger;
import java.util.List;

public interface ServerApi {

    List<Book> getAllBooksJson();

    List<Book> getAllBooksJsonSortedByCode();

    Book getBookJson(BigInteger code);

    void updateBookJson(Book book);

    void addNewBookJson(Book book);

    List<Book> getAllBooksXml();

    List<Book> getAllBooksXmlSortedByCode();

    Book getBookXml(BigInteger code);

    void updateBookXml(Book book);

    void addNewBookXml(Book book);

}
