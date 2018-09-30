package com.example.eugene.weatherapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.weatherapp.R;
import com.example.eugene.weatherapp.model.DataReceiver;
import com.example.eugene.weatherapp.model.dto.response.InternalResponse;
import com.example.eugene.weatherapp.model.dto.request.OpenWeatherRequest;
import com.example.eugene.weatherapp.model.interaction.remotecall.OpenWeatherFactory;
import com.example.eugene.weatherapp.model.operation.getWeather.dataparser.DataParser;
import com.example.eugene.weatherapp.model.operation.getWeather.dto.response.SuccessfulResponse;
import com.example.eugene.weatherapp.model.type.ResponseType;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_LATITUDE = "49.2511597";
    private static final String DEFAULT_LONGITUDE = "-123.1453633";

    private static final int REQUEST_FINE_LOCATION = 101;
    private boolean isCustomLocation = false;
    private String longitude;
    private String latitude;

    private TextView cityField,
            detailsField,
            currentTemperatureField,
            humidityField,
            pressureField,
            weatherIcon,
            minTemp,
            maxTemp,
            windSpeed;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.d("location", "permissions refused");
                    Toast.makeText(getApplicationContext(), "Need location permissions to continue", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("location", "Permission received");
                    requestCurrentWeather();
                }

                break;
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

        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            longitude = bundle.getString("longitude");
            latitude = bundle.getString("latitude");
            isCustomLocation = true;
        }

        Typeface weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");
        Typeface fontAwesome = Typeface.createFromAsset(getAssets(), "fonts/FontAwesome.ttf");

        cityField = findViewById(R.id.city_field);
        detailsField = findViewById(R.id.details_field);
        currentTemperatureField = findViewById(R.id.current_temperature_field);
        humidityField = findViewById(R.id.humidity_field);
        pressureField = findViewById(R.id.pressure_field);
        weatherIcon = findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        minTemp = findViewById(R.id.minTempField);
        maxTemp = findViewById(R.id.maxTempField);
        windSpeed = findViewById(R.id.windSpeedField);

        Button updateButton = findViewById(R.id.updateWeatherBtn);

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestCurrentWeather();
            }
        });

        Button forecastButton = findViewById(R.id.goToForecast);
        forecastButton.setTypeface(weatherFont);

        forecastButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Forecast.class);
                Bundle bundle = new Bundle();
                bundle.putString("longitude", getLongitude());
                bundle.putString("latitude", getLatitude());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        Button cityListButton = (Button)findViewById(R.id.goToListBtn);
        cityListButton.setTypeface(fontAwesome);

        cityListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityList.class);
                startActivity(intent);
            }
        });

        requestCurrentWeather();
    }

    private void requestCurrentWeather() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (!isCustomLocation && ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Log.d("location", "Real location received");
                        Log.d("location", "Logntitude:" + String.valueOf(location.getLongitude()));
                        Log.d("location", "Latitude:" + String.valueOf(location.getLatitude()));
                        latitude = String.valueOf(location.getLatitude());
                        longitude = String.valueOf(location.getLongitude());

                        onLocationChanged(getLatitude(), getLongitude());
                    } else {
                        Log.d("location", "Null location response");
                        onLocationChanged(getLatitude(), getLongitude());
                    }
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isCustomLocation) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            }

            onLocationChanged(getLatitude(), getLongitude());
        }
    }

    private void onLocationChanged(String latitude, String longtitude) {
        Log.d("location", "Function Called");
        DataReceiver.placeIdTask asyncTask = new DataReceiver.placeIdTask(new DataReceiver.AsyncResponse() {
            public void processFinish(InternalResponse response) {
                Log.d("location", "Got remote response");

                if (response.getType() == ResponseType.SUCCESSFUL) {
                    Log.d("location", "Response is successful");
                    SuccessfulResponse successfulResponse = (SuccessfulResponse) response;

                    cityField.setText(successfulResponse.getCity());
                    detailsField.setText(successfulResponse.getDescription());
                    currentTemperatureField.setText(successfulResponse.getCurrentTemperature());
                    humidityField.setText(successfulResponse.getHumidity());
                    pressureField.setText(successfulResponse.getPressure());
                    weatherIcon.setText(Html.fromHtml(successfulResponse.getIcon()));
                    minTemp.setText(successfulResponse.getMinTemperature());
                    maxTemp.setText(successfulResponse.getMaxTemperature());
                    windSpeed.setText(successfulResponse.getWindSpeed());
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
                .setLongitude(longtitude);

        asyncTask.execute((new OpenWeatherFactory()).getWeather(openWeatherRequest));
    }

    private String getLatitude() {
        if (latitude == null) {
            latitude = DEFAULT_LATITUDE;
        }

        return  latitude;
    }

    private String getLongitude() {
        if (longitude == null) {
            longitude = DEFAULT_LONGITUDE;
        }

        return longitude;
    }
}
