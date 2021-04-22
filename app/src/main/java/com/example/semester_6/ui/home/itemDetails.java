package com.example.semester_6.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.semester_6.MainActivity;
import com.example.semester_6.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.util.HashMap;

public class itemDetails extends AppCompatActivity {
    private TextView bookNameFullDetailsjava,bookAuthorFullDetailsjava,bookTypeFullDetailsjava,userNameJava,userEmailJava,userPhoneJava,
            bookRentingFullDetailsJava,bookSellingFullDetailsJava,bookAdressFullDetailsJava,bookZipcodeFullDetailsJava,renttimeFullDetailsJava;
    private Intent intent;
    private ImageView bookimg,addCartItemdetailsJava;
    private Button chatBtn,callBtn;
    private   String userPhone,bookwpnumber;
    private FirebaseAuth mAuth;
    private String profileimg;
    private ImageView profile_image;
    private String name,bookauth,booktype,bookRentPrice,bookSellPrice,bookAdrress,bookZip,bookimgx,keyitemdetail,myUID,rentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);


        bookAuthorFullDetailsjava = findViewById(R.id.bookAuthorFullDetails);
        bookNameFullDetailsjava = findViewById(R.id.bookNameFullDetails);
        bookTypeFullDetailsjava = findViewById(R.id.bookTypeFullDetails);
        bookRentingFullDetailsJava = findViewById(R.id.bookRentingFullDetails);
        bookSellingFullDetailsJava = findViewById(R.id.bookSellingFullDetails);
        bookAdressFullDetailsJava = findViewById(R.id.bookAdressFullDetails);
        bookZipcodeFullDetailsJava = findViewById(R.id.bookZipcodeFullDetails);
        renttimeFullDetailsJava = findViewById(R.id.renttimeFullDetails);
        userNameJava = findViewById(R.id.userName);
        userEmailJava = findViewById(R.id.userEmail);
        userPhoneJava = findViewById(R.id.userPhone);
        bookimg = findViewById(R.id.bookImgfullDetails);
        addCartItemdetailsJava = findViewById(R.id.addCartItemdetails);
        chatBtn = findViewById(R.id.chatWp);
        callBtn = findViewById(R.id.callButton);

        profile_image  = findViewById(R.id.profileimgitemdetails);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myItemDetailsHome = currentUser.getUid();

        intent = new Intent(getIntent());
        String mykey = intent.getStringExtra("key");
        String MyUID = intent.getStringExtra("myUID");


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference2 =  firebaseDatabase.getReference("books4All").child("userData").child(MyUID);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String userName = snapshot.child("name").getValue(String.class);
                    String userEmail = snapshot.child("email").getValue(String.class);
                    userPhone = snapshot.child("phone").getValue(String.class);
                    profileimg = snapshot.child("profileurl").getValue(String.class);
                    if(profileimg!=null) {
                        Glide.with(profile_image.getContext()).load(profileimg).into(profile_image);
                    }
                    userNameJava.setText(userName);
                    userEmailJava.setText(userEmail);
                    userPhoneJava.setText(userPhone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(itemDetails.this, "Error:"+error, Toast.LENGTH_LONG).show();
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemDetails.this);
                LayoutInflater inflater = itemDetails.this.getLayoutInflater();
                View view2 = inflater.inflate(R.layout.seefullprofile,null);
                ImageView myimgfullsee = view2.findViewById(R.id.seefullimg);
                Glide.with(myimgfullsee.getContext()).load(profileimg).into(myimgfullsee);
                builder.setView(view2);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase2.getReference("books4All").child("booksDetails").child(mykey);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    name = snapshot.child("bookname").getValue(String.class);
                    bookauth = snapshot.child("authorname").getValue(String.class);
                    booktype = snapshot.child("booktype").getValue(String.class);
                    bookRentPrice = snapshot.child("rentingprice").getValue(String.class);
                    bookSellPrice = snapshot.child("sellingprice").getValue(String.class);
                    bookAdrress = snapshot.child("address").getValue(String.class);
                    bookZip = snapshot.child("zipcode").getValue(String.class);
                    bookimgx = snapshot.child("imgUrl").getValue(String.class);
                    keyitemdetail = snapshot.child("key").getValue(String.class);
                    myUID = snapshot.child("myUID").getValue(String.class);
                    bookwpnumber = snapshot.child("wpnumber").getValue(String.class);
                    rentTime = snapshot.child("renttime").getValue(String.class);

                    String y = "₹ " + bookSellPrice;
                    String x = "₹ " + bookRentPrice;

                    bookNameFullDetailsjava.setText(name);
                    bookAuthorFullDetailsjava.setText("Author: "+bookauth);
                    bookTypeFullDetailsjava.setText("Book Type: "+booktype);
                    bookRentingFullDetailsJava.setText("Renting Price: " + x);
                    bookSellingFullDetailsJava.setText("Selling Price :" + y);
                    bookZipcodeFullDetailsJava.setText(bookAdrress +","+ bookZip);
                    renttimeFullDetailsJava.setText(rentTime);
                    Glide.with(bookimg.getContext()).load(bookimgx).into(bookimg);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(itemDetails.this, "Error:"+error, Toast.LENGTH_LONG).show();
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean insalled = appInstallorNot("com.whatsapp");
                if (insalled){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://api.WhatsApp.com/send?phone="+"+91"+bookwpnumber+"&text=i like your book "+"'"+name+"'"+" on book4All"));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(itemDetails.this, "whatsapp not!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+userPhone));
                startActivity(intent);
            }
        });

        addCartItemdetailsJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prossecctomycart(keyitemdetail,myUID,name,bookauth,booktype,bookRentPrice,bookSellPrice,bookAdrress,bookZip,bookimgx,myItemDetailsHome,rentTime,bookwpnumber);
            }
        });


    }

    private boolean appInstallorNot(String s) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        packageManager.getPackageArchiveInfo(s, PackageManager.GET_ACTIVITIES);
        app_installed  = true;
        return app_installed;
    }
    private void prossecctomycart(String key, String myUID, String bookname, String authorname, String booktype, String rentingprice, String sellingprice, String address, String zipcode, String imgUrl,String myUIDHome,String renttimes,String wpnumber) {
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
        map.put("renttime",renttimes);
        reference.child(key).setValue(map);
        Toast.makeText(itemDetails.this, "Saved in 'MyCart'", Toast.LENGTH_LONG).show();
    }
}