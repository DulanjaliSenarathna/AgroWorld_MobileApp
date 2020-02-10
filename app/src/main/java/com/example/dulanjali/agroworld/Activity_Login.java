package com.example.dulanjali.agroworld;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class Activity_Login extends AppCompatActivity {


    private Button register, login;
    private TextInputLayout inputUserName, inputPassword;
    private CheckBox chkBoxRememberMe;
    private TextView forgot_password;

    private String parentDnName = "users";

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__login);

        inputUserName = findViewById(R.id.id_username);
        inputPassword = findViewById(R.id.id_password);
        chkBoxRememberMe = findViewById(R.id.remember);
        register = findViewById(R.id.nav_reg);
        login = findViewById(R.id.btn_login);

        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if(checkbox.equals("true"))
        {
            startActivity(new Intent(Activity_Login.this,Activity_Dashboard.class));

        }

        else if(checkbox.equals("false"))
        {
            Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
        }

        chkBoxRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(Activity_Login.this, "CHECKED", Toast.LENGTH_SHORT).show();
                }

                else if(!buttonView.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(Activity_Login.this, "UNCHECKED", Toast.LENGTH_SHORT).show();

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateUserName() | !validatePassword() ) {
                    return;

                } else {

                    isUser();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this,Activity_Register.class));
            }
        });
    }

    private Boolean validatePassword()
    {
        String val = inputPassword.getEditText().getText().toString();

        if(val.isEmpty())
        {
            inputPassword.setError("Field cannot be empty");
            return false;
        }
        else
        {
            inputPassword.setError(null);
            inputPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUserName()
    {
        String val = inputUserName.getEditText().getText().toString();

        if(val.isEmpty())
        {
            inputUserName.setError("Field cannot be empty");
            return false;
        }
        else
        {
            inputUserName.setError(null);
            inputUserName.setErrorEnabled(false);
            return true;
        }
    }

    private void isUser() {
        final String userEnteredUsername = inputUserName.getEditText().getText().toString().trim();
        final String userEnteredPassword = inputPassword.getEditText().getText().toString().trim();

        reference = FirebaseDatabase.getInstance().getReference().child("users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
         checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if(dataSnapshot.exists())
                 {
                     inputUserName.setError(null);
                     inputUserName.setErrorEnabled(false);

                     String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                     if(passwordFromDB.equals(userEnteredPassword))
                     {
                         inputUserName.setError(null);
                         inputUserName.setErrorEnabled(false);

                         String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                         String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                         String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                         String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);

                         Intent dash = new Intent(Activity_Login.this,Activity_Profile.class);

                         dash.putExtra("name",nameFromDB);
                         dash.putExtra("username",usernameFromDB);
                         dash.putExtra("email",emailFromDB);
                         dash.putExtra("phoneNo",phoneNoFromDB);
                         dash.putExtra("password",passwordFromDB);

                         startActivity(dash);
                     }
                     else
                     {
                         inputPassword.setError("Wrong Password");
                         inputPassword.requestFocus();
                     }
                 }

                 else
                 {
                     inputUserName.setError("No such user exist");
                     inputUserName.requestFocus();
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

    }
}