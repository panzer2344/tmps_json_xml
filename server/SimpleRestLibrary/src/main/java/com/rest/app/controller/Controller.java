package com.rest.app.controller;

import com.rest.app.dto.Book;
import com.rest.app.dto.BooksList;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@RestController("/")
public class Controller {

    private Logger logger = Logger.getLogger(Controller.class.getName());

    private HashMap<Integer, Book> books = new HashMap<Integer, Book>() {{
        put(1, new Book(1, "Harry Potter", "Rowling", 1994));
        put(2, new Book(2, "War and peace", "Tolstoi", 1847));
    }};

    @GetMapping(value = "books/json/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book getBookJson(@PathVariable("code") Integer code) {
        logger.info("books/json with code");
        return books.get(code);
    }

    @GetMapping(value = "books/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllBooksJson() {
        logger.info("books/json");
        return new ArrayList<>(books.values());
    }

    @GetMapping(value = "books/xml/{code}", produces = MediaType.APPLICATION_XML_VALUE)
    public Book getBookXml(@PathVariable("code") Integer code) {
        logger.info("books/xml with code");
        return books.get(code);
    }

    @GetMapping(value = "books/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public BooksList getAllBooksXml() {
        logger.info("books/xml");
        return new BooksList( new ArrayList<>(books.values()));
    }

    @PostMapping(value = "books", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity changeBookInMap(@RequestBody Book book) {
        books.put(book.code, book);
        System.out.println(book);
        return ResponseEntity.ok("edited");
    }

}
