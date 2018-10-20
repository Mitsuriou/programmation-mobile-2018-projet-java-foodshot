package ca.qc.cgmatane.informatique.foodshot;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;
import ca.qc.cgmatane.informatique.foodshot.modele.ModeleMessage;
import ca.qc.cgmatane.informatique.foodshot.serveur.NouvellePublicationAPI;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Localisation extends AppCompatActivity {

    //localisation
    private final long UPDATE_INTERVAL = 10 * 1000;
    private final long FASTEST_INTERVAL = 2000;
    private LocationRequest locationRequest;
    private double latitude;
    private double longitude;

    public Localisation(){
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        if (ActivityCompat.checkSelfPermission(Localisation.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            onLocationChanged(locationResult.getLastLocation());
                        }
                    },
                    Looper.myLooper());
        }
        else {
            this.latitude = 0.0;
            this.longitude = 0.0;
        }
    }

    public void onLocationChanged(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

}
