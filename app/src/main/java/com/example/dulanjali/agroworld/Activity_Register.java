package com.example.dulanjali.agroworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_Register extends AppCompatActivity {

    Button btnLogin;
    TextInputLayout inputName,inputUserName,inputEmail,inputPhoneNo,inputPassword;
    MaterialButton btnRegister;

    private FirebaseAuth mAuth;
    private DatabaseReference storeUserDefaultReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register);

        btnLogin = findViewById(R.id.nav_login);
        inputUserName = findViewById(R.id.reg_username);
        inputEmail = findViewById(R.id.reg_email);
        inputPassword = findViewById(R.id.reg_password);
        btnRegister = findViewById(R.id.reg_button);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateUserName() | !validateEmail() | !validatePassword() )
                {
                    return;
                }

               final String name = inputUserName.getEditText().getText().toString();
                String email = inputEmail.getEditText().getText().toString();
                String password = inputPassword.getEditText().getText().toString();

                RegisterAccount(name,email,password);

            }
        });

        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Activity_Register.this,Activity_Login.class));
                    }
                }
        );
    }

    private void RegisterAccount(final String name, String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            String current_user_id = mAuth.getCurrentUser().getUid();
                            storeUserDefaultReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_user_id);

                            storeUserDefaultReference.child("user_name").setValue(name);
                            storeUserDefaultReference.child("user_image").setValue("user");
                            storeUserDefaultReference.child("user_thumb_image").setValue("user_image")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Intent regIntent = new Intent(Activity_Register.this,Activity_Login.class);
                                                regIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(regIntent);
                                                finish();
                                            }

                                        }
                                    });


                        }

                    }
                });

    }

    private Boolean validateUserName()
    {
        String val = inputUserName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty())
        {
            inputUserName.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()>=15)
        {
            inputUserName.setError("Username is too long");
            return false;
        }
        else if(!val.matches(noWhiteSpace))
        {
            inputUserName.setError("White Spaces are not allowed");
            return false;
        }
        else
        {
            inputUserName.setError(null);
            inputUserName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail()
    {
        String val = inputEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty())
        {
            inputEmail.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(emailPattern))
        {
            inputEmail.setError("Invalid Email");
            return false;
        }
        else
        {
            inputEmail.setError(null);
            inputEmail.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validatePassword()
    {
        String val = inputPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}"+
                "$";


        if(val.isEmpty())
        {
            inputPassword.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(passwordVal))
        {
            inputPassword.setError("Password is too weak");
            return false;
        }
        else
        {
            inputPassword.setError(null);
            inputPassword.setErrorEnabled(false);
            return true;
        }
    }


}
