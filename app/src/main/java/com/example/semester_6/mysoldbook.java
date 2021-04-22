package com.example.semester_6;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class mysoldbook extends Fragment {
    private FirebaseAuth mAuth;
    private RecyclerView recView;
    private FirebaseRecyclerOptions<mySoldBookModel> options;
    private FirebaseRecyclerAdapter<mySoldBookModel, mySoldBookMyViewHolder> adapter;
    private String xxxx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mysoldbook, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myUIDSold = currentUser.getUid();

        recView= root.findViewById(R.id.recViewHistory);
//        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<mySoldBookModel> options =
                new  FirebaseRecyclerOptions.Builder<mySoldBookModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("books4All").child("booksDetails").orderByChild("myUID").equalTo(myUIDSold), mySoldBookModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<mySoldBookModel, mySoldBookMyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull mySoldBookMyViewHolder holder, int position, @NonNull mySoldBookModel model) {
                String bookNameHome = model.getBookname();
                if(bookNameHome.length()>15)
                    bookNameHome=bookNameHome.substring(0,15)+"...";

                holder.bookname.setText(bookNameHome);
                Glide.with(holder.imgUrl.getContext()).load(model.getImgUrl()).into(holder.imgUrl);

                holder.viewHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),itemdetails_mysold.class);
                        intent.putExtra("keyForSold",model.getKey());
                        intent.putExtra("myUIDForSold",myUIDSold);
                        intent.putExtra("myUIDForUserForSold",model.getMyUID());
                        startActivity(intent);
                    }
                });
                holder.deleteInsold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        xxxx = model.getKey();
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference reference = firebaseDatabase.getReference("books4All").child("booksDetails");
                        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.imgUrl);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Are you sure?");
                        builder.setMessage("you want to delete your book "+model.getBookname());
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                storageReference.delete();
                                reference.child(xxxx).removeValue();

                                DatabaseReference reference23 = firebaseDatabase.getReference("books4All").child("Cart");
                                reference23.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot snapshotCart:snapshot.getChildren()){
                                            for(DataSnapshot snapshot1:snapshotCart.getChildren()){
                                                if(snapshot1.getKey().equals(xxxx)){
                                                    snapshot1.getRef().removeValue();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });
                       builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                               dialogInterface.dismiss();
                           }
                       });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
            @NonNull
            @Override
            public mySoldBookMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mysold_row,parent,false);
                return new mySoldBookMyViewHolder(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recView.setAdapter(adapter);
        return root;
    }
}