package com.example.jsonlabnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.jsonlabnew.model.Book;
import com.example.jsonlabnew.service.RestService;

import java.math.BigInteger;

public class ChangeBookActivity extends AppCompatActivity {

    public static final String BOOK_CODE_EXTRA_MESSAGE = "BOOK_CODE_EXTRA_MESSAGE";
    public static final String ACTIVITY_TYPE_EXTRA_MESSAGE = "ACTIVITY_TYPE_EXTRA_MESSAGE";
    public static final String SWITCHER_VALUE_EXTRA_MESSAGE = "SWITCHER_VALUE_EXTRA_MESSAGE";

    public enum ActivityType {
        ADD, CHANGE
    }

    private ActivityType activityType;
    private BigInteger bookCode;
    private DisplayAllBooksActivity.Switcher jsonXmlSwithcerValue;

    private RestService restService = RestService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_book);

        bookCode = new BigInteger(getIntent().getStringExtra(BOOK_CODE_EXTRA_MESSAGE));
        activityType = ActivityType.valueOf(getIntent().getStringExtra(ACTIVITY_TYPE_EXTRA_MESSAGE));
        jsonXmlSwithcerValue = DisplayAllBooksActivity.Switcher.valueOf(getIntent().getStringExtra(SWITCHER_VALUE_EXTRA_MESSAGE));
    }

    public void onCancel(View view) {
        Intent intent = new Intent(this, DisplayAllBooksActivity.class);
        startActivity(intent);
    }

    public void onSend(View view) {
        EditText nameTextBox = findViewById(R.id.nameTextBox);
        EditText authorTextBox = findViewById(R.id.authorTextBox);
        EditText publisherYearTextBox = findViewById(R.id.publishYearTextBox);

        String name = nameTextBox.getText().toString();
        String author = authorTextBox.getText().toString();
        String publisherYearString = publisherYearTextBox.getText().toString();

        BigInteger publisherYear;
        if(publisherYearString != null && "" != publisherYearString) {
            publisherYear = new BigInteger(publisherYearString);
        } else {
            publisherYear = BigInteger.ZERO;
        }


        if(activityType != null ) {

            if(ActivityType.CHANGE.equals(activityType)) {

                if(DisplayAllBooksActivity.Switcher.JSON.equals(jsonXmlSwithcerValue)) {
                    restService.updateBookJson(new Book(bookCode, name, author, publisherYear),
                            new Consumer<Void>() {
                                @Override
                                public void accept(Void aVoid) {
                                }
                            },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) {
                                    Log.i("ChangeBookActivity", "onSend" + throwable.getMessage(), throwable);
                                }
                            }
                    );
                } else {
                    restService.updateBookXml(new Book(bookCode, name, author, publisherYear),
                            new Consumer<Void>() {
                                @Override
                                public void accept(Void aVoid) {
                                }
                            },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) {
                                    Log.i("ChangeBookActivity", "onSend" + throwable.getMessage(), throwable);
                                }
                            }
                    );
                }

            } else {

                if(DisplayAllBooksActivity.Switcher.JSON.equals(jsonXmlSwithcerValue)) {
                    restService.addNewBookJson(new Book(bookCode, name, author, publisherYear),
                            new Consumer<Void>() {
                                @Override
                                public void accept(Void aVoid) {
                                }
                            },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) {
                                    Log.i("ChangeBookActivity", "onSend" + throwable.getMessage(), throwable);
                                }
                            }
                    );

                } else {
                    restService.addNewBookXml(new Book(bookCode, name, author, publisherYear),
                            new Consumer<Void>() {
                                @Override
                                public void accept(Void aVoid) {
                                }
                            },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) {
                                    Log.i("ChangeBookActivity", "onSend" + throwable.getMessage(), throwable);
                                }
                            }
                    );
                }

            }

        }
    }


}
