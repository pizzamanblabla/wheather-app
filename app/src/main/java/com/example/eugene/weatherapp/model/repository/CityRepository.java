package com.example.eugene.weatherapp.model.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.eugene.weatherapp.model.dao.CityDao;
import com.example.eugene.weatherapp.model.dbo.CityRoomDatabase;
import com.example.eugene.weatherapp.model.entity.City;

import java.util.List;

public class CityRepository {
    private CityDao cityDao;
    private LiveData<List<City>> allCities;

    public CityRepository(Application application) {
        CityRoomDatabase db = CityRoomDatabase.getDatabase(application);
        cityDao = db.cityDao();
        allCities = cityDao.getAllWords();
    }

    public LiveData<List<City>> getAllCities() {
        return allCities;
    }

    public void insert (City city) {
        new insertAsyncTask(cityDao).execute(city);
    }

    private static class insertAsyncTask extends AsyncTask<City, Void, Void> {

        private CityDao mAsyncTaskDao;

        insertAsyncTask(CityDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final City... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}

