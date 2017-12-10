package info.diepnguyen.locationtracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class BookList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference bookList;

    FirebaseRecyclerAdapter<Book,BookListViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Firebase
        database = FirebaseDatabase.getInstance();
        bookList = database.getReference("book");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_book);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadListBook();

    }

    private void loadListBook() {
        adapter = new FirebaseRecyclerAdapter<Book, BookListViewHolder>(Book.class
                ,R.layout.book_item
                ,BookListViewHolder.class
                ,bookList) {
            @Override
            protected void populateViewHolder(BookListViewHolder viewHolder, Book model, int position) {
                viewHolder.bookName.setText(model.getBookName());
                viewHolder.authorName.setText(model.getAuthorName());
                viewHolder.userEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                Picasso.with(getBaseContext()).load(model.getPhotoURL()).into(viewHolder.bookImg);
                final Book local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(BookList.this,""+local.getBookName(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };

        //Set adapter
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                returnMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnMenu() {
        Intent intent = new Intent(BookList.this,MenuActivity.class);
        startActivity(intent);
    }
}
