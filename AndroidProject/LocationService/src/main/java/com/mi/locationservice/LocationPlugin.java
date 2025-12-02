package com.mi.locationservice;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.unity3d.player.UnityPlayer;

public class LocationPlugin {

    private static final int REQUEST_LOCATION_PERMISSION = 1001;
    private static Activity activity;
    private static LocationManager locationManager;

    public static void Initialize(Activity unityActivity) {
        activity = unityActivity;
        locationManager = (LocationManager) activity.getSystemService(Activity.LOCATION_SERVICE);
    }

    public static void RequestLocation() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }
        startLocationUpdates();
    }

    private static void startLocationUpdates() {
        try {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        String locationData = location.getLatitude() + "," + location.getLongitude();
                        Log.d("LocationPlugin", "Location: " + locationData);
                        UnityPlayer.UnitySendMessage("LocationListener", "OnLocationReceived", locationData);
                    }
                    locationManager.removeUpdates(this);
                }
                @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
                @Override public void onProviderEnabled(String provider) {}
                @Override public void onProviderDisabled(String provider) {}
            }, null);
        } catch (SecurityException e) {
            Log.e("LocationPlugin", "SecurityException: " + e.getMessage());
        }
    }
}
