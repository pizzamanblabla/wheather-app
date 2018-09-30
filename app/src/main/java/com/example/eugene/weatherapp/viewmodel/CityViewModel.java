package com.example.eugene.weatherapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.eugene.weatherapp.model.entity.City;
import com.example.eugene.weatherapp.model.repository.CityRepository;

import java.util.List;

public class CityViewModel extends AndroidViewModel {
    private CityRepository repository;
    private LiveData<List<City>> allCities;

    public CityViewModel(@NonNull Application application) {
        super(application);
        repository = new CityRepository(application);
        allCities = repository.getAllCities();
    }

    public LiveData<List<City>> getAllCities() {
        return allCities;
    }

    public void insert(City city) {
        repository.insert(city);
    }
}