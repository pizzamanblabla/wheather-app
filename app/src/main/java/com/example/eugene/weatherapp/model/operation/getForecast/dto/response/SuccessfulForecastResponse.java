package com.example.eugene.weatherapp.model.operation.getForecast.dto.response;

import com.example.eugene.weatherapp.model.dto.response.InternalResponse;
import com.example.eugene.weatherapp.model.type.ResponseType;

import java.util.List;

public class SuccessfulForecastResponse implements InternalResponse {

    private List<ForecastItem> list;

    public List<ForecastItem> getList() {
        return list;
    }

    public SuccessfulForecastResponse setList(List<ForecastItem> list) {
        this.list = list;
        return this;
    }

    @Override
    public ResponseType getType() {
        return ResponseType.SUCCESSFUL;
    }
}
