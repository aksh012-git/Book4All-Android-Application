package com.example.semester_6;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myCartMyViewHolder extends RecyclerView.ViewHolder
{
    public TextView booknameCart;
    public ImageView imgUrlCart;
    public View viewCart;
    public myCartMyViewHolder(@NonNull View itemView)
    {
        super(itemView);
        booknameCart = itemView.findViewById(R.id.booknameCart);
        imgUrlCart = itemView.findViewById(R.id.bookImgCart);
        viewCart = itemView;
    }
}
