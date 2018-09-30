package com.example.eugene.weatherapp.model.operation.getForecast.dataparser;

import android.util.Log;

import com.example.eugene.weatherapp.model.dto.response.ExceptionalResponse;
import com.example.eugene.weatherapp.model.dto.response.InternalResponse;
import com.example.eugene.weatherapp.model.interaction.dataparser.DataParserInterface;
import com.example.eugene.weatherapp.model.operation.getForecast.dto.response.ForecastItem;
import com.example.eugene.weatherapp.model.operation.getForecast.dto.response.SuccessfulForecastResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataParser implements DataParserInterface {
    @Override
    public InternalResponse parse(JSONObject json) {
        try {
            JSONArray list = json.getJSONArray("list");
            List<ForecastItem> forecastItemList = new ArrayList<>();

            for (int i = 0; i < list.length(); i++) {
                forecastItemList.add(parseForecast(list.getJSONObject(i)));
            }

            return (new SuccessfulForecastResponse()).setList(forecastItemList);
        } catch (Exception e) {
            return new ExceptionalResponse();
        }
    }

    public ForecastItem parseForecast(JSONObject json) {
        try {
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            JSONObject wind = json.getJSONObject("wind");
            DateFormat df = DateFormat.getDateTimeInstance();

            Timestamp ts = new Timestamp(json.getLong("dt") * 1000);
            Date date = new Date(ts.getTime());

            return (new ForecastItem())
                    .setDescription(details.getString("description").toUpperCase(Locale.US))
                    .setTemperature(String.format("%.0f", main.getDouble("temp")) + "°C")
                    .setHumidity(main.getString("humidity") + "%")
                    .setPressure(main.getString("pressure") + " hPa")
                    .setWindSpeed(String.format("%.1f m/s", wind.getDouble("speed")))
                    .setDate(date);

        } catch (Exception e) {
            return new ForecastItem();
        }
    }
}
