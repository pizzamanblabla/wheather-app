package com.example.eugene.weatherapp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.eugene.weatherapp.model.dto.response.ErroneousResponse;
import com.example.eugene.weatherapp.model.dto.response.InternalResponse;
import com.example.eugene.weatherapp.model.interaction.dataparser.DataParserInterface;
import com.example.eugene.weatherapp.model.interaction.remotecall.RemoteCall;

import org.json.JSONObject;

public class DataReceiver {

    public interface AsyncResponse {

        void processFinish(InternalResponse response);
    }

    public static class placeIdTask extends AsyncTask<RemoteCall, Void, JSONObject> {

        private DataParserInterface dataParser;

        private AsyncResponse delegate = null;

        public placeIdTask(AsyncResponse asyncResponse, DataParserInterface dataParser) {
            delegate = asyncResponse;
            this.dataParser = dataParser;
        }

        @Override
        protected JSONObject doInBackground(RemoteCall ... params) {
            JSONObject jsonWeather = null;

            try {
                Log.d("location", "Getting Weather JSON");
                jsonWeather = params[0].call();
            } catch (Exception e) {
                Log.d("error", "Cannot process JSON results", e);
            }

            return jsonWeather;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (json != null) {
                Log.d("location", json.toString());

                delegate.processFinish(dataParser.parse(json));
            } else {
                Log.d("location", "Received empty response");

                delegate.processFinish(new ErroneousResponse());
            }
        }
    }
}
