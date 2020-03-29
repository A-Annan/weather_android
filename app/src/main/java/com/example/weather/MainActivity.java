package com.example.weather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.MeteoItem;
import com.example.weather.MeteoListModel;
import com.example.weather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText CityET;
    private ListView WeatherList;
    List<MeteoItem> data = new ArrayList<>();
    private MeteoListModel model;
    private ImageButton Ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CityET = findViewById(R.id.editTextName);
        WeatherList = findViewById(R.id.listView);
        Ok = findViewById(R.id.imageButton);
        model = new MeteoListModel(getApplicationContext(), R.layout.listviewlayout, data);
        WeatherList.setAdapter(model);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT);
                toast.show();
                data.clear();
                model.notifyDataSetChanged();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String cityname = CityET.getText().toString();
                String url = "https://samples.openweathermap.org/data/2.5/forecast?q=" + cityname + "&appid=a4578e39643716894ec78b28a71c7110";
                Log.e("Response", url);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    Log.e("Response", response);

                                    JSONObject jsonObject = null;
                                    JSONArray jsonArray = null;
                                    jsonObject = new JSONObject(response);
                                    jsonArray = jsonObject.getJSONArray("list");
                                    Log.i("Json",jsonArray.toString());
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        MeteoItem meteoItem = new MeteoItem();
                                        JSONObject d = jsonArray.getJSONObject(i);
                                        Date date = new Date(d.getLong("dt") * 1000);
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy'T'HH:mm");
                                        String dateString = sdf.format(date);
                                        JSONObject main = d.getJSONObject("main");
                                        JSONArray weather = d.getJSONArray("weather");
                                        int tempMin = (int) (main.getDouble("temp_min") - 273.15);
                                        int tempMax = (int) (main.getDouble("temp_max") - 273.15);
                                        int pression = main.getInt("pressure");
                                        int humidity = main.getInt("humidity");
//data.add(i, String.format(dateString+"\n"+"Min=%d °c, Max=%d °C",tempMin,tempMax));
                                        meteoItem.tempMax = tempMax;
                                        meteoItem.tempMin = tempMin;
                                        meteoItem.pression = pression;
                                        meteoItem.Humidite = humidity;
                                        meteoItem.date = dateString;
                                        meteoItem.image = weather.getJSONObject(0).getString("main");
                                            data.add(meteoItem);

                                    }
                                    model.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("ESPONSE", "ROR");

                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("this", "error");
                        Toast toast = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT);
                        toast.show();


                    }
                });
                queue.add(stringRequest);
            }
        });


    }
}
