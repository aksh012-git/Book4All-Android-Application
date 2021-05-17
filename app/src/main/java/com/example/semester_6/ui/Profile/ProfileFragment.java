package com.example.semester_6.ui.Profile;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.semester_6.MainActivity;
import com.example.semester_6.R;
import com.example.semester_6.nav;
import com.example.semester_6.sellPage;
import com.example.semester_6.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.Instant;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
private ProgressBar bar;
TextView hedaNamejava,nameprofilejava,emailprofilejava,phoneprofilejava;
private FirebaseAuth mAuth;
Button deleteAccountJava,resetpassword;
private  TextView editprofile;
private String name,email,phone,profileimg;
private FirebaseDatabase firebaseDatabase;
private DatabaseReference reference;
private Uri imageUri;
private AlertDialog.Builder builder;
private AlertDialog dialog;
private ImageView aditprofilepic,profile_image;
private StorageReference storage = FirebaseStorage.getInstance().getReference();
public View onCreateView(@NonNull LayoutInflater inflater,
                     ViewGroup container, Bundle savedInstanceState) {
View root = inflater.inflate(R.layout.fragment_profile, container, false);

mAuth = FirebaseAuth.getInstance();
//get currentUser
FirebaseUser currentUser = mAuth.getCurrentUser();
String myUID = currentUser.getUid();

//get reference from firebase realtime
firebaseDatabase = FirebaseDatabase.getInstance();
reference= firebaseDatabase.getReference("books4All").child("userData").child(myUID);
DatabaseReference referenceBookDetails = firebaseDatabase.getReference("books4All").child("booksDetails");
DatabaseReference referenceCart = firebaseDatabase.getReference("books4All").child("Cart");

Query query = referenceBookDetails.orderByChild("myUID").equalTo(myUID);

//find textView
hedaNamejava = root.findViewById(R.id.headNameProfile);
nameprofilejava = root.findViewById(R.id.nameProfile);
emailprofilejava = root.findViewById(R.id.emailProfile);
phoneprofilejava = root.findViewById(R.id.phoneProfile);
deleteAccountJava = root.findViewById(R.id.deleteAccount);
editprofile = root.findViewById(R.id.editprofile);

resetpassword = root.findViewById(R.id.resetpass);
bar = root.findViewById(R.id.proBar1);

//edit profile pic
profile_image = root.findViewById(R.id.profile_image);
aditprofilepic = root.findViewById(R.id.editprofilepic);
aditprofilepic.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,2);
    }
});


//fatch value from firebase
reference.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            name = snapshot.child("name").getValue(String.class);
            email = snapshot.child("email").getValue(String.class);
            phone = snapshot.child("phone").getValue(String.class);
            profileimg = snapshot.child("profileurl").getValue(String.class);
            if(profileimg!=null) {
                Glide.with(profile_image.getContext()).load(profileimg).into(profile_image);
            }
            setData(name,email,phone);
        }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

//see full img
profile_image.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view2 = inflater.inflate(R.layout.seefullprofile,null);
        ImageView myimgfullsee = view2.findViewById(R.id.seefullimg);
        Glide.with(myimgfullsee.getContext()).load(profileimg).into(myimgfullsee);
        myimgfullsee.setImageURI(imageUri);
        builder.setView(view2);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
});


deleteAccountJava.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are you sure?");
        builder.setMessage("if you press delete button then your all data will be deleted from Book4All");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            //remove All Data-----------------------------------------------
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String xxx1 = snapshot.child("profileurl").getValue(String.class);
                                    StorageReference storageReference1 = FirebaseStorage.getInstance().getReferenceFromUrl(xxx1);
                                    storageReference1.delete();
                                    reference.removeValue();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshotprofile:snapshot.getChildren()){
                                        String xzxz = snapshotprofile.child("imgUrl").getValue(String.class);
                                        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(xzxz);
                                        referenceCart.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot snapshotCart:snapshot.getChildren()){
                                                    for(DataSnapshot snapshot1:snapshotCart.getChildren()){
                                                        if(snapshot1.getKey().equals(snapshotprofile.child("key").getValue())){
                                                            snapshot1.getRef().removeValue();
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        storageReference.delete();
                                        snapshotprofile.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            referenceCart.child(myUID).getRef().removeValue();
                            //--------------------------------------------------------------------------------------------
                            Toast.makeText(getContext(),"your Account Deleted", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getContext(),MainActivity.class));
                            getActivity().finish();
                        }
                        else {
                            Toast.makeText(getContext(),"Problem: "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
});

editprofile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view1 = inflater.inflate(R.layout.editprofile,null);
        EditText editnamexml = view1.findViewById(R.id.editnamexml);
        EditText editphonexml = view1.findViewById(R.id.editphonexml);
        editnamexml.setText(name);
        editphonexml.setText(phone);
        builder.setView(view1).setPositiveButton("Make Changes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HashMap<String , Object> map = new HashMap<>();
                map.put("name",editnamexml.getText().toString());
                map.put("phone",editphonexml.getText().toString());
                reference.updateChildren(map);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            name = snapshot.child("name").getValue(String.class);
                            email = snapshot.child("email").getValue(String.class);
                            phone = snapshot.child("phone").getValue(String.class);
                            setData(name,email,phone);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
});

resetpassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to reset your password?").setPositiveButton("reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(),"Password reset link sent to your email account", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Somthin went wrong! "+ e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
});
return root;
}
@Override
public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
super.onActivityResult(requestCode, resultCode, data);
if (requestCode==2 && resultCode==RESULT_OK && data!=null){
    imageUri = data.getData();
    profile_image.setImageURI(imageUri);
    builder = new AlertDialog.Builder(getContext());
    builder.setCancelable(false);
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View viewsell = inflater.inflate(R.layout.prograssbar,null);
    builder.setView(viewsell).setTitle("Profile image uploading");
    dialog = builder.create();
    dialog.show();

   reference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            String xxx = snapshot.child("profileurl").getValue(String.class);
            if (xxx!=null){
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(xxx);
            storageReference.delete();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    HashMap<String, Object> map = new HashMap<>();
    StorageReference fileref = storage.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
    fileref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    map.put("profileurl",String.valueOf(uri));
                    reference.updateChildren(map);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                profileimg = snapshot.child("profileurl").getValue(String.class);
                                if(profileimg!=null) {
                                    Glide.with(profile_image.getContext()).load(profileimg).into(profile_image);
                                    dialog.dismiss();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(),"Somthin went wrong! "+ error.getMessage(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });
                }
            });
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(getContext(),"Somthin went wrong! "+ e.getMessage(), Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    });


}
}
//setValue of text view
private void setData(String name, String email, String phone) {
hedaNamejava.setText(email);
emailprofilejava.setText(email);
nameprofilejava.setText(name);
phoneprofilejava.setText(phone);
bar.setVisibility(View.INVISIBLE);

}
private String getFileExtension(Uri uri) {
ContentResolver cr = getActivity().getContentResolver();
MimeTypeMap mime = MimeTypeMap.getSingleton();
return mime.getExtensionFromMimeType(cr.getType(uri));
}

}