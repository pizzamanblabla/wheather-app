package com.example.eugene.weatherapp.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eugene.weatherapp.R;
import com.example.eugene.weatherapp.model.operation.getForecast.dto.response.ForecastItem;

import java.text.SimpleDateFormat;
import java.util.List;

public class ForecastListAdapter extends ArrayAdapter<ForecastItem> {
    private final Context context;
    private final List<ForecastItem> values;

    public ForecastListAdapter(@NonNull Context context, int resource, List<ForecastItem> values) {
        super(context, -1, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.forecast_item, parent, false);

        SimpleDateFormat format = new SimpleDateFormat("h a MMMM d");

        ForecastItem item = values.get(position);
        TextView temperature = rowView.findViewById(R.id.forecastTemp);
        TextView date = rowView.findViewById(R.id.forecastDate);
        TextView desc = rowView.findViewById(R.id.forecastDesc);
        temperature.setText(item.getTemperature());
        date.setText(format.format(item.getDate()));
        desc.setText(item.getDescription());


        return rowView;
    }

}