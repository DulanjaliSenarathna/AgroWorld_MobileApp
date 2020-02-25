package com.example.dulanjali.agroworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.dulanjali.agroworld.Model.Post;
import com.example.dulanjali.agroworld.ViewHolder.PostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Activity_Blog extends AppCompatActivity {

    FloatingActionButton addPostbtn;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__blog);


        addPostbtn = findViewById(R.id.floatingActionButton);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");


        addPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Blog.this,Activity_Post.class));

            }
        });

        recyclerView = findViewById(R.id.postList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(mDatabase,Post.class)
                .build();

        FirebaseRecyclerAdapter<Post, PostViewHolder> adapter =
                new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {

                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.textUserName.setText(model.getUser_name());
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
