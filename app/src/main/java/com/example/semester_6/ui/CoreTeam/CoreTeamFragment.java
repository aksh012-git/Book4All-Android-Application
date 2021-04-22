package com.example.semester_6.ui.CoreTeam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.semester_6.R;

public class CoreTeamFragment extends Fragment {


    private ImageView linkdinaksh,instaaksh,linkdinvasu,instavasu;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_coreteam, container, false);
        linkdinaksh = root.findViewById(R.id.linkdinaksh);
        linkdinvasu = root.findViewById(R.id.linkdinvasu);
        instaaksh = root.findViewById(R.id.instaaksh);
        instavasu =root.findViewById(R.id.instavasu);

        linkdinaksh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goUrl("https://www.linkedin.com/in/aksh-maradiya-015a49187");
            }
        });
        linkdinvasu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goUrl("https://www.linkedin.com/in/vasu-hudka-21a26a210");
            }
        });
        instaaksh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goUrl("https://instagram.com/aksh.012?igshid=o9rluyhzdugv");
            }
        });
        instavasu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            goUrl("https://instagram.com/_vasu_hudka_?igshid=mj97shvwvd0m");
            }
        });

        return root;
    }

    private void goUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}