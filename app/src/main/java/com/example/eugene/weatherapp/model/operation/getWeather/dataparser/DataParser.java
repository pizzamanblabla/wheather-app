package com.example.eugene.weatherapp.model.operation.getWeather.dataparser;

import com.example.eugene.weatherapp.model.dto.response.ExceptionalResponse;
import com.example.eugene.weatherapp.model.dto.response.InternalResponse;
import com.example.eugene.weatherapp.model.interaction.dataparser.DataParserInterface;
import com.example.eugene.weatherapp.model.operation.getWeather.dto.response.SuccessfulResponse;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DataParser implements DataParserInterface {
    @Override
    public InternalResponse parse(JSONObject json) {
        try {
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            JSONObject wind = json.getJSONObject("wind");
            JSONObject coords = json.getJSONObject("coord");
            DateFormat df = DateFormat.getDateTimeInstance();

            return (new SuccessfulResponse())
                    .setCity(String.format("%s, %s", json.getString("name").toUpperCase(Locale.US), json.getJSONObject("sys").getString("country")))
                    .setDescription(details.getString("description").toUpperCase(Locale.US))
                    .setCurrentTemperature(String.format("%.2f", main.getDouble("temp")) + "°C")
                    .setHumidity(main.getString("humidity") + "%")
                    .setPressure(main.getString("pressure") + " hPa")
                    .setIcon(setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)
                    ).setMinTemperature(String.format("%.2f", main.getDouble("temp_min")) + "°C")
                    .setMaxTemperature(String.format("%.2f", main.getDouble("temp_max")) + "°C")
                    .setWindSpeed(String.format("%.1f m/s", wind.getDouble("speed")))
                    .setLatitude(coords.getString("lat"))
                    .setLongtitude(coords.getString("lon"));

        } catch (Exception e) {
            return new ExceptionalResponse();
        }
    }

    private static String setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if (actualId == 800) {
            long currentTime = new Date().getTime();

            if (currentTime >= sunrise && currentTime < sunset) {
                icon = "&#xf00d;";
            } else {
                icon = "&#xf02e;";
            }
        } else {

            switch(id) {
                case 2 : icon = "&#xf01e;";
                    break;
                case 3 : icon = "&#xf01c;";
                    break;
                case 7 : icon = "&#xf014;";
                    break;
                case 8 : icon = "&#xf013;";
                    break;
                case 6 : icon = "&#xf01b;";
                    break;
                case 5 : icon = "&#xf019;";
                    break;
            }
        }

        return icon;
    }
}
