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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    private TextInputLayout inputEmail, inputPassword;
    private CheckBox chkBoxRememberMe;
    private TextView forgot_password;

    private String parentDnName = "users";

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__login);

        inputEmail = findViewById(R.id.id_username);
        inputPassword = findViewById(R.id.id_password);
        chkBoxRememberMe = findViewById(R.id.remember);
        register = findViewById(R.id.nav_reg);
        login = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot_pw);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if(checkbox.equals("true"))
        {
            startActivity(new Intent(Activity_Login.this,Activity_Dashboard.class));

        }

        else if(checkbox.equals("false"))
        {
            Toast.makeText(this, "You are not Login", Toast.LENGTH_SHORT).show();
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

                String email = inputEmail.getEditText().getText().toString();
                String password = inputPassword.getEditText().getText().toString();

                if (!validateUserName() | !validatePassword() ) {
                    return;

                } else {
                    loginToUserAccount(email,password);
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this,Activity_Register.class));
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this,Activity_ResetPassword.class));

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
        String val = inputEmail.getEditText().getText().toString();

        if(val.isEmpty())
        {
            inputEmail.setError("Field cannot be empty");
            return false;
        }
        else
        {
            inputEmail.setError(null);
            inputEmail.setErrorEnabled(false);
            return true;
        }
    }

    private void loginToUserAccount(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent logIntent = new Intent(Activity_Login.this,Activity_Dashboard.class);
                            startActivity(logIntent);
                        }

                        else
                        {
                            Toast.makeText(Activity_Login.this, "Please enter Correct Credentials", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }
}