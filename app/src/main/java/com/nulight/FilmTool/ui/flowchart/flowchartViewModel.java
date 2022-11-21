package com.nulight.FilmTool.ui.flowchart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class flowchartViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public flowchartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the flowchart fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}