package com.example.covofficial.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covofficial.repository.DataLoadListener;
import com.example.covofficial.repository.LocationRepository;

import java.util.List;

public class LocationViewModel extends ViewModel {
    MutableLiveData<List<String>> location;



    public LiveData<List<String>> getLocations(DataLoadListener context){
        location = LocationRepository.getInstance(context).loadLocations();
        return location;
    }
}
