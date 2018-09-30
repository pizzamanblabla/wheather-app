package com.example.eugene.weatherapp.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.eugene.weatherapp.model.entity.City;

import java.util.List;

@Dao
public interface CityDao {

    @Insert
    void insert(City word);

    @Query("DELETE FROM city")
    void deleteAll();

    @Query("SELECT * from city ORDER BY name ASC")
    LiveData<List<City>> getAllWords();
}