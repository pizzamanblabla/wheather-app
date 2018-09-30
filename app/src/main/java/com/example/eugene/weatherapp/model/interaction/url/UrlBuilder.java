package com.example.eugene.weatherapp.model.interaction.url;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilder implements UrlBuilderInterface {
    private String baseUrl;
    private String method;
    private String params;

    public UrlBuilder(String baseUrl, String method, String params) {
        this.baseUrl = baseUrl;
        this.method = method;
        this.params = params;
    }

    @Override
    public URL build() {
        try {
            String requestString = baseUrl + method + "?" + params;

            return new URL(requestString);
        } catch (MalformedURLException e) {
            Log.d("exception", e.getMessage());

            return null;
        }
    }
}
