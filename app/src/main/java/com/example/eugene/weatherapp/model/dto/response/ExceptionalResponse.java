package com.example.eugene.weatherapp.model.dto.response;

import com.example.eugene.weatherapp.model.type.ResponseType;

public class ExceptionalResponse implements InternalResponse {
    @Override
    public ResponseType getType() {
        return ResponseType.EXCEPTIONAL;
    }
}
