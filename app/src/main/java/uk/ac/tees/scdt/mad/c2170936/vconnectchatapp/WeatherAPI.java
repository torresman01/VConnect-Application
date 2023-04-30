package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather?appid=c1e16193c78869577a83edced8be3c1f&units=metric")
    Call<OpenWeatherMain>getWeatherWithLocation(@Query("lat")double lat,@Query("lon")double lon);

    @GET("weather?appid=c1e16193c78869577a83edced8be3c1f&units=metric")
    Call<OpenWeatherMain>getWeatherWithCity(@Query("q")String name);
}
