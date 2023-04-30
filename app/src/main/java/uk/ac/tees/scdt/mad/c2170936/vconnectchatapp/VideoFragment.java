package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoFragment extends Fragment {

    private TextView city, temperature, weatherCondition, humidity, maxTemp, minTemp, pressure, wind;
    private ImageView imageView;

    LocationManager locationManager;
    LocationListener locationListener;
    double lat,lon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_shots, container, false);

        city = view.findViewById(R.id.textViewCity);
        temperature = view.findViewById(R.id.textViewTemp);
        weatherCondition = view.findViewById(R.id.textViewWeatherCond);
        humidity = view.findViewById(R.id.textViewHumidity);
        maxTemp = view.findViewById(R.id.textViewMaxTemp);
        minTemp = view.findViewById(R.id.textViewMinTemp);
        pressure = view.findViewById(R.id.textViewPressure);
        wind = view.findViewById(R.id.textViewWind);
        imageView = view.findViewById(R.id.imageViewBackground);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();

                Log.e("lat :",String.valueOf(lat));
                Log.e("lon :",String.valueOf(lon));

                getWeatherData(lat,lon);
            }
        };


        if (ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) getContext(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,50,locationListener);
        }


        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && permissions.length > 0 && ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,50,locationListener);
        }
    }

    public void getWeatherData(double lat,double lon){

        WeatherAPI weatherAPI = RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<OpenWeatherMain> call = weatherAPI.getWeatherWithLocation(lat,lon);

        call.enqueue(new Callback<OpenWeatherMain>() {
            @Override
            public void onResponse(Call<OpenWeatherMain> call, Response<OpenWeatherMain> response) {

                city.setText(response.body().getName()+", "+response.body().getSys().getCountry());
                temperature.setText(response.body().getMain().getTemp()+" °C");
                weatherCondition.setText(response.body().getWeather().get(0).getDescription());
                humidity.setText(" : "+response.body().getMain().getHumidity()+"%");
                maxTemp.setText(" : "+response.body().getMain().getTempMax()+" °C");
                minTemp.setText(" : "+response.body().getMain().getTempMin()+" °C");
                pressure.setText(" : "+response.body().getMain().getPressure());
                wind.setText(" : "+response.body().getWind().getSpeed());

                String iconCode = response.body().getWeather().get(0).getIcon();
                Picasso.get().load("https://openweathermap.org/img/wn/"+iconCode+"@2x.png")
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(imageView);


            }

            @Override
            public void onFailure(Call<OpenWeatherMain> call, Throwable t) {

            }
        });
    }












}