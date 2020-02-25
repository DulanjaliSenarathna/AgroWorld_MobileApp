package com.example.dulanjali.agroworld.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.example.dulanjali.agroworld.Interface.ItemClickListner;
import com.example.dulanjali.agroworld.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CircularImageView uDp;
    public TextView textUserName, textTime,textTitle, textDescription;
    public ImageView imageView;
    public ItemClickListner listner;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        uDp = (CircularImageView)itemView.findViewById(R.id.uPicture);
        imageView = (ImageView) itemView.findViewById(R.id.pImage);
        textUserName = (TextView)itemView.findViewById(R.id.uName);
        textTime = (TextView)itemView.findViewById(R.id.time);
        textTitle = (TextView)itemView.findViewById(R.id.pTitle);
        textDescription= (TextView)itemView.findViewById(R.id.pDescription);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(),false);
    }
}
