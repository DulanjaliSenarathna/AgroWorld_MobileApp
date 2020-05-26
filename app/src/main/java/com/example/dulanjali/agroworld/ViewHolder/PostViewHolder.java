package com.example.dulanjali.agroworld.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.example.dulanjali.agroworld.Interface.ItemClickListener;
import com.example.dulanjali.agroworld.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // views
    public CircularImageView uDp;
    public TextView textUserName, textTime,textTitle, textDescription;
    public ImageView imageView;
    public ItemClickListener listner;



    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        //link views with xml
        uDp = (CircularImageView)itemView.findViewById(R.id.uPicture);
        imageView = (ImageView) itemView.findViewById(R.id.pImage);
        textUserName = (TextView)itemView.findViewById(R.id.uName);
        textTime = (TextView)itemView.findViewById(R.id.time);
        textTitle = (TextView)itemView.findViewById(R.id.pTitle);
        textDescription= (TextView)itemView.findViewById(R.id.pDescription);
    }

    // link with Interface
    public void setItemClickListner(ItemClickListener listner)
    {
        this.listner = listner;
    }

    // onclick method
    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(),false);
    }
}
