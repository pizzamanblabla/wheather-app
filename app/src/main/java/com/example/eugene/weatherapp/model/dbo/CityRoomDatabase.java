package com.example.eugene.weatherapp.model.dbo;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.eugene.weatherapp.model.dao.CityDao;
import com.example.eugene.weatherapp.model.entity.City;

@Database(entities = {City.class}, version = 1)
abstract public class CityRoomDatabase extends RoomDatabase {

    public abstract CityDao cityDao();

    private static CityRoomDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    public static CityRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CityRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CityRoomDatabase.class, "city")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CityDao mDao;

        PopulateDbAsync(CityRoomDatabase db) {
            mDao = db.cityDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //mDao.deleteAll();

            return null;
        }
    }
}
