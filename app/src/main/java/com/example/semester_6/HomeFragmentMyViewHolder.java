package com.example.semester_6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class HomeFragmentMyViewHolder extends RecyclerView.ViewHolder
{
    public TextView bookname,booksellingHomerow,bookrentingHomerow,booklocationHomerow;
    public ImageView imgUrl,myCartAdd;
    public View view;
    public HomeFragmentMyViewHolder(@NonNull View itemView)
    {
        super(itemView);
        bookname = itemView.findViewById(R.id.bookname);
        imgUrl = itemView.findViewById(R.id.bookImg);
        myCartAdd = itemView.findViewById(R.id.myCart);
        booksellingHomerow = itemView.findViewById(R.id.sellingPriceHomerow);
        bookrentingHomerow = itemView.findViewById(R.id.rentingPriceHomerow);
        booklocationHomerow = itemView.findViewById(R.id.locationHomerow);
        view = itemView;
    }
}