package com.example.eugene.weatherapp.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eugene.weatherapp.R;
import com.example.eugene.weatherapp.model.DataReceiver;
import com.example.eugene.weatherapp.model.adapter.ForecastListAdapter;
import com.example.eugene.weatherapp.model.dto.request.OpenWeatherRequest;
import com.example.eugene.weatherapp.model.dto.response.InternalResponse;
import com.example.eugene.weatherapp.model.interaction.remotecall.OpenWeatherFactory;
import com.example.eugene.weatherapp.model.operation.getForecast.dataparser.DataParser;
import com.example.eugene.weatherapp.model.operation.getForecast.dto.response.SuccessfulForecastResponse;
import com.example.eugene.weatherapp.model.type.ResponseType;

public class Forecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }

        setContentView(R.layout.activity_forecast);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String longitude = bundle.getString("longitude");
            String latitude = bundle.getString("latitude");

            final ListView listview = (ListView) findViewById(R.id.listView);
            final Context context = this;

            DataReceiver.placeIdTask asyncTask = new DataReceiver.placeIdTask(new DataReceiver.AsyncResponse() {
                public void processFinish(InternalResponse response) {
                    Log.d("location", "Got remote response");

                    if (response.getType() == ResponseType.SUCCESSFUL) {
                        Log.d("location", "Response is successful");
                        SuccessfulForecastResponse successfulResponse = (SuccessfulForecastResponse) response;

                        final ForecastListAdapter adapter = new ForecastListAdapter(
                                context,
                                android.R.layout.simple_list_item_1,
                                successfulResponse.getList()
                        )
                                ;
                        listview.setAdapter(adapter);

                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                R.string.weather_data_filed,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
            }, new DataParser());

            OpenWeatherRequest openWeatherRequest = (new OpenWeatherRequest())
                    .setLatitude(latitude)
                    .setLongitude(longitude);

            asyncTask.execute((new OpenWeatherFactory()).getForecast(openWeatherRequest));

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.weather_data_filed,
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
