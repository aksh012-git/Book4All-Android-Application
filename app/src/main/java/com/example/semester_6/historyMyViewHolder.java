package com.example.semester_6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class historyMyViewHolder extends RecyclerView.ViewHolder
{
    TextView bookname;
    ImageView imgUrl,deleteInsold;
    public View viewHistory;
    public historyMyViewHolder(@NonNull View itemView) {
        super(itemView);
        bookname = itemView.findViewById(R.id.booknameH);
        imgUrl = itemView.findViewById(R.id.bookImgH);
        deleteInsold =itemView.findViewById(R.id.deleteInSold);
    }
}
