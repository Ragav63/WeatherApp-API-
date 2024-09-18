# WeatherApp-API-
API callbacks involves several steps, from setting up the user interface to making network requests to fetch weather data from an external API

Steps to Create a Weather App Using API Callbacks
Set Up Your Project:

Create a new Android project in Android Studio.
Ensure you have the necessary permissions in your AndroidManifest.xml for internet access:
xml
Copy code
<uses-permission android:name="android.permission.INTERNET" />
Design the User Interface:

Create a simple layout in activity_main.xml with elements to display weather information (e.g., temperature, weather description, city name) and a button to fetch the data.

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/btn_getCityId"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:text="Get City Id"
        app:layout_constraintEnd_toStartOf="@+id/btn_getWeatherByCityId"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btn_getWeatherByCityId"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Weather by Id"
        app:layout_constraintStart_toEndOf="@+id/btn_getCityId"
        app:layout_constraintEnd_toStartOf="@+id/btn_getWeatherByCityName"
        app:layout_constraintTop_toTopOf="@id/btn_getCityId" />

    <Button
        android:id="@+id/btn_getWeatherByCityName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="20dp"
        android:text="Weather by Name"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/btn_getWeatherByCityId"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/et_dataInput"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginTop="10dp"
        android:layout_marginEnd="20dp"
        android:ems="10"
        android:hint="City Name"
        android:inputType="text"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btn_getCityId" />

    <ListView
        android:id="@+id/lv_weatherReports"
        android:layout_width="409dp"
        android:layout_height="626dp"
        android:layout_marginStart="20dp"
        android:layout_marginTop="10dp"
        android:layout_marginEnd="20dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/et_dataInput" />
</androidx.constraintlayout.widget.ConstraintLayout>

Create a Data Class for the API Response:

Define a data class to map the JSON response from the weather API.


package com.example.weatherapirest_api_callbacks;

public class WeatherReportModel {

    private int id;
    private String weather_state_name;
    private String weather_state_abbr;
    private String wind_direction_compass;
    private String created;
    private String applicable_date;
    private float min_temp;
    private float max_temp;
    private float the_temp;
    private float wind_speed;
    private float wind_direction;
    private int air_pressure;
    private int humidity;
    private float visibility;
    private int predictability;

    public WeatherReportModel(int id, String weather_state_name, String weather_state_abbr, String wind_direction_compass, String created, String applicable_date, float min_temp, float max_temp, float the_temp, float wind_speed, float wind_direction, int air_pressure, int humidity, float visibility, int predictability) {
        this.id = id;
        this.weather_state_name = weather_state_name;
        this.weather_state_abbr = weather_state_abbr;
        this.wind_direction_compass = wind_direction_compass;
        this.created = created;
        this.applicable_date = applicable_date;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.the_temp = the_temp;
        this.wind_speed = wind_speed;
        this.wind_direction = wind_direction;
        this.air_pressure = air_pressure;
        this.humidity = humidity;
        this.visibility = visibility;
        this.predictability = predictability;
    }

    public WeatherReportModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeather_state_name() {
        return weather_state_name;
    }

    public void setWeather_state_name(String weather_state_name) {
        this.weather_state_name = weather_state_name;
    }

    public String getWeather_state_abbr() {
        return weather_state_abbr;
    }

    public void setWeather_state_abbr(String weather_state_abbr) {
        this.weather_state_abbr = weather_state_abbr;
    }

    public String getWind_direction_compass() {
        return wind_direction_compass;
    }

