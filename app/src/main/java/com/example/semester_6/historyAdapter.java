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


public class historyAdapter extends FirebaseRecyclerAdapter<historyModel, historyAdapter.myviewholder>
{
    public historyAdapter(@NonNull FirebaseRecyclerOptions<historyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull historyModel model)
    {
        holder.bookname.setText(model.getBookname());
        Glide.with(holder.imgUrl.getContext()).load(model.getImgUrl()).into(holder.imgUrl);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
           View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row,parent,false);
           return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView bookname;
        ImageView imgUrl;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            bookname = itemView.findViewById(R.id.booknameH);
            imgUrl = itemView.findViewById(R.id.bookImgH);
        }
    }
}
