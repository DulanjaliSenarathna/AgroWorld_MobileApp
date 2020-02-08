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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class Activity_Login extends AppCompatActivity {


    private Button register,login;
    private TextInputEditText email,password;
    private CheckBox chkBoxRememberMe;

    private FirebaseAuth mAuth;

    private ProgressBar loginProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__login);

        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.nav_reg);
        login = findViewById(R.id.btn_login);
        email = findViewById(R.id.mail);
        password = findViewById(R.id.passwrd);
        loginProgress = findViewById(R.id.progressBar);
        chkBoxRememberMe = findViewById(R.id.remember);

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

        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Activity_Login.this,Activity_Register.class));
                    }
                }
        );

        login.setOnClickListener(
                new View.OnClickListener() {

                   @Override
                    public void onClick(View v) {


                       String loginEmail= email.getText().toString();
                       String loginPwd= password.getText().toString();

                       if(!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPwd))
                       {
                           loginProgress.setVisibility(View.VISIBLE);

                           mAuth.signInWithEmailAndPassword(loginEmail,loginPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {

                                   if(task.isSuccessful())
                                   {
                                        sendToMain();
                                   }

                                   else
                                   {
                                       String errorMessage = task.getException().getMessage();
                                       Toast.makeText(Activity_Login.this, "Error : "+errorMessage,Toast.LENGTH_LONG).show();
                                   }

                                   loginProgress.setVisibility(View.INVISIBLE);

                               }
                           });
                       }

                    }
                }
        );

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
           sendToMain();
        }
    }

    private void sendToMain()
    {
        Intent mainIntent = new Intent(Activity_Login.this,Activity_Dashboard.class);
        startActivity(mainIntent);
        finish();
    }
}
