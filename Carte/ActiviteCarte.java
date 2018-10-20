package ca.qc.cgmatane.informatique.foodshot;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ca.qc.cgmatane.informatique.foodshot.modele.ModelePublication;
import ca.qc.cgmatane.informatique.foodshot.serveur.LocalisationAPI;

public class ActiviteCarte extends FragmentActivity implements OnMapReadyCallback {

    private List<ModelePublication> listePublication;
    private GoogleMap mMap;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Localisation localisation = new Localisation();
        latitude = localisation.getLatitude();
        longitude = localisation.getLongitude();
        listePublication = new ArrayList<>();
        LocalisationAPI localisationAPI = new LocalisationAPI(latitude, longitude);
        try {
            localisationAPI.execute().get();
            for (int i = 0; i < localisationAPI.getListePublication().size(); i++) {
                listePublication.add(localisationAPI.getListePublication().get(i));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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

        // Add a marker in Sydney and move the camera
        for (int i = 0; i < listePublication.size(); i++){
            LatLng position = new LatLng(listePublication.get(i).getLatitude(), listePublication.get(i).getLongitude());
            mMap.addMarker(new MarkerOptions().position(position).title(listePublication.get(i).getTitre() + listePublication.get(i).getUsername()));
        }

        LatLng position = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));


    }
}
