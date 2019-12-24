package com.example.jsonlabnew.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.math.BigInteger;
import java.util.Objects;

@Root(strict = false, name = "book")
public class Book {

    @SerializedName("code") @Expose @Element
    private BigInteger code;
    @SerializedName("name") @Expose @Element
    private String name;
    @SerializedName("author") @Expose @Element
    private String author;
    @SerializedName("publishYear") @Expose @Element
    private BigInteger publishYear;

    public Book() {}

    public Book(BigInteger code, String name, String author, BigInteger publishYear) {
        this.code = code;
        this.name = name;
        this.author = author;
        this.publishYear = publishYear;
    }

    public Book(Book another) {
        this.code = another.code;
        this.name = another.name;
        this.author = another.author;
        this.publishYear = another.publishYear;
    }

    public BigInteger getCode() {
        return code;
    }

    public void setCode(BigInteger code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigInteger getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(BigInteger publishYear) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getCode(), book.getCode()) &&
                Objects.equals(getName(), book.getName()) &&
                Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getPublishYear(), book.getPublishYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getName(), getAuthor(), getPublishYear());
    }
}
