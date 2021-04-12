package com.example.semester_6;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myCartMyViewHolder extends RecyclerView.ViewHolder
{
    public TextView booknameCart,booksellingCartrow,bookrentingCartrow,booklocationCartrow;
    public ImageView imgUrlCart,deleteincart;
    public View viewCart;
    public myCartMyViewHolder(@NonNull View itemView)
    {
        super(itemView);
        booknameCart = itemView.findViewById(R.id.booknameCart);
        imgUrlCart = itemView.findViewById(R.id.bookImgCart);
        deleteincart = itemView.findViewById(R.id.deleteInCart);
        booksellingCartrow = itemView.findViewById(R.id.sellingPriceCartrow);
        bookrentingCartrow = itemView.findViewById(R.id.rentingPriceCartrow);
        booklocationCartrow = itemView.findViewById(R.id.locationCartrow);
        viewCart = itemView;
    }
}
