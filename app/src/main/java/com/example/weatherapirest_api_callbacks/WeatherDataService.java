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
