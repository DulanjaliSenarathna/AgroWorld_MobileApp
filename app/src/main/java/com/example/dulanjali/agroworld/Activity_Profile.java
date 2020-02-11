package com.example.dulanjali.agroworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_Profile extends AppCompatActivity {

    private CircleImageView displayProfileImage;
    private TextInputLayout fullName,userName, phoneNo;
    private TextView fullNameLabel, userNameLabel;

    private final static int Gallery_Pick = 1;

    private DatabaseReference getUserDataReference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__profile);

        fullName = findViewById(R.id.prof_name);
        userName = findViewById(R.id.prof_usrname);
        phoneNo = findViewById(R.id.prof_phone);
        fullNameLabel = findViewById(R.id.full_name);
        userNameLabel = findViewById(R.id.user_name);
        displayProfileImage = findViewById(R.id.profile_img);

        mAuth = FirebaseAuth.getInstance();
        String online_user_id = mAuth.getCurrentUser().getUid();
        getUserDataReference = FirebaseDatabase.getInstance().getReference().child("users").child(online_user_id);

        getUserDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String topname = dataSnapshot.child("user_fullname").getValue().toString();
                String topusername = dataSnapshot.child("user_name").getValue().toString();
                String fullname = dataSnapshot.child("user_fullname").getValue().toString();
                String username = dataSnapshot.child("user_name").getValue().toString();
                String phone = dataSnapshot.child("user_phone").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();
                String thumb_image = dataSnapshot.child("user_thumb_image").getValue().toString();

                fullNameLabel.setText(topname);
                userNameLabel.setText(topusername);
                fullName.getEditText().setText(fullname);
                userName.getEditText().setText(username);
                phoneNo.getEditText().setText(phone);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        displayProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Pick);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
}