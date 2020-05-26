package com.example.dulanjali.agroworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.dulanjali.agroworld.Model.Post;
import com.example.dulanjali.agroworld.ViewHolder.PostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Activity_Blog extends AppCompatActivity {

    // views
    FloatingActionButton addPostbtn;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth mAuth;

    FirebaseRecyclerAdapter<Post,PostViewHolder> searchAdapter;
    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__blog);

        //Firebase Database references
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");

        //link views with xml
        addPostbtn= findViewById(R.id.fab);
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchBar);

        //Search bar hint
        materialSearchBar.setHint("Enter Post Title");

        //Search Posts
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for(String search:suggestList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    onStart();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        // button click to add new post
        addPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Blog.this,Activity_Post.class));

            }
        });

        //
        recyclerView = findViewById(R.id.postList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }



    //Search Posts by post title
    private void startSearch(CharSequence text) {

        Query searchByName = mDatabase.orderByChild("title").equalTo(text.toString());
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(searchByName,Post.class)
                .build();


        // Load filtered Post in Recycler view
        searchAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.textUserName.setText(model.getUsername());
                holder.textTime.setText(model.getTime());
                holder.textTitle.setText(model.getTitle());
                holder.textDescription.setText(model.getDescription());
                Picasso.get().load(model.getuDp()).into(holder.uDp);
            }


            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.blog_row,parent,false);
                return new PostViewHolder(itemView);
            }
        };
        searchAdapter.startListening();
        recyclerView.setAdapter(searchAdapter);
    }

    // Load all Posts when open the Blog page
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(mDatabase,Post.class)
                .build();

        final FirebaseRecyclerAdapter<Post, PostViewHolder> adapter =
                new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {


                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.textUserName.setText(model.getUsername());
                        holder.textTime.setText(model.getTime());
                        holder.textTitle.setText(model.getTitle());
                        holder.textDescription.setText(model.getDescription());
                        Picasso.get().load(model.getuDp()).into(holder.uDp);

                    }

                    @NonNull
                    @Override
                    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row,parent,false);
                        PostViewHolder holder = new PostViewHolder(view);
                        return holder;


                    }
                };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
