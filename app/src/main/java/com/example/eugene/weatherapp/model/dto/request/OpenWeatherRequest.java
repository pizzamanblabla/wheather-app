package com.example.eugene.weatherapp.model.dto.request;

public class OpenWeatherRequest implements InternalRequest {
    private String latitude;
    private String longitude;
    private String query;

    public String getLatitude() {
        return latitude;
    }

    public OpenWeatherRequest setLatitude(String latitude) {
        this.latitude = latitude;

        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public OpenWeatherRequest setLongitude(String longitude) {
        this.longitude = longitude;

        return this;
    }

    public String getQuery() {
        return query;
    }

    public OpenWeatherRequest setQuery(String query) {
        this.query = query;
        return this;
    }
}
