package com.example.jsonlabnew.server.api;

import com.example.jsonlabnew.model.Book;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StubServerApi implements ServerApi {

    private HashMap<BigInteger, Book> stubBooks = new HashMap<BigInteger, Book>(){{
        put(BigInteger.valueOf(1L),
                new Book(
                        BigInteger.valueOf(1L),
                        "firstName",
                        "firstAuthor",
                        BigInteger.valueOf(1994L)));
        put(BigInteger.valueOf(2L),
                new Book(
                        BigInteger.valueOf(2L),
                        "secondName",
                        "secondAuthor",
                        BigInteger.valueOf(1995L)));
        put(BigInteger.valueOf(3L),
                new Book(
                        BigInteger.valueOf(3L),
                        "thirdName",
                        "thirdAuthor",
                        BigInteger.valueOf(1996L)));
        put(BigInteger.valueOf(4L),
                new Book(
                        BigInteger.valueOf(4L),
                        "fourthName",
                        "fourthAuthor",
                        BigInteger.valueOf(1997L)));
        put(BigInteger.valueOf(5L),
                new Book(
                        BigInteger.valueOf(5L),
                        "fourthName5",
                        "fourthAuthor5",
                        BigInteger.valueOf(19975L)));
        put(BigInteger.valueOf(6L),
                new Book(
                        BigInteger.valueOf(6L),
                        "fourthName6",
                        "fourthAuthor6",
                        BigInteger.valueOf(19976L)));
        put(BigInteger.valueOf(7L),
                new Book(
                        BigInteger.valueOf(7L),
                        "fourthName7",
                        "fourthAuthor7",
                        BigInteger.valueOf(19977L)));
    }};

    @Override
    public List<Book> getAllBooksJson() {
        return new LinkedList<>(stubBooks.values());
    }

    @Override
    public Book getBookJson(BigInteger code) {
        return new Book(stubBooks.get(code));
    }

    @Override
    public void updateBookJson(Book book) {
        stubBooks.put(book.getCode(), book);
    }

    @Override
    public void addNewBookJson(Book book) {
        stubBooks.put(book.getCode(), book);
    }

    @Override
    public List<Book> getAllBooksXml() {
        return new LinkedList<>(stubBooks.values());
    }

    @Override
    public Book getBookXml(BigInteger code) {
        return new Book(stubBooks.get(code));
    }

    @Override
    public void updateBookXml(Book book) {
        stubBooks.put(book.getCode(), book);
    }

    @Override
    public void addNewBookXml(Book book) {
        stubBooks.put(book.getCode(), book);
    }

    @Override
    public List<Book> getAllBooksJsonSortedByCode() {
        return stubBooks.values()
                .stream()
                .sorted(new Comparator<Book>() {
                    @Override
                    public int compare(Book b1, Book b2) {
                        return b1.getCode().compareTo(b2.getCode());
                    }
                })
                .collect(Collectors.<Book>toList());
    }

    @Override
    public List<Book> getAllBooksXmlSortedByCode() {
        return stubBooks.values()
                .stream()
                .sorted(new Comparator<Book>() {
                    @Override
                    public int compare(Book b1, Book b2) {
                        return b1.getCode().compareTo(b2.getCode());
                    }
                })
                .collect(Collectors.<Book>toList());
    }
}
