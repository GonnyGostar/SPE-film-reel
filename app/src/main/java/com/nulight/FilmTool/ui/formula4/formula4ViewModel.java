package com.nulight.FilmTool.ui.formula4;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class formula4ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public formula4ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the formula 4 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}