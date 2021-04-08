package com.example.semester_6;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.RemoteCallbackList;
import android.util.Log;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class History extends Fragment {
    private FirebaseAuth mAuth;
    private RecyclerView recView;
    private FirebaseRecyclerOptions<historyModel> options;
    private FirebaseRecyclerAdapter<historyModel, historyMyViewHolder> adapter;
    private String xxxx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myUIDSold = currentUser.getUid();

        recView= root.findViewById(R.id.recViewHistory);
//        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<historyModel> options =
                new  FirebaseRecyclerOptions.Builder<historyModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("books4All").child("booksDetails").orderByChild("myUID").equalTo(myUIDSold), historyModel.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<historyModel, historyMyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull historyMyViewHolder holder, int position, @NonNull historyModel model) {
                String bookNameHome = model.getBookname();
                if(bookNameHome.length()>15)
                    bookNameHome=bookNameHome.substring(0,15)+"...";

                holder.bookname.setText(bookNameHome);
                Glide.with(holder.imgUrl.getContext()).load(model.getImgUrl()).into(holder.imgUrl);

                holder.deleteInsold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        xxxx = model.getKey();
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference reference = firebaseDatabase.getReference("books4All").child("booksDetails");
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
            }
            @NonNull
            @Override
            public historyMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row,parent,false);
                return new historyMyViewHolder(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recView.setAdapter(adapter);
        return root;
    }
}