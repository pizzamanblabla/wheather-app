package com.example.eugene.weatherapp.model.interaction.remotecall;

import com.example.eugene.weatherapp.model.dto.request.OpenWeatherRequest;
import com.example.eugene.weatherapp.model.interaction.url.UrlBuilder;

public class OpenWeatherFactory {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String WEATHER_METHOD = "weather";
    private static final String FORECAST_METHOD = "forecast";
    private static final String COORDINATES_PARAMS_TEMPLATE = "lat=%s&lon=%s&units=metric";
    private static final String QUERY_PARAMS_TEMPLATE = "q=%s&units=metric";

    public RemoteCall getWeather(OpenWeatherRequest request) {
        UrlBuilder urlBuilder = new UrlBuilder(BASE_URL, WEATHER_METHOD, resolveParamsByRequest(request));

        return new RemoteCall(urlBuilder);
    }

    public RemoteCall getForecast(OpenWeatherRequest request) {
        UrlBuilder urlBuilder = new UrlBuilder(BASE_URL, FORECAST_METHOD, resolveParamsByRequest(request));

        return new RemoteCall(urlBuilder);
    }

    private String resolveParamsByRequest(OpenWeatherRequest request) {
        return request.getQuery() != null
                ? String.format(QUERY_PARAMS_TEMPLATE, request.getQuery())
                : String.format(COORDINATES_PARAMS_TEMPLATE, request.getLatitude(), request.getLongitude());
    }
}
