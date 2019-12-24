package com.example.jsonlabnew.service;

import androidx.core.util.Consumer;

import com.example.jsonlabnew.converter.JsonXmlConverterFactory;
import com.example.jsonlabnew.model.Book;
import com.example.jsonlabnew.model.BooksList;
import com.example.jsonlabnew.server.api.AsyncServerApi;

import java.math.BigInteger;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestService {

    private static RestService instance;
    private static final String BASE_URL = "http://10.0.2.2:8081/";
    private Retrofit retrofit;

    private RestService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(new JsonXmlConverterFactory())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
//                        new Persister(new AnnotationStrategy())))
                .client(client.build())
                .build();
    }

    public AsyncServerApi getAsyncServerApi() {
        return retrofit.create(AsyncServerApi.class);
    }

    public static RestService getInstance() {
        if(instance == null) {
            instance = new RestService();
        }
        return instance;
    }

    public void getAllBooksJson(Consumer<List<Book>> onResponse, Consumer<Throwable> onFailure) {
        getInstance()
                .getAsyncServerApi()
                .getAllBooksJson()
                .enqueue(new DefaultCallback<>(onResponse, onFailure));
    }

    public void getAllBooksJsonSortedByCode(Consumer<List<Book>> onResponse, Consumer<Throwable> onFailure) {
        getInstance()
                .getAsyncServerApi()
                .getAllBooksJsonSortedByCode()
                .enqueue(new DefaultCallback<>(onResponse, onFailure));
    }

    public void getBookJson(BigInteger code, Consumer<Book> onResponse, Consumer<Throwable> onFailure) {
        getInstance()
                .getAsyncServerApi()
                .getBookJson(code)
                .enqueue(new DefaultCallback<>(onResponse, onFailure));
    }

    public void updateBookJson(Book book, Consumer<Void> onResponse, Consumer<Throwable> onFailure) {
        getInstance()
                .getAsyncServerApi()
                .updateBookJson(book)
                .enqueue(new DefaultCallback<>(onResponse, onFailure));
    }

    public void addNewBookJson(Book book, Consumer<Void> onResponse, Consumer<Throwable> onFailure) {
        getInstance()
                .getAsyncServerApi()
                .addNewBookJson(book)
                .enqueue(new DefaultCallback<>(onResponse, onFailure));
    }

    public void getAllBooksXml(Consumer<BooksList> onResponse, Consumer<Throwable> onFailure) {
        getInstance()
                .getAsyncServerApi()
                .getAllBooksXml()
                .enqueue(new DefaultCallback<>(onResponse, onFailure));
    }

    public void getAllBooksXmlSortedByCode(Consumer<BooksList> onResponse, Consumer<Throwable> onFailure) {
        getInstance()
                .getAsyncServerApi()
                .getAllBooksXmlSortedByCode()
                .enqueue(new DefaultCallback<>(onResponse, onFailure));
    }

    public void getBookXml(BigInteger code, Consumer<Book> onResponse, Consumer<Throwable> onFailure) {
        getInstance()
                .getAsyncServerApi()
                .getBookXml(code)
                .enqueue(new DefaultCallback<>(onResponse, onFailure));
    }

    public void updateBookXml(Book book, Consumer<Void> onResponse, Consumer<Throwable> onFailure) {
        getInstance()
                .getAsyncServerApi()
                .updateBookXml(book)
                .enqueue(new DefaultCallback<>(onResponse, onFailure));
    }

    public void addNewBookXml(Book book, Consumer<Void> onResponse, Consumer<Throwable> onFailure) {
        getInstance()
                .getAsyncServerApi()
                .addNewBookXml(book)
                .enqueue(new DefaultCallback<>(onResponse, onFailure));
    }

    public static class DefaultCallback<T> implements Callback<T> {
        private Consumer<T> onResponse;
        private Consumer<Throwable> onFailure;

        public DefaultCallback(Consumer<T> onResponse, Consumer<Throwable> onFailure) {
            this.onResponse = onResponse;
            this.onFailure = onFailure;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            onResponse.accept(response.body());
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            onFailure.accept(t);
        }
    }
}
