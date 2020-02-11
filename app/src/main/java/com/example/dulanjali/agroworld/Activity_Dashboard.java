package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Dashboard extends AppCompatActivity {

    private Button logout;
    private ImageView updateProfile,blog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dashboard);

        mAuth = FirebaseAuth.getInstance();

        logout = findViewById(R.id.log_out);
        updateProfile = findViewById(R.id.update_profile);
        blog=findViewById(R.id.blog);

        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logOut();
                        startActivity(new Intent(Activity_Dashboard.this,Activity_Login.class));

                        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember","false");
                        editor.apply();

                        finish();
                    }
                }
        );

        updateProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Activity_Dashboard.this,Activity_Profile.class));
                    }
                }
        );

        blog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Activity_Dashboard.this,Activity_Blog.class));
                    }
                }
        );
    }

    private void logOut()
    {
        sendToLogin();
    }

    private void sendToLogin()
    {
        Intent intent = new Intent(Activity_Dashboard.this, Activity_Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null)
        {
            Intent loginPageIntent = new Intent(Activity_Dashboard.this,Activity_Login.class);
            loginPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginPageIntent);
            finish();

        }

    }
}
