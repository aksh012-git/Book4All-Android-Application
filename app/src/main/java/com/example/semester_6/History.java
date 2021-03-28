package com.example.semester_6;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class History extends Fragment {
    private FirebaseAuth mAuth;
    private RecyclerView recView;
    historyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myUID = currentUser.getUid();

        recView= root.findViewById(R.id.recViewHistory);
//        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<historyModel> options =
                new  FirebaseRecyclerOptions.Builder<historyModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("books4All").child("booksDetails").orderByChild("myUID").equalTo(myUID), historyModel.class)
                        .build();


        adapter=new historyAdapter(options);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recView.setAdapter(adapter);
        return root;
    }
}