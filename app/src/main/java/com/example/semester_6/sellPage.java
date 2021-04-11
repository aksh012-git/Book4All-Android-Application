package com.example.semester_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class sellPage extends AppCompatActivity {
    private Button uplodeButtonjava;
    private EditText bookNamejava,authornamejava,booktypejava,rentingjava,sellingjava,addadressjava,zipcodejava;
    private ImageView uploadImage;
    private FirebaseAuth mAuth;
    private Uri imageUri;
    private ProgressBar bar;
    private StorageReference storage = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page);
        uplodeButtonjava = findViewById(R.id.myuplodeButton);

        bookNamejava = findViewById(R.id.uplodeBookname);
        authornamejava = findViewById(R.id.uplodeAuthorname);
        booktypejava = findViewById(R.id.uplodeBooktype);
        rentingjava = findViewById(R.id.uploadRenting);
        sellingjava = findViewById(R.id.uploadSelling);
        addadressjava = findViewById(R.id.uploadAddress);
        zipcodejava = findViewById(R.id.uploadZipcode);
        uploadImage = findViewById(R.id.uploadBookImg);
        bar = findViewById(R.id.sellprogressBar);



        //init auth
        mAuth = FirebaseAuth.getInstance();
        uplodeButtonjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setVisibility(View.VISIBLE);
                bookNamejava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                authornamejava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                booktypejava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                rentingjava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                sellingjava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                addadressjava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                zipcodejava.onEditorAction(EditorInfo.IME_ACTION_DONE);
                String bookname = bookNamejava.getText().toString();
                String authorname = authornamejava.getText().toString();
                String booktype= booktypejava.getText().toString();
                String rentingprice = rentingjava.getText().toString();
                String sellingprice = sellingjava.getText().toString();
                String address = addadressjava.getText().toString();
                String zipcode = zipcodejava.getText().toString();

                FirebaseUser currentUser = mAuth.getCurrentUser();
                String myUID = currentUser.getUid();

                if (imageUri !=null){
                    addBookDetails(bookname,authorname,booktype,rentingprice,sellingprice,address,zipcode,myUID,imageUri);                }
                else {
                    bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(sellPage.this, "Please select Image", Toast.LENGTH_LONG).show();
                }

            }
            
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2 && resultCode==RESULT_OK && data!=null){
            imageUri = data.getData();
            uploadImage.setImageURI(imageUri);
        }
    }

    private void addBookDetails(String bookname, String authorname, String booktype, String rentingprice, String sellingprice, String address, String zipcode,String myUID,Uri uri) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("books4All").child("booksDetails").push();
        String key = reference.getKey();
        if(!myUID.isEmpty()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("bookname", bookname.toLowerCase());
            map.put("authorname", authorname);
            map.put("booktype", booktype);
            map.put("rentingprice", rentingprice);
            map.put("sellingprice", sellingprice);
            map.put("address", address);
            map.put("zipcode", zipcode);
            map.put("myUID", myUID);
            map.put("key", key);

            StorageReference fileref = storage.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            map.put("imgUrl", String.valueOf(uri));
                            reference.setValue(map);
                            bar.setVisibility(View.GONE);
                            Toast.makeText(sellPage.this, "Book details added successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(sellPage.this, nav.class);
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }

    //this methode returns extension of image like jpg png ext.
    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


}