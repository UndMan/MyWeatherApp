package com.example.undesser.myweatherapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.undesser.myweatherapp.dto.dto.WeatherData;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textview;
    EditText edittext;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        textview = (TextView) findViewById(R.id.TextView_Data);
        edittext = (EditText) findViewById(R.id.editText);
        queue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Uri.parse("http://api.openweathermap.org/data/2.5/weather")
                        .buildUpon()
                        .appendQueryParameter("appid", "e0434eb5228dbeb4224694e0b6a35fc6")
                        .appendQueryParameter("q", edittext.getText().toString())
                        .appendQueryParameter("units", "metric")
                        .build()
                        .toString();

                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Gson gson = new Gson();
                                WeatherData data = gson.fromJson(response, WeatherData.class);
                                textview.setText(Double.toString(data.getMain().getTemp()));
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );

                queue.add(request);
            }
        });

    }


}
