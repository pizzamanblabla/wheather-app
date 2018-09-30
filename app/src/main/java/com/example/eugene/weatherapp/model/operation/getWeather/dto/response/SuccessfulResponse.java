package com.example.eugene.weatherapp.model.operation.getWeather.dto.response;

import com.example.eugene.weatherapp.model.dto.response.InternalResponse;
import com.example.eugene.weatherapp.model.type.ResponseType;

public class SuccessfulResponse implements InternalResponse {

    private String city;
    private String description;
    private String currentTemperature;
    private String humidity;
    private String pressure;
    private String icon;
    private String minTemperature;
    private String maxTemperature;
    private String windSpeed;
    private String latitude;
    private String longtitude;

    public String getCity() {
        return city;
    }

    public SuccessfulResponse setCity(String city) {
        this.city = city;

        return this;
    }

    public String getDescription() {
        return description;
    }

    public SuccessfulResponse setDescription(String description) {
        this.description = description;

        return this;
    }

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public SuccessfulResponse setCurrentTemperature(String currentTemperature) {
        this.currentTemperature = currentTemperature;

        return this;
    }

    public String getHumidity() {
        return humidity;
    }

    public SuccessfulResponse setHumidity(String humidity) {
        this.humidity = humidity;

        return this;
    }

    public String getPressure() {
        return pressure;
    }

    public SuccessfulResponse setPressure(String pressure) {
        this.pressure = pressure;

        return this;
    }

    public String getIcon() {
        return icon;
    }

    public SuccessfulResponse setIcon(String icon) {
        this.icon = icon;

        return this;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public SuccessfulResponse setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
        return this;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public SuccessfulResponse setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
        return this;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public SuccessfulResponse setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public SuccessfulResponse setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public SuccessfulResponse setLongtitude(String longtitude) {
        this.longtitude = longtitude;
        return this;
    }

    @Override
    public ResponseType getType() {
        return ResponseType.SUCCESSFUL;
    }
}
