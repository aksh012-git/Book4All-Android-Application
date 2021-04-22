package com.example.semester_6;

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
import com.example.semester_6.ui.home.itemDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class itemDetails_cart extends AppCompatActivity {
    private TextView bookNameFullDetailsjava,bookAuthorFullDetailsjava,bookTypeFullDetailsjava,userNameJava,userEmailJava,userPhoneJava,
            bookRentingFullDetailsJava,bookSellingFullDetailsJava,bookAdressFullDetailsJava,bookZipcodeFullDetailsJava,renttimeFullDetailsJava;
    private Intent intent;
    private ImageView bookimg;
    private Button chatBtn,callBtn;
    private ImageView profile_image;
    private String profileimg;
    private   String userPhone,bookwpnumber ,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_cart);

        bookAuthorFullDetailsjava = findViewById(R.id.bookAuthorFullDetailsCart);
        bookNameFullDetailsjava = findViewById(R.id.bookNameFullDetailsCart);
        bookTypeFullDetailsjava = findViewById(R.id.bookTypeFullDetailsCart);
        bookRentingFullDetailsJava = findViewById(R.id.bookRentingFullDetailsCart);
        bookSellingFullDetailsJava = findViewById(R.id.bookSellingFullDetailsCart);
        bookAdressFullDetailsJava = findViewById(R.id.bookAdressFullDetailsCart);
        bookZipcodeFullDetailsJava = findViewById(R.id.bookZipcodeFullDetailsCart);
        renttimeFullDetailsJava = findViewById(R.id.renttimeFullDetailsCart);
        userNameJava = findViewById(R.id.userNameCart);
        userEmailJava = findViewById(R.id.userEmailCart);
        userPhoneJava = findViewById(R.id.userPhoneCart);
        bookimg = findViewById(R.id.bookImgfullDetailsCart);
        callBtn = findViewById(R.id.callbtnCart);
        chatBtn = findViewById(R.id.chatWpCart);

        profile_image  = findViewById(R.id.profileimgitemdetailscart);


        intent = new Intent(getIntent());
        String mykeyCart = intent.getStringExtra("key");
        String MyUIDCart = intent.getStringExtra("myUID");
        String myUIDForUserCart = intent.getStringExtra("myUIDForUser");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("books4All").child("Cart").child(MyUIDCart).child(mykeyCart);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                     name = snapshot.child("bookname").getValue(String.class);
                    String bookauth = snapshot.child("authorname").getValue(String.class);
                    String booktype = snapshot.child("booktype").getValue(String.class);
                    String bookRentPrice = snapshot.child("rentingprice").getValue(String.class);
                    String bookSellPrice = snapshot.child("sellingprice").getValue(String.class);
                    String bookAdrress = snapshot.child("address").getValue(String.class);
                    String bookZip = snapshot.child("zipcode").getValue(String.class);
                    String bookimgx = snapshot.child("imgUrl").getValue(String.class);
                    bookwpnumber = snapshot.child("wpnumber").getValue(String.class);
                    String retntime = snapshot.child("renttime").getValue(String.class);

                    String y = "₹ " + bookSellPrice;
                    String x = "₹ " + bookRentPrice;

                    bookNameFullDetailsjava.setText(name);
                    bookAuthorFullDetailsjava.setText("Author: "+bookauth);
                    bookTypeFullDetailsjava.setText("Book Type: "+booktype);
                    bookRentingFullDetailsJava.setText("Renting Price: " + x);
                    bookSellingFullDetailsJava.setText("Selling Price :" + y);
                    bookZipcodeFullDetailsJava.setText(bookAdrress +","+ bookZip);
                    renttimeFullDetailsJava.setText(retntime);
                    Glide.with(bookimg.getContext()).load(bookimgx).into(bookimg);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(itemDetails_cart.this, "Error:"+error, Toast.LENGTH_LONG).show();
            }
        });
        DatabaseReference reference2 =  firebaseDatabase.getReference("books4All").child("userData").child(myUIDForUserCart);
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
                Toast.makeText(itemDetails_cart.this, "Error:"+error, Toast.LENGTH_LONG).show();
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemDetails_cart.this);
                LayoutInflater inflater = itemDetails_cart.this.getLayoutInflater();
                View view2 = inflater.inflate(R.layout.seefullprofile,null);
                ImageView myimgfullsee = view2.findViewById(R.id.seefullimg);
                Glide.with(myimgfullsee.getContext()).load(profileimg).into(myimgfullsee);
                builder.setView(view2);
                AlertDialog dialog = builder.create();
                dialog.show();
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
                    Toast.makeText(itemDetails_cart.this, "whatsapp not!!", Toast.LENGTH_SHORT).show();
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
    }

    private boolean appInstallorNot(String s) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        packageManager.getPackageArchiveInfo(s, PackageManager.GET_ACTIVITIES);
        app_installed  = true;
        return app_installed;
    }
}
