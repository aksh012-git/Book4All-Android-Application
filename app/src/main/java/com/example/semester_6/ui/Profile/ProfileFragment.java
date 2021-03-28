package com.example.semester_6.ui.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.semester_6.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private ProgressBar bar;
    TextView hedaNamejava,nameprofilejava,emailprofilejava,phoneprofilejava;
    FirebaseAuth mAuth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        mAuth = FirebaseAuth.getInstance();
        //get currentUser
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String myUID = currentUser.getUid();

        //get reference from firebase realtime
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("books4All").child("userData").child(myUID);

        //find textView
        hedaNamejava = root.findViewById(R.id.headNameProfile);
        nameprofilejava = root.findViewById(R.id.nameProfile);
        emailprofilejava = root.findViewById(R.id.emailProfile);
        phoneprofilejava = root.findViewById(R.id.phoneProfile);
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