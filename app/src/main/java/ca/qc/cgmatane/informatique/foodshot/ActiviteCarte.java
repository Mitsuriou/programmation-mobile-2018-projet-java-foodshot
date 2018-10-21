package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;
import ca.qc.cgmatane.informatique.foodshot.modele.ModelePublication;
import ca.qc.cgmatane.informatique.foodshot.serveur.LocalisationAPI;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class ActiviteCarte extends FragmentActivity implements OnMapReadyCallback {

    private List<ModelePublication> listePublication;
    private GoogleMap mMap;

    //localisation
    private final long UPDATE_INTERVAL = 10 * 1000;
    private final long FASTEST_INTERVAL = 2000;
    private LocationRequest locationRequest;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(ActiviteCarte.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "FoodShot n'a pas la permission d'accéder à votre localisation.", Toast.LENGTH_SHORT).show();
            this.finish();
            return;
        }

        startLocationUpdates();

        SharedPreferences preferencesPartageesLocatisation = getSharedPreferences(Constantes.PREFERENCES_LOCALISATION, Context.MODE_PRIVATE);
        this.latitude = Double.valueOf(preferencesPartageesLocatisation.getString("latitude", "" + 0));
        this.longitude = Double.valueOf(preferencesPartageesLocatisation.getString("longitude", "" + 0));

        if (this.latitude == 0.0 && this.longitude == 0.0) {
            Toast.makeText(this, "Impossible de trouver votre localisation. Veuillez rééssayer dans un instant.", Toast.LENGTH_SHORT).show();
            this.finish();
            return;
        }

        SharedPreferences preferencesPartageesApplication = getSharedPreferences(Constantes.PREFERENCES_GENERALES, Context.MODE_PRIVATE);

        listePublication = new ArrayList<>();
        LocalisationAPI localisationAPI = new LocalisationAPI(preferencesPartageesApplication.getInt("id_utilisateur", -1), latitude, longitude);
        try {
            localisationAPI.execute().get();
            for (ModelePublication publication : localisationAPI.getListePublication()) {
                listePublication.add(publication);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.vue_activite_carte);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (ModelePublication publication : this.listePublication) {
            LatLng position = new LatLng(publication.getLatitude(), publication.getLongitude());
            mMap.addMarker(new MarkerOptions().position(position).title(publication.getTitre() + publication.getNomUtilisateur()));
        }
        if (ActivityCompat.checkSelfPermission(ActiviteCarte.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
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

        if (ActivityCompat.checkSelfPermission(ActiviteCarte.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getFusedLocationProviderClient(ActiviteCarte.this).requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            onLocationChanged(locationResult.getLastLocation());
                        }
                    },
                    Looper.myLooper());
        }
    }

    public void onLocationChanged(Location location) {
        SharedPreferences preferencesPartagees = getSharedPreferences(Constantes.PREFERENCES_LOCALISATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editeur = preferencesPartagees.edit();

        editeur.putString("latitude", "" + location.getLatitude());
        editeur.putString("longitude", "" + location.getLongitude());
        editeur.apply();
        editeur.commit();
    }
}
