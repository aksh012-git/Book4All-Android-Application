package com.example.semester_6.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.semester_6.History;
import com.example.semester_6.R;
import com.example.semester_6.HomeFragmentModel;
import com.example.semester_6.HomeFragmentMyViewHolder;
import com.example.semester_6.sellPage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    Button seelbuttonjava;
    RecyclerView recView;
    private FirebaseRecyclerOptions<HomeFragmentModel> options;
    private FirebaseRecyclerAdapter<HomeFragmentModel, HomeFragmentMyViewHolder> adapter;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        seelbuttonjava = root.findViewById(R.id.sellButton);
        seelbuttonjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),sellPage.class));
            }
        });


        recView= root.findViewById(R.id.recview);
        searchView = root.findViewById(R.id.searchHome);
//        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));


        options = new  FirebaseRecyclerOptions.Builder<HomeFragmentModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("books4All").child("booksDetails"), HomeFragmentModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<HomeFragmentModel, HomeFragmentMyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HomeFragmentMyViewHolder holder, int position, @NonNull HomeFragmentModel model) {

                String bookNameHome = model.getBookname();
                if(bookNameHome.length()>15)
                    bookNameHome=bookNameHome.substring(0,15)+"...";

                holder.bookname.setText(bookNameHome);
                Glide.with(holder.imgUrl.getContext()).load(model.getImgUrl()).into(holder.imgUrl);
                String mykey = model.getKey();
                String myUID = model.getMyUID();
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Search view clear Focus
                        searchView.clearFocus();
                        searchView.setQuery("", false);
                        searchView.setFocusable(false);
                        Intent intent = new Intent(getActivity(),itemDetails.class);
                        intent.putExtra("key",mykey);
                        intent.putExtra("myUID",myUID);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public HomeFragmentMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
                return new HomeFragmentMyViewHolder(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recView.setAdapter(adapter);



        //search view Query-----------------------------------------------------------------
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                prosseccofsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                prosseccofsearch(s);
                return false;
            }
        });
        return root;
    }


    private void prosseccofsearch(String s) {
        options = new  FirebaseRecyclerOptions.Builder<HomeFragmentModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("books4All").child("booksDetails")
                        .orderByChild("bookname").startAt(s).endAt(s+"\uf8ff"), HomeFragmentModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<HomeFragmentModel, HomeFragmentMyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HomeFragmentMyViewHolder holder, int position, @NonNull HomeFragmentModel model) {

                String bookNameHome = model.getBookname();
                if(bookNameHome.length()>15)
                    bookNameHome=bookNameHome.substring(0,15)+"...";

                holder.bookname.setText(bookNameHome);
                Glide.with(holder.imgUrl.getContext()).load(model.getImgUrl()).into(holder.imgUrl);
                String mykey = model.getKey();
                String myUID = model.getMyUID();
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Search view clear Focus
                        searchView.clearFocus();
                        searchView.setQuery("", false);
                        searchView.setFocusable(false);
                        Intent intent = new Intent(getActivity(),itemDetails.class);
                        intent.putExtra("key",mykey);
                        intent.putExtra("myUID",myUID);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public HomeFragmentMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
                return new HomeFragmentMyViewHolder(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recView.setAdapter(adapter);
    }
}
