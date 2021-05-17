    package com.example.semester_6.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.semester_6.R;
import com.example.semester_6.HomeFragmentModel;
import com.example.semester_6.HomeFragmentMyViewHolder;
import com.example.semester_6.sellPage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HomeFragment extends Fragment {
    TextView seelbuttonjava;
    RecyclerView recView;
    private FirebaseRecyclerOptions<HomeFragmentModel> options;
    private FirebaseRecyclerAdapter<HomeFragmentModel, HomeFragmentMyViewHolder> adapter;
    private FirebaseAuth mAuth;
    SearchView searchView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        

        seelbuttonjava = root.findViewById(R.id.sellButton);
        seelbuttonjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.clearFocus();
                searchView.setFocusable(false);
                startActivity(new Intent(getContext(),sellPage.class));
            }
        });


        recView= root.findViewById(R.id.recview);
        searchView = root.findViewById(R.id.searchHome);
//        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myUIDHome = currentUser.getUid();


        searchView.setQueryHint(Html.fromHtml("<font color = #b4a5a5>" + getResources().getString(R.string.searchHint) + "</font>"));
        int id =  searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);
//firebase Ui options--------------------------------------------
        options = new  FirebaseRecyclerOptions.Builder<HomeFragmentModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("books4All").child("booksDetails"), HomeFragmentModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<HomeFragmentModel, HomeFragmentMyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HomeFragmentMyViewHolder holder, int position, @NonNull HomeFragmentModel model) {
                String bookNameHome = model.getBookname();
                if(bookNameHome.length()>20)
                    bookNameHome=bookNameHome.substring(0,20)+"...";
                String location = model.getAddress();
                if(location.length()>17)
                    location=location.substring(0,17)+"...";

                holder.bookname.setText(bookNameHome);
                holder.booksellingHomerow.setText("ForBuy: ₹ "+model.getSellingprice());
                holder.bookrentingHomerow.setText("ForRent: ₹ "+model.getRentingprice()+" "+model.getRenttime());
                holder.booklocationHomerow.setText(location);
                Glide.with(holder.imgUrl.getContext()).load(model.getImgUrl()).into(holder.imgUrl);
                String mykey = model.getKey();
                String myUID = model.getMyUID();
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Search view clear Focus
                        searchView.clearFocus();
                        searchView.setFocusable(false);
                        Intent intent = new Intent(getActivity(),itemDetails.class);
                        intent.putExtra("key",mykey);
                        intent.putExtra("myUID",myUID);
                        startActivity(intent);
                    }
                });

                holder.myCartAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prossecctomycart(model.getKey(),model.getMyUID(),model.getBookname(),model.getAuthorname(),model.getBooktype(),model.getRentingprice(),
                                model.getSellingprice(),model.getAddress(),model.getZipcode(),model.getImgUrl(),myUIDHome,model.getRenttime(),model.getWpnumber());

                    }
                });
            }

            @NonNull
            @Override
            public HomeFragmentMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_singlerow,parent,false);
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

    private void prossecctomycart(String key, String myUID, String bookname, String authorname, String booktype, String rentingprice, String sellingprice, String address, String zipcode, String imgUrl,String myUIDHome,String retntime,String wpnumber) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("books4All").child("Cart").child(myUIDHome);
        HashMap<String,String> map = new HashMap<>();
        map.put("bookname", bookname.toLowerCase());
        map.put("authorname", authorname);
        map.put("booktype", booktype);
        map.put("rentingprice", rentingprice);
        map.put("sellingprice", sellingprice);
        map.put("address", address);
        map.put("zipcode", zipcode);
        map.put("myUID", myUID);
        map.put("key", key);
        map.put("imgUrl",imgUrl);
        map.put("wpnumber",wpnumber);
        map.put("renttime",retntime);
        reference.child(key).setValue(map);
        Toast.makeText(getContext(), "Saved in 'MyCart'", Toast.LENGTH_LONG).show();
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
                if(bookNameHome.length()>23)
                    bookNameHome=bookNameHome.substring(0,23)+"...";
                String location = model.getAddress();
                if(location.length()>19)
                    location=location.substring(0,19)+"...";

                holder.bookname.setText(bookNameHome);
                holder.booksellingHomerow.setText("ForBuy: ₹ "+model.getSellingprice());
                holder.bookrentingHomerow.setText("ForRent: ₹ "+model.getRentingprice()+" "+model.getRenttime());
                holder.booklocationHomerow.setText(location);
                Glide.with(holder.imgUrl.getContext()).load(model.getImgUrl()).into(holder.imgUrl);
                String mykey = model.getKey();
                String myUID = model.getMyUID();
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Search view clear Focus
                        searchView.clearFocus();
                        searchView.setQuery(null, true);
                        searchView.setFocusable(false);
                        Intent intent = new Intent(getActivity(),itemDetails.class);
                        intent.putExtra("key",mykey);
                        intent.putExtra("myUID",myUID);
                        startActivity(intent);
                    }
                });
                holder.myCartAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String myUIDHome = currentUser.getUid();
                        prossecctomycart(model.getKey(),model.getMyUID(),model.getBookname(),model.getAuthorname(),model.getBooktype(),model.getRentingprice(),
                                model.getSellingprice(),model.getAddress(),model.getZipcode(),model.getImgUrl(),myUIDHome,model.getRenttime(),model.getWpnumber());

                    }
                });
            }

            @NonNull
            @Override
            public HomeFragmentMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_singlerow,parent,false);
                return new HomeFragmentMyViewHolder(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recView.setAdapter(adapter);
    }
}
