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
    TextInputLayout inputName,inputUserName,inputEmail,inputPhoneNo,inputPassword;
    MaterialButton btnRegister;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register);

        btnLogin = findViewById(R.id.nav_login);
        inputName = findViewById(R.id.reg_fullname);
        inputUserName = findViewById(R.id.reg_username);
        inputEmail = findViewById(R.id.reg_email);
        inputPhoneNo = findViewById(R.id.reg_phone);
        inputPassword = findViewById(R.id.reg_password);
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

                String name = inputName.getEditText().getText().toString();
                String username = inputUserName.getEditText().getText().toString();
                String email = inputEmail.getEditText().getText().toString();
                String phoneNo = inputPhoneNo.getEditText().getText().toString();
                String password = inputPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name,username,email,phoneNo,password);

                reference.child(username).setValue(helperClass);

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

        String val = inputName.getEditText().getText().toString();

        if(val.isEmpty())
        {
            inputName.setError("Field cannot be empty");
            return false;
        }
        else
        {
            inputName.setError(null);
            inputName.setErrorEnabled(false);
            return true;
        }
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

    private Boolean validatePhone()
    {
        String val = inputPhoneNo.getEditText().getText().toString();

        if(val.isEmpty())
        {
            inputPhoneNo.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()>10)
        {
            inputPhoneNo.setError("Invalid Phone");
            return false;
        }
        else if(val.length()< 10)
        {
            inputPhoneNo.setError("Invalid Phone");
            return false;
        }
        else
        {
            inputPhoneNo.setError(null);
            inputPhoneNo.setErrorEnabled(false);
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
