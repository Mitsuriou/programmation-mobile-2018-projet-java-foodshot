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

public class ActiviteNouvellePublication extends AppCompatActivity {

    private static final int DEMANDE_CAM = 1102;
    private static final String DOSSIER_PHOTO = "FoodShot";

    //localisation
    private final long UPDATE_INTERVAL = 10 * 1000;
    private final long FASTEST_INTERVAL = 2000;
    private LocationRequest locationRequest;
    private double latitude;
    private double longitude;

    // enregistrement de la photo prise
    String outputFilePath;

    // composants graphiques
    protected Button boutonCaptureImage;
    protected Button boutonPosterPublication;
    protected EditText champTitre;
    protected EditText champDescription;
    protected TextView affichageErreurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSharedPreferences(Constantes.COULEURS_PREFERENCES, Context.MODE_PRIVATE).getInt("theme", 1) == 1) {
            setTheme(R.style.AppTheme);
        }
        else
            setTheme(R.style.AppThemeNoir);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_nouvelle_publication);

        this.demanderPermissions();
        this.startLocationUpdates();

        this.champTitre = (EditText) findViewById(R.id.champ_titre_nouvelle_publication);
        this.champDescription = (EditText) findViewById(R.id.champ_description_nouvelle_publication);
        this.affichageErreurs = (TextView) findViewById(R.id.affichage_erreurs_nouvelle_publication);

        this.boutonCaptureImage = (Button) findViewById(R.id.bouton_demarrer_appareil_photo);
        this.boutonCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aucuneAutorisationPourPublier()) {
                    finish();
                    return;
                }
                onCamera();
            }
        });

        this.boutonPosterPublication = (Button) findViewById(R.id.poster_nouvelle_publication);
        this.boutonPosterPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                affichageErreurs.setText("");

                if (aucuneAutorisationPourPublier()) {
                    finish();
                    return;
                }
                if (champTitre.equals("")) {
                    affichageErreurs.setText("Veuillez donner un titre à votre publication");
                    return;
                }

                SharedPreferences preferencesPartagees = getSharedPreferences(Constantes.MES_PREFERENCES, Context.MODE_PRIVATE);

                NouvellePublicationAPI nouvellePublicationAPI = new NouvellePublicationAPI(
                        champTitre.getText().toString(),
                        champDescription.getText().toString(),
                        latitude,
                        longitude,
                        preferencesPartagees.getInt("id_utilisateur", -1)
                );

                try {
                    nouvellePublicationAPI.execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (nouvellePublicationAPI.getListeMessages().size() == 1) {
                    for (ModeleMessage message : nouvellePublicationAPI.getListeMessages()) {
                        affichageErreurs.append(message.getMessage());
                    }
                }
                else {
                    for (ModeleMessage message : nouvellePublicationAPI.getListeMessages()) {
                        affichageErreurs.append(message.getMessage() + "\n");
                    }
                }

                for (ModeleMessage message : nouvellePublicationAPI.getListeMessages()) {
                    if (!message.getType().equals("info")) {
                        return;
                    }
                    Toast.makeText(ActiviteNouvellePublication.this, "Publication postée avec succès ! :D", Toast.LENGTH_SHORT).show();
                    finirActivite();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == DEMANDE_CAM)
            onCaptureImageResult();
    }

    private void finirActivite() {
        this.finish();
    }

    private void onCamera() {
        Intent intentionCapturerPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentionCapturerPhoto.resolveActivity(getPackageManager()) != null) {
            File fichierPhoto = creerFichierImage();
            Uri uriPhoto = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", fichierPhoto);
            outputFilePath = fichierPhoto.getAbsolutePath();
            intentionCapturerPhoto.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
            startActivityForResult(intentionCapturerPhoto, DEMANDE_CAM);
        }
    }

    private File creerFichierImage() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nomFichierImage = "JPEG_" + timeStamp + "_";
        File dossierStockage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), DOSSIER_PHOTO + "/");
        if (!dossierStockage.exists())
            dossierStockage.mkdir();
        return new File(dossierStockage, nomFichierImage + ".jpg");
    }

    private void onCaptureImageResult() {
        if (outputFilePath != null) {
            File f = new File(outputFilePath);
            try {
                File publicFile = copyImageFile(f);
                Uri finalUri = Uri.fromFile(publicFile);
                galleryAddPic(finalUri);
                ((ImageView) findViewById(R.id.conteneur_photo_capturee)).setImageURI(finalUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File copyImageFile(File fileToCopy) throws IOException {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DOSSIER_PHOTO + "/");
        if (!storageDir.exists())
            storageDir.mkdir();
        File copyFile = new File(storageDir, fileToCopy.getName());
        copyFile.createNewFile();
        copy(fileToCopy, copyFile);
        return copyFile;
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    private void galleryAddPic(Uri contentUri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void demanderPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, 1);
    }

    public boolean aucuneAutorisationPourPublier() {
        if (ActivityCompat.checkSelfPermission(ActiviteNouvellePublication.this, CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ActiviteNouvellePublication.this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ActiviteNouvellePublication.this,
                    "Veuillez autoriser FoodShot à accéder à votre appareil photo et à votre stockage pour prendre une photo et la stocker en local",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
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

        if (ActivityCompat.checkSelfPermission(ActiviteNouvellePublication.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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

}
