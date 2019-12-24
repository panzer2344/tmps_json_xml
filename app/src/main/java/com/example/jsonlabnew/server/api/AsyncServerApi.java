package com.example.jsonlabnew.server.api;

import com.example.jsonlabnew.converter.annotation.Json;
import com.example.jsonlabnew.converter.annotation.Xml;
import com.example.jsonlabnew.model.Book;
import com.example.jsonlabnew.model.BooksList;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AsyncServerApi {

    @GET("/books/json") @Json
    Call<List<Book>> getAllBooksJson();

    @GET("/books/json") @Json
    Call<List<Book>> getAllBooksJsonSortedByCode();

    @GET("/books/json/{code}") @Json
    Call<Book> getBookJson(@Path("code") BigInteger code);

    @POST("/books") @Json
    Call<Void> updateBookJson(@Body Book book);

    @PUT("/books") @Json
    Call<Void> addNewBookJson(@Body Book book);

    @GET("/books/xml") @Xml @Headers("Accept: application/xml")
    Call<BooksList> getAllBooksXml();

    @GET("/books/xml") @Xml @Headers("Accept: application/xml")
    Call<BooksList> getAllBooksXmlSortedByCode();

    @GET("/books/xml/{code}") @Xml @Headers("Accept: application/xml")
    Call<Book> getBookXml(@Path("code") BigInteger code);

    @POST("/books") @Xml
    Call<Void> updateBookXml(@Body Book book);

    @PUT("/books") @Xml
    Call<Void> addNewBookXml(@Body Book book);

}
