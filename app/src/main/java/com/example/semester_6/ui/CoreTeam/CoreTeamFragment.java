package com.example.semester_6.ui.CoreTeam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.semester_6.R;

public class CoreTeamFragment extends Fragment {

    private CoreTeamViewModel coreTeamViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        coreTeamViewModel =
                new ViewModelProvider(this).get(CoreTeamViewModel.class);
        View root = inflater.inflate(R.layout.fragment_coreteam, container, false);
        return root;
    }
}