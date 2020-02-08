package com.example.dulanjali.agroworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_Register extends AppCompatActivity {

    Button btnLogin;
    TextInputLayout regName,regUserName,regEmail,regPhoneNo,regPassword;
    MaterialButton btnRegister;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register);

        btnLogin = findViewById(R.id.nav_login);
        regName = findViewById(R.id.reg_fullname);
        regUserName = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phone);
        regPassword = findViewById(R.id.reg_password);
        btnRegister = findViewById(R.id.reg_button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateName() | !validateUserName() | !validateEmail() | !validatePhone() | !validatePassword() )
                {
                    return;
                }

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                String name = regName.getEditText().getText().toString();
                String userName = regUserName.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name,userName,email,phoneNo,password);

                reference.child(phoneNo).setValue(helperClass);

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

    private Boolean validateName()
    {
        String val = regName.getEditText().getText().toString();

        if(val.isEmpty())
        {
            regName.setError("Field cannot be empty");
            return false;
        }
        else
        {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUserName()
    {
        String val = regUserName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty())
        {
            regUserName.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()>=15)
        {
            regUserName.setError("Username is too long");
            return false;
        }
        else if(!val.matches(noWhiteSpace))
        {
            regUserName.setError("White Spaces are not allowed");
            return false;
        }
        else
        {
            regUserName.setError(null);
            regUserName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail()
    {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty())
        {
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(emailPattern))
        {
            regEmail.setError("Invalid Email");
            return false;
        }
        else
        {
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePhone()
    {
        String val = regPhoneNo.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty())
        {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        }
        else
        {
            regPhoneNo.setError(null);
            return true;
        }
    }

    private Boolean validatePassword()
    {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}"+
                "$";


        if(val.isEmpty())
        {
            regPassword.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(passwordVal))
        {
            regPassword.setError("Password is too weak");
            return false;
        }
        else
        {
            regPassword.setError(null);
            return true;
        }
    }




}
