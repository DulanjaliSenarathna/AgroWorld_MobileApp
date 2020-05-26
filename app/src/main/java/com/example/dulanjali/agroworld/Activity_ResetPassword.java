package com.example.dulanjali.agroworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_ResetPassword extends AppCompatActivity {

    //Views
    TextInputLayout reset_email;
    MaterialButton reset_button;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__reset_password);

        //link views with xml
        reset_email = findViewById(R.id.reset_email);
        reset_button = findViewById(R.id.reset_button);

        firebaseAuth = FirebaseAuth.getInstance();

        //button click to send link to Email
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = reset_email.getEditText().getText().toString();

                if(email.equals(""))
                {
                    Toast.makeText(Activity_ResetPassword.this, "Please enter a valid email ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()
                            ) {
                                Toast.makeText(Activity_ResetPassword.this, "Please check your email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_ResetPassword.this,Activity_Login.class));
                            }
                            else
                            {
                                String error = task.getException().getMessage();
                                Toast.makeText(Activity_ResetPassword.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
