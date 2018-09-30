package com.example.eugene.weatherapp.model.interaction.dataparser;

import com.example.eugene.weatherapp.model.dto.response.InternalResponse;

import org.json.JSONObject;

public interface DataParserInterface {
    InternalResponse parse(JSONObject json);
}
