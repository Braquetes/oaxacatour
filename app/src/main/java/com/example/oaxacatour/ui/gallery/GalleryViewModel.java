package com.example.oaxacatour.ui.gallery;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Catálogo");
    }

    public LiveData<String> getText() {
        return mText;
    }
}