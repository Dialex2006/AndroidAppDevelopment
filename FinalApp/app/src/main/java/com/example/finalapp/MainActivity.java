package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    //GPS location variables
    private double latitude, longitude;

    //switch state variable
    boolean switchStatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting the default switch state (GPS switch)
        Switch switchGPS = (Switch) findViewById(R.id.switchGPS);
        switchGPS.setChecked(switchStatus);

        //if switched is activated
        if (switchGPS.isChecked()) {
            LocationManager locationMaganer = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Check if the user has granted permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //we don't have permission so ask it here:
                Toast.makeText(this, "No permission!", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        0
                );

                return;
            }


            //trying to request location updates
            try {
                locationMaganer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } catch (Exception e) {
                Log.d("Location problem", e.toString());
            }

        }

    }



    //Method to start another activity to show Currency exchange rates
    public void openRatesActivity (View view) {
        //creating an intent
        Intent intent = new Intent(this, RatesActivity.class);
        startActivity(intent);
    }



    public void showMapActivity(View view) {

        // initiate a Switch
        Switch switchGPS = (Switch) findViewById(R.id.switchGPS);
        if (switchGPS.isChecked()) {

            //new intent to save location data: latitude and longitude
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri geoLocation = Uri.parse("geo:" + latitude + "," + longitude + "?z=12");
            intent.setData(geoLocation);

            if (intent.resolveActivity(getPackageManager()) != null) {
                //checking if coordinates are zero or not to make sure GPS location has been defined
                if (latitude == 0 && longitude == 0)
                    Toast.makeText(this, "Try again in a few seconds to let GPS sensor define coordinates", Toast.LENGTH_LONG).show();
                //starting activity if it's not NULL and has non-zero coordinates
                else startActivity(intent);
            }

        }
        //Show a message in case the switch was not activated
        else Toast.makeText(this, "GPS sensor is NOT active! Please switch the checkbox above", Toast.LENGTH_LONG).show();

    }


    //updating coordinates whenever GPS location is changed
    @Override
    public void onLocationChanged(@NonNull Location location) {
        //We now can read the latitude and longitude from the "location" parameter
        latitude = location.getLatitude();
        longitude = location.getLongitude();

    }


    public void startGPS (View view) {

        // initiate a Switch
        Switch switchGPS = (Switch) findViewById(R.id.switchGPS);
        switchStatus = true;
        //switchGPS.setChecked(switchStatus);

        if (switchGPS.isChecked()) {
            LocationManager locationMaganer = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Check if the user has granted permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //we don't have permission so ask it here:
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        0
                );

                    return;
            }
            switchStatus = true;


            //trying to request location updates
            try {
                locationMaganer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } catch (Exception e) {
                Log.d("Location problem", e.toString());
            }


        }
        else switchStatus = false;

    }


    //method on resuming activity
    @Override
    public void onResume(){
        super.onResume();

        //setting the GPS switch state
        Switch switchGPS = (Switch) findViewById(R.id.switchGPS);
        switchGPS.setChecked(switchStatus);
    }



}