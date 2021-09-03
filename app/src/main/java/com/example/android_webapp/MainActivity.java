package com.example.android_webapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btn_cityID, btn_getWeatherByID, btn_getWeatherByName;
    EditText et_dataInput;
    ListView lv_weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign value to each control on the layout
        btn_cityID = findViewById(R.id.btn_getCityID);
        btn_getWeatherByID = findViewById(R.id.btn_getWeatherCityID);
        btn_getWeatherByName = findViewById(R.id.btn_getWeatherByCityName);
        et_dataInput = findViewById(R.id.et_dataInput);
        lv_weatherReport = findViewById(R.id.lv_weatherReport);

        // click listeners for each button
        btn_cityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("btn city click");
                callApi();
            }
        });

        btn_getWeatherByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("get w by id");
            }
        });

        btn_getWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("get w by name");
            }
        });


    }

    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void callApi () {
        String queryInput = et_dataInput.getText().toString();
        String url ="https://www.metaweather.com/api/location/search/?query=" + queryInput;

// Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        toastMessage("Response is: "+ response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                toastMessage("Error Occurred");
//            }
//        });
        JsonArrayRequest  request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String cityID = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                toastMessage("City ID = " + cityID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastMessage("Something Wrong");
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(request);
    }
}