    public void setWind_direction_compass(String wind_direction_compass) {
        this.wind_direction_compass = wind_direction_compass;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getApplicable_date() {
        return applicable_date;
    }

    public void setApplicable_date(String applicable_date) {
        this.applicable_date = applicable_date;
    }

    public float getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(float min_temp) {
        this.min_temp = min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(float max_temp) {
        this.max_temp = max_temp;
    }

    public float getThe_temp() {
        return the_temp;
    }

    public void setThe_temp(float the_temp) {
        this.the_temp = the_temp;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public float getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(float wind_direction) {
        this.wind_direction = wind_direction;
    }

    public int getAir_pressure() {
        return air_pressure;
    }

    public void setAir_pressure(int air_pressure) {
        this.air_pressure = air_pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public int getPredictability() {
        return predictability;
    }

    public void setPredictability(int predictability) {
        this.predictability = predictability;
    }

    @Override
    public String toString() {
        return  weather_state_name + '\'' +
                ", Date='" + applicable_date + '\'' +
                ", Low=" + min_temp +
                ", High=" + max_temp +
                ", Temp=" + the_temp ;
    }
}



Create singleton class for making it as single instance of api call 

package com.example.weatherapirest_api_callbacks;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton instance;
    private RequestQueue requestQueue;

    private ImageLoader imageLoader;
    private static Context ctx;

    private MySingleton(Context context){
        ctx=context;
        requestQueue=getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context){
        if (instance==null){
            instance=new MySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(ctx.getApplicationContext());

        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}


add dependency


implementation("com.android.volley:volley:1.2.1")


package com.example.weatherapirest_api_callbacks;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";


    public static Context context;
    static String cityId;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(String cityId);
    }

    public static void getCityId(String cityName, VolleyResponseListener volleyResponseListener){

        String url= QUERY_FOR_CITY_ID +cityName;

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cityId="";
                try {
                    JSONObject cityInfo=response.getJSONObject(0);
                    cityId=cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(context, "City Id "+cityId, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onResponse(cityId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something Wrong");
            }

        });
        MySingleton.getInstance(context).addToRequestQueue(request);
        //return cityId;
    }

    public interface ForeCastByIdResponseListener {
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getCityForecastByID(String cityId, ForeCastByIdResponseListener foreCastByIdResponseListener){
        List<WeatherReportModel> weatherReportModels = new ArrayList<>();

        String url=QUERY_FOR_CITY_WEATHER_BY_ID+cityId;

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    JSONArray consolidated_weather_list=response.getJSONArray("consolidated_weather");

                    for(int i=0;i<consolidated_weather_list.length();i++) {
                        WeatherReportModel one_day_weather=new WeatherReportModel();

                        JSONObject first_day_from_api = (JSONObject) consolidated_weather_list.get(i);
                        one_day_weather.setId(first_day_from_api.getInt("id"));
                        one_day_weather.setWeather_state_name(first_day_from_api.getString("weather_state_name"));
                        one_day_weather.setWeather_state_abbr(first_day_from_api.getString("weather_state_abbr"));
                        one_day_weather.setWind_direction_compass(first_day_from_api.getString("wind_direction_compass"));
                        one_day_weather.setCreated(first_day_from_api.getString("created"));
                        one_day_weather.setApplicable_date(first_day_from_api.getString("applicable_date"));
                        one_day_weather.setMin_temp(first_day_from_api.getLong("min_temp"));
                        one_day_weather.setMax_temp(first_day_from_api.getLong("max_temp"));
                        one_day_weather.setThe_temp(first_day_from_api.getLong("the_temp"));
                        one_day_weather.setWind_speed(first_day_from_api.getLong("wind_speed"));
                        one_day_weather.setWind_direction(first_day_from_api.getLong("wind_direction"));
                        one_day_weather.setAir_pressure(first_day_from_api.getInt("air_pressure"));
                        one_day_weather.setHumidity(first_day_from_api.getInt("humidity"));
                        one_day_weather.setVisibility(first_day_from_api.getLong("visibility"));
                        one_day_weather.setPredictability(first_day_from_api.getInt("predictability"));

                        weatherReportModels.add(one_day_weather);
                    }

                    foreCastByIdResponseListener.onResponse(weatherReportModels);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public interface GetCityForeCastByNameCallback{
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }
    public void getCityForeCastByName(String cityName, GetCityForeCastByNameCallback getCityForeCastByNameCallback){
        getCityId(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String cityId) {
                getCityForecastByID(cityId, new ForeCastByIdResponseListener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        getCityForeCastByNameCallback.onResponse(weatherReportModels);
                    }
                });
            }
        });
    }
}


and then call those from the MainActivity.java

package com.example.weatherapirest_api_callbacks;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_cityId, btn_getWeatherById, btn_getWeatherByName;
    EditText et_dataInput;

    ListView lv_weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_cityId=findViewById(R.id.btn_getCityId);
        btn_getWeatherById=findViewById(R.id.btn_getWeatherByCityId);
        btn_getWeatherByName=findViewById(R.id.btn_getWeatherByCityName);

        et_dataInput=findViewById(R.id.et_dataInput);

        lv_weatherReport=findViewById(R.id.lv_weatherReports);

        WeatherDataService weatherDataService=new WeatherDataService(MainActivity.this);


        btn_cityId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getCityId(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String cityId) {
                        Toast.makeText(MainActivity.this, "Returned City Id"+cityId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_getWeatherById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getCityForecastByID(et_dataInput.getText().toString(),new WeatherDataService.ForeCastByIdResponseListener(){

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModel) {
                        ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,weatherReportModel);
                        lv_weatherReport.setAdapter(arrayAdapter);
                    }
                });
            }
        });

        btn_getWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getCityForeCastByName(et_dataInput.getText().toString(),new WeatherDataService.GetCityForeCastByNameCallback(){

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModel) {
                        ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,weatherReportModel);
                        lv_weatherReport.setAdapter(arrayAdapter);
                    }
                });            }
        });
    }
}

Here you must use your own apikey to get those details and all accordingly. Without that it wouldn't work.
