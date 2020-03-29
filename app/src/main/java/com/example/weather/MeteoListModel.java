package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeteoListModel extends ArrayAdapter<MeteoItem> {
    private List<MeteoItem> meteoItemList;
    private int resource;
    public static Map<String,Integer> images=new HashMap<>(); static{
        images.put("Clear",R.drawable.clear);
        images.put("Clouds",R.drawable.cloudy);
        images.put("Rain",R.drawable.rain);
        images.put("thunderstormspng",R.drawable.thunder);
    }

    public MeteoListModel(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public MeteoListModel(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public MeteoListModel(@NonNull Context context, int resource, @NonNull MeteoItem[] objects) {
        super(context, resource, objects);
    }

    public MeteoListModel(@NonNull Context context, int resource, int textViewResourceId, @NonNull MeteoItem[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public MeteoListModel(@NonNull Context context, int resource, @NonNull List<MeteoItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.meteoItemList = objects;
    }

    public MeteoListModel(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<MeteoItem> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(getContext()).inflate(resource, parent, false);

        ImageView imageView = listItem.findViewById(R.id.imageView);
        TextView textViewTempMax = listItem.findViewById(R.id.textViewmax);
        TextView textViewTempMin = listItem.findViewById(R.id.textViewmin);
        TextView textViewPression = listItem.findViewById(R.id.textViewPression);
        TextView textViewHumidite = listItem.findViewById(R.id.textViewHumidité);
        TextView textViewDate = listItem.findViewById(R.id.textViewdate);
        String key = meteoItemList.get(position).image;

        if(key!=null)
            imageView.setImageResource(images.get(key));
            textViewTempMax.setText(String.valueOf(meteoItemList.get(position).tempMax)+" °C");
            textViewTempMin.setText(String.valueOf(meteoItemList.get(position).tempMin)+" °C");
            textViewPression.setText(String.valueOf(meteoItemList.get(position).pression)+" hPa");
            textViewHumidite.setText(String.valueOf(meteoItemList.get(position).Humidite)+" %");
            textViewDate.setText(String.valueOf(meteoItemList.get(position).date));
        return listItem;
    }

}
