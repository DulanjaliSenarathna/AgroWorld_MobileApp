package com.example.dulanjali.agroworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dulanjali.agroworld.Model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import static java.security.AccessController.getContext;

public class Activity_Profile extends AppCompatActivity {

    private CircleImageView displayProfileImage;
    private TextInputLayout fullName,userName, phoneNo;
    private TextView fullNameLabel, userNameLabel;
    private Button btnUpdateProfile;

    private final static int Gallery_Pick = 1;
    private StorageReference storeProfileImagestorageRef;

    private DatabaseReference getUserDataReference;
    private FirebaseAuth mAuth;
    private Uri imageUri;
    private StorageTask uploadTask;
    private FirebaseUser fuser;


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
        btnUpdateProfile = findViewById(R.id.btn_update_prof);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        final String online_user_id = mAuth.getCurrentUser().getUid();
        getUserDataReference = FirebaseDatabase.getInstance().getReference().child("users").child(online_user_id);
        storeProfileImagestorageRef = FirebaseStorage.getInstance().getReference().child("profile_images");

        getUserDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                String topname = dataSnapshot.child("user_fullname").getValue().toString();
                String topusername = dataSnapshot.child("user_name").getValue().toString();
                String fullname = dataSnapshot.child("user_fullname").getValue().toString();
                String username = dataSnapshot.child("user_name").getValue().toString();
                String phone = dataSnapshot.child("user_phone").getValue().toString();

                fullNameLabel.setText(topname);
                userNameLabel.setText(topusername);
                fullName.getEditText().setText(fullname);
                userName.getEditText().setText(username);
                phoneNo.getEditText().setText(phone);

                if (user.getImageURL() != null) {
                    if (user.getImageURL().equals("default")) {
                        displayProfileImage.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        Glide.with(Activity_Profile.this).load(user.getImageURL()).into(displayProfileImage);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        displayProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImage();

            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = fullName.getEditText().getText().toString();

                    getUserDataReference.child("user_fullname").setValue(fullname)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(Activity_Profile.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_Profile.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                String username = userName.getEditText().getText().toString();
                getUserDataReference.child("user_name").setValue(username)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Activity_Profile.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(Activity_Profile.this, "Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                String phone = phoneNo.getEditText().getText().toString();
                getUserDataReference.child("user_phone").setValue(phone)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Activity_Profile.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(Activity_Profile.this, "Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }

    private void openImage()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent,Gallery_Pick);
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage()
    {
        if(imageUri !=null)
        {
            final StorageReference fileReference = storeProfileImagestorageRef.child(System.currentTimeMillis()
            +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        getUserDataReference = FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid());
                        HashMap <String,Object> map = new HashMap<>();
                        map.put("imageURL",mUri);
                        getUserDataReference.updateChildren(map);

                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else
        {
            Toast.makeText(getApplicationContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null && data.getData() != null)
        {
            imageUri = data.getData();

            if(uploadTask != null && uploadTask.isInProgress())
            {
                Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            }
            else
            {
                uploadImage();
            }
        }

    }
}