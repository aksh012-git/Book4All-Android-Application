package com.example.semester_6.ui.Profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.semester_6.MainActivity;
import com.example.semester_6.R;
import com.example.semester_6.nav;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private ProgressBar bar;
    TextView hedaNamejava,nameprofilejava,emailprofilejava,phoneprofilejava;
    private FirebaseAuth mAuth;
    Button deleteAccountJava;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);


        mAuth = FirebaseAuth.getInstance();
        //get currentUser
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myUID = currentUser.getUid();

        //get reference from firebase realtime
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference= firebaseDatabase.getReference("books4All").child("userData").child(myUID);
        DatabaseReference referenceBookDetails = firebaseDatabase.getReference("books4All").child("booksDetails");
        DatabaseReference referenceCart = firebaseDatabase.getReference("books4All").child("Cart");

        Query query = referenceBookDetails.orderByChild("myUID").equalTo(myUID);

        //find textView
        hedaNamejava = root.findViewById(R.id.headNameProfile);
        nameprofilejava = root.findViewById(R.id.nameProfile);
        emailprofilejava = root.findViewById(R.id.emailProfile);
        phoneprofilejava = root.findViewById(R.id.phoneProfile);
        deleteAccountJava = root.findViewById(R.id.deleteAccount);
        bar = root.findViewById(R.id.proBar1);


        //fatch value from firebase
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);
                    setData(name,email,phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                                    reference.removeValue();
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot snapshotprofile:snapshot.getChildren()){
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

        return root;
    }

    //setValue of text view
    private void setData(String name, String email, String phone) {
        hedaNamejava.setText(email);
        emailprofilejava.setText(email);
        nameprofilejava.setText(name);
        phoneprofilejava.setText(phone);
        bar.setVisibility(View.INVISIBLE);

    }
}