package com.example.semester_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.semester_6.ui.home.itemDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.internal.DiskLruCache;

import javax.xml.namespace.QName;

public class myCart extends AppCompatActivity {
    RecyclerView recViewCart;
    private FirebaseRecyclerOptions<myCartModel> options;
    private FirebaseRecyclerAdapter<myCartModel, myCartMyViewHolder> adapter;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        recViewCart = findViewById(R.id.myCartRecview);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myUIDHomeCart = currentUser.getUid();

        options = new  FirebaseRecyclerOptions.Builder<myCartModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("books4All").child("Cart").child(myUIDHomeCart), myCartModel.class)
                .build();


        adapter = new FirebaseRecyclerAdapter<myCartModel, myCartMyViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull myCartMyViewHolder holder, int position, @NonNull myCartModel model) {
                String bookNameHome = model.getBookname();
                if(bookNameHome.length()>15)
                    bookNameHome=bookNameHome.substring(0,15)+"...";
                String location = model.getAddress();
                if(location.length()>19)
                    location=location.substring(0,19)+"...";

                holder.booknameCart.setText(bookNameHome);
                holder.booksellingCartrow.setText("ForBuy: ₹ "+model.getSellingprice());
                holder.bookrentingCartrow.setText("ForRent: ₹ "+model.getRentingprice()+" "+model.getRenttime());
                holder.booklocationCartrow.setText(location);
                Glide.with(holder.imgUrlCart.getContext()).load(model.getImgUrl()).into(holder.imgUrlCart);

                holder.viewCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(myCart.this,itemDetails_cart.class);
                        intent.putExtra("key",model.getKey());
                        intent.putExtra("myUID",myUIDHomeCart);
                        intent.putExtra("myUIDForUser",model.getMyUID());
                        startActivity(intent);
                    }
                });
                holder.deleteincart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference reference = firebaseDatabase.getReference("books4All").child("Cart").child(myUIDHomeCart);
                        reference.child(model.getKey()).removeValue();
                    }
                });
            }

            @NonNull
            @Override
            public myCartMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_row,parent,false);
                return new myCartMyViewHolder(view);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(myCart.this,2,GridLayoutManager.VERTICAL,false);
        recViewCart.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recViewCart.setAdapter(adapter);
    }
}