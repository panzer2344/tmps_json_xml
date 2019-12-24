package com.example.jsonlabnew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonlabnew.model.Book;
import com.example.jsonlabnew.model.BooksList;
import com.example.jsonlabnew.server.api.ServerApi;
import com.example.jsonlabnew.server.api.StubServerApi;
import com.example.jsonlabnew.service.RestService;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DisplayAllBooksActivity extends AppCompatActivity {

    public static final Logger logger = Logger.getLogger(DisplayAllBooksActivity.class.getName());

    //private ServerApi serverApi;
    private RecyclerView recyclerView;
    private RestService restService;
    private DisplayAllBooksAdapter displayAllBooksAdapter;

    public enum Switcher {
        JSON, XML;
    }

    private Switch jsonXmlSwitch;
    private static Switcher jsonXmlSwitcherValue = Switcher.JSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_books);

        //serverApi = new StubServerApi();
        restService = RestService.getInstance();
        recyclerView = findViewById(R.id.booksListView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jsonXmlSwitch = findViewById(R.id.jsonXmlSwitch);
        jsonXmlSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                jsonXmlSwitcherValue = isChecked ? Switcher.XML : Switcher.JSON;
            }
        });

        update();
    }

    public void update() {
        displayAllBooksAdapter = new DisplayAllBooksAdapter(new Book[0]);
        recyclerView.setAdapter(displayAllBooksAdapter);
    }

    public void addNew(View view) {
        Intent intent = new Intent(this, ChangeBookActivity.class);
        intent.putExtra(ChangeBookActivity.ACTIVITY_TYPE_EXTRA_MESSAGE, ChangeBookActivity.ActivityType.ADD.toString());
        intent.putExtra(ChangeBookActivity.BOOK_CODE_EXTRA_MESSAGE, BigInteger.ZERO.toString());
        intent.putExtra(ChangeBookActivity.SWITCHER_VALUE_EXTRA_MESSAGE, jsonXmlSwitcherValue.toString());
        startActivity(intent);
    }

    public void reloadData(View view) {
        if(jsonXmlSwitcherValue.equals(Switcher.JSON)) {
            restService.getAllBooksJsonSortedByCode(
                    new Consumer<List<Book>>() {
                        @Override
                        public void accept(List<Book> books) {
                            if (books != null) {
                                Log.i("DISPLAY_ALL_BOOKS", books.toString());
                                displayAllBooksAdapter.changeData(books);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            if (throwable != null)
                                Log.e("DISPLAY_ALL_BOOKS_ERROR",
                                        throwable.getMessage(),
                                        throwable);
                        }
                    }
            );
        } else {
            restService.getAllBooksXmlSortedByCode(
                    new Consumer<BooksList>() {
                        @Override
                        public void accept(BooksList books) {
                            if (books != null) {
                                Log.i("DISPLAY_ALL_BOOKS", books.toString());
                                displayAllBooksAdapter.changeData(books.getBookList());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            if (throwable != null)
                                Log.e("DISPLAY_ALL_BOOKS_ERROR",
                                        throwable.getMessage(),
                                        throwable);
                        }
                    }
            );
        }
    }

    public static class DisplayAllBooksAdapter
            extends RecyclerView.Adapter<DisplayAllBooksAdapter.DisplayAllBookViewHolder> {
        private Book[] books;

        public DisplayAllBooksAdapter(Book[] books) {
            this.books = books;
            logger.log(Level.ALL, books.toString());
        }

        public static class DisplayAllBookViewHolder extends RecyclerView.ViewHolder
                                                     implements View.OnClickListener {
            private Context context;

            private RestService restService;

            private TextView codeTextView;
            private TextView nameTextView;
            private TextView authorTextView;
            private TextView publishYearTextView;

            private Button changeBookBtn;
            private Button reloadBookBtn;

            public DisplayAllBookViewHolder(@NonNull View itemView) {
                super(itemView);

                context = itemView.getContext();

                restService = RestService.getInstance();

                codeTextView = itemView.findViewById(R.id.codeTextView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                authorTextView = itemView.findViewById(R.id.authorTextView);
                publishYearTextView = itemView.findViewById(R.id.publishYearTextView);

                changeBookBtn = itemView.findViewById(R.id.changeBtn);
                reloadBookBtn = itemView.findViewById(R.id.reloadBtn);

                changeBookBtn.setOnClickListener(this);
                reloadBookBtn.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.changeBtn) {
                    Intent intent = new Intent(context, ChangeBookActivity.class);
                    intent.putExtra(ChangeBookActivity.BOOK_CODE_EXTRA_MESSAGE, codeTextView.getText());
                    intent.putExtra(ChangeBookActivity.ACTIVITY_TYPE_EXTRA_MESSAGE, ChangeBookActivity.ActivityType.CHANGE.toString());
                    intent.putExtra(ChangeBookActivity.SWITCHER_VALUE_EXTRA_MESSAGE, jsonXmlSwitcherValue.toString());
                    context.startActivity(intent);

                } else if(v.getId() == R.id.reloadBtn) {
                    if(jsonXmlSwitcherValue.equals(Switcher.JSON)) {

                        restService.getBookJson(new BigInteger(codeTextView.getText().toString()),
                                new Consumer<Book>() {
                                    @Override
                                    public void accept(Book book) {
                                        if(book != null) {
                                            codeTextView.setText(getOrDefault(book.getCode(), ""));
                                            nameTextView.setText(book.getName());
                                            authorTextView.setText(book.getAuthor());
                                            publishYearTextView.setText(getOrDefault(book.getPublishYear(), ""));
                                        }
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) {
                                        Log.i("DisplayAllBookViewHolder", "reloadBtn.onClick");
                                    }
                                }
                        );
                    } else {
                        restService.getBookXml(new BigInteger(codeTextView.getText().toString()),
                                new Consumer<Book>() {
                                    @Override
                                    public void accept(Book book) {
                                        if(book != null) {
                                            codeTextView.setText(getOrDefault(book.getCode(), ""));
                                            nameTextView.setText(book.getName());
                                            authorTextView.setText(book.getAuthor());
                                            publishYearTextView.setText(getOrDefault(book.getPublishYear(), ""));
                                        }
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) {
                                        Log.i("DisplayAllBookViewHolder", "reloadBtn.onClick");
                                    }
                                }
                        );
                    }
                }
            }
        }

        @NonNull
        @Override
        public DisplayAllBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_display_book, parent, false);

            return new DisplayAllBookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DisplayAllBookViewHolder holder, int position) {
            holder.codeTextView.setText(getOrDefault(books[position].getCode(), ""));
            holder.nameTextView.setText(books[position].getName());
            holder.authorTextView.setText(books[position].getAuthor());
            holder.publishYearTextView.setText(getOrDefault(books[position].getPublishYear(), ""));
        }

        @Override
        public int getItemCount() {
            return books.length;
        }

        public void changeData(List<Book> books) {
            if(books != null) {
                this.books = books.toArray(new Book[0]);
                notifyDataSetChanged();
            }
        }

        private static String getOrDefault(BigInteger value, String defaultValue) {
            return value == null ? defaultValue : value.toString();
        }
    }
}
