package com.example.eugene.weatherapp.model.operation.getForecast.dto.response;

import java.util.Date;

public class ForecastItem {

    private String description;
    private String temperature;
    private String pressure;
    private String humidity;
    private String windSpeed;
    private Date date;

    public String getDescription() {
        return description;
    }

    public ForecastItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTemperature() {
        return temperature;
    }

    public ForecastItem setTemperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public String getPressure() {
        return pressure;
    }

    public ForecastItem setPressure(String pressure) {
        this.pressure = pressure;
        return this;
    }

    public String getHumidity() {
        return humidity;
    }

    public ForecastItem setHumidity(String humidity) {
        this.humidity = humidity;
        return this;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public ForecastItem setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ForecastItem setDate(Date date) {
        this.date = date;
        return this;
    }
}
