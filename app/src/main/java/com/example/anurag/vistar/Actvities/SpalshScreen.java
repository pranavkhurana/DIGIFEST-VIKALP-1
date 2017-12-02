package com.example.anurag.vistar.Actvities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anurag.vistar.ExtraClasses.Utility;
import com.example.anurag.vistar.Interface.ListLoadedListener;
import com.example.anurag.vistar.MainActivity;
import com.example.anurag.vistar.R;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;


public class SpalshScreen extends AppCompatActivity implements LocationListener {

    public static final String MY_PREFS_NAME = "myPref";
    ProgressBar progressBar;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.getIndeterminateDrawable().setColorFilter(
                Color.WHITE, PorterDuff.Mode.MULTIPLY);


        if (Utility.isConnected(this)) {
           getLocation();
        } else {
//            MyAlert alert = new MyAlert();
//            alert.setCancelable(false);
//            alert.show(getFragmentManager(), "MyAlert");
        }
    }




    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, this);
        } catch (SecurityException e) {
            Log.d("karma", "Excpetion " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String longitude = location.getLongitude() + "";
        String latitude = location.getLatitude() + "";
        //Toast.makeText(this,"Current Location: \" "+"Lat: "+ location.getLatitude() + " Long :" +location.getLongitude(),Toast.LENGTH_LONG).show();
        locationManager.removeUpdates(this);
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("currentLong", longitude);
        editor.putString("currentLat", latitude);
        Log.d("karma","lat is "+latitude +"long is "+longitude);
        editor.apply();
        Log.d("karma", "Location Changed");
        startActivity(new Intent(this,UserActivity.class));


    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(SpalshScreen.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {


    }


}
