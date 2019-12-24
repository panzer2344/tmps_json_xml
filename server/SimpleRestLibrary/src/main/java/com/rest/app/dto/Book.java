package com.rest.app.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "book")
public class Book {
    public Integer code;
    public String name;
    public String author;
    public Integer publishYear;

    public Book(Integer code, String name, String author, Integer publishYear) {
        this.code = code;
        this.name = name;
        this.author = author;
        this.publishYear = publishYear;
    }

    @Override
    public String toString() {
        return "Book{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publishYear=" + publishYear +
                '}';
    }
}
