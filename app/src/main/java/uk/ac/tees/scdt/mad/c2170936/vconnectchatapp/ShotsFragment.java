package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ShotsFragment extends Fragment {

    private TextView city, temperature, weatherCondition, humidity, maxTemp, minTemp, pressure, wind;
    private ImageView imageView;


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


        return view;
    }
}