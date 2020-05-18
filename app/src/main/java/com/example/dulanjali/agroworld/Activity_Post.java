package com.example.dulanjali.agroworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Activity_Post extends AppCompatActivity {

    private String title,description,saveCurrentDate,getSaveCurrentTime;
    private ImageButton mPostImage;
    private MaterialButton addPostBtn;
    private static final int GALLERY_REQUEST = 123;
    private EditText newPostTitle, newPostDesc;
    private String downloadImageUrl;
    private ProgressDialog loadingBar;
    public String postRandomKey;
    private FloatingActionButton addPost;


    private Uri imageData;

    private StorageReference storePostImagestorageRef;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference productDataRef;
    private FirebaseUser fuser;
    private DatabaseReference userDbRef;

    //user info
    String name,email,uid,dp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__post);

        mPostImage = findViewById(R.id.imageSelect);
        addPostBtn = findViewById(R.id.create_list_button);
        newPostTitle=findViewById(R.id.new_note_title);
        newPostDesc=findViewById(R.id.new_note_content);
        loadingBar = new ProgressDialog(this);


        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

        //get some info of current user to include in post
        userDbRef=FirebaseDatabase.getInstance().getReference("users");
        Query query = userDbRef.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    name = ""+ds.child("username").getValue();
                    dp = ""+ds.child("imageURL").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        fuser = FirebaseAuth.getInstance().getCurrentUser();
        storePostImagestorageRef = FirebaseStorage.getInstance().getReference().child("blog_images");
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        productDataRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick an image"),GALLERY_REQUEST);

            }
        });

        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePostData();
            }
        });

    }

    private void checkUserStatus()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null)
        {
            email = user.getEmail();
            uid = user.getUid();
        }
        else
        {
            startActivity(new Intent(this,Activity_Login.class));
            finish();

        }
    }

    private void ValidatePostData()
    {
        title = newPostTitle.getText().toString();
        description = newPostDesc.getText().toString();

        if(imageData==null)
        {
            Toast.makeText(this, "Image is Required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(title))
        {
            Toast.makeText(this, "Product title is required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(description))
        {
            Toast.makeText(this, "Product description is required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StorePostInformation();
        }


    }

    private void StorePostInformation()
    {
        loadingBar.setMessage("Please wait, while we are adding new post");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        getSaveCurrentTime = currentTime.format(calendar.getTime());

        postRandomKey = saveCurrentDate + getSaveCurrentTime;

        final StorageReference filepath = storePostImagestorageRef.child(imageData.getLastPathSegment()+postRandomKey + ".jpg");

        final UploadTask uploadTask = filepath.putFile(imageData);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(Activity_Post.this, "Error" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(Activity_Post.this, "Image uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task <Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(Activity_Post.this, "Post image saved ", Toast.LENGTH_SHORT).show();

                            SavePostInfoToDatabase();
                        }
                    }
                });
            }
        });




    }

    private void SavePostInfoToDatabase()
    {
        HashMap<String,Object> postMap = new HashMap<>();
        postMap.put("uid",uid);
        postMap.put("username",name);
        postMap.put("email",email);
        postMap.put("uDp",dp);
        postMap.put("pid",postRandomKey);
        postMap.put("date",saveCurrentDate);
        postMap.put("time",getSaveCurrentTime);
        postMap.put("description",description);
        postMap.put("image",downloadImageUrl);
        postMap.put("title",title);

        productDataRef.child(postRandomKey).updateChildren(postMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful())
                       {

                           startActivity(new Intent(Activity_Post.this,Activity_Blog.class));
                           loadingBar.dismiss();
                           Toast.makeText(Activity_Post.this, "Post is added successfully", Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           loadingBar.dismiss();
                           String message = task.getException().toString();
                           Toast.makeText(Activity_Post.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                           
                       }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK&& data !=null)
        {
             imageData = data.getData();
            mPostImage.setImageURI(imageData);
        }
    }
}
