package com.example.eugene.weatherapp.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.example.eugene.weatherapp.model.DataReceiver;
import com.example.eugene.weatherapp.model.adapter.CityListAdapter;
import com.example.eugene.weatherapp.model.dto.response.InternalResponse;
import com.example.eugene.weatherapp.model.entity.City;
import com.example.eugene.weatherapp.model.dto.request.OpenWeatherRequest;
import com.example.eugene.weatherapp.model.interaction.remotecall.OpenWeatherFactory;
import com.example.eugene.weatherapp.model.operation.getWeather.dataparser.DataParser;
import com.example.eugene.weatherapp.model.operation.getWeather.dto.response.SuccessfulResponse;
import com.example.eugene.weatherapp.model.type.ResponseType;
import com.example.eugene.weatherapp.viewmodel.CityViewModel;
import com.example.eugene.weatherapp.R;
import com.example.eugene.weatherapp.viewmodel.RecyclerViewClickListener;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class CityList extends AppCompatActivity implements RecyclerViewClickListener {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private CityViewModel cityViewModel;

    @Override
    public void recyclerViewListClicked(View view, int position) {
        Intent intent = new Intent(CityList.this, MainActivity.class);

        try {
            List<City> cityList = cityViewModel.getAllCities().getValue();
            City city = cityList.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("longitude", city.getLongitude());
            bundle.putString("latitude", city.getLatitude());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } catch (NullPointerException e) {
            Snackbar.make(view, "Wow, something went really wrong!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }

        setContentView(R.layout.activity_city_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CityList.this, AddNewCity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CityListAdapter adapter = new CityListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cityViewModel = ViewModelProviders.of(this).get(CityViewModel.class);

        cityViewModel.getAllCities().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable final List<City> cities) {
                adapter.setCities(cities);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            DataReceiver.placeIdTask asyncTask = new DataReceiver.placeIdTask(new DataReceiver.AsyncResponse() {
                public void processFinish(InternalResponse response) {
                    Log.d("location", "Got remote response");

                    if (response.getType() == ResponseType.SUCCESSFUL) {
                        Log.d("location", "Response is successful");
                        SuccessfulResponse successfulResponse = (SuccessfulResponse) response;

                        City city = new City(successfulResponse.getCity(), successfulResponse.getLatitude(), successfulResponse.getLongtitude());

                        cityViewModel.insert(city);
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                R.string.wrong_city_format,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
            }, new DataParser());

            OpenWeatherRequest openWeatherRequest = (new OpenWeatherRequest()).setQuery(data.getStringExtra(AddNewCity.EXTRA_REPLY));

            asyncTask.execute((new OpenWeatherFactory()).getWeather(openWeatherRequest));
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
