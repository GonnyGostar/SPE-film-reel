package com.nulight.FilmTool.ui.filePage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class formula3ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public formula3ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the formula 3 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}