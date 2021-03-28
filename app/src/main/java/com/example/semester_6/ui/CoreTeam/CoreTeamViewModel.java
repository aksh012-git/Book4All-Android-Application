package com.example.semester_6.ui.CoreTeam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoreTeamViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CoreTeamViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}