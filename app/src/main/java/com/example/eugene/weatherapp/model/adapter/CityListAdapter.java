package com.example.eugene.weatherapp.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eugene.weatherapp.R;
import com.example.eugene.weatherapp.model.entity.City;
import com.example.eugene.weatherapp.viewmodel.RecyclerViewClickListener;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {

    private final LayoutInflater mInflater;
    private List<City> cities;
    private RecyclerViewClickListener itemListener;

    public CityListAdapter(Context context, RecyclerViewClickListener itemListener) {
        this.itemListener = itemListener;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.city_list_item, parent, false);
        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        if (cities != null) {
            City current = cities.get(position);
            holder.wordItemView.setText(current.getName());
        } else {
            holder.wordItemView.setText(R.string.no_cities_in_list);
        }
    }

    public void setCities(List<City> cities){
        this.cities = cities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (cities != null) {
            return cities.size();
        } else{
            return 0;
        }
    }

    class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView wordItemView;

        private CityViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}
