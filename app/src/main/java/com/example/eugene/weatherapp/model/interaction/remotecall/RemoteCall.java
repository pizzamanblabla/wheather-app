package com.example.eugene.weatherapp.model.interaction.remotecall;

import android.util.Log;

import com.example.eugene.weatherapp.model.interaction.url.UrlBuilderInterface;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteCall {

    private static final String OPEN_WEATHER_MAP_API = "8e3cd0eed86b3ab900289736e272c185";

    private UrlBuilderInterface urlBuilder;

    public RemoteCall(UrlBuilderInterface urlBuilder) {
        this.urlBuilder = urlBuilder;
    }

    public JSONObject call() {
        try {
            URL url = urlBuilder.build();

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            Log.d("location", "Got json data");

            while ((tmp=reader.readLine()) != null) {
                json.append(tmp).append("\n");
            }

            reader.close();
            connection.disconnect();

            JSONObject data = new JSONObject(json.toString());

            if (data.getInt("cod") != 200) {
                return null;
            }

            return data;
        } catch(Exception e) {
            Log.d("location", "Got Exception: " + e.getMessage(), e);

            return null;
        }
    }
}
